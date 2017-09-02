package com.myobexercise.payslip.service.payslip;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.myobexercise.payslip.dao.payment.PayslipBatchRepository;
import com.myobexercise.payslip.domain.payment.IncomeItem;
import com.myobexercise.payslip.domain.payment.Payslip;
import com.myobexercise.payslip.domain.payment.PayslipBatch;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PayslipBatchService {

	@Autowired
	private PayslipService payslipService;

	@Autowired
	private PayslipBatchRepository payslipBatchRepository;

	public PayslipBatchService() {
		super();
	}

	public PayslipService getPayslipService() {
		return payslipService;
	}

	public void setPayslipService(PayslipService payslipService) {
		this.payslipService = payslipService;
	}

	// @Bean(name = "multipartResolver")
	// public CommonsMultipartResolver createMultipartResolver() {
	// CommonsMultipartResolver resolver = new CommonsMultipartResolver();
	// resolver.setDefaultEncoding("utf-8");
	// resolver.setMaxUploadSize(1048576); // 1MB max
	// return resolver;
	// }

	List<IncomeItem> parsePayslipBatchFileForIncomeItems(MultipartFile file) {
		CsvMapper csvMapper = new CsvMapper();

		MappingIterator<IncomeItem> mappingIterator;
		List<IncomeItem> incomeItems;
		try {
			mappingIterator = csvMapper.readerWithTypedSchemaFor(IncomeItem.class)
					.readValues(new InputStreamReader(file.getInputStream()));
			incomeItems = mappingIterator.readAll();
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Problem parsing CSV data.", e);
		} catch (IOException e) {
			throw new RuntimeException("Problem reading CSV file.", e);
		}
		return incomeItems;
	}

	public PayslipBatch createPayslipBatch(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		List<IncomeItem> incomeItems = parsePayslipBatchFileForIncomeItems(file);
		List<Payslip> payslips = createPayslips(incomeItems);
		return createPayslipBatchForPayslips(filename, payslips);
	}

	protected PayslipBatch createPayslipBatchForPayslips(String originalFilename,
			List<Payslip> payslips) {
		PayslipBatch payslipBatch = new PayslipBatch();
		payslipBatch.setProcessedDateTime(LocalDateTime.now());
		payslipBatch
				.setFilename(determineBatchFilename(originalFilename, payslipBatch.getProcessedDateTime()));
		payslipBatch.setPayslips(payslips);
		return payslipBatchRepository.save(payslipBatch);
	}

	protected String determineBatchFilename(String originalFilename,
			LocalDateTime processedDateTime) {
		StringBuilder batchFilename = new StringBuilder(StringUtils.isEmpty(originalFilename)
				? processedDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSSSSS"))
				: originalFilename);
		batchFilename.append("-payslips.csv");
		String filename = batchFilename.toString();
		return filename;
	}

	protected List<Payslip> createPayslips(List<IncomeItem> incomeItems) {
		return incomeItems.stream().map(i -> payslipService.createPayslip(i))
				.collect(Collectors.toList());
	}

	public List<PayslipBatch> findAllPayslipBatches() {
		return payslipBatchRepository.findAll();
	}

	public PayslipBatch findPayslipBatchById(Long id) {
		PayslipBatch payslipBatch = payslipBatchRepository.findOneAndFetchPayslips(id);
		return payslipBatch;
	}
}
