package com.deva.webproj.jms;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class DownloadMessageConsumer implements Runnable,MessageListener{

	private String correlationId ;
	public boolean  downloadComplete = false;
	public DownloadMessageConsumer(String correlationId)
	{
		this.correlationId = correlationId ;
	}
	public void run() {
		// TODO Auto-generated method stub
		ActiveMQConnectionFactory connectionfactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			Connection connection = connectionfactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination deatination = session.createQueue("TEST.FOO");
			MessageConsumer consumer = session.createConsumer(deatination);
			Message message = consumer.receive(1000);
			if(message instanceof TextMessage)
			{
				TextMessage textMessage  = (TextMessage) message;
				String text = textMessage.getText();
				System.out.println("Received:"+text);
			}
			
			else if(message instanceof BytesMessage)
			{
				if(correlationId != null && correlationId.equals(message.getJMSCorrelationID()))
				{
				
				BytesMessage bm = (BytesMessage) message;
				File file = new File("C:\\Temp\\messages\\receive\\doc1.txt");
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream buf = new BufferedOutputStream(fos);
				int i;
				while((i=bm.readInt())!=-1)
				{
					buf.write(i);
				}
				
				System.out.println("buf = "+buf);
				buf.close();
				fos.close();
				
				downloadComplete = true;
				}
				
			}
			
			consumer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		
	}

	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		String messageText = null;
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                messageText = textMessage.getText();
                System.out.println("messageText = " + messageText);
            }
        } catch (JMSException e) {
            //Handle the exception appropriately
        }
		
	}
	
	public void downloadMessage(String corrId)
	{
		// TODO Auto-generated method stub
				ActiveMQConnectionFactory connectionfactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
				try {
					Connection connection = connectionfactory.createConnection();
					connection.start();
					Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
					Destination deatination = session.createQueue("TEST.FOO");
					MessageConsumer consumer = session.createConsumer(deatination,"JMSCorrelationID='"+corrId+"'");
					Message message = consumer.receive(1000);
					if(message instanceof TextMessage)
					{
						TextMessage textMessage  = (TextMessage) message;
						String text = textMessage.getText();
						System.out.println("Received:"+text);
					}
					
					else if(message instanceof BytesMessage)
					{
						//if(correlationId != null && correlationId.equals(message.getJMSCorrelationID()))
						//{
						
						BytesMessage bm = (BytesMessage) message;
						File file = new File("C:\\Temp\\messages\\receive\\doc1.txt");
						FileOutputStream fos = new FileOutputStream(file);
						BufferedOutputStream buf = new BufferedOutputStream(fos);
						int i;
						while((i=bm.readInt())!=-1)
						{
							buf.write(i);
						}
						
						System.out.println("buf = "+buf);
						buf.close();
						fos.close();
						
						downloadComplete = true;
						}
						
					//}
					
					consumer.close();
					session.close();
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				 catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
	}
	
	
	public static void main(String[] args) {
		DownloadMessageConsumer downloader = new DownloadMessageConsumer("BLRDEV1001_BLRDEV1002_1463673579923");
		downloader.downloadMessage("BLRDEV1001_BLRDEV1002_1463673579923");
		int i = 0;
		while(downloader.downloadComplete != true)
		{
			
		}
	}

}
