package com.CSCI.constants;

public class SMTPConstants {
	
	public static final Integer PORT_NUMBER=15432;
	public static final Integer QUIT_CODE=221;
	public static final Integer HANDSHAKE_CODE=220;
	public static final Integer RCPT_CODE=250;
	public static final Integer DATA_CODE=354;
	
	
	public static final String QUIT="Bye";
	public static final String GREET="I am a CSCI 6220 SMTP server";
	public static final String RCPT="Ok";
	public static final String DATA="End data with <CR><LF>.<CR><LF>";
	public static final String SERVER_HOSTNAME_STRING="pg-01.gswcm.net";
	public static final Integer ERROR_CODE = 400;
	public static final String ERROR_STRING = "Invalid command";
    public static String SERVER_HOST;
	
	public static String getSERVER_HOST() {
		return SERVER_HOST;
	}
	public static void setSERVER_HOST(String sERVER_HOST) {
		SERVER_HOST = sERVER_HOST;
	}

}
