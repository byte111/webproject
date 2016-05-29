package com.deva.webproj.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.BlobMessage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class UploadMessageProducer implements Runnable{

	private byte b[] ;
	private String correlationId ;
	
	public UploadMessageProducer(byte b[],String correlationId)
	{
		this.b = b ; 
		this.correlationId = correlationId ; 
	}
	
	public void run() {
		// TODO Auto-generated method stub
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("TEST.FOO");
			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		//	String text = "Hello world from" + Thread.currentThread().getName()+" : " + this.hashCode();
			//TextMessage message = session.createTextMessage(text);
			BytesMessage bMessage = session.createBytesMessage();
		/*	InputStream in = new FileInputStream(new File("C:\\Temp\\messages\\send\\doc1.txt"));
			System.out.println("filePath in upload message is " + filePath);
			byte bytes[];
			BufferedInputStream bis = new BufferedInputStream(bytes);
			InputStream in = new FileInputStream(new File(filePath));
			BufferedInputStream buf= new BufferedInputStream(in);
			int i;
			while((i=buf.read())!=-1)
			{
				System.out.println("i="+i);
				bMessage.writeInt(i);
			}
			bMessage.writeInt(-1);
			*/
			
			bMessage.writeBytes(b);
			//Correlation id  = sender id + receiver id + milisec long
			bMessage.setJMSCorrelationID(correlationId);
			System.out.println("Sent message" + bMessage.hashCode() + " :" + Thread.currentThread().getName());

			producer.send(bMessage);
		//	buf.close();
			
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		//UploadMessageProducer sender = new UploadMessageProducer();
	//	DownloadMessageConsumer consumer = new DownloadMessageConsumer();
	//	Thread thread = new Thread(consumer);
	//	thread.start();
		
		
		
	}
	
	
	

}
