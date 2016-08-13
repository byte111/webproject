package com.deva.webproj.controller;

import java.io.IOException;

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

import com.deva.webproj.constants.WebprojectConstants;
import com.deva.webproj.exceptions.ValidationException;
import com.deva.webproj.helper.CommonHelper;
import com.deva.webproj.jaxb.Userprofile;
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
	
	@RequestMapping(value="/createuser", method = RequestMethod.GET )
	public ModelMap createProfile(@FormParam("") Userprofile userprofile) throws Exception, ValidationException
	{
		ModelMap modelvallist = new ModelMap();
		try 
		{
			CommonHelper.validate(userprofile);
			
			Client client = new Client();
			WebResource service = client.resource(UriBuilder.fromUri(WebprojectConstants.RESTURI.trim()+"user/createprofile").build());
			ClientResponse response  = service.entity(userprofile).post(ClientResponse.class);
			ObjectMapper mapper = new ObjectMapper();
			Userprofile newprof = mapper.readValue(response.getEntityInputStream(), Userprofile.class);
			modelvallist.addAttribute(WebprojectConstants.SUCCESSMSG, "User profile created successfully.");
			modelvallist.addAttribute(WebprojectConstants.RESULTVO, newprof);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (ValidationException e) {			
			e.printStackTrace();
			throw new ValidationException();
		}
		return modelvallist;
	}
	
	public static void main(String[] args)
	
	{
		Client client = new Client();
		WebResource service = client.resource(UriBuilder.fromUri(WebprojectConstants.RESTURI.trim()+"user/profile").build());		
		ClientResponse response = service.queryParam("compId", "dev").accept(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(ClientResponse.class);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			Userprofile userprof = mapper.readValue(response.getEntityInputStream(), Userprofile.class);
			
			System.out.println(userprof.getAddress());
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("response="+response.getEntity(Userprofile.class).getCompid());
	}

}
