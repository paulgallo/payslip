package com.myobexercise.payslip.dao.payslip;

import static org.assertj.core.api.Assertions.assertThat;

import com.myobexercise.payslip.dao.payslip.PayslipBatchRepository;
import com.myobexercise.payslip.domain.payslip.Payslip;
import com.myobexercise.payslip.domain.payslip.batch.PayslipBatch;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PayslipBatchRepositoryTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	private PayslipBatchRepository payslipBatchRepository;

	@Test
	public void testPersistFlushFind() {
		PayslipBatch payslipBatchToSave1 = createSamplePayslipBatch();
		PayslipBatch payslipBatch = this.entityManager.persistFlushFind(payslipBatchToSave1);

		assertThatPayslipBatchesMatch(payslipBatchToSave1, payslipBatch);
	}

	@Test
	public void testFindAllByOrderById() {
		PayslipBatch payslipBatchToSave1 = createSamplePayslipBatch();
		PayslipBatch payslipBatchToSave2 = createSamplePayslipBatch();
		this.entityManager.persistAndFlush(payslipBatchToSave1);
		this.entityManager.persistAndFlush(payslipBatchToSave2);

		List<PayslipBatch> payslipBatches = payslipBatchRepository.findAllByOrderById();

		assertThat(payslipBatches).hasSize(2);
		assertThatPayslipBatchesMatch(payslipBatchToSave1, payslipBatches.get(0));
		assertThatPayslipBatchesMatch(payslipBatchToSave2, payslipBatches.get(1));
	}

	@Test
	public void testSave() {
		PayslipBatch payslipBatchToSave1 = createSamplePayslipBatch();
		PayslipBatch payslipBatchSaveResult = payslipBatchRepository.save(payslipBatchToSave1);
		entityManager.flush();
		PayslipBatch payslipBatchFound =
				payslipBatchRepository.findOneAndFetchPayslips(payslipBatchSaveResult.getId());

		assertThatPayslipBatchesMatch(payslipBatchToSave1, payslipBatchFound);

		PayslipBatch payslipBatchToSave2 = createSamplePayslipBatch();
		PayslipBatch payslipBatchSaveResult2 = payslipBatchRepository.save(payslipBatchToSave2);
		assertThat(payslipBatchToSave2).isSameAs(payslipBatchSaveResult2);
		assertThat(payslipBatchSaveResult2.getId()).isGreaterThan(0);
	}

	@Test
	public void testFindOneAndFetchPayslips() {
		PayslipBatch payslipBatchToSave1 = createSamplePayslipBatch();
		PayslipBatch payslipBatchSaveResult = this.entityManager.persistAndFlush(payslipBatchToSave1);
		PayslipBatch payslipBatchFound =
				payslipBatchRepository.findOneAndFetchPayslips(payslipBatchSaveResult.getId());

		assertThatPayslipBatchesMatch(payslipBatchToSave1, payslipBatchFound);
	}

	private void assertThatPayslipBatchesMatch(PayslipBatch expectedPayslipBatch,
			PayslipBatch actualPayslipBatch) {
		assertThat(expectedPayslipBatch).isEqualToIgnoringGivenFields(actualPayslipBatch, "payslips");
		assertThat(expectedPayslipBatch.getPayslips()).usingElementComparatorIgnoringFields("id")
				.isEqualTo(actualPayslipBatch.getPayslips());
	}

	private PayslipBatch createSamplePayslipBatch() {
		PayslipBatch payslipBatch = new PayslipBatch();

		LocalDateTime processedDateTime = LocalDateTime.now();
		payslipBatch.setProcessedDateTime(processedDateTime);

		List<Payslip> payslips =
				IntStream.range(1, 5).mapToObj(this::createSamplePayslip).collect(Collectors.toList());
		payslipBatch.setPayslips(payslips);

		return payslipBatch;
	}

	private Payslip createSamplePayslip(int index) {
		return new Payslip("fullName" + index, "payPeriod" + index, BigDecimal.valueOf(1 * index),
				BigDecimal.valueOf(2 * index), BigDecimal.valueOf(3 * index),
				BigDecimal.valueOf(4 * index));
	}

}
