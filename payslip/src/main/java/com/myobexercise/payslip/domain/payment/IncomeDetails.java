package com.myobexercise.payslip.domain.payment;

import java.math.BigDecimal;

public class IncomeDetails {

	private String firstName;
	private String lastName;
	private BigDecimal annualSalary;
	private BigDecimal superannuationRate;
	private String payPeriod;
	
	public IncomeDetails() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public BigDecimal getAnnualSalary() {
		return annualSalary;
	}

	public void setAnnualSalary(BigDecimal annualSalary) {
		this.annualSalary = annualSalary;
	}

	public BigDecimal getSuperannuationRate() {
		return superannuationRate;
	}

	public void setSuperannuationRate(BigDecimal superannuationRate) {
		this.superannuationRate = superannuationRate;
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}
	
}
