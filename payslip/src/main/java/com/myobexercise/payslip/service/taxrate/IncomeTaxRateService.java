package com.myobexercise.payslip.service.taxrate;

import com.myobexercise.payslip.dao.taxrate.IncomeRatePeriodRepository;
import com.myobexercise.payslip.domain.payslip.IncomeItem;
import com.myobexercise.payslip.domain.taxrate.IncomeTaxRateBracket;
import com.myobexercise.payslip.domain.taxrate.IncomeTaxRatePeriod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class IncomeTaxRateService {

	@Autowired
	private IncomeRatePeriodRepository incomeRatePeriodRepository;

	public IncomeTaxRateService() {}

	public IncomeRatePeriodRepository getIncomeRatePeriodRepository() {
		return incomeRatePeriodRepository;
	}

	public void setIncomeRatePeriodRepository(IncomeRatePeriodRepository incomeRatePeriodRepository) {
		this.incomeRatePeriodRepository = incomeRatePeriodRepository;
	}

	/**
	 * Finds the {@link IncomeTaxRateBracket} for the {@link IncomeItem#getSalary()}. NOTE:
	 * {@link IncomeItem#getPayPeriod()} is currently disregarded.
	 * 
	 * @param incomeItem The {@link IncomeItem} to evaluate.
	 * @return {@link IncomeTaxRateBracket} for the incomeItem
	 */
	public IncomeTaxRateBracket findIncomeTaxRateBracketForIncomeItem(IncomeItem incomeItem) {
		IncomeTaxRatePeriod incomeTaxRatePeriod = findIncomeRatePeriod(incomeItem);
		List<IncomeTaxRateBracket> matchingTaxBrackets = null;
		if (incomeTaxRatePeriod != null) {
			matchingTaxBrackets = incomeTaxRatePeriod.getTaxBrackets().stream()
					.filter(t -> incomeItemMatchesBracket(incomeItem, t)).collect(Collectors.toList());
		}

		validateMatchingTaxBrackets(incomeItem, matchingTaxBrackets);

		return matchingTaxBrackets.get(0);
	}

	protected void validateMatchingTaxBrackets(IncomeItem incomeItem,
			List<IncomeTaxRateBracket> matchingTaxBrackets) {
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
			IncomeTaxRateBracket incomeTaxRateBracket) {
		BigDecimal salary = incomeItem.getSalary().setScale(0, BigDecimal.ROUND_HALF_UP);
		return salary.compareTo(incomeTaxRateBracket.getBracketStartValue()) >= 0
				&& (incomeTaxRateBracket.getBracketEndValue() == null
						|| salary.compareTo(incomeTaxRateBracket.getBracketEndValue()) <= 0);
	}

	protected IncomeTaxRatePeriod findIncomeRatePeriod(IncomeItem incomeItem) {
		LocalDate incomeItemPayPeriodDate = determineIncomeItemPayPeriodDate(incomeItem);
		return incomeRatePeriodRepository.findByPayPeriodDate(incomeItemPayPeriodDate);
	}

	protected LocalDate determineIncomeItemPayPeriodDate(IncomeItem incomeItem) {
		// Current system limitation - Only 2012-2013 Financial Year supported.
		return LocalDate.of(2012, 7, 1);
	}

}
