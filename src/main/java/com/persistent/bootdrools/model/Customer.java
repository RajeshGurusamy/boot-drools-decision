package com.persistent.bootdrools.model;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Component
public class Customer {
 	private Double requested_loan_amount;
 	private Double income;
 	private Double transunion_score;
 	private Double existing_loan_amount;

 	private String result;
}
