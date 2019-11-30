package com.genoves.calculator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genoves.calculator.pojo.request.Request;
import com.genoves.calculator.process.InfixProcess;

@RestController
public class InfixController {

	private InfixProcess process;
	public InfixController(InfixProcess process) {
		this.process = process;
	}
	
	@RequestMapping("/evaluate")
	public ResponseEntity<?> controller(@RequestBody Request exp){
		
		return process.process(exp.getExp());
		
	}
	
	
}
