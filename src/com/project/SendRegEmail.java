package com.project;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Servlet implementation class SendRegEmail
 */
@WebServlet("/sendregemail")

public class SendRegEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Receiver's details
		String to = RegistrationStep1.email;
		//Sender's details
		//I have made a gmail account with following credentials for this project
		String from = "xyzbankcare@gmail.com";
		final String username = "xyzbankcare@gmail.com";
		final String password = "XYZ@147258369";
		
		Properties props = new Properties();
		
		String host = "smtp.gmail.com";
		
		props.put("mail.smtp.auth", "true");
		//we are using tls connection to send email
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// Get the Session object
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try {
			
			//create a MimeMessage object
			Message message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Successful Registration");
			message.setText("Dear " + RegistrationStep1.first_name + " " + RegistrationStep1.last_name
					+ ",\n\n" + "Thankyou for registering in Online Banking System of XYZ Bank.\n\n"
					+ "Do not share your login credentials with anyone. For any further queries, you can reach "
					+ "us via email at customerxyzbank@gmail.com.\n\n" + "Regards,\n" + "XYZ Bank"
					);
			
			Transport.send(message);
			
		} catch (MessagingException e) {
			
			throw new RuntimeException(e);
			
		}
		
		response.sendRedirect("index.html");
	}

}
