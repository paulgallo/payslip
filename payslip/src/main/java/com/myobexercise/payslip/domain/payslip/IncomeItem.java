package com.myobexercise.payslip.domain.payslip;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.myobexercise.payslip.validator.ValidPayPeriod;

import java.math.BigDecimal;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.core.style.ToStringCreator;

@JsonPropertyOrder({"firstName", "lastName", "salary", "superannuationRate", "payPeriod"})
public class IncomeItem {

	@NotBlank
	@Size(min = 1, max = 50)
	private String firstName;

	@NotBlank
	@Size(min = 1, max = 50)
	private String lastName;

	@NotNull
	@Min(value = 0)
	@Max(value = 1000000000)
	private BigDecimal salary;

	@NotNull
	@Min(value = 0)
	@Max(value = 1)
	private BigDecimal superannuationRate;

	@NotBlank
	@ValidPayPeriod
	private String payPeriod;

	public IncomeItem() {}

	public IncomeItem(String firstName, String lastName, BigDecimal salary,
			BigDecimal superannuationRate, String payPeriod) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.superannuationRate = superannuationRate;
		this.payPeriod = payPeriod;
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

	@Override
	public String toString() {
		return new ToStringCreator(this).append(firstName).append(lastName).append(salary)
				.append(superannuationRate).append(payPeriod).toString();
	}
}
