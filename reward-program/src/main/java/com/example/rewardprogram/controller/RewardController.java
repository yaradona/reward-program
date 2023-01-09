package com.example.rewardprogram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.rewardprogram.advice.error.CustomerNotFoundException;
import com.example.rewardprogram.dto.RewardsResponse;
import com.example.rewardprogram.service.RewardServiceImpl;

@RestController
@RequestMapping("/api")
public class RewardController {

	@Autowired
	private RewardServiceImpl rewardService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RewardController.class);

	@GetMapping("/rewardpoints/{id}")
	@ResponseStatus(HttpStatus.OK)
	public RewardsResponse getRewardPoints(@PathVariable("id") String customerId) throws CustomerNotFoundException {
		LOGGER.info("Inside RewardController()");
		return rewardService.getRewardPoints(customerId);
	}

}
