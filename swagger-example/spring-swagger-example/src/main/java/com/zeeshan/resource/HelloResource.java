package com.zeeshan.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/rest/hello")
@Api(value="HelloWorld Resource",description="Shows the Hello World Resource")
public class HelloResource {

	@ApiOperation(value = "Returns Hello World")
	@ApiResponses(value = { @ApiResponse(code = 100, message = "100 is the message"),
			@ApiResponse(code = 200, message = "Successfull Hello World"),
			@ApiResponse(code = 403, message = "Access is Forbidden") })
	@GetMapping
	public String hello() {
		return "Hello World....!";
	}

	@ApiOperation(value = "Returns Hello World")
	@PostMapping("/post")
	public String helloAdd(@RequestBody final String hello) {
		return hello;
	}

	@ApiOperation(value = "Returns Hello World")
	@PutMapping("/put")
	public String helloPut(@RequestBody final String hello) {
		return hello;
	}
}
