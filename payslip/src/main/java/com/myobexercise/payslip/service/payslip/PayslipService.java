package com.myobexercise.payslip.service.payslip;

import com.myobexercise.payslip.domain.payment.IncomeItem;
import com.myobexercise.payslip.domain.payment.Payslip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayslipService {

	@Autowired
	PayslipCalculatorService payslipCalculatorService;

	public PayslipService() {}

	public PayslipCalculatorService getPayslipCalculatorService() {
		return payslipCalculatorService;
	}

	public void setPayslipCalculatorService(PayslipCalculatorService payslipCalculatorService) {
		this.payslipCalculatorService = payslipCalculatorService;
	}

	public Payslip createPayslip(IncomeItem incomeItem) {
		return payslipCalculatorService.createPayslip(incomeItem);
	}

}
