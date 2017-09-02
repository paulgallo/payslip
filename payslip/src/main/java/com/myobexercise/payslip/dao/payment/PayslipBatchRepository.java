package com.myobexercise.payslip.dao.payment;

import com.myobexercise.payslip.domain.payment.PayslipBatch;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

@org.springframework.stereotype.Repository
public interface PayslipBatchRepository extends Repository<PayslipBatch, Long> {
	List<PayslipBatch> findAll();

	@Query("SELECT p FROM PayslipBatch p LEFT JOIN FETCH p.payslips WHERE p.id = (:id)")
	PayslipBatch findOneAndFetchPayslips(@Param("id") Long id);

	PayslipBatch save(PayslipBatch entity);
}
