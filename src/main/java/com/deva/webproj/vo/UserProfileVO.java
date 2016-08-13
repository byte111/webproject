package com.deva.webproj.vo;

public class UserProfileVO {
	String compname;
	String compid;
	String address;
	int retention;
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


}
