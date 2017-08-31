package com.myobexercise.payslip.domain.payment;

public enum PayPeriod {
	JAN("01 January - 31 January"),
	FEB("01 February - 28 February"),
	FEB_LEAP_YEAR("01 February - 29 February");
	
	private String payPeriodMonthId;
	
	PayPeriod(String payPeriodMonthId) {
		this.payPeriodMonthId = payPeriodMonthId;
	}
	
	public String getPayPeriodMonthId() {
		return payPeriodMonthId;
	}
}
