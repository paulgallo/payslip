package com.myobexercise.payslip.domain.payslip;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "PAYSLIP")
@JsonPropertyOrder({"fullName", "payPeriod", "grossIncome", "incomeTax", "superannuation"})
public class Payslip {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "FULL_NAME", nullable = false)
	private String fullName;

	@Column(name = "PAY_PERIOD", nullable = false)
	private String payPeriod;

	@Column(name = "GROSS_INCOME", precision = 20, scale = 0, nullable = false)
	private BigDecimal grossIncome;

	@Column(name = "INCOME_TAX", precision = 20, scale = 0, nullable = false)
	private BigDecimal incomeTax;

	@Column(name = "NET_INCOME", precision = 20, scale = 0, nullable = false)
	private BigDecimal netIncome;

	@Column(name = "SUPERANNUATION", precision = 20, scale = 0, nullable = false)
	private BigDecimal superannuation;

	public Payslip() {}

	/**
	 * Convenience constructor for all {@link Payslip} properties.
	 * 
	 * @param fullName Full name of person receiving pay.
	 * @param payPeriod Pay period description.
	 * @param grossIncome Calculated gross income for defined pay period.
	 * @param incomeTax Calculated income tax for defined pay period.
	 * @param netIncome Calculated net income for defined pay period.
	 * @param superannuation Calculated superannuation for defined pay period.
	 */
	public Payslip(String fullName, String payPeriod, BigDecimal grossIncome, BigDecimal incomeTax,
			BigDecimal netIncome, BigDecimal superannuation) {
		super();
		this.fullName = fullName;
		this.payPeriod = payPeriod;
		this.grossIncome = grossIncome;
		this.incomeTax = incomeTax;
		this.netIncome = netIncome;
		this.superannuation = superannuation;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append(id).append(fullName).append(payPeriod)
				.append(grossIncome).append(incomeTax).append(netIncome).append(superannuation).toString();
	}
}
