package com.deva.webproj.vo;

public class UserMO {
	private String loginId;
	private String password;
	public void setLoginId(String loginId)
	{
		this.loginId = loginId;
	}
	public void setPassword(String password)
	{
		this.password = password;	
	}
	public String getLoginId()
	{
		return loginId;
	}
	public String getPassword()
	{
		return password;
	}
	
	public UserMO(String loginId,String password)
	{
		this.loginId = loginId;
		this.password = password;
	}
}
