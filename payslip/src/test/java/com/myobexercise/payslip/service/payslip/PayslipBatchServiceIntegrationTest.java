package com.myobexercise.payslip.service.payslip;

import com.myobexercise.payslip.PayslipApplication;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayslipApplication.class)
public class PayslipBatchServiceIntegrationTest {

	@Autowired
	private PayslipBatchService payslipBatchService;

	@Test
	public void testCreatePayslipBatch() {

	}

}
