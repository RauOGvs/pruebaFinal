package com.genoves.calculator.process;

public interface IProcess<I, O> {

	
	public O process(I input);
}
