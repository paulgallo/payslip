package com.myobexercise.payslip.domain.taxrate;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="INCOME_RATE_TAX_BRACKET", 
		uniqueConstraints={
	    @UniqueConstraint(columnNames = {"BRACKET_START_VALUE", "INCOME_RATE_PERIOD_ID"})
	})
public class IncomeRateTaxBracket {
	@Id
	@Column
	private Long id;
	
	@Column(name="BRACKET_START_VALUE", scale = 0)
	private BigDecimal bracketStartValue;
	
	@Column(name="TAX_RATE_PERCENTAGE", precision = 8, scale = 5)
	private BigDecimal taxRatePercentage;
	
	@Transient
	private IncomeRateTaxBracket nextTaxBracket;
	
//	@Transient
//	private BigDecimal bracketEndValue;
//	
//	@Transient
//	private BigDecimal accumulatedTaxValue;
	
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

//	public BigDecimal getBracketEndValue() {
//		return bracketEndValue;
//	}
//
//	public void setBracketEndValue(BigDecimal bracketEndValue) {
//		this.bracketEndValue = bracketEndValue;
//	}
//
//	public BigDecimal getAccumulatedTaxValue() {
//		return accumulatedTaxValue;
//	}
//
//	public void setAccumulatedTaxValue(BigDecimal accumulatedTaxValue) {
//		this.accumulatedTaxValue = accumulatedTaxValue;
//	}

	public IncomeRateTaxBracket getNextTaxBracket() {
		return nextTaxBracket;
	}

	public void setNextTaxBracket(IncomeRateTaxBracket nextTaxBracket) {
		this.nextTaxBracket = nextTaxBracket;
	}
	
	
	
}
