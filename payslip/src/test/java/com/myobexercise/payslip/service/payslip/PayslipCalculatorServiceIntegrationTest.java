package com.myobexercise.payslip.service.payslip;

import static org.junit.Assert.assertEquals;

import com.myobexercise.payslip.PayslipApplication;
import com.myobexercise.payslip.domain.payslip.IncomeItem;
import com.myobexercise.payslip.domain.payslip.Payslip;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayslipApplication.class)
public class PayslipCalculatorServiceIntegrationTest {

	public static final Map<IncomeItem, Payslip> SAMPLE_TEST_DATA;

	static {
		SAMPLE_TEST_DATA = new HashMap<>();
		SAMPLE_TEST_DATA.put(
				createIncomeItem("David", "Rudd", BigDecimal.valueOf(60050), BigDecimal.valueOf(0.09),
						"01 March – 31 March"),
				createPayslip("David Rudd", "01 March – 31 March", BigDecimal.valueOf(5004),
						BigDecimal.valueOf(922), BigDecimal.valueOf(4082), BigDecimal.valueOf(450)));
		SAMPLE_TEST_DATA.put(
				createIncomeItem("Ryan", "Chen", BigDecimal.valueOf(120000), BigDecimal.valueOf(0.1),
						"01 March – 31 March"),
				createPayslip("Ryan Chen", "01 March – 31 March", BigDecimal.valueOf(10000),
						BigDecimal.valueOf(2696), BigDecimal.valueOf(7304), BigDecimal.valueOf(1000)));
	}


	@Autowired
	PayslipCalculatorService calculatorService;

	@Test
	public void testCalculateIncomeTaxForMonth_UsingSampleValues() {
		SAMPLE_TEST_DATA.keySet().stream()
				.forEach(i -> assertPayslip(i, calculatorService.createPayslip(i)));
	}

	private static IncomeItem createIncomeItem(String firstName, String lastName, BigDecimal salary,
			BigDecimal superannuationRate, String payPeriod) {
		IncomeItem incomeItem = new IncomeItem();
		incomeItem.setFirstName(firstName);
		incomeItem.setLastName(lastName);
		incomeItem.setSalary(salary);
		incomeItem.setSuperannuationRate(superannuationRate);
		incomeItem.setPayPeriod(payPeriod);
		return incomeItem;
	}

	private static Payslip createPayslip(String fullName, String payPeriod, BigDecimal grossIncome,
			BigDecimal incomeTax, BigDecimal netIncome, BigDecimal superannuation) {
		Payslip payslip = new Payslip();
		payslip.setFullName(fullName);
		payslip.setPayPeriod(payPeriod);
		payslip.setGrossIncome(grossIncome);
		payslip.setIncomeTax(incomeTax);
		payslip.setNetIncome(netIncome);
		payslip.setSuperannuation(superannuation);
		return payslip;
	}

	public void assertPayslip(IncomeItem incomeItem, Payslip actualPayslip) {
		Payslip expectedPayslip = SAMPLE_TEST_DATA.get(incomeItem);
		assertEquals(expectedPayslip.getFullName(), actualPayslip.getFullName());
		assertEquals(expectedPayslip.getPayPeriod(), actualPayslip.getPayPeriod());
		assertEquals(expectedPayslip.getGrossIncome(), actualPayslip.getGrossIncome());
		assertEquals(expectedPayslip.getIncomeTax(), actualPayslip.getIncomeTax());
		assertEquals(expectedPayslip.getNetIncome(), actualPayslip.getNetIncome());
		assertEquals(expectedPayslip.getSuperannuation(), actualPayslip.getSuperannuation());
	}

}
