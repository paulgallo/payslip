package com.myobexercise.payslip.domain.payslip;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

public class PercentageDeserializer extends JsonDeserializer<BigDecimal> {

	public PercentageDeserializer() {
		super();
	}

	@Override
	public BigDecimal deserialize(JsonParser parser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		String percentageString = parser.getText().trim();

		Number number;
		try {
			number = new DecimalFormat("0.0#%").parse(percentageString);
		} catch (ParseException e) {
			throw new IllegalArgumentException(
					"Invalid Percentage value. Format must be 0.0#%. Invalid value = " + percentageString);
		}
		return new BigDecimal(number.toString());
	}

}
