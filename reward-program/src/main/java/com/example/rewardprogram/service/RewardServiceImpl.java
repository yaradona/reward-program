package com.example.rewardprogram.service;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rewardprogram.Utils.RewardConstants;
import com.example.rewardprogram.advice.error.CustomerNotFoundException;
import com.example.rewardprogram.dto.RewardsResponse;
import com.example.rewardprogram.model.CustomerTranscation;
import com.example.rewardprogram.repository.RewardRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RewardServiceImpl implements RewardService {

	@Autowired
	private RewardRepository rewardRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RewardServiceImpl.class);

	public RewardsResponse getRewardPoints(String customerId) throws CustomerNotFoundException {

		List<CustomerTranscation> customerTranscationList;

		try {
			
			LOGGER.info("Inside getRewardPoints()  :");

			// Getting all the transaction records from the database for the given
			// CustomerId
			customerTranscationList = rewardRepository.findByCustomerId(customerId);

			// Calculating reward points for each transaction and saving the reward points
			// back to database for that transaction
			calculateRewardPointsAndSave(customerTranscationList);

			// Calculating reward points by monthly
			List<Object[]> customerRewardsReport = rewardRepository.rewardsPointsPerMonth(customerId);
			Map<String, Long> rewardsMap = customerRewardsReport.stream()
					.collect(Collectors.toMap(a -> (String) a[0], a -> (Long) a[1]));

			// Calculating total reward points
			Integer totalRewardsPoints = rewardRepository.totalRewardsPoints(customerId);

			// Returning total reward points details
			return new RewardsResponse(customerId, rewardsMap, totalRewardsPoints);

		} catch (AopInvocationException e) {
			log.error(" AopInvocationException occured in getRewardPoints ", e);
			if (null == e.getRootCause())
				throw new CustomerNotFoundException("Customer details are not found for customerId - " + customerId);
		}
		return null;

	}

	private void calculateRewardPointsAndSave(List<CustomerTranscation> customerTranscationList) {
		
		LOGGER.info("Inside calculateRewardPointsAndSave()  :");

		// Place holder for reward points for over 50$
		int rewardPointsLevelOne = 0;

		// Place holder for reward points for over 100$
		int rewardPointsLevelTwo = 0;

		// Place holder for total reward points
		int totalRewardPoints = 0;

		ListIterator<CustomerTranscation> customerTranscationIter = customerTranscationList.listIterator();

		// Iterate through each customerTranscation to calculate reward points
		while (customerTranscationIter.hasNext()) {

			CustomerTranscation customerTxObj = customerTranscationIter.next();

			double transaction_amount = customerTxObj.getTransacationAmount();

			// Logic to calculate reward point based on transaction_amount
			if (transaction_amount >= RewardConstants.REWARDS_THRESHOLD_NUMBER_HUNDRED) {
				rewardPointsLevelTwo = (int) ((transaction_amount - RewardConstants.REWARDS_THRESHOLD_NUMBER_HUNDRED)
						* 2);
				rewardPointsLevelOne = (int) ((transaction_amount - RewardConstants.REWARDS_THRESHOLD_NUMBER_FIFTY)
						- (transaction_amount - RewardConstants.REWARDS_THRESHOLD_NUMBER_HUNDRED));
			} else if (transaction_amount >= RewardConstants.REWARDS_THRESHOLD_NUMBER_FIFTY) {
				rewardPointsLevelOne = (int) (transaction_amount - RewardConstants.REWARDS_THRESHOLD_NUMBER_FIFTY);
			} else {
				rewardPointsLevelOne = 0;
				rewardPointsLevelTwo = 0;
			}

			totalRewardPoints = rewardPointsLevelTwo + rewardPointsLevelOne;

			customerTxObj.setRewardPoints(totalRewardPoints);

			rewardRepository.save(customerTxObj);
		}
	}

}
