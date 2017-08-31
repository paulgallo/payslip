package com.myobexercise.payslip.dao.taxrate;

import java.time.LocalDate;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.myobexercise.payslip.domain.taxrate.IncomeRatePeriod;

public interface IncomeRatePeriodRepository extends Repository<IncomeRatePeriod, Long> {
	
	@Query("select i from IncomeRatePeriod i where ?1 between i.startDate and i.endDate")
	@Cacheable("incomeRatePeriods")
	IncomeRatePeriod findByPayPeriodDate(LocalDate payPeriodDate);
}
