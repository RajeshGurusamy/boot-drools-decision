package com.persistent.bootdrools.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
public class RulesCompiler {

	ResourcePatternResolver resourcePatternResolver;
	
	private StatelessKieSession sKieSession;
	
	public RulesCompiler(@Autowired ResourcePatternResolver resourcePatternResolver) {
		this.resourcePatternResolver = resourcePatternResolver;
		
		List<String> rulesList;

        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath:rules/*.xls");
            rulesList = new ArrayList<>();

            for(Resource r : resources) {
            	System.out.println("Compiling resource file --> " + r.getFilename());
                InputStream is = getClass().getResourceAsStream("/rules/" + r.getFilename());
                SpreadsheetCompiler sc = new SpreadsheetCompiler();
                String rules=sc.compile(is, InputType.XLS);
                System.out.println("Compiled drools file == " + rules);

                rulesList.add(rules);
            }

            KieHelper kh = new KieHelper();
            rulesList.forEach(r -> kh.addContent(r, ResourceType.DRL));

            sKieSession = kh.build().newStatelessKieSession();

        } catch (Exception e) {
        	System.err.println("Error while reading the resource"+ e);
        }
    }
	
}
