package com.CSCI.fsm;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import com.CSCI.bo.EmailBO;
import com.CSCI.constants.SMTPConstants;
import com.CSCI.server.SMTPServer;


public class FSM {

	private static final Class[] SIGNATURE = new Class[0];
	 private static final Object[] PARAMETERS = new Object[0];
	PrintWriter response_to_client;
	private Socket client_socket;
	private EmailBO emailBO;
	final static Logger logger = Logger.getLogger(SMTPServer.class);
	static String command=null;
	
	
	public FSM(PrintWriter response_to_client,Socket client_socket,EmailBO emailBO) {    	
    	this.response_to_client=response_to_client;
    	this.client_socket= client_socket;
    	this.emailBO= emailBO;
}
	
	public void firePreDefinedEvent(String comd){
		this.command=comd;
		try{
		FSMEventEnum fsmEventEnum=FSMEventEnum.valueOf(comd.substring(0,4));
		this.invoke(fsmEventEnum.toString().toLowerCase());
		}catch(IllegalArgumentException ex){
			this.invoke("message");
		}
		
	}
	 public boolean invoke(final String methodName) {
	        Class clas = this.getClass();
	        try {
	            Method method = clas.getDeclaredMethod(methodName, SIGNATURE);
	            method.invoke(this, PARAMETERS);
	        } catch (SecurityException se) {
	        	logger.error(se);
	            return false;
	        } catch (NoSuchMethodException nsme) {
	        	logger.error(nsme);
	            return false;
	        } catch (IllegalArgumentException iae) {
	        	logger.error(iae);
	            return false;
	        } catch (IllegalAccessException iae) {
	        	logger.error(iae);
	            return false;
	        } catch (InvocationTargetException ite) {
	        	logger.error(ite);
	            return false;
	        }
	        return true;
	    }
	
	/* ------------------------------------------------------------------------ */
	// STATES
	// 
	// Each method below is the activity corresponding to a state in the
	// SCXML document (see class constructor for pointer to the document).
	/* ------------------------------------------------------------------------ */
	
	
	 public void mail() {			
			 if(command!=""){
				    Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(command);
				    while (m.find()) {
				        emailBO.setFrom((m.group()));
				    }
			 } 
			reply(SMTPConstants.RCPT_CODE+" "+SMTPConstants.RCPT);
		 }
	 
	 public void quit() {		
		reply(SMTPConstants.QUIT_CODE+" "+SMTPConstants.QUIT);
		 try {
				client_socket.close();
			}
			catch (IOException e) {
				logger.info(e.getCause());	
			}
	 }
	 
	 public void helo() {
		
		 reply(SMTPConstants.RCPT_CODE+" "+SMTPConstants.getSERVER_HOST());	
		
	 }
	 
	 public void rcpt() {		
		
		 if(command!=""){
			    Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(command);
			    while (m.find()) {
			    	 emailBO.getTo().add((m.group()));
			    }
		    } 		       
		  reply(SMTPConstants.RCPT_CODE+" "+SMTPConstants.RCPT);
	 } 
	 public void message() {				
		 if(emailBO.isDataFlag()){			   
			  emailBO.setBody(command);	
			  reply(SMTPConstants.RCPT_CODE+" "+SMTPConstants.RCPT);
		    } 	else{
		    	 reply(SMTPConstants.ERROR_CODE+" "+SMTPConstants.ERROR_STRING);
		    }	       
		
	 } 
	 
	 public void data() {		
		 emailBO.setDataFlag(true);
		 reply(SMTPConstants.DATA+" "+SMTPConstants.DATA);
		 
	 }
	 
	 private void reply (String command){
			logger.info("Server :"+command);
			if (!client_socket.isClosed()){
				response_to_client.println(command);
			}		
			return;
		}
}
