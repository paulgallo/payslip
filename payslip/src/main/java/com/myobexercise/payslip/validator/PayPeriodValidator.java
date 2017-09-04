package com.myobexercise.payslip.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

public class PayPeriodValidator implements ConstraintValidator<ValidPayPeriod, String> {

	// Very basic validation implementation to support current system limitation of Whole Months in
	// 2012 - 2013 Financial Year
	public static final String[] VALID_PAY_PERIODS =
			new String[] {"01\\s+January\\s*.\\s*31\\s+January", "01\\s+February\\s*.\\s*28\\s+February",
					"01\\s+March\\s*.\\s*31\\s+March", "01\\s+April\\s*.\\s*30\\s+April",
					"01 May\\s*.\\s*3\\s+May", "0\\s+June\\s*.\\s*3\\s+June", "0\\s+July\\s*.\\s*3\\s+July",
					"0\\s+August\\s*.\\s*3\\s+August", "0\\s+September\\s*.\\s*3\\s+September",
					"0\\s+October\\s*.\\s*3\\s+October", "0\\s+November\\s*.\\s*3\\s+November",
					"0\\s+December\\s*.\\s*3\\s+December"};

	public PayPeriodValidator() {
		super();
	}

	@Override
	public void initialize(ValidPayPeriod constraintAnnotation) {}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!StringUtils.hasText(value)) {
			return false;
		}

		for (String validPattern : VALID_PAY_PERIODS) {
			if (value.matches(validPattern)) {
				return true;
			}
		}
		return false;
	}

}
