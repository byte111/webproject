package com.deva.webproj.controller;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.deva.webproj.constants.WebprojectConstants;
import com.deva.webproj.jaxb.Partners;
import com.deva.webproj.jaxb.Tradpartners;
import com.deva.webproj.jaxb.UserProfileDetails;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Controller
public class TradingPartnerController 
{
		@RequestMapping(value = "/createtradpart", method = RequestMethod.GET)
		public Tradpartners createTradingPartner(Tradpartners tradpartner)
		{
			Tradpartners responsepart = null;
			try
			{
				
			tradpartner.getPartners().get(0).setPartnershipid(new Random().nextInt(100));
			ObjectMapper mapper = new ObjectMapper();	
			mapper.writeValue(new File(WebprojectConstants.SERIALIZATIONTEMPPATH.trim()+"_"+System.currentTimeMillis()), tradpartner);
			Client client = new Client();
			WebResource service = client.resource(UriBuilder.fromUri(WebprojectConstants.RESTURI.trim()+"tradpartners/createpartnership").build());
			ClientResponse response = service.type(javax.ws.rs.core.MediaType.APPLICATION_XML).accept(javax.ws.rs.core.MediaType.APPLICATION_XML).post(ClientResponse.class,tradpartner);
			System.out.println("response = " + response);
			
			
			if(response.getStatus() == HttpStatus.CREATED.value())
			{
				try 
				{
					responsepart = mapper.readValue(response.getEntityInputStream(), Tradpartners.class);
				//	mapper.readValue(response, Tradpartners.class);
				} catch (Exception e) 
				{
					
					e.printStackTrace();
					
					throw new IOException(e);
				}
				return responsepart;
			}
			
			else
			{
				System.out.println(" Response code returned from DBConnector  = " + response.getStatus());
				throw new Exception();
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
			}
			finally
			{}
			return responsepart;
			
			
			
		}
		
		public static void main(String[] args) {
			try
			{
			 Tradpartners tradpartobj = new Tradpartners();
			 Partners partobj = new Partners();
			 
			 UserProfileDetails userProfileDetails  = new UserProfileDetails();
			 userProfileDetails.setCompid("dev2");
			/// partobj.setPartnershipid(3);
			 partobj.setOwneruser(userProfileDetails);
			 //  = new UserProfileDetails();
			 userProfileDetails.setCompid("dev4");
			 partobj.setPartneruser(userProfileDetails);
			 partobj.setCharges("SEND");
			 
			 tradpartobj.getPartners().add(partobj);
			 
			 TradingPartnerController obj = new TradingPartnerController();
			 Tradpartners resp = obj.createTradingPartner(tradpartobj);
			 
			 System.out.println("resp = "+resp);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
}
