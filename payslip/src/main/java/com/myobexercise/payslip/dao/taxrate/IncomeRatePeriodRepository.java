package com.myobexercise.payslip.dao.taxrate;

import com.myobexercise.payslip.domain.taxrate.IncomeTaxRatePeriod;

import java.time.LocalDate;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface IncomeRatePeriodRepository extends Repository<IncomeTaxRatePeriod, Long> {
	
	@Query("select i from IncomeTaxRatePeriod i where ?1 between i.startDate and i.endDate")
	@Cacheable("incomeRatePeriods")
	IncomeTaxRatePeriod findByPayPeriodDate(LocalDate payPeriodDate);
}
