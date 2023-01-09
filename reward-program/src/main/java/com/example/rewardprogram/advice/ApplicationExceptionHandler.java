package com.example.rewardprogram.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.rewardprogram.advice.error.CustomerNotFoundException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CustomerNotFoundException.class)
	public Map<String, String> handleAopException(CustomerNotFoundException ex) {
		Map<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("errorMessage", ex.getMessage());
		return errorMap;
	}
}
