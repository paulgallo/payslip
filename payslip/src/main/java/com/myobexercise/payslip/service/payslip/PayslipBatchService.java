package com.myobexercise.payslip.service.payslip;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.myobexercise.payslip.dao.payment.PayslipBatchRepository;
import com.myobexercise.payslip.domain.payment.IncomeItem;
import com.myobexercise.payslip.domain.payment.Payslip;
import com.myobexercise.payslip.domain.payment.PayslipBatch;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
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

	protected List<IncomeItem> parseCSVFileForIncomeItems(MultipartFile file) {
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

	public PayslipBatch createPayslipBatchForCSVFile(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		List<IncomeItem> incomeItems = parseCSVFileForIncomeItems(file);
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
		StringBuilder batchFilename = new StringBuilder("payslips-");
		if (StringUtils.isEmpty(originalFilename)) {
			batchFilename
					.append(processedDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSSSSS")));
			batchFilename.append(".csv");
		} else {
			batchFilename.append(originalFilename);
		}

		return batchFilename.toString();
	}

	protected List<Payslip> createPayslips(List<IncomeItem> incomeItems) {
		return incomeItems.stream().map(i -> payslipService.createPayslip(i))
				.collect(Collectors.toList());
	}

	public List<PayslipBatch> findAllPayslipBatches() {
		return payslipBatchRepository.findAllByOrderById();
	}

	public PayslipBatch createCSVFileForPayslipBatchId(Long payslipBatchId,
			StringWriter stringWriter) {
		PayslipBatch payslipBatch = payslipBatchRepository.findOneAndFetchPayslips(payslipBatchId);
		if (payslipBatch == null) {
			throw new RuntimeException(
					"Payslip Batch not found for Payslip Batch Id: " + payslipBatchId + ".");
		}
		CsvMapper csvMapper = new CsvMapper();
		ObjectWriter objectWriter = csvMapper.writerWithTypedSchemaFor(Payslip.class);
		payslipBatch.getPayslips().stream().forEachOrdered(p -> {
			try {
				stringWriter.append(objectWriter.writeValueAsString(p));
				stringWriter.append(System.lineSeparator());
			} catch (JsonProcessingException e) {
				throw new RuntimeException(
						"Problem loading CSV data for Payslip Batch Id: " + payslipBatchId + ".");
			}
		});

		return payslipBatch;
	}

	public String createCSVFileForPayslipBatchId2(Long payslipBatchId) {
		PayslipBatch payslipBatch = payslipBatchRepository.findOneAndFetchPayslips(payslipBatchId);
		if (payslipBatch == null) {
			throw new RuntimeException(
					"Payslip Batch not found for Payslip Batch Id: " + payslipBatchId + ".");
		}
		CsvMapper csvMapper = new CsvMapper();
		ObjectWriter objectWriter = csvMapper.writerWithTypedSchemaFor(Payslip.class);
		StringBuilder stringWriter = new StringBuilder();
		payslipBatch.getPayslips().stream().forEachOrdered(p -> {
			try {
				stringWriter.append(objectWriter.writeValueAsString(p));
			} catch (JsonProcessingException e) {
				throw new RuntimeException(
						"Problem loading CSV data for Payslip Batch Id: " + payslipBatchId + ".");
			}
		});

		return stringWriter.toString();
	}

	public PayslipBatch findPayslipBatchById(Long payslipBatchId) {
		return payslipBatchRepository.findOneAndFetchPayslips(payslipBatchId);
	}

}
