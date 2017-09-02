package com.myobexercise.payslip.domain.taxrate;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "INCOME_RATE_TAX_BRACKET", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "BRACKET_START_VALUE", "INCOME_RATE_PERIOD_ID" }),
		@UniqueConstraint(columnNames = { "BRACKET_END_VALUE", "INCOME_RATE_PERIOD_ID" }) })
public class IncomeRateTaxBracket {
	@Id
	@Column
	private Long id;

	@Column(name = "BRACKET_START_VALUE", precision = 20, scale = 0, nullable = false)
	private BigDecimal bracketStartValue;

	@Column(name = "BRACKET_END_VALUE", precision = 20, scale = 0, nullable = true)
	private BigDecimal bracketEndValue;

	@Column(name = "TAX_RATE_PERCENTAGE", precision = 20, scale = 5, nullable = false)
	private BigDecimal taxRatePercentage;

	@Column(name = "ADDITIONAL_TAX_AMOUNT", precision = 20, scale = 0, nullable = false)
	private BigDecimal additionalTaxAmount;

	public IncomeRateTaxBracket() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBracketStartValue() {
		return bracketStartValue;
	}

	public void setBracketStartValue(BigDecimal bracketStartValue) {
		this.bracketStartValue = bracketStartValue;
	}

	public BigDecimal getTaxRatePercentage() {
		return taxRatePercentage;
	}

	public void setTaxRatePercentage(BigDecimal taxRatePercentage) {
		this.taxRatePercentage = taxRatePercentage;
	}

	public BigDecimal getBracketEndValue() {
		return bracketEndValue;
	}

	public void setBracketEndValue(BigDecimal bracketEndValue) {
		this.bracketEndValue = bracketEndValue;
	}

	public BigDecimal getAdditionalTaxAmount() {
		return additionalTaxAmount;
	}

	public void setAdditionalTaxAmount(BigDecimal additionalTaxAmount) {
		this.additionalTaxAmount = additionalTaxAmount;
	}

}
