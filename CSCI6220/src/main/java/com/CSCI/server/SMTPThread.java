package com.CSCI.server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.CSCI.bo.EmailBO;
import com.CSCI.constants.SMTPConstants;
import com.CSCI.fsm.FSM;
import com.CSCI.fsm.FSMEventEnum;

/*
 * Individual ServerThread listens for the client to tell it what command to run, then
 * runs that command and sends the output of that command to the client
 *
 */
public class SMTPThread extends Thread {
	Socket client_socket = null;
	PrintWriter response_to_client;
	BufferedReader client_input_command;		
	private FSM fsm=null;
	EmailBO emailBO=null;
	final static Logger logger = Logger.getLogger(SMTPServer.class);
	
	public SMTPThread(Socket client_socket,EmailBO emailBO) {
		this.client_socket = client_socket;	
		this.emailBO=emailBO;		
	}
	
	public void run() {
		logger.info("Accepted client connection...In thread");
		try {			
			response_to_client = new PrintWriter(client_socket.getOutputStream(), true);
			client_input_command = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
			fsm=new FSM(response_to_client,client_socket,emailBO);			
			logger.info("Reader and writer created. ");			
			
			String command;			
		    while  ((command = client_input_command.readLine()) == null);	
		    logger.info("Client  :"+command);
			
			if (legalCommand(command)) {
				fsm.firePreDefinedEvent(command);				
			}		
			
		}
		catch (IOException e) {
			logger.info(e.getCause());
		} 
	}
	public boolean legalCommand(String command){
		if(command==null||command==""){
			return false;
		}else{
			return true;
		}		
	}
}

