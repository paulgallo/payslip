package com.myobexercise.payslip.web.payslip.batch;

import com.myobexercise.payslip.domain.payslip.batch.PayslipBatch;
import com.myobexercise.payslip.service.payslip.batch.PayslipBatchService;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/payslip-batches")
public class PayslipBatchController {

	@Autowired
	private PayslipBatchService payslipBatchService;

	public PayslipBatchController() {}

	public PayslipBatchService getPayslipBatchService() {
		return payslipBatchService;
	}

	public void setPayslipBatchService(PayslipBatchService payslipBatchService) {
		this.payslipBatchService = payslipBatchService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PayslipBatch savePayslipBatch(@RequestBody PayslipBatch payslipBatch) {
		return payslipBatchService.createNewPayslipBatch(payslipBatch);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<PayslipBatch> findAllPayslipBatches() throws IOException {
		return payslipBatchService.findAllPayslipBatches();
	}

	@GetMapping("/{payslipBatchId}")
	@ResponseStatus(HttpStatus.OK)
	public PayslipBatch findPayslipBatchById(@PathVariable Long payslipBatchId) {
		return payslipBatchService.findPayslipBatchById(payslipBatchId);
	}

	protected void addErrorMessage(Model model, String errorMessage) {
		model.addAttribute("errorMessage", errorMessage);
	}

	protected void clearErrorMessage(Model model) {
		addErrorMessage(model, null);
	}

	@GetMapping("error")
	@ExceptionHandler(RuntimeException.class)
	public String handleBatchFileException(RuntimeException exc,
			RedirectAttributes redirectAttributes) {
		// TODO Add more detailed exception handling here - Need to move to Global Handler
		redirectAttributes.addFlashAttribute("message", null);

		StringBuilder builder = new StringBuilder(exc.getMessage());
		if (exc.getCause() != null) {
			builder.append(exc.getCause().getMessage());
		}
		redirectAttributes.addFlashAttribute("errorMessage", builder.toString());

		return "redirect:/";
	}
}
