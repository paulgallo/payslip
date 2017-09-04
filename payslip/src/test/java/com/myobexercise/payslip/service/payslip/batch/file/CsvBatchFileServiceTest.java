package com.myobexercise.payslip.service.payslip.batch.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.myobexercise.payslip.domain.payslip.IncomeItem;
import com.myobexercise.payslip.domain.payslip.batch.PayslipBatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

@RunWith(MockitoJUnitRunner.class)
public class CsvBatchFileServiceTest {

	@Mock
	private Validator validator;

	@Mock
	MappingIterator<IncomeItem> mappingIterator;

	@InjectMocks
	CsvBatchFileService batchFileService;

	@Test
	public void constructPayslipBatch() throws JsonProcessingException, IOException {
		MultipartFile file = mock(MultipartFile.class);

		when(file.getOriginalFilename()).thenReturn("filename");
		CsvBatchFileService spyBatchFileService = spy(batchFileService);

		doReturn(mappingIterator).when(spyBatchFileService).constructCsvMappingIterator(eq(file),
				any());

		List<IncomeItem> incomeItems = new ArrayList<>();
		when(mappingIterator.readAll()).thenReturn(incomeItems);

		PayslipBatch payslipBatch = spyBatchFileService.constructPayslipBatch(file);

		assertThat(payslipBatch.getBatchName())
				.isEqualTo(CsvBatchFileService.PAYSLIPS_FILE_PREFIX + "filename");
		assertThat(payslipBatch.getIncomeItems()).isSameAs(incomeItems);
	}

}
