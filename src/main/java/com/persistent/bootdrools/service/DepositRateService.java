package com.persistent.bootdrools.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistent.bootdrools.model.DepositRequest;

@Service
public class DepositRateService {

	@Autowired
	private KieContainer kieContainer;
	
	public DepositRequest getInterestRate(String loanType, Integer durationInYear, Integer age) {
		KieSession kieSession = kieContainer.newKieSession();
		DepositRequest depositRequest = new DepositRequest(loanType, durationInYear, age);
		kieSession.insert(depositRequest);
		kieSession.fireAllRules();
		kieSession.dispose();
		return depositRequest;
	}
}
