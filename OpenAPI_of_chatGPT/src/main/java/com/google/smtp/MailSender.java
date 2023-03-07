package com.google.smtp;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	final private static String from="",
								host="smtp.gmail.com",
								SMTPCODE = "";
	private Properties properties;
	private Session session;
	private boolean enable = true;//use to avoid exception
	public MailSender() {
		//set properties for use smtp
		properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		if(SMTPCODE.equals("your code")) {
			System.out.println("你好!因為應用程式密碼屬於重要個資，所以我沒有附在程式碼中!\n若想執行程式請使用自己的金鑰!\n謝謝!");
			enable = false;
			return;
		}
		//connected and certified
		session = Session.getDefaultInstance(properties,new Authenticator(){
			public PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(from, SMTPCODE); 
			}
		});
	}
	/*
	public static void main(String[] args) {
		MailSender ms = new MailSender();
		String result =  ms.send(from, "Hello!", "This mail send by java through gmail smtp!");
		System.out.println(result);
	}
	*/
	public String send(String to,String subject,String text) {
		if(!enable) return "Didn't set smtp code!"; 
		if(to.equals("")) return "";
		try{
			//set message
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(text);
			//send
			Transport.send(message);
		}catch (MessagingException e) {
			e.printStackTrace();
			return e.toString();
		}
		return "Sent message successfully!";
	}
}
