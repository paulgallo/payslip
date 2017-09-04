package com.myobexercise.payslip.validator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PayPeriodValidatorTest {

	@Test
	public void testIsValid_Only2012to2013WholeMonthPeriodsAllowed() {
		PayPeriodValidator payPeriodValidator = new PayPeriodValidator();
		assertThat(payPeriodValidator.isValid("01 January - 31 January", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 February - 28 February", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 March - 31 March", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 April - 30 April", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 May - 31 May", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 June - 30 June", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 July - 31 July", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 August - 31 August", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 September - 30 September", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 October - 31 October", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 November - 30 November", null)).isTrue();
		assertThat(payPeriodValidator.isValid("01 December - 31 December", null)).isTrue();

		assertThat(payPeriodValidator.isValid("01 February - 29 February", null)).isFalse();
		assertThat(payPeriodValidator.isValid(" ", null)).isFalse();
		assertThat(payPeriodValidator.isValid(null, null)).isFalse();
	}



}
