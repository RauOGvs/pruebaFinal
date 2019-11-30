package com.genoves.calculator.process;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.genoves.calculator.pojo.response.ResponsePojo;
import com.genoves.calculator.repository.IConverToList;
import com.genoves.calculator.repository.IValidate;
import com.genoves.calculator.util.MessageUtils;
import com.genoves.calculator.util.ResponseCodes;

@Service
public class InfixProcess implements IProcess<String, ResponseEntity<?>> {
	
	private IConverToList<String, List<String>> convertImplementation;
	private IValidate<List<String>, Boolean> validateImplementation;
	private Logger log;
	private IProcess<List<String>, String> sortProcess;
	
	public InfixProcess(IConverToList<String, List<String>> convertImplementation, IValidate<List<String>, Boolean> validateImplementation, IProcess<List<String>, String> sortProcess) {
		this.convertImplementation = convertImplementation;
		this.validateImplementation = validateImplementation;
		this.log = LoggerFactory.getLogger(getClass());
		this.sortProcess = sortProcess;
	}

	@Override
	public ResponseEntity<?> process(String input) {
		
		ResponsePojo response = new ResponsePojo();
		try {
			log.info("Input INFIX_EXPRESSION: {}", input);
			List<String> listToProcess = convertImplementation.convertToList(input);
			if(!validateImplementation.validate(listToProcess)) {
				log.error("error: {} ", MessageUtils.ERROR_400.replace("{val}", input));
				response.setMessage(MessageUtils.ERROR_400.replace("{val}", input));
				return new ResponseEntity<>(response, HttpStatus.resolve(ResponseCodes.ERROR));
			}
			
			String stringResult = sortProcess.process(listToProcess);
			
			if(stringResult!=null) {
				
				response.setInfix(input);
				response.setPostfix(stringResult.replaceAll(",", ""));
				
			}
			return new ResponseEntity<>(response, HttpStatus.resolve(ResponseCodes.SUCCESS));
		}catch(Exception e) {
			log.error("Error general {}", e, e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());
		}
		response.setMessage("Some error ocurred while processing the expression");
		
		return new ResponseEntity<>(response, HttpStatus.resolve(ResponseCodes.ERROR_GENERAL));
	}
	
	
	
}
