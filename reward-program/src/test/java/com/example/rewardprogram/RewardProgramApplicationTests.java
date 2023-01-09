package com.example.rewardprogram;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.rewardprogram.advice.error.CustomerNotFoundException;
import com.example.rewardprogram.controller.RewardController;
import com.example.rewardprogram.dto.RewardsResponse;
import com.example.rewardprogram.model.CustomerTranscation;
import com.example.rewardprogram.repository.RewardRepository;
import com.example.rewardprogram.service.RewardServiceImpl;

@SpringBootTest
public class RewardProgramApplicationTests {

	@Autowired
	private RewardController rewardController;

	@InjectMocks
	RewardServiceImpl mockRewardServiceImpl;

	@Mock
	RewardRepository mockRewardRepository;

	private CustomerTranscation customerTranscationSaved;

	private List<CustomerTranscation> customerTranscationList;

	private List<CustomerTranscation> customerTranscationList_LessThan_50;

	private CustomerTranscation customerTranscationList_LessThan_50_Saved;

	private List<CustomerTranscation> customerTranscationList_GreatherThan_50_LessThan_100;

	private CustomerTranscation customerTranscationList_GreatherThan_50_LessThan_100_Saved;
	
	private List<CustomerTranscation> customerTranscationList_Equals_50;

	private CustomerTranscation customerTranscationList_Equals_50_Saved;

	private List<CustomerTranscation> customerTranscationList_Equals_100;

	private CustomerTranscation customerTranscationList_Equals_100_Saved;

	private List<Object[]> rewardPointMonthyList;

	@BeforeEach
	public void setup() {

	}

	@Test
	public void contextLoads() throws Exception {
		assertThat(rewardController).isNotNull();
	}

	@Test
	public void testRewardPoints() throws CustomerNotFoundException {
		
		customerTranscationSaved = new CustomerTranscation(1L, "11", 120.0, new Date(01 / 01 / 2023), 90);

		customerTranscationList = Arrays.asList(new CustomerTranscation(1L, "11", 120.0, new Date(01 / 01 / 2023), 0),
				new CustomerTranscation(2L, "11", 130.0, new Date(01 / 02 / 2023), 0));

		rewardPointMonthyList = new ArrayList<>();
		Object[] FirstMonth = new Object[] { "January", 90L };
		Object[] SecondMonth = new Object[] { "February", 110L };
		rewardPointMonthyList = Arrays.asList(FirstMonth, SecondMonth);

		when(mockRewardRepository.findByCustomerId(any())).thenReturn(customerTranscationList);

		when(mockRewardRepository.save(any(CustomerTranscation.class))).thenReturn(customerTranscationSaved);

		when(mockRewardRepository.rewardsPointsPerMonth(any())).thenReturn(rewardPointMonthyList);

		when(mockRewardRepository.totalRewardsPoints(any())).thenReturn(320);

		RewardsResponse rewardsResponse = mockRewardServiceImpl.getRewardPoints("11");

		assertEquals("11", rewardsResponse.getCustomerId());
		assertEquals(320, rewardsResponse.getTotalRewardPoints());
		assertEquals(90, rewardsResponse.getMonthyRewards().get("January"));
		assertEquals(110, rewardsResponse.getMonthyRewards().get("February"));
	}

	@Test
	public void getCustomerIdTestException() throws CustomerNotFoundException {

		when(mockRewardRepository.findByCustomerId("12")).thenThrow(new AopInvocationException(""));

		Exception exception = assertThrows(CustomerNotFoundException.class, () -> {
			mockRewardServiceImpl.getRewardPoints("12");
		});
		String expectedMessage = "Customer details are not found for customerId - 12";
		assertTrue(exception.getMessage().contains(expectedMessage));
	}

	@Test
	void calculateRewardPoint_LessThan_50() throws CustomerNotFoundException {

		customerTranscationList_LessThan_50 = Arrays.asList(
				new CustomerTranscation(1L, "11", 40.0, new Date(01 / 01 / 2023), 0));
		
		customerTranscationList_LessThan_50_Saved = new CustomerTranscation(1L, "11", 40.0, new Date(01 / 01 / 2023), 0);
				
		when(mockRewardRepository.findByCustomerId(any())).thenReturn(customerTranscationList_LessThan_50);
		
		when(mockRewardRepository.save(any(CustomerTranscation.class))).thenReturn(customerTranscationList_LessThan_50_Saved);
		
		when(mockRewardRepository.totalRewardsPoints(any())).thenReturn(0);
		
		RewardsResponse rewardsResponse = mockRewardServiceImpl.getRewardPoints("11");

		assertEquals(0, rewardsResponse.getTotalRewardPoints());
	}
	
	@Test
	void calculateRewardPoint_Equals_50() throws CustomerNotFoundException {

		customerTranscationList_Equals_50 = Arrays.asList(
				new CustomerTranscation(1L, "11", 50.0, new Date(01 / 01 / 2023), 0));
		
		customerTranscationList_Equals_50_Saved = new CustomerTranscation(1L, "11", 50.0, new Date(01 / 01 / 2023), 0);
				
		when(mockRewardRepository.findByCustomerId(any())).thenReturn(customerTranscationList_Equals_50);
		
		when(mockRewardRepository.save(any(CustomerTranscation.class))).thenReturn(customerTranscationList_Equals_50_Saved);
		
		when(mockRewardRepository.totalRewardsPoints(any())).thenReturn(0);
		
		RewardsResponse rewardsResponse = mockRewardServiceImpl.getRewardPoints("11");

		assertEquals(0, rewardsResponse.getTotalRewardPoints());
	}

	@Test
	void test_calculateRewardPoint__GreatherThan_50_LessThan_100() throws CustomerNotFoundException {

		customerTranscationList_GreatherThan_50_LessThan_100 = Arrays.asList(
				new CustomerTranscation(1L, "11", 70.0, new Date(01 / 01 / 2023), 0));
		
		customerTranscationList_GreatherThan_50_LessThan_100_Saved = new CustomerTranscation(1L, "11", 70.0, new Date(01 / 01 / 2023), 20);
				
		when(mockRewardRepository.findByCustomerId(any())).thenReturn(customerTranscationList_GreatherThan_50_LessThan_100);
		
		when(mockRewardRepository.save(any(CustomerTranscation.class))).thenReturn(customerTranscationList_GreatherThan_50_LessThan_100_Saved);
		
		when(mockRewardRepository.totalRewardsPoints(any())).thenReturn(20);
		
		RewardsResponse rewardsResponse = mockRewardServiceImpl.getRewardPoints("11");

		assertEquals(20, rewardsResponse.getTotalRewardPoints());
	}
	
	@Test
	void test_calculateRewardPoint_Equlas_100() throws CustomerNotFoundException {

		customerTranscationList_Equals_100 = Arrays.asList(
				new CustomerTranscation(1L, "11", 100.0, new Date(01 / 01 / 2023), 0));
		
		customerTranscationList_Equals_100_Saved = new CustomerTranscation(1L, "11", 100.0, new Date(01 / 01 / 2023), 50);
				
		when(mockRewardRepository.findByCustomerId(any())).thenReturn(customerTranscationList_Equals_100);
		
		when(mockRewardRepository.save(any(CustomerTranscation.class))).thenReturn(customerTranscationList_Equals_100_Saved);
		
		when(mockRewardRepository.totalRewardsPoints(any())).thenReturn(50);
		
		RewardsResponse rewardsResponse = mockRewardServiceImpl.getRewardPoints("11");

		assertEquals(50, rewardsResponse.getTotalRewardPoints());
	}

}
