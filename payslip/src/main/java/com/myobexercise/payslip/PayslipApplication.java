package com.myobexercise.payslip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

// Need to manually add support for jsr310 java 8 java.time.*
@EntityScan(
      basePackageClasses = {PayslipApplication.class, Jsr310JpaConverters.class}
)
@EnableCaching
@SpringBootApplication
public class PayslipApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayslipApplication.class, args);
		System.out.println("AFTER RUN GALLO");
	}
}
