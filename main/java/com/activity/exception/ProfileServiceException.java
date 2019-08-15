package com.  .activity.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ProfileServiceException extends ActivityException {

	
	public ProfileServiceException()
	{
		super();
	}

	public ProfileServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProfileServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ProfileServiceException(String message)
	{
		super(message);
		message=message;
	}

	public ProfileServiceException(Throwable cause)
	{
		super(cause);
	}
	
	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}
	
	public String getPrintStackTrace(){
		StringWriter writer= new StringWriter();
		PrintWriter printWriter= new PrintWriter(writer);		
		this.printStackTrace(printWriter);
		return writer.toString();
	}
	
	@Override
	public ActivityExceptionsEnum getType() {
		// TODO Auto-generated method stub
		return ActivityExceptionsEnum.PROFILEEXCEPTIONTYPE;
	}

}
