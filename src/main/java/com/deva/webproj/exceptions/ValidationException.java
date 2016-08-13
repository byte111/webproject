package com.deva.webproj.exceptions;

public class ValidationException extends Throwable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ValidationException()
	{
		
	}
	
	public ValidationException(String msg)
	{
		super(msg);
	}

}
