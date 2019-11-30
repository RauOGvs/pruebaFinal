package com.genoves.calculator.util;

import java.util.ArrayList;
import java.util.List;

public class Operators {
	
	public static final String XPRESSION = "(?=[-+*/()])|(?<=[-+*/()])"; 
	public static final List<String> operatorOptions(){
		
		List<String> listOperators = new ArrayList<>();
		
		listOperators.add("+");
		listOperators.add("-");
		listOperators.add("*");
		listOperators.add("/");
		
		return listOperators;
		
	}

}
