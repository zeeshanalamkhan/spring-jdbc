package com.zeeshan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zeeshan.model.Employee;
import com.zeeshan.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = "/emp/{id}")
	public ResponseEntity<Employee> getEmp(@PathVariable Integer id) {

		Employee emp = employeeService.findEmployeeById(id);

		return new ResponseEntity<>(emp, HttpStatus.FOUND);
	}

	@GetMapping(value = "/emp")
	public ResponseEntity<List<Employee>> getAllEmp() {

		List<Employee> listEmp = employeeService.findAllEmployee();

		return new ResponseEntity<>(listEmp, HttpStatus.FOUND);
	}

	@PostMapping(value = "/emp")
	public ResponseEntity<Employee> saveNewEmployee(@RequestBody Employee employee) {

		Employee emp = employeeService.saveEmployee(employee);

		return new ResponseEntity<>(emp, HttpStatus.CREATED);
	}

	@PutMapping(value = "/emp/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Integer id, @RequestBody Employee employee) {

		Employee emp = employeeService.updateEmployee(id, employee);

		return new ResponseEntity<>(emp, HttpStatus.OK);
	}

	@DeleteMapping(value = "/emp/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Integer id) {

		String msg = employeeService.deleteEmployee(id);

		return new ResponseEntity<>(msg, HttpStatus.OK);
	}
}
