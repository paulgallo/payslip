package com.myobexercise.payslip.web.payslip;

import com.myobexercise.payslip.domain.payment.PayslipBatch;
import com.myobexercise.payslip.service.payslip.PayslipBatchService;
import com.myobexercise.payslip.service.payslip.PayslipService;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PayslipController {

	@Autowired
	private PayslipBatchService payslipBatchService;

	@Autowired
	private PayslipService payslipService;

	public PayslipController() {}

	public PayslipService getPayslipService() {
		return payslipService;
	}

	public void setPayslipService(PayslipService payslipService) {
		this.payslipService = payslipService;
	}

	public PayslipBatchService getPayslipBatchService() {
		return payslipBatchService;
	}

	public void setPayslipBatchService(PayslipBatchService payslipBatchService) {
		this.payslipBatchService = payslipBatchService;
	}

	@PostMapping(path = "/payslip-batches")
	public String handlePayslipBatchFileUpload(
			@RequestParam("payslipBatchFile") MultipartFile payslipBatchFile,
			RedirectAttributes redirectAttributes) {
		payslipBatchService.createPayslipBatchForCSVFile(payslipBatchFile);

		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + payslipBatchFile.getOriginalFilename() + ".");

		return "redirect:/";
	}

	@GetMapping("/")
	public String listUploadedPayslipBatches(Model model) throws IOException {

		List<PayslipBatch> payslipBatches = payslipBatchService.findAllPayslipBatches();

		payslipBatches.stream()
				.forEach(p -> p.setLink(MvcUriComponentsBuilder
						.fromMethodName(PayslipController.class, "retrievePayslipBatchAsCSV", p.getId()).build()
						.toString()));

		model.addAttribute("payslipBatches", payslipBatches.stream()
				.sorted(Comparator.comparing(PayslipBatch::getId)).collect(Collectors.toList()));

		return "payslipBatchForm";
	}


	@GetMapping("/payslip-batches/{payslipBatchId}")
	@ResponseBody
	public ResponseEntity<String> retrievePayslipBatchAsCSV(@PathVariable Long payslipBatchId) {

		StringWriter csvWriter = new StringWriter();
		PayslipBatch payslipBatch =
				payslipBatchService.createCSVFileForPayslipBatchId(payslipBatchId, csvWriter);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + payslipBatch.getFilename() + "\"")
				.body(csvWriter.toString());
	}

	//
	// @PostMapping("/")
	// public String handleFileUpload(@RequestParam("file") MultipartFile file,
	// RedirectAttributes redirectAttributes) {
	//
	// storageService.store(file);
	// redirectAttributes.addFlashAttribute("message",
	// "You successfully uploaded " + file.getOriginalFilename() + "!");
	//
	// return "redirect:/";
	// }

}
