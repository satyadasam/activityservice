package com.  .activity.exception;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import static com.  .activity.exception.ActivityExceptionsEnum.*;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProcessorException extends ActivityException {
	
	
	private static final long serialVersionUID = 1L;
	
	private String message=null;

	public ProcessorException()
	{
		super();
	}

	public ProcessorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProcessorException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ProcessorException(String message)
	{
		super(message);
		message=message;
	}

	public ProcessorException(Throwable cause)
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
	public  ActivityExceptionsEnum getType() {
		// TODO Auto-generated method stub
		return PROCESSOREXCEPTION;
	}
	
	

}
