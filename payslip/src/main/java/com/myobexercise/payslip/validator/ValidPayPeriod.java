package com.myobexercise.payslip.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = PayPeriodValidator.class)
public @interface ValidPayPeriod {
	String message() default "Invalid pay period";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
