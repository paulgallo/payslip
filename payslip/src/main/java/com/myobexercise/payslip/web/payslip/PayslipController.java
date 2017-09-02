package com.myobexercise.payslip.web.payslip;

import com.myobexercise.payslip.service.payslip.PayslipBatchService;
import com.myobexercise.payslip.service.payslip.PayslipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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

	@PostMapping("/")
	public String savePayslipBatch(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		payslipBatchService.createPayslipBatch(file);

		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	// @GetMapping("/")
	// public String listUploadedFiles(Model model) throws IOException {
	//
	// model.addAttribute("files", storageService.loadAll().map(
	// path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
	// "serveFile", path.getFileName().toString()).build().toString())
	// .collect(Collectors.toList()));
	//
	// return "uploadForm";
	// }
	//
	// @GetMapping("/files/{filename:.+}")
	// @ResponseBody
	// public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
	//
	// Resource file = storageService.loadAsResource(filename);
	// return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
	// "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	// }
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
