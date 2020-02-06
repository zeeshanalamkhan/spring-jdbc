package com.zeeshan.repository;

import static com.zeeshan.util.Queries.DELETE_EMPLOYEE;
import static com.zeeshan.util.Queries.GET_ALL_EMPLOYEE;
import static com.zeeshan.util.Queries.GET_EMPLOYEE_BY_ID;
import static com.zeeshan.util.Queries.SAVE_EMPLOYEE;
import static com.zeeshan.util.Queries.UPDATE_EMPLOYEE;
import static com.zeeshan.util.Queries.MSG;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.zeeshan.error.EmployeeNotFoundException;
import com.zeeshan.error.EmployeeNotSaveException;
import com.zeeshan.model.Employee;

@Transactional
@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<Employee> findById(Integer id) {

		try {
			Employee emp = jdbcTemplate.queryForObject(GET_EMPLOYEE_BY_ID, new RowMapper<Employee>() {

				@Override
				public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
				}
			}, id);
			return Optional.of(emp);
		} catch (EmptyResultDataAccessException ex) {
			throw new EmployeeNotFoundException(MSG + id + " not found!");
		}
	}

	@Override
	public Optional<List<Employee>> findAll() {

		List<Employee> listEmp = jdbcTemplate.query(GET_ALL_EMPLOYEE,
				(rs, rowNum) -> new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4)));
		return Optional.of(listEmp);
	}

	@Override
	public Employee save(Employee employee) {
		try {
			jdbcTemplate.update(SAVE_EMPLOYEE, employee.getEmpNo(), employee.getEmpName(), employee.getDesg(),
					employee.getSalary());
			Optional<Employee> emp = findById(employee.getEmpNo());
			if (emp.isPresent()) {
				return emp.get();
			} else {
				return null;
			}

		} catch (DuplicateKeyException | IncorrectResultSizeDataAccessException ex) {
			throw new EmployeeNotSaveException(MSG + employee.getEmpNo() + " already exists!");
		}
	}

	@Override
	public Employee update(Integer id, Employee employee) {

		// Update Employee
		// Updates EmployeeName and EmployeeDesg
		Optional<Employee> emp = findById(id);
		if (emp.isPresent()) {
			emp.get().setEmpName(employee.getEmpName());
			emp.get().setDesg(employee.getDesg());
			jdbcTemplate.update(UPDATE_EMPLOYEE, emp.get().getEmpName(), emp.get().getDesg(), emp.get().getSalary(),
					emp.get().getEmpNo());
			return emp.get();
		} else {
			return null;
		}

	}

	@Override
	public Integer delete(Integer id) {
		Integer count = 0;
		if (!findById(id).isPresent()) {
			throw new EmployeeNotFoundException(MSG + id + " not found!");
		}
		count = jdbcTemplate.update(DELETE_EMPLOYEE, id);
		return count;
	}
}