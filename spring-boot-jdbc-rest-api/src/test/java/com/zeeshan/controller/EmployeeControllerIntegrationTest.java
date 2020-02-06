package com.zeeshan.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeeshan.SpringBootJdbcRestApiApplication;
import com.zeeshan.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootJdbcRestApiApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private HttpHeaders httpHeaders = new HttpHeaders();

	@Test
	public void testSaveNewEmployee() throws Exception {

		Employee emp = new Employee();
		emp.setEmpNo(1002);
		emp.setEmpName("RAJIV");
		emp.setSalary(45820.20);
		emp.setDesg("SALES");

		String jsonInput = mapToJson(emp);
		String URI = "/emp";
		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(emp, httpHeaders);
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(formFullURLWithPort(URI), HttpMethod.POST,
				httpEntity, String.class);
		String jsonOutput = responseEntity.getBody();
		assertEquals(jsonOutput, jsonInput);
		assertEquals(responseEntity.getStatusCode().value(), HttpStatus.CREATED.value());
	}

	@Test
	public void testSaveNewEmployeeFailed() throws Exception {
		String URI = "/emp";
		Employee emp = new Employee(1001, "JAMSHED", "PRC", 52061.20);

		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(emp, httpHeaders);

		testRestTemplate.exchange(formFullURLWithPort(URI), HttpMethod.POST, httpEntity, String.class);

		Employee emp1 = new Employee(1001, "ZEESHAN", "JAVA DEV", 45205.80);
		HttpEntity<Employee> httpEntity1 = new HttpEntity<Employee>(emp1, httpHeaders);

		ResponseEntity<String> resposneEntity = testRestTemplate.exchange(formFullURLWithPort(URI), HttpMethod.POST,
				httpEntity1, String.class);
		assertEquals(resposneEntity.getStatusCode().value(), HttpStatus.BAD_REQUEST.value());

	}

	@Test
	public void testGetEmp() throws Exception {

		String URI_TO_SAVE_EMP = "/emp";
		String URI_TO_GET_EMP_BY_ID = "/emp/1004";
		Employee emp = new Employee(1004, "ZAHEER KHAN", "MANAGER", 69250.50);
		String jsonEmp = mapToJson(emp);
		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(emp, httpHeaders);
		testRestTemplate.exchange(formFullURLWithPort(URI_TO_SAVE_EMP), HttpMethod.POST, httpEntity, String.class);

		HttpEntity<Employee> httpEntity1 = new HttpEntity<Employee>(httpHeaders);
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(formFullURLWithPort(URI_TO_GET_EMP_BY_ID),
				HttpMethod.GET, httpEntity1, String.class);

		assertThat(responseEntity.getBody()).isEqualTo(jsonEmp);// assertThat(responseEntity.getStatusCode().value()).isEqualTo(HttpStatus.FOUND.value());
	}

	@Test
	public void testGetEmpFailed() {

		String URI_TO_SAVE_EMP = "/emp";
		String URI_TO_GET_EMP_BY_ID = "emp/1002";
		Employee emp = new Employee(1001, "ZAHEER", "SALES", 54260.50);
		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(emp, httpHeaders);
		testRestTemplate.exchange(formFullURLWithPort(URI_TO_SAVE_EMP), HttpMethod.POST, httpEntity, String.class);

		httpEntity = new HttpEntity<Employee>(null, httpHeaders);
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(formFullURLWithPort(URI_TO_GET_EMP_BY_ID),
				HttpMethod.GET, httpEntity, String.class);
		int outputStatusCode = responseEntity.getStatusCodeValue();
		assertThat(outputStatusCode).isEqualTo(HttpStatus.NOT_FOUND.value());

	}

	@Test
	public void testUpdateEmployee() throws Exception {

		String URI_TO_SAVE_EMP = "/emp";
		String URI_TO_UPDATE_EMP = "/emp/1005";
		Employee emp = new Employee();
		emp.setEmpNo(1005);
		emp.setEmpName("RAJESH");
		emp.setDesg("SALES");
		emp.setSalary(45026.50);
		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(emp, httpHeaders);
		testRestTemplate.exchange(formFullURLWithPort(URI_TO_SAVE_EMP), HttpMethod.POST, httpEntity, String.class);

		Employee emp2 = new Employee();
		emp2.setEmpNo(emp.getEmpNo());
		emp2.setEmpName("Mahesh");
		emp2.setDesg("Manager");
		emp2.setSalary(emp.getSalary());
		String jsonActual = mapToJson(emp2);
		httpEntity = new HttpEntity<Employee>(emp2, httpHeaders);
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(formFullURLWithPort(URI_TO_UPDATE_EMP),
				HttpMethod.PUT, httpEntity, String.class);
		String jsonExpected = responseEntity.getBody();
		assertThat(jsonExpected).isEqualTo(jsonActual);
	}

	@Test
	public void testUpdateEmployeeFailed() {

		String URI_TO_SAVE_EMP = "/emp";
		String URI_TO_UPDATE_EMP = "/emp/1007";
		Employee emp = new Employee();
		emp.setEmpNo(1001);
		emp.setDesg("SALES");
		emp.setEmpName("RAMIZ");
		emp.setSalary(7853.02);

		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(emp, httpHeaders);
		testRestTemplate.exchange(formFullURLWithPort(URI_TO_SAVE_EMP), HttpMethod.POST, httpEntity, String.class);

		Employee emp2 = new Employee(emp.getEmpNo(), emp.getEmpName(), "MANAGER", emp.getSalary());
		httpEntity = new HttpEntity<Employee>(emp2, httpHeaders);
		ResponseEntity<String> resposenEntity = testRestTemplate.exchange(formFullURLWithPort(URI_TO_UPDATE_EMP),
				HttpMethod.PUT, httpEntity, String.class);
		int actualStatusCode = HttpStatus.NOT_FOUND.value();
		int expectedStatusCode = resposenEntity.getStatusCodeValue();

		assertThat(expectedStatusCode).isEqualTo(actualStatusCode);

	}

	@Test
	public void testDeleteEmp() {

		String URI_TO_SAVE_EMP = "/emp";
		String URI_TO_DELETE_EMP = "/emp/1006";
		Employee emp = new Employee(1006, "RAHIM", "JAVA DEV", 85695.30);
		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(emp, httpHeaders);
		testRestTemplate.exchange(formFullURLWithPort(URI_TO_SAVE_EMP), HttpMethod.POST, httpEntity, String.class);

		httpEntity = new HttpEntity<Employee>(null, httpHeaders);
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(formFullURLWithPort(URI_TO_DELETE_EMP),
				HttpMethod.DELETE, httpEntity, String.class);
		assertEquals(responseEntity.getStatusCodeValue(), HttpStatus.OK.value());
		// assertThat(responseEntity.getBody()).isEqualTo("Employee with ID: " +
		// emp.getEmpNo() + " deleted successfully");

	}

	@Test
	public void testDeleteEmployeeFailed() {
		String URI_TO_DELETE_EMP = " /emp/1001";
		HttpEntity<Employee> httpEntity = new HttpEntity<>(null, httpHeaders);
		ResponseEntity<String> responseEntity = testRestTemplate.exchange(formFullURLWithPort(URI_TO_DELETE_EMP),
				HttpMethod.DELETE, httpEntity, String.class);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND.value());

	}

	private String formFullURLWithPort(String URI) {
		return "http://localhost:" + port + URI;
	}

	private String mapToJson(Object emp) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(emp);
	}

}
