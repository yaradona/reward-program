package com.example.rewardprogram.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name = "CUSTOMERTRANSCATION")
public class CustomerTranscation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long transcationId;
	
	private String customerId;
	
	private double transacationAmount;
	
	private Date transacationDate;
	
	private int rewardPoints;
}
