package fr.iutinfo.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;

public class MailSender {

	public static void sendMessageTest() {    
		
		System.getProperties().setProperty("proxySet","true");
		System.getProperties().put("socksProxyHost", "cache.univ-lille1.fr");
		System.getProperties().put("socksProxyPort", "3128");
		
		// Recipient's email ID needs to be mentioned.
		String to = "dashingames@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "dashingames@gmail.com";

		// Assuming you are sending email from localhost
		String host = "smtp.free.fr";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is actual message");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}