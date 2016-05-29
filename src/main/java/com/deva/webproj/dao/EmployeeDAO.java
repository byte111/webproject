package com.deva.webproj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.deva.webproj.vo.Employee;

public class EmployeeDAO {
	
	public JdbcTemplate jdbcTemplate;
	public EmployeeDAO() {
		jdbcTemplate = new JdbcTemplate(com.deva.webproj.dao.DataSource.getDataSource());
	}
	
	public int insertEmployeeDetails()
	{
		String sqlInsert = "INSERT INTO EMPLOYEE(Empid,empname,AGE) VALUES (?,?,?)";
		int result=0;
		try {
			result = jdbcTemplate.update(sqlInsert, "1003","Devashish","26");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void getEmployeeDetails()
	{
		String sqlSelect = "SELECT * FROM EMPLOYEE";
		RowMapper<Employee> rowMapper = new RowMapper<Employee>() {

			public Employee mapRow(ResultSet result, int rowNum) throws SQLException {
				Employee employee = new Employee();
				employee.setEmployeeId(result.getInt("Empid"));
				employee.setEmployeeName(result.getString("empname"));
				employee.setAge(result.getInt("AGE"));
				
				return employee;
			}
			
		};
		List<Employee> employeeList = jdbcTemplate.query(sqlSelect,rowMapper);
		for(Employee e: employeeList)
		{
			System.out.println(e.getAge()+":"+e.getEmployeeId()+":"+e.getEmployeeName());
		}
		
	}
	
	
	
	public static void main(String[] args) {
	//	System.out.println(new EmployeeDAO().insertEmployeeDetails());
		
		new EmployeeDAO().getEmployeeDetails();
	}
	
	

}
