package com.genoves.calculator.repository.implementation;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.genoves.calculator.repository.IConverToList;
import com.genoves.calculator.util.Operators;

@Service
public class ConvertResultImplementation implements IConverToList<String, List<String>>{

	
	public ConvertResultImplementation() {
	
	}
	
	@Override
	public List<String> convertToList(String input) {
		
		
		String[] val = input.split(",");
		List<String> listVal = Arrays.asList(val);
		return listVal ;
	}
	
	
	

}
