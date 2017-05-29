package com.CSCI.fsm;

public enum FSMEventEnum {

	CONNECT("CONNECT"), 	
	QUIT("QUIT"),	
	HELO("HELO"),	
	RCPT("RCPT"),	
	DATA("DATA"),	
	MAIL("MAIL"),	
	;

	private final String eventName;

	private FSMEventEnum(String eventName) {
		this.eventName = eventName;
	}
	
	public String getEventName() {		
		return eventName;
	}
	
	public static String getNamesAsCsv(){
		StringBuilder sb = new StringBuilder();
		for (FSMEventEnum e : FSMEventEnum.values()) {
			sb.append(e.name());
			sb.append(",");
		}
		return sb.substring(0,sb.length()-2);
	}
	
}
