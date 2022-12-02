package com.persistent.bootdrools.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.bootdrools.model.DepositRequest;
import com.persistent.bootdrools.service.DepositRateService;

@RestController
@RequestMapping(path = "/payment")
public class DepositRateController {

	@Autowired
	private DepositRateService depositRateService;

	@GetMapping(value = "/v1/getInterestRate")
	public ResponseEntity<String> getInterestRate(@RequestParam(required = true) String loanType,
			@RequestParam(required = true) Integer durationInYear, @RequestParam(required = true) Integer age) {
		DepositRequest depositRequest = depositRateService.getInterestRate(loanType, durationInYear, age);
		String message = "The interest rate for the loadType "+depositRequest.getLoanType() +" for the age of "+depositRequest.getAge() +" is " + depositRequest.getInterestRate();;
		return new ResponseEntity<String>(message,HttpStatus.ACCEPTED);
	}
	
}