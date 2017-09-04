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
					"01 May\\s*.\\s*31\\s+May", "01\\s+June\\s*.\\s*30\\s+June",
					"01\\s+July\\s*.\\s*31\\s+July", "01\\s+August\\s*.\\s*31\\s+August",
					"01\\s+September\\s*.\\s*30\\s+September", "01\\s+October\\s*.\\s*31\\s+October",
					"01\\s+November\\s*.\\s*30\\s+November", "01\\s+December\\s*.\\s*31\\s+December"};

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
