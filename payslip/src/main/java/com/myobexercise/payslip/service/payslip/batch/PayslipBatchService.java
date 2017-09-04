package com.myobexercise.payslip.service.payslip.batch;

import com.myobexercise.payslip.dao.payslip.PayslipBatchRepository;
import com.myobexercise.payslip.domain.payslip.IncomeItem;
import com.myobexercise.payslip.domain.payslip.Payslip;
import com.myobexercise.payslip.domain.payslip.batch.PayslipBatch;
import com.myobexercise.payslip.service.payslip.PayslipCalculatorService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayslipBatchService {

	@Autowired
	private PayslipCalculatorService payslipCalculatorService;

	@Autowired
	private PayslipBatchRepository payslipBatchRepository;

	public PayslipBatchService() {
		super();
	}

	public PayslipCalculatorService getPayslipCalculatorService() {
		return payslipCalculatorService;
	}

	public void setPayslipCalculatorService(PayslipCalculatorService payslipCalculatorService) {
		this.payslipCalculatorService = payslipCalculatorService;
	}

	public PayslipBatchRepository getPayslipBatchRepository() {
		return payslipBatchRepository;
	}

	public void setPayslipBatchRepository(PayslipBatchRepository payslipBatchRepository) {
		this.payslipBatchRepository = payslipBatchRepository;
	}

	public PayslipBatch createNewPayslipBatch(PayslipBatch payslipBatch) {
		payslipBatch.setPayslips(createPayslips(payslipBatch.getIncomeItems()));
		payslipBatch.setProcessedDateTime(LocalDateTime.now());
		return payslipBatchRepository.save(payslipBatch);
	}

	protected List<Payslip> createPayslips(List<IncomeItem> incomeItems) {
		return incomeItems.stream().map(i -> payslipCalculatorService.createPayslip(i))
				.collect(Collectors.toList());
	}

	public List<PayslipBatch> findAllPayslipBatches() {
		return payslipBatchRepository.findAllByOrderById();
	}

	public PayslipBatch findPayslipBatchById(Long payslipBatchId) {
		return payslipBatchRepository.findOneAndFetchPayslips(payslipBatchId);
	}

}
