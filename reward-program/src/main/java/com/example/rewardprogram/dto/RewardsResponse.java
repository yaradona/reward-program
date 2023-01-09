package com.example.rewardprogram.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardsResponse {

	private String customerId;
	private Map<String, Long> monthyRewards;
	private int totalRewardPoints;
}
