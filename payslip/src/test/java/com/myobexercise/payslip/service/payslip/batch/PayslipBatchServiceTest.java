package com.myobexercise.payslip.service.payslip.batch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.myobexercise.payslip.dao.payslip.PayslipBatchRepository;
import com.myobexercise.payslip.domain.payslip.IncomeItem;
import com.myobexercise.payslip.domain.payslip.Payslip;
import com.myobexercise.payslip.domain.payslip.batch.PayslipBatch;
import com.myobexercise.payslip.service.payslip.PayslipCalculatorService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PayslipBatchServiceTest {

	@Mock
	private PayslipCalculatorService payslipCalculatorService;

	@Mock
	private PayslipBatchRepository payslipBatchRepository;

	@InjectMocks
	PayslipBatchService payslipBatchService;

	@Test
	public void test_createNewPayslipBatch() {
		PayslipBatch payslipBatch = new PayslipBatch();
		List<IncomeItem> incomeItems = new ArrayList<>();
		IncomeItem incomeItem = new IncomeItem();
		incomeItems.add(incomeItem);
		payslipBatch.setIncomeItems(incomeItems);
		Payslip payslip = new Payslip();
		PayslipBatch savedPayslipBatch = new PayslipBatch();

		when(payslipCalculatorService.createPayslip(incomeItem)).thenReturn(payslip);
		when(payslipBatchRepository.save(payslipBatch)).thenReturn(savedPayslipBatch);
		PayslipBatch newPayslipBatch = payslipBatchService.createNewPayslipBatch(payslipBatch);

		assertThat(payslipBatch.getPayslips()).hasSize(1);
		assertThat(payslipBatch.getPayslips().get(0)).isSameAs(payslip);
		assertThat(newPayslipBatch).isSameAs(savedPayslipBatch);
	}

	@Test
	public void test_findAllPayslipBatches() {
		List<PayslipBatch> payslipBatches = new ArrayList<>();
		when(payslipBatchRepository.findAllByOrderById()).thenReturn(payslipBatches);

		List<PayslipBatch> allPayslipBatches = payslipBatchService.findAllPayslipBatches();
		assertThat(allPayslipBatches).isSameAs(payslipBatches);
	}

	@Test
	public void test_findPayslipBatchById() {
		Long payslipBatchId = 1L;
		PayslipBatch payslipBatch = new PayslipBatch();
		when(payslipBatchRepository.findOneAndFetchPayslips(payslipBatchId)).thenReturn(payslipBatch);

		PayslipBatch foundPayslipBatch = payslipBatchService.findPayslipBatchById(payslipBatchId);
		assertThat(foundPayslipBatch).isSameAs(payslipBatch);
	}

}
