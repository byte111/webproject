package com.deva.webproj.dao;

import java.text.SimpleDateFormat;

import org.springframework.jdbc.datasource.SimpleDriverDataSource;

public class DataSource {


	public static final SimpleDriverDataSource  getDataSource()
	{
		SimpleDriverDataSource datasource = new SimpleDriverDataSource();
		try {			
			datasource.setDriver(new oracle.jdbc.driver.OracleDriver());
			datasource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			datasource.setUsername("SYSTEM");
			datasource.setPassword("dev");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return datasource;
	}
	public static void main(String[] args) {
		System.out.println(DataSource.getDataSource());
	}
}
