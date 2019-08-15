package com.  .activity.qna.moc.exception;

@SuppressWarnings("serial")
public class MocException extends Throwable{
	private String message;
	protected String description;
	protected Exception exception;
	
	public MocException(){}
	
	public MocException(String message){
		this.message = message;
	}
	
	public MocException(String message, Exception e){
		this.message = message;
		this.exception = e;
	}
	
	public MocException(String message, String description, Exception e){
		this.message = message;
		this.description = description;
		this.exception = e;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	
	
}
