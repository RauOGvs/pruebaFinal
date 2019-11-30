package com.genoves.calculator.repository.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.genoves.calculator.repository.IValidate;
import com.genoves.calculator.util.Operators;

@Service
public class ValidateImplementation implements IValidate<List<String>, Boolean> {

	
	public ValidateImplementation() {
	
	}
	
	@Override
	public Boolean validate(List<String> infix) {
		
		if(infix.get(0).contains("-") && !Operators.operatorOptions().contains(infix)   ) {
			return false;
		}
		return true;
	}

}
