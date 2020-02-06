package com.zeeshan.service;

import java.util.List;

import com.zeeshan.model.Employee;

public interface EmployeeService {

	public Employee findEmployeeById(Integer id);

	public List<Employee> findAllEmployee();

	public Employee saveEmployee(Employee employee);

	public Employee updateEmployee(Integer id, Employee employee);

	public String deleteEmployee(Integer id);

}
