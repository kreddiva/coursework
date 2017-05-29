
package com.CSCI.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import com.CSCI.bo.EmailBO;
import com.CSCI.service.EmailService;
import com.CSCI.util.EmailUtil;

public class EmailServiceImpl  implements EmailService{
	    
@Autowired
private JavaMailSender mailSender;
	  
    public void sendEmail(EmailBO emailBO)
    {
    	EmailUtil.sendEmail(emailBO, mailSender);
    }

}
