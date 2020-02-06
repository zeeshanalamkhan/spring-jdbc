package com.zeeshan.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeeshan.error.EmployeeNotFoundException;
import com.zeeshan.error.EmployeeNotSaveException;
import com.zeeshan.error.EmployeeNotUpdateException;
import com.zeeshan.model.Employee;
import com.zeeshan.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class, secure = false)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EmployeeService employeeService;

	@Test
	public void testGetEmp() throws Exception {

		Employee emp = new Employee(1001, "Zaheer Khan", " Manager", 52630.30);
		String URI = "/emp/1001";
		when(employeeService.findEmployeeById(Mockito.anyInt())).thenReturn(emp);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept("application/json")
				.contentType("application/json");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(emp);
		String jsonOutput = mvcResult.getResponse().getContentAsString();
		assertEquals(jsonOutput, expectedJson);
		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.FOUND.value());
	}

	@Test
	public void testGetEmpFailed() throws Exception {

		Employee emp = new Employee();
		emp.setEmpNo(1002);

		String URI = "/emp/1001";
		when(employeeService.findEmployeeById(Mockito.anyInt())).thenThrow(new EmployeeNotFoundException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept("application/json")
				.contentType("application/json");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());

	}

	@Test
	public void testGetAllEmp() throws Exception {

		Employee emp1 = new Employee(1001, "Rajesh", "Sales", 45063.50);
		Employee emp2 = new Employee(1002, "Rameez", "Sales", 45630.50);
		List<Employee> empList = new ArrayList<>();
		empList.add(emp1);
		empList.add(emp2);

		String URI = "/emp";
		when(employeeService.findAllEmployee()).thenReturn(empList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept("application/json")
				.contentType("application/json");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String jsonOutput = mvcResult.getResponse().getContentAsString();
		String expectedOutput = mapToJson(empList);
		assertEquals(expectedOutput, jsonOutput);

		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.FOUND.value());

	}

	@Test
	public void testGetAllEmpFailed() throws Exception {

		String URI = "/emp";
		when(employeeService.findAllEmployee()).thenThrow(new EmployeeNotFoundException());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept("application/json")
				.contentType("application/json");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.NOT_FOUND.value());

	}

	@Test
	public void testSaveNewEmployee() throws Exception {

		Employee emp = new Employee();
		emp.setEmpNo(1001);
		emp.setEmpName("Rajesh");
		emp.setDesg("H/W Engr");
		emp.setSalary(25690.52);

		String URI = "/emp";
		when(employeeService.saveEmployee(Mockito.any(Employee.class))).thenReturn(emp);
		String jsonInput = mapToJson(emp);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).content(jsonInput)
				.contentType("application/json").accept("application/json");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String jsonOutput = mvcResult.getResponse().getContentAsString();
		assertEquals(jsonOutput, jsonInput);
		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.CREATED.value());

	}

	@Test
	public void testSaveNewEmployeeFailed() throws Exception {

		Employee emp = new Employee();
		emp.setEmpNo(1001);
		emp.setEmpName("Rajesh");
		emp.setDesg("H/W Engr");
		emp.setSalary(25690.52);

		String URI = "/emp";
		when(employeeService.saveEmployee(Mockito.any(Employee.class))).thenThrow(new EmployeeNotSaveException());
		String jsonInput = mapToJson(emp);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).content(jsonInput)
				.contentType("application/json").accept("application/json");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());

	}

	@Test
	public void testUpdateEmployee() throws Exception {

		Employee emp = new Employee(1001, "Rajeev", "Sales", 52360.50);
		when(employeeService.saveEmployee(Mockito.any(Employee.class))).thenReturn(emp);

		String URI = "/emp/1001";

		Employee emp1 = new Employee();
		emp1.setEmpNo(emp.getEmpNo());
		emp1.setEmpName("Ramesh");
		emp1.setDesg(emp.getDesg());
		emp1.setSalary(45620.50);
		when(employeeService.updateEmployee(Mockito.anyInt(), Mockito.any(Employee.class))).thenReturn(emp1);
		String jsonInput = mapToJson(emp1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).content(jsonInput)
				.contentType("application/json").accept("application/json");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String outputJson = mvcResult.getResponse().getContentAsString();
		assertEquals(jsonInput, outputJson);
		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
	}

	@Test
	public void testUpdateEmployeeFailed() throws Exception {

		String URI = "/emp/1002";
		Employee emp = new Employee(1001, "ZAHEER", "SALES", 56203.05);
		String jsonInput = mapToJson(emp);
		when(employeeService.updateEmployee(Mockito.anyInt(), Mockito.any(Employee.class)))
				.thenThrow(new EmployeeNotUpdateException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI).content(jsonInput).accept("application/json")
				.contentType("application/json");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		int resultStatus = mvcResult.getResponse().getStatus();
		assertEquals(resultStatus, HttpStatus.BAD_REQUEST.value());

	}

	@Test
	public void testDeleteEmployee() throws Exception {

		String response = "Employee Deleted";
		String URI = "/emp/1001";
		when(employeeService.deleteEmployee(Mockito.anyInt())).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI).accept("application/json")
				.contentType("application/json");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		String outputResponse = mvcResult.getResponse().getContentAsString();
		assertEquals(outputResponse, response);
		assertEquals(mvcResult.getResponse().getStatus(), HttpStatus.OK.value());
	}

	@Test
	public void testDeleteEmployeeFailed() throws Exception {

		String URI = "/emp/1001";
		when(employeeService.deleteEmployee(Mockito.anyInt())).thenThrow(new EmployeeNotFoundException());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI).accept("application/json")
				.contentType("application/json");
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		int resultStatusCode = mvcResult.getResponse().getStatus();

		assertEquals(resultStatusCode, HttpStatus.NOT_FOUND.value());

	}

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);

	}

}
