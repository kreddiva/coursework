package com.CSCI.bo;

import java.util.HashSet;

public class EmailBO {
	
	 private String from;
	 private HashSet<String> to=new HashSet<String>();       
	 private String subject;	 
	 private String body;	
	private boolean dataFlag;
	 
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public HashSet<String> getTo() {
		return to;
	}
	public void setTo(HashSet<String> to) {
		this.to = to;
	}
	 public boolean isDataFlag() {
			return dataFlag;
		}
		public void setDataFlag(boolean dataFlag) {
			this.dataFlag = dataFlag;
		}
}
