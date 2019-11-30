package com.genoves.calculator.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.genoves.calculator.repository.IConverToList;
import com.genoves.calculator.util.Operators;

@Service
public class ResultProcess implements IProcess<String, Double> {

	private IConverToList<String, List<String>> convertResultImplementation;
	private Logger log;
	public ResultProcess(IConverToList<String, List<String>> convertResultImplementation) {
		this.convertResultImplementation = convertResultImplementation;
		this.log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public Double process(String input) {
		
		try {
			
			List<String> resultList = convertResultImplementation.convertToList(input);
			List<String> operatorStack = new ArrayList<>();
			List<String> outputStack = new ArrayList<>();
			Double resultadoParcial = new Double(0);
			Double resultadoFinal;
//			List<String> operatorStack = new ArrayList<>();
			int position = 0;
			
			for(String result : resultList) {
				
				if(Operators.operatorOptions().contains(result)) {
					
					
					 resultadoParcial = opciones(result, resultList, position);
					 
					
				}else {
					if(resultadoParcial!=null ) {
						 
						 resultadoFinal = total(result, resultadoParcial);
					 }
				}
					
					
				position++;
				
			}
			
			
			
		}catch(Exception e) {
			
			log.error("Error general {}", e, e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());
			
		}
		
		
		return null;
	}

	private Double total(String result, Double resultadoParcial) {

		if("/".equals(result)) {
			return resultadoParcial/Double.valueOf(result);
		}
		else if("*".equals(result)) {
			return resultadoParcial*Double.valueOf(result);
		}else if("+".equals(result)) {
			return resultadoParcial+Double.valueOf(result);
		}else if("-".equals(result)) {
			return resultadoParcial-Double.valueOf(result);
		}
		return null;
	}

	private Double opciones(String result, List<String> resultList, int position) {
		
			if("/".equals(result)) {
				return Double.valueOf(resultList.get(position-1))/Double.valueOf(resultList.get(position+1));
			}
			else if("*".equals(result)) {
				return Double.valueOf(resultList.get(position+1))*Double.valueOf(resultList.get(position+1));
			}else if("+".equals(result)) {
				return Double.valueOf(resultList.get(position+1))+Double.valueOf(resultList.get(position+1));
			}else if("-".equals(result)) {
				return Double.valueOf(resultList.get(position+1))-Double.valueOf(resultList.get(position+1));
			}
			return null;
	}
}
