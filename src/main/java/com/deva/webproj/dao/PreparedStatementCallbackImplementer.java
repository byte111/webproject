package com.deva.webproj.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;

public class PreparedStatementCallbackImplementer implements PreparedStatementCallback<Integer>{

	private Object obj = null;
	public PreparedStatementCallbackImplementer(Object obj)
	{
		this.obj = obj;
	}
	
	public Integer doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
		
		
		return 0;
	}
	

}
