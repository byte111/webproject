package com.deva.webproj.vo;

public class DataUpload 
{
	private String correlationId;
	private String senderId;
	private String receiverId;
	private java.util.Date sentDateTime;
	public String getCorrelationId() {
		return correlationId;
	}
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public java.util.Date getSentDateTime() {
		return sentDateTime;
	}
	public void setSentDateTime(java.util.Date sentDateTime) {
		this.sentDateTime = sentDateTime;
	}



}
