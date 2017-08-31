package com.myobexercise.payslip.service.payslip;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.myobexercise.payslip.PayslipApplication;
import com.myobexercise.payslip.domain.payment.IncomeDetails;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayslipApplication.class)
public class CalculatorServiceIntegrationTest {

	@Autowired
	CalculatorService calculatorService;
	
	@Test
	public void testCalculateIncomeTaxForMonth() {
		IncomeDetails incomeDetails = new IncomeDetails();
		incomeDetails.setFirstName("David");
		incomeDetails.setLastName("Rudd");
		incomeDetails.setPayPeriod("01 March – 31 March");
		incomeDetails.setAnnualSalary(BigDecimal.valueOf(60050));
		incomeDetails.setSuperannuationRate(BigDecimal.valueOf(0.09));
		
		BigDecimal incomeTaxForMonth = calculatorService.calculateIncomeTaxForMonth(incomeDetails);
		
		assertEquals(BigDecimal.valueOf(922), incomeTaxForMonth);
	}

}
