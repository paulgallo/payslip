package com.myobexercise.payslip.web.payslip;

import com.myobexercise.payslip.domain.payslip.batch.PayslipBatch;
import com.myobexercise.payslip.service.payslip.batch.file.CsvBatchFileService;
import com.myobexercise.payslip.web.payslip.batch.PayslipBatchController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class PayslipClientController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	CsvBatchFileService csvBatchFileService;

	public PayslipClientController() {
		super();
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	protected String replacePathOnBaseUrl(String newPath) {
		return MvcUriComponentsBuilder.fromController(PayslipBatchController.class).replacePath(newPath)
				.build().toString();
	}

	/**
	 * The main client form for the Employee Monthly Payslip form. Responsible for retrieving all
	 * persisted {@link PayslipBatch} resources via the REST API.
	 * 
	 * @param model {@link Model}
	 * @return Form form
	 */
	@GetMapping
	public String listUploadedPayslipBatches(Model model) {
		ResponseEntity<List<PayslipBatch>> payslipBatchesEntity =
				restTemplate.exchange(replacePathOnBaseUrl("/api/payslip-batches"), HttpMethod.GET, null,
						new ParameterizedTypeReference<List<PayslipBatch>>() {});


		List<PayslipBatch> payslipBatches = payslipBatchesEntity.getBody();
		payslipBatches.stream()
				.forEach(p -> p.setLink(MvcUriComponentsBuilder
						.fromMethodName(PayslipClientController.class, "findPayslipBatchById", p.getId(), model)
						.build().toString()));

		model.addAttribute("payslipBatches", payslipBatches);

		return "payslipBatchForm";
	}

	/**
	 * Responsible for receiving the payslip file, parsing it and calling the REST API to persist the
	 * resource.
	 * 
	 * @param payslipBatchFile The {@link MultipartFile} to be persisted.
	 * @param redirectAttributes {@link RedirectAttributes}
	 * @param model {@link Model}
	 * @return Redirects to the home page if successful.
	 */
	@PostMapping
	public String handlePayslipBatchFileUpload(
			@RequestParam("payslipBatchFile") MultipartFile payslipBatchFile,
			RedirectAttributes redirectAttributes, Model model) {
		PayslipBatch payslipBatch = csvBatchFileService.constructPayslipBatch(payslipBatchFile);

		ResponseEntity<PayslipBatch> responseEntity = restTemplate.postForEntity(
				replacePathOnBaseUrl("/api/payslip-batches"), payslipBatch, PayslipBatch.class);

		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			redirectAttributes.addFlashAttribute("errorMessage", null);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded " + payslipBatchFile.getOriginalFilename()
							+ " to Payslip Batch Id:" + responseEntity.getBody().getId() + ".");
		}

		return "redirect:/";
	}

	/**
	 * Responsible for retrieving a single {@link PayslipBatch} via the REST API and then transforms
	 * it into a CSV File Resource for download.
	 * 
	 * @param payslipBatchId Id of the {@link PayslipBatch}
	 * @param model {@link Model}
	 * @return The CSV file content for the {@link PayslipBatch}.
	 */
	@GetMapping("{payslipBatchId}")
	@ResponseBody
	public ResponseEntity<String> findPayslipBatchById(@PathVariable Long payslipBatchId,
			Model model) {
		PayslipBatch payslipBatch = restTemplate.getForObject(
				replacePathOnBaseUrl("/api/payslip-batches/" + payslipBatchId), PayslipBatch.class);
		String fileContentsForPayslipBatch =
				csvBatchFileService.createFileContentsForPayslipBatch(payslipBatch);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"attachment; filename=\"" + payslipBatch.getBatchName() + "\"")
				.body(fileContentsForPayslipBatch);
	}

	/**
	 * The client error handler page. Redirects any errors to the homepage so that they can be placed
	 * on the model and displayed by the client.
	 * 
	 * @param exc Catches any exceptions so they can be displayed.
	 * @param redirectAttributes {@link RedirectAttributes}
	 * @return Redirects to home page.
	 */
	@GetMapping("error")
	@ExceptionHandler(RuntimeException.class)
	public String handleBatchFileException(RuntimeException exc,
			RedirectAttributes redirectAttributes) {
		// TODO Add more detailed exception handling here
		redirectAttributes.addFlashAttribute("message", null);

		StringBuilder builder = new StringBuilder(exc.getMessage());
		if (exc.getCause() != null) {
			builder.append(exc.getCause().getMessage());
		}
		redirectAttributes.addFlashAttribute("errorMessage", builder.toString());

		return "redirect:/";
	}

}
