package com.zeeshan.error;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpError {

	private Integer errCode;
	private String msg;
	private Date timestamp;

}
