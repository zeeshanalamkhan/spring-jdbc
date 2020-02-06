package com.zeeshan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.zeeshan.error.EmployeeNotFoundException;
import com.zeeshan.error.EmployeeNotSaveException;
import com.zeeshan.error.EmployeeNotUpdateException;
import com.zeeshan.model.Employee;
import com.zeeshan.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

	@MockBean
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void testSaveEmployee() {

		Employee emp = new Employee();
		emp.setEmpNo(1001);
		emp.setEmpName("RAJESH");
		emp.setDesg("Java Developer");
		emp.setSalary(56985.50);

		when(employeeRepository.save(emp)).thenReturn(emp);
		assertThat(employeeService.saveEmployee(emp)).isEqualTo(emp);

	}

	@Test(expected = EmployeeNotSaveException.class)
	public void testSaveEmployeeFailed() {

		Employee emp = new Employee();
		emp.setEmpNo(1001);
		emp.setEmpName("RAJESH");
		emp.setDesg("Java Developer");
		emp.setSalary(56985.50);

		when(employeeRepository.save(emp)).thenReturn(emp);

		Employee emp1 = new Employee();
		emp.setEmpNo(1001);
		emp.setEmpName("RAVISH");
		emp.setDesg(".NET Developer");
		emp.setSalary(90985.80);

		when(employeeRepository.save(emp)).thenReturn(null);

		assertThat(employeeService.saveEmployee(emp1)).isInstanceOf(EmployeeNotSaveException.class);

	}

	@Test
	public void testFindEmployeeById() {

		Employee emp = new Employee();
		emp.setEmpNo(1001);
		emp.setEmpName("Ravish");
		emp.setDesg(".NET Developer");
		emp.setSalary(59630.60);

		when(employeeRepository.findById(emp.getEmpNo())).thenReturn(Optional.of(emp));
		assertThat(employeeService.findEmployeeById(emp.getEmpNo())).isEqualTo(emp);

	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testFindEmployeeByIdFailed() {

		when(employeeRepository.findById(1002)).thenThrow(new EmployeeNotFoundException());
		assertThat(employeeService.findEmployeeById(1002)).isInstanceOf(EmployeeNotFoundException.class);

	}

	@Test
	public void testFindAllEmployee() {

		List<Employee> empList = new ArrayList<>();

		Employee emp = new Employee();
		emp.setEmpNo(1001);
		emp.setEmpName("Ravish");
		emp.setDesg(".NET Developer");
		emp.setSalary(59630.60);

		Employee emp1 = new Employee();
		emp1.setEmpNo(1002);
		emp1.setEmpName("Rajesh");
		emp1.setDesg(".Java Developer");
		emp1.setSalary(45800.20);

		empList.add(emp);
		empList.add(emp1);

		when(employeeRepository.findAll()).thenReturn(Optional.of(empList));
		assertThat(employeeService.findAllEmployee().size()).isEqualTo(2);
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testFindAllEmployeeFailed() {

		when(employeeRepository.findAll()).thenThrow(new EmployeeNotFoundException());

		assertThat(employeeService.findAllEmployee()).isInstanceOf(EmployeeNotFoundException.class);

	}

	@Test
	public void testUpdateEmployee() {

		Employee emp = new Employee();
		emp.setEmpNo(1001);
		emp.setEmpName("Ravish");
		emp.setDesg(".NET Developer");
		emp.setSalary(59630.60);

		Employee emp1 = new Employee();
		emp1.setEmpName("Rajesh");
		emp1.setDesg("DB ADMIN");

		when(employeeRepository.save(emp)).thenReturn(emp);

		when(employeeRepository.update(emp.getEmpNo(), emp1)).thenReturn(emp1);
		assertThat(employeeService.updateEmployee(emp.getEmpNo(), emp1)).isEqualTo(emp1);

	}

	@Test(expected = EmployeeNotUpdateException.class)
	public void testUpdateEmployeeFailed() {

		Employee emp = new Employee();
		emp.setEmpNo(1001);
		emp.setEmpName("Ravish");
		emp.setDesg(".NET Developer");
		emp.setSalary(59630.60);

		Employee emp1 = new Employee();
		emp1.setEmpName("Rajesh");
		emp1.setDesg("DB ADMIN");

		when(employeeRepository.save(emp)).thenReturn(emp);

		when(employeeRepository.update(1002, emp1)).thenReturn(null);
		assertThat(employeeService.updateEmployee(1002, emp1)).isInstanceOf(EmployeeNotUpdateException.class);

	}

	@Test
	public void testDeleteEmployee() {

		Employee emp = new Employee();
		emp.setEmpNo(1001);
		emp.setEmpName("Ravish");
		emp.setDesg(".NET Developer");
		emp.setSalary(59630.60);

		when(employeeRepository.delete(emp.getEmpNo())).thenReturn(1);
		assertThat(employeeService.deleteEmployee(emp.getEmpNo()))
				.isEqualTo("Employee with ID: " + emp.getEmpNo() + " deleted successfully");

	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testDeleteEmployeeFailed() {

		when(employeeRepository.delete(1002)).thenReturn(0);
		assertThat(employeeService.deleteEmployee(1002)).isInstanceOf(EmployeeNotFoundException.class);

	}

}
