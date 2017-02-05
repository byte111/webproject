package com.deva.webproj.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.UriBuilder;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.UserDataHandler;

import com.deva.webproj.constants.WebprojectConstants;
import com.deva.webproj.exceptions.ValidationException;
import com.deva.webproj.helper.CommonHelper;
import com.deva.webproj.jaxb.Userprofile;
import com.deva.webproj.vo.UserMO;
import com.deva.webproj.vo.UserProfileVO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Controller
public class ProfileController {

	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public ModelMap viewProfile(@RequestParam("compId") String compId)
	{
		ModelMap modelvallist = new  ModelMap();
		if(compId == null || compId.trim().length() == 0)
		{
			modelvallist.addAttribute(WebprojectConstants.ERRMSG, "Company Id passed cannot be null");
			return modelvallist;
		}
			
		Userprofile userprof = null;
		try 
		{
			Client client = new Client();		
			WebResource service = client.resource(UriBuilder.fromUri(WebprojectConstants.RESTURI.trim()+"user/profile").build());		
			ClientResponse response = service.queryParam("compId", compId.trim()).accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			userprof = mapper.readValue(response.getEntityInputStream(), Userprofile.class);
			
			modelvallist.addAttribute(WebprojectConstants.SUCCESSMSG, "User profile retrieved successfully.");
			modelvallist.addAttribute(WebprojectConstants.RESULTVO, userprof);
			
			
		}
		catch (Exception e)
		
		{
			e.printStackTrace();
		}
		
		return modelvallist;
	}
	
	
	
	@RequestMapping(value="/displayprof", method = RequestMethod.GET)
	public ModelMap displayProf(HttpServletRequest  request)
	{
		ModelMap modelvallist = new  ModelMap();
		
		String compId = null,view=null;
		
		if(request.getSession().getAttribute(WebprojectConstants.LOGGEDUSERINFO) != null)
		{
			compId = ((UserMO)request.getSession().getAttribute(WebprojectConstants.LOGGEDUSERINFO)).getLoginId();
		}
		else
		{
			view="error";
			modelvallist.addAttribute(WebprojectConstants.ERRMSG, "Invalid session. Cannot load profile details.");
			return null;
		}
		
		if(compId == null || compId.trim().length() == 0)
		{
			modelvallist.addAttribute(WebprojectConstants.ERRMSG, "Company Id passed cannot be null");
			return modelvallist;
		}
			
		Userprofile userprof = null;
		try 
		{
			Client client = new Client();		
			WebResource service = client.resource(UriBuilder.fromUri(WebprojectConstants.RESTURI.trim()+"user/profile").build());		
			ClientResponse response = service.queryParam("compId", compId.trim()).accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			userprof = mapper.readValue(response.getEntityInputStream(), Userprofile.class);
			modelvallist.addAttribute(WebprojectConstants.SUCCESSMSG, "User profile retrieved successfully.");
			modelvallist.addAttribute(WebprojectConstants.RESULTVO, userprof);
			
			
		}
		catch (Exception e)
		
		{
			e.printStackTrace();
		}
		
		return modelvallist;
	}
	
	@RequestMapping(value="/createuser", method = RequestMethod.POST )
	public ModelAndView createProfile(@RequestParam("name") String name,@RequestParam("compid") String compid,@RequestParam("address") String address, HttpServletRequest request) throws Exception, ValidationException
	{
		ModelMap modelvallist = null;
		UserProfileVO userprofile = null;
		String tempfilename = null;
		Userprofile getprof = null;
		try 
		{
			modelvallist = new ModelMap();
			userprofile = new UserProfileVO();
			userprofile.setCompname(name.trim());
			userprofile.setCompid(compid.trim());
			userprofile.setAddress(address.trim());
			userprofile.setRetention(3);
			
			CommonHelper.validateCreateUser(userprofile);
			
			tempfilename = System.currentTimeMillis()+"";
			
			ObjectMapper mapper = new ObjectMapper();
			
			
			mapper.writeValue(new File(WebprojectConstants.SERIALIZATIONTEMPPATH.trim()+"_"+System.currentTimeMillis()), userprofile);
			
			Client client = new Client();
		
			
			WebResource service = client.resource(UriBuilder.fromUri("http://localhost:8041/DBConnector/rest/user/createprofile").build());
			ClientResponse response  = service.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(ClientResponse.class,userprofile);
			
			if(response.getStatus() == 200)
			{
		
				
			getprof = mapper.readValue(response.getEntityInputStream(), Userprofile.class);
			
			request.getSession().setAttribute(WebprojectConstants.LOGGEDUSERINFO, getprof);
			
			modelvallist.addAttribute(WebprojectConstants.SUCCESSMSG, "User profile created successfully.");
			modelvallist.addAttribute(WebprojectConstants.RESULTVO, getprof);
			modelvallist.addAttribute("defpwd",getprof.getuserCreds().getPassword().toLowerCase().trim());
			}
			else
			{
				return new ModelAndView("error", new ModelMap("errormsg", "Error in creating user details. Company id already exists."));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			
			return new ModelAndView("error", new ModelMap("errormsg", e));
			
		//	throw new Exception(e);
		} catch (ValidationException e) {			
			e.printStackTrace();
			
			return new ModelAndView("error", new ModelMap("errormsg", e));
			
		//	throw new ValidationException(e.toString());
		}
		
		return new ModelAndView("welcomepage", modelvallist);
		//return modelvallist;
	}
	
	
	@RequestMapping(value="/settings", method = RequestMethod.GET)
	public ModelAndView loadSettings()
	{
		return new ModelAndView("settings");
	}
	
	
	@RequestMapping(value="/updateprofile" , method = RequestMethod.PUT)
	public String updateUserData(HttpServletRequest request)
	{
		
		return null;
	}
	
	public static void main(String[] args)
	{
		Client client = new Client();
		WebResource service = client.resource(UriBuilder.fromUri(WebprojectConstants.RESTURI.trim()+"user/profile").build());		
		ClientResponse response = service.queryParam("compId", "dev").accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			Userprofile userprof = mapper.readValue(response.getEntityInputStream(), Userprofile.class);
			
			System.out.println(userprof.getuserCreds());
		} catch (JsonParseException e) {
			
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		//System.out.println("response="+response.getEntity(Userprofile.class).getCompid());
	}

}
