package com.myobexercise.payslip.service.payslip;

import static org.junit.Assert.fail;

import com.myobexercise.payslip.PayslipApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayslipApplication.class)
public class PayslipServiceIntegrationTest {

	@Autowired
	private PayslipService payslipService;

	@Test
	public void testCreatePayslipBatch() {
		fail();
		// payslipService.createPayslipBatch(
		// new ArrayList<>(CalculatorServiceIntegrationTest.SAMPLE_TEST_DATA.keySet()));
		// List<PayslipBatch> payslipBatches = payslipService.findAllPayslipBatches();
		//
		// Long id = payslipBatches.get(0).getId();
		// String batchName = payslipBatches.get(0).getBatchName();
		// PayslipBatch payslipBatchById = payslipService.findPayslipBatchById(id);
		// Payslip payslip = payslipBatchById.getPayslip().get(0);
		// System.out.println("Batch = " + batchName + " Payslip = " + payslip.getFullName());

		// payslipBatches.stream()
		// .forEach(p ->
		// payslipService.findPayslipBatchById(p.getId()).getPayslip().stream()
		// .sorted(Comparator.comparing(Payslip::getId))
		// .forEach(d -> System.out.println("Batch = " + p.getBatchName() + " Payslip =
		// " + d.getFullName())));
	}

}
