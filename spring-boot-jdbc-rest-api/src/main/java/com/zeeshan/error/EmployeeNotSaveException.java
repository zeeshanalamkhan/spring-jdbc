package com.zeeshan.error;

public class EmployeeNotSaveException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmployeeNotSaveException() {
		super();
	}

	public EmployeeNotSaveException(String message) {
		super(message);
	}

	public EmployeeNotSaveException(Throwable casue) {
		super(casue);
	}

	public EmployeeNotSaveException(String message, Throwable cause) {
		super(message, cause);
	}

}
