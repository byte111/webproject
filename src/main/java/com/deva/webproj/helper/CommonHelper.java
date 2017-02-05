package com.deva.webproj.helper;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.UUID;

import com.deva.webproj.exceptions.ValidationException;
import com.deva.webproj.vo.UserProfileVO;

public class CommonHelper {

	
	public static Integer getCompId(String compname) throws Exception
	{
		int compId = 0;
		
		try {
			if(compname == null || compname.trim().length() == 0 )
				throw new Exception("Company name cannot be null.");
			for(char c: compname.toCharArray())
			{
				
				compId += ( c + new Random(c).nextInt());
				
			}		
			
			compId =  new Random(compId).nextInt() ;
			return (compId < 0 ? (0 - compId) : compId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public static String getFirstTimePassword() throws Exception
	{
		try {
			return  UUID.randomUUID().toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception while generatig random password.");		
		}		
	
	}
	public static boolean validateCreateUser(UserProfileVO userprof) throws ValidationException
	{
		if(userprof == null )
		{
			throw new ValidationException("User profile values cannot be null." );		
		}
		
		else if(userprof.getCompname() == null || 
				userprof.getCompname().trim().length() == 0 )
		{
			throw new ValidationException("Company name cannot be null." );	
		}
		
		else if(userprof.getCompid() == null || 
				userprof.getCompid().trim().length() ==0)
		{
			throw new ValidationException("Company id cannot be null." );	
		}
		
		return true;
		
	}
	
	public static boolean validate(Object object) throws IllegalArgumentException,ValidationException, IllegalAccessException
	{
		for(Field field : object.getClass().getDeclaredFields())
		{
			Object value  = null;
			try {
				value  = field.get(object);
				if(value == null)
					throw new ValidationException(value+ " cannot be null.");
				else if(field.getType().isInstance(String.class) && value.toString().trim().length() == 0 )
					throw new ValidationException(value+ " cannot be empty.");
					
			} catch (IllegalArgumentException e) {				
				e.printStackTrace();
				throw new IllegalArgumentException(e);
			} catch (IllegalAccessException e) {				
				e.printStackTrace();
				throw new IllegalAccessException(e.getMessage() +" while accessing "+value);
			}
			
		}
		return true;
		
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(getCompId("dev"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
