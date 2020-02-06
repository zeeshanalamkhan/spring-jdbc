package com.zeeshan.repository;

import java.util.List;
import java.util.Optional;

import com.zeeshan.model.Employee;

public interface EmployeeRepository {

	public Optional<Employee> findById(Integer id);

	public Optional<List<Employee>> findAll();

	public Employee save(Employee employee);

	public Employee update(Integer id, Employee employee);

	public Integer delete(Integer id);

}
