package com.CSCI.util;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * This is a util class which parses the template passed by the emailservice and merges it with 
 * the dynaic values from HahMap and finally sends the email.
 * 
 * @author kreddiva
 * 
 */

public class EmailUtil {

	

	public static void sendEmail(final com.CSCI.bo.EmailBO emailBO,final JavaMailSender mailSender) {
      
       
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setFrom(emailBO.getFrom());
				//message.setTo(emailBO.getTo());
				message.setSubject(emailBO.getSubject());			
				message.setText(emailBO.getBody(), true);
				
			}
			
		};
		mailSender.send(preparator);
		
	}

}
