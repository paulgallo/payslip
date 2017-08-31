package com.myobexercise.payslip.domain.payment;

import java.math.BigDecimal;

public class PayslipDetails {

	private String fullName;
	private String payPeriod;
	private BigDecimal grossIncome;
	private BigDecimal incomeTax;
	private BigDecimal netIncome;
	private BigDecimal superannuation;
	
	public PayslipDetails() {
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPayPeriod() {
		return payPeriod;
	}

	public void setPayPeriod(String payPeriod) {
		this.payPeriod = payPeriod;
	}

	public BigDecimal getGrossIncome() {
		return grossIncome;
	}

	public void setGrossIncome(BigDecimal grossIncome) {
		this.grossIncome = grossIncome;
	}

	public BigDecimal getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(BigDecimal incomeTax) {
		this.incomeTax = incomeTax;
	}

	public BigDecimal getNetIncome() {
		return netIncome;
	}

	public void setNetIncome(BigDecimal netIncome) {
		this.netIncome = netIncome;
	}

	public BigDecimal getSuperannuation() {
		return superannuation;
	}

	public void setSuperannuation(BigDecimal superannuation) {
		this.superannuation = superannuation;
	}
	
}
