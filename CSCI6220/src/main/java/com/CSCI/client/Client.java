package com.CSCI.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client {
	public static void main(String[] args) {


	    String host = "localhost";
	    int port = 15432;

	    try {
	     
	      Socket socket = new Socket(host, port);	    
	      // Get the socket's input stream and
	      // decorate it with a decoder and a buffer
	      BufferedReader br = new BufferedReader(
	                            new InputStreamReader(
	                             socket.getInputStream()
	                             )
	                          );

	      // Get the server's response (date and time)
	      String line;
	      while ((line = br.readLine()) != null) {
	        System.out.println("S:\t"+line);
	        if(!(line.substring(0,4).equals("221"))){
	        	  DataOutputStream os = new DataOutputStream(socket.getOutputStream());
	        	  String command=getCommand();
	              os.writeBytes(command);	        	
	        }else{
	        	socket.close();
	        }
	      }
	      // Close streams and the socket
	      br.close();
	      socket.close();
	    } catch (UnknownHostException exc) {
	        System.out.println("Unknown host: " + host);
	    } catch (Exception exc) {
	         exc.printStackTrace();
	    }
	  
	}        
	public static String getCommand(){
		 Scanner scanner = new Scanner(System.in);
	      System.out.print("C:\t");
	      String sentence = scanner.nextLine();
		return sentence+"\n";
	}
}