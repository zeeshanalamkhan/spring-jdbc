package com.zeeshan.error;

public class EmployeeNotUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmployeeNotUpdateException() {
		super();
	}

	public EmployeeNotUpdateException(String message) {
		super(message);
	}

	public EmployeeNotUpdateException(Throwable casue) {
		super(casue);
	}

	public EmployeeNotUpdateException(String message, Throwable cause) {
		super(message, cause);
	}

}
