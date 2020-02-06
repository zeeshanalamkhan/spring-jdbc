package com.zeeshan.error;

public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException() {
		super();
	}

	public EmployeeNotFoundException(String message) {
		super(message);
	}

	public EmployeeNotFoundException(Throwable casue) {
		super(casue);
	}

	public EmployeeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
