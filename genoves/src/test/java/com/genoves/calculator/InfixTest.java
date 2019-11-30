package com.genoves.calculator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.genoves.calculator.process.IProcess;
import com.genoves.calculator.process.InfixProcess;
import com.genoves.calculator.process.ResultProcess;
import com.genoves.calculator.process.SortProcess;
import com.genoves.calculator.repository.IConverToList;
import com.genoves.calculator.repository.IValidate;
import com.genoves.calculator.repository.implementation.ConvertImplementation;
import com.genoves.calculator.repository.implementation.ConvertResultImplementation;
import com.genoves.calculator.repository.implementation.ValidateImplementation;
import com.genoves.calculator.util.Operators;

public class InfixTest  {
	
	private Operators operators;
	private IValidate<List<String>, Boolean> validateInfix;
	private IConverToList<String, List<String>> convert;
	private IProcess<List<String>, String> sortProcess;
	private IProcess<String, Double> processResult;
	
	@Test
	public void infixSuccess() {
		
		String infix="1+2.5/3*4";
		
		convert= new  ConvertImplementation();
		
		validateInfix = new ValidateImplementation();
		
		sortProcess= new  SortProcess();
		
		IProcess<String, ResponseEntity<?>> processSuccess = new InfixProcess(convert, validateInfix, sortProcess);
		
		processSuccess.process(infix);
		
	}
	@Test
	public void infixSuccessError() {
		
		String infix="1+2.5/3*4";
		
		convert= new  ConvertImplementation();
		
		IProcess<String, ResponseEntity<?>> processSuccess = new InfixProcess(convert, null, sortProcess);
		
		Assert.assertEquals(500, processSuccess.process(infix).getStatusCodeValue());
		
	}
	
	@Test
	public void infix400() {
		
		String infix="-1+2.5/3*4";
		
		convert= new  ConvertImplementation();
		
		validateInfix = new ValidateImplementation();
		
		IProcess<String, ResponseEntity<?>> processSuccess = new InfixProcess(convert, validateInfix, sortProcess);
		
		Assert.assertEquals(400, processSuccess.process(infix).getStatusCodeValue());
		
	}
	
	@Test
	public void convertToListTest() {
		
		String infix="1+2.5/3*4";
		
		List<String> listTest = new ArrayList<>();
		
		listTest.add("1");
		listTest.add("+");
		listTest.add("2.5");
		listTest.add("/");
		listTest.add("3");
		listTest.add("*");
		listTest.add("4");
		convert= new  ConvertImplementation();
		
		Assert.assertEquals(listTest, convert.convertToList(infix));  
	}
	@Test
	public void validateList() {
		
		
		List<String> listTest = new ArrayList<>();
		
		listTest.add("1");
		listTest.add("+");
		listTest.add("2.5");
		listTest.add("/");
		listTest.add("3");
		listTest.add("*");
		listTest.add("4");
		validateInfix= new  ValidateImplementation();
		
		Assert.assertEquals(true, validateInfix.validate(listTest));  
	}
	
	@Test
	public void validateListError() {
		
		
		List<String> listTest = new ArrayList<>();
		
		listTest.add("-1");
		listTest.add("+");
		listTest.add("2.5");
		listTest.add("/");
		listTest.add("3");
		listTest.add("*");
		listTest.add("4");
		validateInfix= new  ValidateImplementation();
		
		Assert.assertEquals(false, validateInfix.validate(listTest));  
	}
	
	@Test
	public void sortProcess() {
		
		
		List<String> listTest = new ArrayList<>();
		
		listTest.add("1");
		listTest.add("/");
		listTest.add("2.5");
		listTest.add("-");
		listTest.add("3");
		listTest.add("*");
		listTest.add("4");

		listTest.add("+");
		listTest.add("4");
		sortProcess= new  SortProcess();
		
		Assert.assertNotNull(sortProcess.process(listTest));  
	}
	
	@Test
	public void sortProcessDuplicates() {
		
		
		List<String> listTest = new ArrayList<>();
		
		listTest.add("1");
		listTest.add("*");
		listTest.add("2.5");
		listTest.add("-");
		listTest.add("3");
		listTest.add("*");
		listTest.add("4");

		listTest.add("+");
		listTest.add("4");
		sortProcess= new  SortProcess();
		
		Assert.assertNotNull(sortProcess.process(listTest));  
	}
	
	@Test
	public void sortProcessPlus() {
		
		
		List<String> listTest = new ArrayList<>();
		
		listTest.add("1");
		listTest.add("*");
		listTest.add("2.5");
		listTest.add("+");
		listTest.add("3");
		listTest.add("*");
		listTest.add("4");

		listTest.add("-");
		listTest.add("4");
		sortProcess= new  SortProcess();
		
		Assert.assertNotNull(sortProcess.process(listTest));  
	}
	
	@Test
	public void sortProcessMulti() {
		
		
		List<String> listTest = new ArrayList<>();
		
		listTest.add("1");
		listTest.add("*");
		listTest.add("2.5");
		listTest.add("-");
		listTest.add("3");
		listTest.add("/");
		listTest.add("4");

		listTest.add("+");
		listTest.add("4");
		sortProcess= new  SortProcess();
		
		Assert.assertNotNull(sortProcess.process(listTest));  
	}
	@Test
	public void sortProcessNull() {
		
		
		List<String> listTest = new ArrayList<>();
		
		listTest.add("1");
		listTest.add("/");
		listTest.add("2.5");
		listTest.add("-");
		listTest.add("3");
		listTest.add("*");
		listTest.add("4");

		listTest.add("+");
		listTest.add("4");
		sortProcess= new  SortProcess();
		
		Assert.assertNull(sortProcess.process(null));  
	}
	
	@Test
	public void processResult() {
		
		
		convert= new  ConvertResultImplementation();
		processResult = new  ResultProcess(convert);
		
		Assert.assertNull(processResult.process("1,2.5,3,/,4,*,+"));  
	}
	
	
	
	
	 

}
