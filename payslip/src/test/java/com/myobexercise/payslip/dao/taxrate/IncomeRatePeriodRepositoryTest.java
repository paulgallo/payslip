package com.myobexercise.payslip.dao.taxrate;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.myobexercise.payslip.PayslipApplication;
import com.myobexercise.payslip.domain.taxrate.IncomeTaxRatePeriod;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayslipApplication.class)
public class IncomeRatePeriodRepositoryTest {

	@Autowired
	private IncomeRatePeriodRepository incomeRatePeriodRepository;

	@Test
	public void testFindByPayPeriodDate() {
		// TODO Refine test to use test data in separate in-memory TEST DB
		assertNull(incomeRatePeriodRepository.findByPayPeriodDate(LocalDate.of(2011, 1, 1)));
		assertNull(incomeRatePeriodRepository.findByPayPeriodDate(LocalDate.of(2012, 6, 30)));
		assertNull(incomeRatePeriodRepository.findByPayPeriodDate(LocalDate.of(2013, 7, 1)));
		assertNull(incomeRatePeriodRepository.findByPayPeriodDate(LocalDate.of(2015, 12, 1)));

		assertNotNull(incomeRatePeriodRepository.findByPayPeriodDate(LocalDate.of(2012, 7, 1)));
		IncomeTaxRatePeriod findByPayPeriodDate =
				incomeRatePeriodRepository.findByPayPeriodDate(LocalDate.of(2013, 6, 30));
		assertNotNull(findByPayPeriodDate);
	}

}
