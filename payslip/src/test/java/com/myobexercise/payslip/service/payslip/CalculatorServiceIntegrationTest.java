package com.myobexercise.payslip.service.payslip;

import static org.junit.Assert.assertEquals;

import com.myobexercise.payslip.PayslipApplication;
import com.myobexercise.payslip.domain.payment.IncomeItem;
import com.myobexercise.payslip.domain.payment.Payslip;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayslipApplication.class)
public class CalculatorServiceIntegrationTest {

	public static final LocalDate SAMPLE_PAY_DATE = LocalDate.of(2012, 7, 1);
	public static final Map<IncomeItem, Payslip> SAMPLE_TEST_DATA;

	static {
		SAMPLE_TEST_DATA = new HashMap<>();
		SAMPLE_TEST_DATA.put(
				new IncomeItem("David", "Rudd", BigDecimal.valueOf(60050), BigDecimal.valueOf(0.09),
						"01 March – 31 March", SAMPLE_PAY_DATE),
				new Payslip("David Rudd", "01 March – 31 March", BigDecimal.valueOf(5004),
						BigDecimal.valueOf(922), BigDecimal.valueOf(4082), BigDecimal.valueOf(450)));
		SAMPLE_TEST_DATA.put(
				new IncomeItem("Ryan", "Chen", BigDecimal.valueOf(120000), BigDecimal.valueOf(0.1),
						"01 March – 31 March", SAMPLE_PAY_DATE),
				new Payslip("Ryan Chen", "01 March – 31 March", BigDecimal.valueOf(10000),
						BigDecimal.valueOf(2696), BigDecimal.valueOf(7304), BigDecimal.valueOf(1000)));
	}


	@Autowired
	PayslipCalculatorService calculatorService;

	@Test
	public void testCalculateIncomeTaxForMonth_UsingSampleValues() {
		SAMPLE_TEST_DATA.keySet().stream()
				.forEach(i -> assertPayslip(i, calculatorService.createPayslip(i)));
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
