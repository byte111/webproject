package com.deva.webproj.service;

import java.util.Date;
import java.util.List;

import com.deva.webproj.vo.DataUpload;
import com.deva.webproj.dao.DataUploadDAO;

public class DBService {

	public int insertDataUploadRecord(String receiverId,String correlationId,String senderId)
	{
		int result = 0;
		try {
			DataUpload dataUploadObj = new DataUpload();
			dataUploadObj.setCorrelationId(correlationId);
			dataUploadObj.setReceiverId(receiverId);
			dataUploadObj.setSenderId(senderId);
			dataUploadObj.setSentDateTime(new Date());
			result = new DataUploadDAO().insertDataUploadRecord(dataUploadObj);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		return result;
	}
	
	public List<DataUpload> getAllDataUploadedList(String receiverId)
	{
		List<DataUpload> list = new DataUploadDAO().getAllDataUploadedList(receiverId);
		return list;
	}
}
