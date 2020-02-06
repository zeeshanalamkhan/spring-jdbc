package com.zeeshan.util;

public class Queries {

	private Queries() {

	}

	public static final String GET_EMPLOYEE_BY_ID = "SELECT ENO, ENAME, JOB, SAL FROM EMP5 WHERE ENO=?";
	public static final String GET_ALL_EMPLOYEE = "SELECT ENO, ENAME, JOB, SAL FROM EMP5";
	public static final String SAVE_EMPLOYEE = "INSERT INTO EMP5(ENO, ENAME, JOB, SAL) VALUES (?,?,?,?)";
	public static final String UPDATE_EMPLOYEE = "UPDATE EMP5 SET ENAME=?, JOB=?, SAL=? WHERE ENO=? ";
	public static final String DELETE_EMPLOYEE = "DELETE FROM EMP5 WHERE ENO =?";
	public static final String MSG = "Employee with ID: ";

}
