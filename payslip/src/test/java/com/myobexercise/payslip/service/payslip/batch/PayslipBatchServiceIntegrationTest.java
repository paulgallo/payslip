package com.myobexercise.payslip.service.payslip.batch;

import static org.assertj.core.api.Assertions.assertThat;

import com.myobexercise.payslip.PayslipApplication;
import com.myobexercise.payslip.domain.payslip.IncomeItem;
import com.myobexercise.payslip.domain.payslip.batch.PayslipBatch;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayslipApplication.class)
public class PayslipBatchServiceIntegrationTest {

	@Autowired
	PayslipBatchService payslipBatchService;

	@Test
	public void test_creationAndRetrieval() {
		// Test create, list all and find by id.
		PayslipBatch payslipBatch = new PayslipBatch();
		payslipBatch.setBatchName("batchName");
		LocalDateTime now = LocalDateTime.now();
		payslipBatch.setProcessedDateTime(now);
		List<IncomeItem> incomeItems = new ArrayList<IncomeItem>();
		IncomeItem incomeItem = new IncomeItem();
		incomeItem.setFirstName("firstName");
		incomeItem.setLastName("lastName");
		incomeItem.setPayPeriod("01 January - 31 January");
		incomeItem.setSalary(BigDecimal.valueOf(60050));
		incomeItem.setSuperannuationRate(BigDecimal.valueOf(0.09));
		incomeItems.add(incomeItem);
		payslipBatch.setIncomeItems(incomeItems);

		PayslipBatch newPayslipBatch = payslipBatchService.createNewPayslipBatch(payslipBatch);
		assertThat(newPayslipBatch).isNotNull();
		assertThat(newPayslipBatch.getId()).isGreaterThan(0);

		List<PayslipBatch> allPayslipBatches = payslipBatchService.findAllPayslipBatches();
		assertThat(allPayslipBatches).hasSize(1);

		PayslipBatch payslipBatchById =
				payslipBatchService.findPayslipBatchById(newPayslipBatch.getId());
		assertThat(payslipBatchById).isNotNull();
		assertThat(payslipBatchById.getId()).isGreaterThan(0);
		assertThat(payslipBatchById.getPayslips()).hasSize(1);
		assertThat(payslipBatchById.getPayslips().get(0).getGrossIncome())
				.isEqualTo(BigDecimal.valueOf(5004));
		assertThat(payslipBatchById.getPayslips().get(0).getIncomeTax())
				.isEqualTo(BigDecimal.valueOf(922));
		assertThat(payslipBatchById.getPayslips().get(0).getNetIncome())
				.isEqualTo(BigDecimal.valueOf(4082));
		assertThat(payslipBatchById.getPayslips().get(0).getSuperannuation())
				.isEqualTo(BigDecimal.valueOf(450));
		assertThat(payslipBatchById.getPayslips().get(0).getPayPeriod())
				.isEqualTo("01 January - 31 January");
	}

}
