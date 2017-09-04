package com.myobexercise.payslip.service.payslip.batch.file;

public class BatchFileException extends RuntimeException {

	private static final long serialVersionUID = 1924718686330657052L;

	public BatchFileException() {
		super();
	}

	public BatchFileException(String message) {
		super(message);
	}

	public BatchFileException(Throwable cause) {
		super(cause);
	}

	public BatchFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public BatchFileException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
