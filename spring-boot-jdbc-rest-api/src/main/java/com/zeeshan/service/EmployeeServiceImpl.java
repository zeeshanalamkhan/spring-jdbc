package com.zeeshan.service;

import java.util.List;
import java.util.Optional;
import static com.zeeshan.util.Queries.MSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zeeshan.error.EmployeeNotFoundException;
import com.zeeshan.error.EmployeeNotSaveException;
import com.zeeshan.error.EmployeeNotUpdateException;
import com.zeeshan.model.Employee;
import com.zeeshan.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee findEmployeeById(Integer id) {

		Optional<Employee> oEmp = employeeRepository.findById(id);
		if (oEmp.isPresent()) {
			return oEmp.get();
		} else {
			return null;
		}
	}

	@Override
	public List<Employee> findAllEmployee() {

		Optional<List<Employee>> oList = employeeRepository.findAll();

		if (!oList.isPresent()) {
			throw new EmployeeNotFoundException("No Employee Exists");
		}

		return oList.get();

	}

	@Override
	public Employee saveEmployee(Employee employee) {

		Employee emp = employeeRepository.save(employee);
		if (emp == null) {
			throw new EmployeeNotSaveException("Employee couldn't be saved");
		}
		return emp;
	}

	@Override
	public Employee updateEmployee(Integer id, Employee employee) {

		Employee emp = employeeRepository.update(id, employee);

		if (emp == null) {
			throw new EmployeeNotUpdateException(MSG + id + " couldn't be updated");
		}

		return emp;
	}

	@Override
	public String deleteEmployee(Integer id) {

		Integer count = employeeRepository.delete(id);

		if (count == 0) {
			throw new EmployeeNotFoundException(MSG + id + " doesn't exists");
		}
		return MSG + id + " deleted successfully";
	}

}
