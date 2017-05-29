package com.CSCI.server;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;
import com.CSCI.bo.EmailBO;
import com.CSCI.constants.SMTPConstants;

/* 
 * SMTPServer listens for incoming connections on a dedicated port and starts ServerThreads to handle those connections
 *
 */
public class SMTPServer {

	final static Logger logger = Logger.getLogger(SMTPServer.class);

	public static void main(String[] args) {
		
		/*ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");    	 
    	EmailService emailService = (EmailService) context.getBean("emailService");
    	EmailBO emailBO=new EmailBO();
    	emailService.sendEmail(emailBO);*/
		AtomicInteger num_threads = new AtomicInteger(0);		
		ArrayList<Thread> threadBucket = new ArrayList<Thread>();
		EmailBO emailBO=new EmailBO();
		
		try {			
			// listen for incoming connections on port 15432
			ServerSocket socket = new ServerSocket(SMTPConstants.PORT_NUMBER);
			String serverHost=socket.getInetAddress().getHostName();
			SMTPConstants.setSERVER_HOST(serverHost);			
			logger.info("Server listening on port:"+SMTPConstants.PORT_NUMBER);
			
			while(true) {
				/* accept a new connection */
				Socket client_socket = socket.accept();	
				client_socket.setKeepAlive(true);
				PrintWriter response_to_client = new PrintWriter(client_socket.getOutputStream(), true);
				response_to_client.println(SMTPConstants.HANDSHAKE_CODE+" "+SMTPConstants.GREET);
				/* start a new ServerThread to handle the connection and send
				output to the client */
				Thread thread = new Thread(new SMTPThread(client_socket,emailBO));
				threadBucket.add(thread);
				thread.start();
				//num_threads.incrementAndGet();				
				logger.info( "Thread " + num_threads.get() + " started.");
				System.out.println( "Thread " + num_threads.get() + " started.");
			}
		}
		catch (IOException ex){
			System.out.println("IO EXCEPTION");
			logger.info(ex);
		}
	}
}

