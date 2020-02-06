package com.zeeshan.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeeshan.error.EmployeeNotFoundException;
import com.zeeshan.error.EmployeeNotSaveException;
import com.zeeshan.model.Employee;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
public class EmployeeRepositoryTest {

	// private static final String QUERY = "CREATE TABLE EMP5(ENO NUMBER(10), ENAME
	// VARCHAR(20), JOB VARCHAR(20), SAL NUMBER(8,2))";
	private static final String QUERY = "CREATE TABLE IF NOT EXISTS  EMP5(ENO NUMBER(10), ENAME VARCHAR(20), JOB VARCHAR(20), SAL NUMBER(8,2))";
	private static final String INSERT_QUERY = "INSERT INTO EMP5 VALUES(1, 'ZAHEER', '.NET',12563.30)";
	private static final String TRUNCATE_QUERY = "TRUNCATE TABLE EMP5";
	private static final String DROP_QUERY = "DROP TABLE EMP5";

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Before
	public void init() {
		jdbcTemplate.execute(QUERY);
		jdbcTemplate.execute(INSERT_QUERY);
	}

	@After
	public void cleanup() {
		jdbcTemplate.execute(DROP_QUERY);
	}

	@Test
	public void testFindById() {

		Employee emp = new Employee();
		emp.setEmpNo(new Integer(2));
		emp.setEmpName("Zeeshan Khan");
		emp.setDesg("Python");
		emp.setSalary(112255.55);
		Employee savedInDB = employeeRepository.save(emp);
		Employee getFromDB = employeeRepository.findById(savedInDB.getEmpNo()).get();

		assertEquals(savedInDB, getFromDB);
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testFindByIdFailed() {

		int id = 2;
		when(employeeRepository.findById(id))
				.thenThrow(new EmployeeNotFoundException("Employee with ID: " + id + " not found"));

	}

	@Test
	public void testSave() {

		Employee emp = new Employee();
		emp.setEmpNo(new Integer(2));
		emp.setEmpName("Zeeshan Khan");
		emp.setDesg("Python");
		emp.setSalary(112255.55);
		Employee savedInDB = employeeRepository.save(emp);

		assertEquals(savedInDB, emp);
	}

	@Test(expected = EmployeeNotSaveException.class)
	public void testSaveFailed() {

		Employee emp = new Employee();
		emp.setEmpNo(new Integer(2));
		emp.setEmpName("Zeeshan Khan");
		emp.setDesg("Python");
		emp.setSalary(112255.55);
		employeeRepository.save(emp);

		Employee emp1 = new Employee();
		emp1.setEmpNo(new Integer(2));
		emp1.setEmpName("Zeeshan Khan");
		emp1.setDesg("Python");
		emp1.setSalary(112255.55);

		when(employeeRepository.save(emp1))
				.thenThrow(new EmployeeNotSaveException("Empoloyee with ID: " + emp1.getEmpNo() + " already exists"));
	}

	@Test
	public void testFindAll() {

		Employee emp = new Employee();
		emp.setEmpNo(new Integer(2));
		emp.setEmpName("Zeeshan Khan");
		emp.setDesg("Python");
		emp.setSalary(112255.55);

		Employee emp1 = new Employee();
		emp1.setEmpNo(new Integer(3));
		emp1.setEmpName("Jamsed Khan");
		emp1.setDesg("Django");
		emp1.setSalary(56968.30);

		employeeRepository.save(emp);
		employeeRepository.save(emp1);
		List<Employee> listEmp = employeeRepository.findAll().get();
		assertEquals(3, listEmp.size());
	}

	@Test
	public void testFindAllFailed() {

		jdbcTemplate.execute(TRUNCATE_QUERY);
		assertEquals(true, employeeRepository.findAll().get().isEmpty());
	}

	@Test
	public void testUpdate() {

		Employee emp = employeeRepository.findById(new Integer(1)).get();
		emp.setDesg("Manager");
		emp.setEmpName("Manoj");
		Employee savedInDB = employeeRepository.update(emp.getEmpNo(), emp);
		assertEquals(savedInDB, employeeRepository.findById(new Integer(1)).get());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testUpdateFailed() {

		Employee emp = new Employee(2, "Ravi", "N/W Engr", 52630.56);
		Employee savedInDB = employeeRepository.save(emp);
		int id = 3;
		savedInDB.setEmpName("Rajeev");
		when(employeeRepository.update(id, savedInDB))
				.thenThrow(new EmployeeNotFoundException("Employee with ID: " + id + " does not exist"));
	}

	@Test
	public void testDelete() {

		Integer count = employeeRepository.delete(new Integer(1));
		assertEquals(count, new Integer(1));
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testDeleteFailed() {

		when(employeeRepository.delete(2)).thenThrow(new EmployeeNotFoundException());
	}

}
