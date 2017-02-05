package com.deva.webproj.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserProfileVO {
	
	private String compname;
	private String compid;
	private String address;
	private int retention;
	
	@XmlElement
	
	private UserCredentialsMO usercreds;
	
	public void setUsercredentials(UserCredentialsMO usercreds)
	{
		this.usercreds = usercreds;
	}
	
	public UserCredentialsMO getUsercredentials()
	{
		return usercreds;
	}
	
	public String getCompname() {
		return compname;
	}
	
	public String getCompid() {
		return compid;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getRetention() {
		return retention;
	}
	
	public void setCompname(String compname) {
		this.compname = compname;
	}
	
	public void setCompid(String compid) {
		this.compid = compid;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setRetention(int retention) {
		this.retention = retention;
	}

	@Override
	public String toString()
	{
		System.out.println("["
									+ "compname=" + this.compname
									+ "compid=" + this.compid
									+ "address=" + this.address
									+ "retention=" + this.retention
									+"]");
		return address;
		
	}
}
