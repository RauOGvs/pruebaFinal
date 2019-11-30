package com.genoves.calculator.repository;

public interface IValidate<I, O> {

	public O validate(I infix);
	
}
