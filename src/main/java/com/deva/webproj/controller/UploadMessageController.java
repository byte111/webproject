package com.deva.webproj.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.ws.rs.core.UriBuilder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.deva.webproj.constants.WebprojectConstants;
import com.deva.webproj.helper.JMSHelper;
import com.deva.webproj.service.DBService;
import com.deva.webproj.vo.UserMO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@Controller
public class UploadMessageController {

	
	@RequestMapping(value="/UploadMessage", method = RequestMethod.POST)	
	public String returnUploadResult(MultipartHttpServletRequest request,@RequestParam("dataFile") MultipartFile file,@RequestParam("receiverList") String receiverId)
	{
		System.out.println("in UploadMessage receiverList =" +receiverId);
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
		
		 System.out.println("fileName="+fileName);
	
		try {
			
			byte[] bytes = file.getBytes();
			
			String correlationId = JMSHelper.createCorrelationId(userMO.getLoginId(), receiverId);
		
			File uploadFile = new File("C:\\Temp\\webprojdatastore\\"+correlationId);
			
			if(!uploadFile.exists())
			{
				uploadFile.createNewFile();
			}
			
			BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(uploadFile));
			bos.write(bytes);
			DBService dbService = new DBService();
			
			int result = dbService.insertDataUploadRecord(receiverId,correlationId,userMO.getLoginId());
			
			if(result ==1 )
			{
				postBillEvent(userMO.getLoginId(),receiverId,fileName,filePart.getSize());
			}
			System.out.println("result = "+result);
			//insertDataUploadRecord
			bos.close();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "updateResult";
		
	}
	
	
	private boolean postBillEvent(String senderId,String receiverId,String filename,long fileSize) throws IOException
	{	
		
		String response = "false";
		
		try {
			
		Client client = new Client();
		
		filename = filename + System.currentTimeMillis()+".xml";
		
		String localFilePath = "C:\\Temp\\webprojdatastore\\"+filename;
		
		File billeventfile = new File(localFilePath);
		
		FileWriter fileWriter = new FileWriter(billeventfile);
		
		/*
		 * <BillEvent>
			<billingId></billingId>
			<senderId></senderId>
			<receiverId>110021</receiverId>
			<fileize></fileize>
			<timestamp></timestamp>
			</BillEvent>
		 * 
		 */
			
		
		fileWriter.write("<BillEvent>\n");
		fileWriter.write("<billingId>"+JMSHelper.getBillingId()+"</billingId>\n");
		fileWriter.write("<senderId>"+senderId+"</senderId>\n");
		fileWriter.write("<receiverId>"+receiverId+"</receiverId>\n");
		fileWriter.write("<fileize>"+fileSize+"</fileize>\n");
		fileWriter.write("<timestamp>"+System.currentTimeMillis()+"</timestamp>\n");
		fileWriter.write("</BillEvent>");
		
		fileWriter.flush();
		
		WebResource service = client.resource(UriBuilder.fromUri("http://localhost:8040/webprojreports-1.0.0.0/rest/bill/").build());
		
		System.out.println("Is DBConnector  REST component is up ?  " + service.path("testcomp").accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class));
		
		
		response = service.path("postBillEvnt").type(javax.ws.rs.core.MediaType.APPLICATION_XML).accept(javax.ws.rs.core.MediaType.TEXT_PLAIN).entity(
                new File(localFilePath)).post(String.class);
		
		
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new IOException();
		}

		 return (response == "true") ?  true :  false;
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
