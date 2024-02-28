package com.Flipkart.Flipkart.response;


public class MessageResponse {
	
	private Object result;
    private String message;
	public MessageResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public MessageResponse(Object result, String message) {
		super();
		this.result = result;
		this.message = message;
	}

	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
