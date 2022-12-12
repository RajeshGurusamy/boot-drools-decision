package com.persistent.bootdrools.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.core.command.runtime.rule.AgendaGroupSetFocusCommand;
import org.kie.api.command.Command;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistent.bootdrools.model.Customer;

@Service
public class RulesProcessingService {
	
	@Autowired
	private KieContainer kieContainer;
	
	public Customer getInterestRate(Customer req, String service) {
	
		StatelessKieSession kSession = kieContainer.newStatelessKieSession();
		AgendaGroupSetFocusCommand agendaGroup = new AgendaGroupSetFocusCommand(service);
		List<Command> cmd = new ArrayList<>();
		cmd.add(CommandFactory.newInsert(req));
		cmd.add(agendaGroup);
		cmd.add(CommandFactory.newFireAllRules());
		cmd.add(CommandFactory.newGetObjects(new ClassObjectFilter(Customer.class), "output"));
		ExecutionResults executionResults = kSession.execute(CommandFactory.newBatchExecution(cmd));
		List<Customer> customerList = (List<Customer>) (Collection<?>)  executionResults.getValue("output");
		System.out.println("customerList Output"+ customerList);
		return customerList.get(0);
	}

}
