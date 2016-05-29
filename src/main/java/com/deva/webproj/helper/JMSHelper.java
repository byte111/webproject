package com.deva.webproj.helper;

import java.util.Date;

public class JMSHelper {
	public static String createCorrelationId(String senderId,String receiverId)
	{
		return senderId+"_"+receiverId+"_"+new Date().getTime();
	}
}
