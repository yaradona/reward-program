package com.example.rewardprogram.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.rewardprogram.model.CustomerTranscation;

@Repository
public interface RewardRepository extends JpaRepository<CustomerTranscation, Long> {

	List<CustomerTranscation> findByCustomerId(String customerId);

	@Query("select monthname(transacationDate ),sum(rewardPoints ) from CustomerTranscation where customerId = :customerId  group by monthname(transacationDate )")
	List<Object[]> rewardsPointsPerMonth(@Param("customerId") String customerId);

	@Query("select sum(rewardPoints) from CustomerTranscation  where customerId = :customerId group by customerId ")
	int totalRewardsPoints(@Param("customerId") String customerId);

}
