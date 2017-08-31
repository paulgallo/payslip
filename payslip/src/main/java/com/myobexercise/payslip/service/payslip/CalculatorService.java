package com.myobexercise.payslip.service.payslip;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.myobexercise.payslip.dao.taxrate.IncomeRatePeriodRepository;
import com.myobexercise.payslip.domain.payment.IncomeDetails;
import com.myobexercise.payslip.domain.payment.PayslipDetails;
import com.myobexercise.payslip.domain.taxrate.IncomeRatePeriod;
import com.myobexercise.payslip.domain.taxrate.IncomeRateTaxBracket;


@Component
public class CalculatorService {

	@Autowired
	private IncomeRatePeriodRepository incomeRatePeriodRepository;
	
	private Map<LocalDate, List<IncomeRateTaxBracket>> incomeRateTaxBracketCache = new HashMap<>();
	
	public CalculatorService() {
	}
	
	public Map<LocalDate, List<IncomeRateTaxBracket>> getIncomeRateTaxBracketCache() {
		return incomeRateTaxBracketCache;
	}

	public void setIncomeRateTaxBracketCache(Map<LocalDate, List<IncomeRateTaxBracket>> incomeRateTaxBracketCache) {
		this.incomeRateTaxBracketCache = incomeRateTaxBracketCache;
	}

	public List<PayslipDetails> calculatePayslipDetailsList(List<IncomeDetails> incomeDetailsList) {
		return incomeDetailsList
		.stream()
		.map(i -> calculatePayslipDetails(i))
		.collect(Collectors.toList());
	}
	
	protected PayslipDetails calculatePayslipDetails(IncomeDetails incomeDetails) {
		PayslipDetails payslipDetails = createBasicPayslipDetails(incomeDetails);
		
		calculatePayslipAmountValues(payslipDetails, incomeDetails);		
						
		return payslipDetails;
	}
	
	protected PayslipDetails createBasicPayslipDetails(IncomeDetails incomeDetails) {
		PayslipDetails payslipDetails = new PayslipDetails();
		
		payslipDetails.setFullName(calculatePayslipFullName(incomeDetails));
		payslipDetails.setPayPeriod(incomeDetails.getPayPeriod());
		
		return payslipDetails;
	}

	protected void calculatePayslipAmountValues(PayslipDetails payslipDetails, IncomeDetails incomeDetails) {
		payslipDetails.setGrossIncome(calculateGrossIncomeForMonth(incomeDetails));
		payslipDetails.setIncomeTax(calculateIncomeTaxForMonth(incomeDetails));
	}

	protected BigDecimal calculateIncomeTaxForMonth(IncomeDetails incomeDetails) {
		LocalDate financialYearStart = determineFinancialYearStartForIncomeDetails(incomeDetails);
		
		List<IncomeRateTaxBracket> incomeRateTaxBrackets = determineIncomeRateTaxBracketsForIncomeDetails(financialYearStart);
		
		BigDecimal annualSalary = incomeDetails.getAnnualSalary();
		
		BigDecimal totalAnnualTax = incomeRateTaxBrackets.stream()
		.filter(t -> annualSalary.compareTo(t.getBracketStartValue()) >= 0)
		.map(t -> calculateIncomeTaxAmountForBracket(annualSalary, t))
		.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return totalAnnualTax.divide(BigDecimal.valueOf(12), 0, BigDecimal.ROUND_HALF_UP);
	}

	protected BigDecimal calculateIncomeTaxAmountForBracket(BigDecimal annualSalary, IncomeRateTaxBracket incomeTaxBracket) {
		BigDecimal endValue = incomeTaxBracket.getNextTaxBracket() != null ? annualSalary.min(incomeTaxBracket.getNextTaxBracket().getBracketStartValue()) : annualSalary; 
		return endValue
				.subtract(incomeTaxBracket.getBracketStartValue())
				.multiply(incomeTaxBracket.getTaxRatePercentage());
	}
	
	protected List<IncomeRateTaxBracket> determineIncomeRateTaxBracketsForIncomeDetails(LocalDate financialYearStart) {
		List<IncomeRateTaxBracket> incomeRateTaxBrackets = incomeRateTaxBracketCache.get(financialYearStart);
		
		if (incomeRateTaxBrackets == null) {
			IncomeRatePeriod incomeRatePeriod = incomeRatePeriodRepository.findByPayPeriodDate(financialYearStart);
						
			if (incomeRatePeriod == null) {
				incomeRateTaxBrackets = new ArrayList<>();
			}
			else {
				incomeRateTaxBrackets = incomeRatePeriod.getTaxBrackets();
				IncomeRateTaxBracket previousIncomeRateTaxBracket = null;
				
				for (IncomeRateTaxBracket incomeRateTaxBracket : incomeRateTaxBrackets) {
					if (previousIncomeRateTaxBracket != null) {
						previousIncomeRateTaxBracket.setNextTaxBracket(incomeRateTaxBracket);
					}
										
					previousIncomeRateTaxBracket = incomeRateTaxBracket;
				}
			}
			
			if (CollectionUtils.isEmpty(incomeRateTaxBrackets)) {
				incomeRateTaxBrackets = new ArrayList<>();
				throw new IllegalArgumentException("Income Rate Tax Brackets not currently configured for Financial Year: " + financialYearStart);
			}
			
			incomeRateTaxBracketCache.put(financialYearStart, incomeRateTaxBrackets);
		}
		
		return incomeRateTaxBrackets;
	}

	protected LocalDate determineFinancialYearStartForIncomeDetails(IncomeDetails incomeDetails) {
		// Current system limitation - only processes a single financial year - hardcoded to 2012 - 13
		return LocalDate.of(2012, 7, 1);
	}

	protected BigDecimal calculateGrossIncomeForMonth(IncomeDetails incomeDetails) {
		return incomeDetails.getAnnualSalary().divide(BigDecimal.valueOf(12), 0, BigDecimal.ROUND_HALF_UP);
	}

	protected String calculatePayslipFullName(IncomeDetails incomeDetails) {
		return Stream.of(incomeDetails.getFirstName(), incomeDetails.getLastName()).
		filter(StringUtils::hasText).
		collect(Collectors.joining(" "));
	}

}
