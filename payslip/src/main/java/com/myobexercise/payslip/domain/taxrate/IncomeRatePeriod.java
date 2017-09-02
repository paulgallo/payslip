package com.myobexercise.payslip.domain.taxrate;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "INCOME_RATE_PERIOD")
public class IncomeRatePeriod {

	@Id
	private Long id;

	@Column(name = "START_DATE", nullable = false)
	private LocalDate startDate;

	@Column(name = "END_DATE", nullable = false)
	private LocalDate endDate;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "INCOME_RATE_PERIOD_ID")
	private List<IncomeRateTaxBracket> taxBrackets;

	public IncomeRatePeriod() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public List<IncomeRateTaxBracket> getTaxBrackets() {
		return taxBrackets;
	}

	public void setTaxBrackets(List<IncomeRateTaxBracket> taxBrackets) {
		this.taxBrackets = taxBrackets;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append(this.id).append(this.startDate).append(this.endDate)
				.toString();
	}

}
