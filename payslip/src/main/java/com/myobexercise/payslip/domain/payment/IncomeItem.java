package com.myobexercise.payslip.domain.payment;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.core.style.ToStringCreator;

@JsonPropertyOrder({"firstName", "lastName", "salary", "superannuationRate", "payPeriod"})
public class IncomeItem {

	private String firstName;
	private String lastName;
	private BigDecimal salary;
	@JsonDeserialize(using = PercentageDeserializer.class)
	private BigDecimal superannuationRate;
	private String payPeriod;

	// Current system limitation - Only financial year 2012 - 2013 supported
	private LocalDate payPeriodStartDate = LocalDate.of(2012, 7, 1);

	public IncomeItem() {}

	public IncomeItem(String firstName, String lastName, BigDecimal salary,
			BigDecimal superannuationRate, String payPeriod, LocalDate payPeriodStartDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.superannuationRate = superannuationRate;
		this.payPeriod = payPeriod;
		this.payPeriodStartDate = payPeriodStartDate;
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

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
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

	public LocalDate getPayPeriodStartDate() {
		return payPeriodStartDate;
	}

	public void setPayPeriodStartDate(LocalDate payPeriodStartDate) {
		this.payPeriodStartDate = payPeriodStartDate;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append(firstName).append(lastName).append(salary)
				.append(superannuationRate).append(payPeriod).toString();
	}
}
