package com.zeeshan.error;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeExceptionHandler {

	@ExceptionHandler(value = EmployeeNotFoundException.class)
	public ResponseEntity<EmpError> noEmployeeFoundHandler(EmployeeNotFoundException ex) {

		EmpError error = new EmpError();
		error.setErrCode(HttpStatus.NOT_FOUND.value());
		error.setMsg(ex.getMessage());
		error.setTimestamp(new Date());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(value = EmployeeNotSaveException.class)
	public ResponseEntity<EmpError> employeeNotSavedHandler(EmployeeNotSaveException ex) {

		EmpError error = new EmpError();
		error.setErrCode(HttpStatus.BAD_REQUEST.value());
		error.setMsg(ex.getMessage());
		error.setTimestamp(new Date());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = EmployeeNotUpdateException.class)
	public ResponseEntity<EmpError> employeeNotUpdateHandler(EmployeeNotUpdateException ex) {

		EmpError error = new EmpError();
		error.setErrCode(HttpStatus.BAD_REQUEST.value());
		error.setMsg(ex.getMessage());
		error.setTimestamp(new Date());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
