package com.deva.webproj.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.deva.webproj.constants.WebprojectConstants;
import com.deva.webproj.helper.JMSHelper;
import com.deva.webproj.jms.UploadMessageProducer;
import com.deva.webproj.service.DBService;
import com.deva.webproj.vo.UserMO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Controller
public class BasicController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String loginPage()
	{
		System.out.println("in loginPage ");
		//return new ModelAndView("welcomepage", "message", message);  
		return "welcomepage";
	}



	@RequestMapping(value = "/loadHomePage", method = RequestMethod.POST)
	public String loadHomePage(@RequestParam(value="loginId") String loginId,@RequestParam(value="password") String password, HttpServletRequest request) throws Exception
	{
		ModelMap attr = new ModelMap();
		
		try {
			
			if(validateLogin(loginId,password))
			{
				request.getSession().setAttribute(WebprojectConstants.LOGGEDUSERINFO, new UserMO(loginId, password));
				return "home";  
			}

			else
			{
				attr.addAttribute("loginmsg", "Invalid login credentials");
				return "welcomepage";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Exception in vallidating credentials from DBConnector REST."+e);	
		}
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String loadDashboard()
	{		
		return "dashboard";  
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request)
	{
		request.getSession().invalidate();
		return "welcomepage";  
	}



	private boolean validateLogin(String username,String password) throws Exception
	{
		String resp = null;
		try {

			Client client  = new Client();
			MultivaluedMap<String, String> params = new MultivaluedMapImpl();		   
			params.add("username", username);
			params.add("password", password);
			WebResource resource = client.resource(UriBuilder.fromUri("http://localhost:8040/webprojreports-1.0.0.0/rest/user/validatelogin").build());
			ClientResponse response = resource.queryParams(params).get(ClientResponse.class);
			resp = response.getEntity(String.class);
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new Exception(e);
		}

		return  ("TRUE".equalsIgnoreCase(resp)) ? true : false;
	}



	/*@RequestMapping(value="/UploadMessage", method = RequestMethod.POST)	
	public String returnUploadResult(MultipartHttpServletRequest request,@RequestParam("dataFile") MultipartFile file)
	{
		System.out.println("in UploadMessage");
		String fileName="";
		MultipartFile filePart = file;

		System.out.println("filepart = "+filePart.toString());
		 fileName = filePart.getOriginalFilename();
		 System.out.println("filepart = "+fileName);
		//File file1 = new File();
		System.out.println("fileName="+fileName);
		//return new ModelAndView("dashboard");  

		try {
			byte[] bytes = file.getBytes();

			System.out.println("===============================");
			for(int  i =0 ; i <bytes.length ; i++)
			{
				System.out.println((char)bytes[i]);
			}
			System.out.println("===============================");
			System.out.println("bytes = "+bytes);

			File writeFile = new File("c:\\test_file.txt");
			BufferedOutputStream stream  = new BufferedOutputStream(new FileOutputStream(writeFile));
			stream.write(bytes);
			stream.close();

			UploadMessageProducer uploadMessage = new UploadMessageProducer(bytes);
			Thread thread = new Thread(uploadMessage);
			thread.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "updateResult";

	}*/

	/*@RequestMapping(value="/UploadMessage", method = RequestMethod.POST)	
	public String returnUploadResult(MultipartHttpServletRequest request,@RequestParam("dataFile") MultipartFile file)
	{
		System.out.println("in UploadMessage");
		String fileName="";
		MultipartFile filePart = file;

		System.out.println("filepart = "+filePart.toString());
		 fileName = filePart.getOriginalFilename();
		 System.out.println("filepart = "+fileName);
		//File file1 = new File();
		System.out.println("fileName="+fileName);
		//return new ModelAndView("dashboard");  

		try {
			byte[] bytes = file.getBytes();

			System.out.println("===============================");
			for(int  i =0 ; i <bytes.length ; i++)
			{
				System.out.println((char)bytes[i]);
			}
			System.out.println("===============================");
			System.out.println("bytes = "+bytes);

			File writeFile = new File("c:\\test_file.txt");
			BufferedOutputStream stream  = new BufferedOutputStream(new FileOutputStream(writeFile));
			stream.write(bytes);
			stream.close();

			String correlationId = JMSHelper.createCorrelationId("BLRDEV1001", "BLRDEV1002");
			UploadMessageProducer uploadMessage = new UploadMessageProducer(bytes,correlationId);
			Thread thread = new Thread(uploadMessage);
			thread.start();
			DBService dbService = new DBService();
			int result = dbService.insertDataUploadRecord("BLRDEV1002",correlationId,"BLRDEV1001");

			//insertDataUploadRecord

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "updateResult";

	}
	 */
}
