package com.myobexercise.payslip.web.payslip;

import static org.assertj.core.api.Assertions.assertThat;

import com.myobexercise.payslip.dao.payment.PayslipBatchRepository;
import com.myobexercise.payslip.domain.payment.PayslipBatch;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PayslipControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PayslipBatchRepository payslipBatchRepository;

	@LocalServerPort
	private int port;


	@Test
	public void test() {
		ClassPathResource resource =
				new ClassPathResource("PayslipControllerIntegrationSample.csv", getClass());

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("file", resource);
		ResponseEntity<String> response = this.restTemplate.postForEntity("/", map, String.class);

		List<PayslipBatch> list = payslipBatchRepository.findAll();
		Long id = list.get(0).getId();
		PayslipBatch payslipBatch = payslipBatchRepository.findOneAndFetchPayslips(id);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.FOUND);

	}

}
