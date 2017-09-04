package com.myobexercise.payslip.service.payslip;

import com.myobexercise.payslip.domain.payslip.IncomeItem;
import com.myobexercise.payslip.domain.payslip.Payslip;
import com.myobexercise.payslip.domain.taxrate.IncomeTaxRateBracket;
import com.myobexercise.payslip.service.taxrate.IncomeTaxRateService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class PayslipCalculatorService {

	// Defaults
	// Divide annual calculations into monthly
	private BigDecimal annualCalculationDivisor = BigDecimal.valueOf(12);
	// Calculation rounding mode is HALF_UP
	private RoundingMode calculationRoundingMode = RoundingMode.HALF_UP;
	// Calculation scale for all calculations is zero
	private int calculationScale = 0;

	@Autowired
	private IncomeTaxRateService incomeTaxRateService;

	public PayslipCalculatorService() {}

	public RoundingMode getCalculationRoundingMode() {
		return calculationRoundingMode;
	}

	public void setCalculationRoundingMode(RoundingMode calculationRoundingMode) {
		Assert.notNull(calculationRoundingMode, "calculationRoundingMode cannot be null.");
		this.calculationRoundingMode = calculationRoundingMode;
	}

	public int getCalculationScale() {
		return calculationScale;
	}

	public void setCalculationScale(int calculationScale) {
		Assert.isTrue(calculationScale >= 0, "calculationScale must be greater than or equal to 0.");
		this.calculationScale = calculationScale;
	}

	public BigDecimal getAnnualCalculationDivisor() {
		return annualCalculationDivisor;
	}

	public void setAnnualCalculationDivisor(BigDecimal annualCalculationDivisor) {
		Assert.isTrue(annualCalculationDivisor.compareTo(BigDecimal.ZERO) > 0,
				"annualCalculationDivisor must be greater than 0.");
		this.annualCalculationDivisor = annualCalculationDivisor;
	}

	public IncomeTaxRateService getIncomeRateService() {
		return incomeTaxRateService;
	}

	public void setIncomeRateService(IncomeTaxRateService incomeTaxRateService) {
		this.incomeTaxRateService = incomeTaxRateService;
	}

	public Payslip createPayslip(IncomeItem incomeItem) {
		Payslip payslip = createPayslipWithFullName(incomeItem);

		calculatePayPeriodValues(payslip, incomeItem);
		calculatePayslipAmountValues(payslip, incomeItem);

		return payslip;
	}

	protected void calculatePayPeriodValues(Payslip payslip, IncomeItem incomeItem) {
		payslip.setPayPeriod(incomeItem.getPayPeriod());
	}

	protected Payslip createPayslipWithFullName(IncomeItem incomeItem) {
		Payslip payslip = new Payslip();

		payslip.setFullName(calculatePayslipFullName(incomeItem));

		return payslip;
	}

	protected void calculatePayslipAmountValues(Payslip payslip, IncomeItem incomeItem) {
		payslip.setGrossIncome(calculateGrossIncome(incomeItem));
		payslip.setIncomeTax(calculateIncomeTax(incomeItem));
		payslip.setNetIncome(calculateNetIncome(payslip));
		payslip.setSuperannuation(calculateSuperannuation(incomeItem, payslip));
	}

	protected BigDecimal calculateSuperannuation(IncomeItem incomeItem, Payslip payslip) {
		return payslip.getGrossIncome().multiply(incomeItem.getSuperannuationRate())
				.setScale(calculationScale, calculationRoundingMode);
	}

	protected BigDecimal calculateNetIncome(Payslip payslip) {
		return payslip.getGrossIncome().subtract(payslip.getIncomeTax()).setScale(calculationScale,
				calculationRoundingMode);
	}

	protected BigDecimal calculateIncomeTax(IncomeItem incomeItem) {
		IncomeTaxRateBracket incomeTaxRateBracket =
				incomeTaxRateService.findIncomeTaxRateBracketForIncomeItem(incomeItem);

		return incomeItem.getSalary().subtract(incomeTaxRateBracket.getBracketStartValue())
				.add(BigDecimal.ONE).multiply(incomeTaxRateBracket.getTaxRatePercentage())
				.add(incomeTaxRateBracket.getAdditionalTaxAmount())
				.divide(annualCalculationDivisor, calculationScale, calculationRoundingMode);
	}

	protected BigDecimal calculateGrossIncome(IncomeItem incomeItem) {
		return incomeItem.getSalary().divide(annualCalculationDivisor, calculationScale,
				calculationRoundingMode);
	}

	protected String calculatePayslipFullName(IncomeItem incomeItem) {
		return Stream.of(incomeItem.getFirstName(), incomeItem.getLastName())
				.filter(StringUtils::hasText).collect(Collectors.joining(" "));
	}

}
