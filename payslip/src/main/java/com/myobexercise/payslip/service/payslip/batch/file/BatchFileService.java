package com.myobexercise.payslip.service.payslip.batch.file;

import com.myobexercise.payslip.domain.payslip.IncomeItem;
import com.myobexercise.payslip.domain.payslip.batch.PayslipBatch;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public interface BatchFileService {

	List<IncomeItem> parseFileForIncomeItems(MultipartFile file);

	void createFileContentsForPayslipBatchId(PayslipBatch payslipBatch);

}
