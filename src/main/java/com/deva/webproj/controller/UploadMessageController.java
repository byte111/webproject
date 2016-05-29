package com.deva.webproj.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.deva.webproj.constants.WebprojectConstants;
import com.deva.webproj.helper.JMSHelper;
import com.deva.webproj.jms.UploadMessageProducer;
import com.deva.webproj.service.DBService;
import com.deva.webproj.vo.UserMO;

@Controller
public class UploadMessageController {

	
	@RequestMapping(value="/UploadMessage", method = RequestMethod.POST)	
	public String returnUploadResult(MultipartHttpServletRequest request,@RequestParam("dataFile") MultipartFile file,@RequestParam("receiverList") String receiverList)
	{
		System.out.println("in UploadMessage receiverList =" +receiverList);
		String fileName="";
		MultipartFile filePart = file;
		
		UserMO userMO = null;
		if(request.getSession(false).getAttribute(WebprojectConstants.LOGGEDUSERINFO) != null)
		{
			userMO = (UserMO) request.getSession().getAttribute(WebprojectConstants.LOGGEDUSERINFO);
		}

		
		System.out.println("filepart = "+filePart.toString());
		 fileName = filePart.getOriginalFilename();
		 System.out.println("filepart = "+fileName);
		//File file1 = new File();
		System.out.println("fileName="+fileName);
		//return new ModelAndView("dashboard");  
		
		try {
			byte[] bytes = file.getBytes();
			
			/*System.out.println("===============================");
			for(int  i =0 ; i <bytes.length ; i++)
			{
				System.out.println((char)bytes[i]);
			}
			System.out.println("===============================");*/
			System.out.println("bytes = "+bytes);
			/*
			File writeFile = new File("c:\\test_file.txt");
			BufferedOutputStream stream  = new BufferedOutputStream(new FileOutputStream(writeFile));
			stream.write(bytes);
			stream.close();
			*/
			String correlationId = JMSHelper.createCorrelationId(userMO.getLoginId(), "BLRDEV1002");
			/*UploadMessageProducer uploadMessage = new UploadMessageProducer(bytes,correlationId);
			Thread thread = new Thread(uploadMessage);
			thread.start();*/
			
			
			File uploadFile = new File("C:\\Temp\\webprojdatastore\\"+correlationId);
			
			if(!uploadFile.exists())
			{
				uploadFile.createNewFile();
			}
			
			BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(uploadFile));
			bos.write(bytes);
			DBService dbService = new DBService();
			int result = dbService.insertDataUploadRecord("BLRDEV1002",correlationId,userMO.getLoginId());
			System.out.println("result = "+result);
			//insertDataUploadRecord
			bos.close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "updateResult";
		
	}
	
	public static void main(String[] args) throws IOException {
		File file = new File("C:/webprojdatastore/test");
		try {
			boolean  b = file.createNewFile();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(!file.exists())
		{
			boolean b = file.createNewFile();
		}
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(new Byte("helllo"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
