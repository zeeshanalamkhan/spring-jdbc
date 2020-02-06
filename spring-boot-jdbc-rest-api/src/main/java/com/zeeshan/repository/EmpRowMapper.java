package com.zeeshan.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zeeshan.model.Employee;

public class EmpRowMapper implements RowMapper<Employee> {

	@Override
	public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

		Employee emp = new Employee();
		emp.setEmpNo(rs.getInt(1));
		emp.setEmpName(rs.getString(2));
		emp.setDesg(rs.getString(3));
		emp.setSalary(rs.getDouble(4));
		return emp;
	}

}
