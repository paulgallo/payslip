package com.myobexercise.payslip.service.taxrate;

import com.myobexercise.payslip.dao.taxrate.IncomeRatePeriodRepository;
import com.myobexercise.payslip.domain.payment.IncomeItem;
import com.myobexercise.payslip.domain.taxrate.IncomeRatePeriod;
import com.myobexercise.payslip.domain.taxrate.IncomeRateTaxBracket;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class IncomeRateService {

	@Autowired
	private IncomeRatePeriodRepository incomeRatePeriodRepository;

	public IncomeRateService() {}

	public IncomeRatePeriodRepository getIncomeRatePeriodRepository() {
		return incomeRatePeriodRepository;
	}

	public void setIncomeRatePeriodRepository(IncomeRatePeriodRepository incomeRatePeriodRepository) {
		this.incomeRatePeriodRepository = incomeRatePeriodRepository;
	}

	public IncomeRateTaxBracket findIncomeRateTaxBracketForIncomeItem(IncomeItem incomeItem) {
		IncomeRatePeriod incomeRatePeriod = findIncomeRatePeriod(incomeItem);
		List<IncomeRateTaxBracket> matchingTaxBrackets = null;
		if (incomeRatePeriod != null) {
			matchingTaxBrackets = incomeRatePeriod.getTaxBrackets().stream()
					.filter(t -> incomeItemMatchesBracket(incomeItem, t)).collect(Collectors.toList());
		}

		validateMatchingTaxBrackets(incomeItem, matchingTaxBrackets);

		return matchingTaxBrackets.get(0);
	}

	protected void validateMatchingTaxBrackets(IncomeItem incomeItem,
			List<IncomeRateTaxBracket> matchingTaxBrackets) {
		if (CollectionUtils.isEmpty(matchingTaxBrackets)) {
			throw new IllegalStateException(
					"ERROR: Matching Tax Bracket not found for Income Details:" + incomeItem);
		}
		if (matchingTaxBrackets.size() > 1) {
			throw new IllegalStateException(
					"ERROR: More than one matching Tax Bracket found for Income Details:" + incomeItem);
		}
	}

	protected boolean incomeItemMatchesBracket(IncomeItem incomeItem,
			IncomeRateTaxBracket incomeRateTaxBracket) {
		BigDecimal salary = incomeItem.getSalary().setScale(0, BigDecimal.ROUND_HALF_UP);
		return salary.compareTo(incomeRateTaxBracket.getBracketStartValue()) >= 0
				&& (incomeRateTaxBracket.getBracketEndValue() == null
						|| salary.compareTo(incomeRateTaxBracket.getBracketEndValue()) <= 0);
	}

	protected IncomeRatePeriod findIncomeRatePeriod(IncomeItem incomeItem) {
		LocalDate incomeItemPayPeriodDate = determineIncomeItemPayPeriodDate(incomeItem);
		return incomeRatePeriodRepository.findByPayPeriodDate(incomeItemPayPeriodDate);
	}

	protected LocalDate determineIncomeItemPayPeriodDate(IncomeItem incomeItem) {
		return incomeItem.getPayPeriodStartDate();
	}

}
