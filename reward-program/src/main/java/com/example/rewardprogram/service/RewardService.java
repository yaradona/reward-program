package com.example.rewardprogram.service;

import com.example.rewardprogram.advice.error.CustomerNotFoundException;
import com.example.rewardprogram.dto.RewardsResponse;

public interface RewardService {

	public RewardsResponse getRewardPoints(String customerId) throws CustomerNotFoundException;
}
