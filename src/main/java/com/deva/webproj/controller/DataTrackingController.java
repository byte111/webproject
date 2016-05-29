package com.deva.webproj.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.deva.webproj.constants.WebprojectConstants;
import com.deva.webproj.jms.DownloadMessageConsumer;
import com.deva.webproj.service.DBService;
import com.deva.webproj.vo.DataUpload;
import com.deva.webproj.vo.UserMO;

@Controller
public class DataTrackingController {


	@RequestMapping(value="/datatrack", method = RequestMethod.GET)	
	public ModelAndView listAllInvoice(HttpServletRequest request)
	{
		ModelMap modeldata = new ModelMap();
		List<DataUpload> listDataupload = new LinkedList<DataUpload>();
		
		UserMO userMO = null;
		if(request.getSession(false).getAttribute(WebprojectConstants.LOGGEDUSERINFO) != null)
		{
			userMO = (UserMO) request.getSession().getAttribute(WebprojectConstants.LOGGEDUSERINFO);
		}

		/*if(request.getSession(false).getAttribute(WebprojectConstants.LOGGEDUSERINFO) != null)
		{
			listDataupload = new DBService().getAllDataUploadedList((String)request.getSession(false).getAttribute("loggedinUserID"));
		}
		else
		{
			listDataupload = new DBService().getAllDataUploadedList("BLRDEV1002");
		}
		*/
		listDataupload = new DBService().getAllDataUploadedList(userMO.getLoginId());
	
		//request.setAttribute("listDataupload", listDataupload);


		modeldata.addAttribute("listDataupload", listDataupload);


		//return "datatrack";
		return new ModelAndView("datatrack", modeldata);
	}

	@RequestMapping(value="/dataDownload", method = RequestMethod.POST)
	public void downloadData(@RequestParam(value="correlationId",required=false) String corrId,HttpServletRequest request,HttpServletResponse response) throws IOException
	{

		/*DownloadMessageConsumer downloader = new DownloadMessageConsumer(corrId);
		Thread t1 = new Thread(downloader);
		if(downloader.downloadComplete == false)
		{
			t1.start();
		}				
		 */
		UserMO userMO = null;
		if(request.getSession(false).getAttribute(WebprojectConstants.LOGGEDUSERINFO) != null)
		{
			userMO = (UserMO) request.getSession().getAttribute(WebprojectConstants.LOGGEDUSERINFO);
		}
	//	corrId = "BLRDEV1001_BLRDEV1002_1463680647213";
		System.out.println("in dataDownload handler");
		FileInputStream instream = null;
		OutputStream outputstream = null;
		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", corrId);
		response.setHeader(headerKey, headerValue);
		try {
			outputstream = response.getOutputStream();

			File downloadFile = new File("C:\\Temp\\webprojdatastore\\"+corrId);


			instream = new FileInputStream(downloadFile);
			byte buffer[] = new byte[4096];
			int bytesRead = -1;

			while( (bytesRead = instream.read(buffer)) != -1)
			{			
				outputstream.write(buffer, 0, bytesRead);
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			instream.close();
			outputstream.flush();
			outputstream.close();
		}

	}
}
