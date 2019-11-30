package com.genoves.calculator.process;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.genoves.calculator.util.Operators;

@Service
public class SortProcess implements IProcess<List<String>, String> {

	private Logger log;

	public SortProcess() {
		this.log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public String process(List<String> input) {

		try {
			List<String> outPutStack = new ArrayList<>();
			List<String> operatorStack = new ArrayList<>();
			List<String> operatorStackFinal = new ArrayList<>();
			for (String var : input) {

				if (!Operators.operatorOptions().contains(var)) {// si no lo contiene es un simbolo
					outPutStack.add(var);
				} else {

					orderResult(outPutStack, operatorStack, var);
				}

			}
			for (int i = operatorStack.size() - 1; i >= 0; i--) {
				operatorStackFinal.add(operatorStack.get(i));
			}
			outPutStack.addAll(operatorStackFinal);
			String returnValue = outPutStack.stream().map(e -> e+",").reduce("", String::concat);
			return returnValue;
		} catch (Exception e) {
			log.error("Error general {}", e, e.getStackTrace()[0].getLineNumber(), e.getStackTrace()[0].getMethodName());
		}
		
		return null;

	}

	private void orderResult(List<String> outPutStack, List<String> operatorStack, String var) {
		if (operatorStack.isEmpty()) {
			operatorStack.add(var);
		} else if (operatorStack.contains(var)) {
			outPutStack.add(var);
			operatorStack.remove(var);
			operatorStack.add(var);
		} else if (operatorStack.contains("*") && "/".equals(var)) {
			outPutStack.add("*");
			operatorStack.remove("*");
			operatorStack.add(var);
		} else if (operatorStack.contains("/") && "*".equals(var)) {
			outPutStack.add("/");
			operatorStack.remove("/");
			operatorStack.add(var);
		} else if (operatorStack.contains("+") && "-".equals(var)) {
			outPutStack.add("+");
			operatorStack.remove("+");
			operatorStack.add(var);
		} else if (operatorStack.contains("-") && "+".equals(var)) {
			outPutStack.add("-");
			operatorStack.remove("-");
			operatorStack.add(var);
		} else {
			operatorStack.add(var);
		}
	}

}
