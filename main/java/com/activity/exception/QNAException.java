package com.  .activity.exception;


import java.io.PrintWriter;
import static com.  .activity.exception.ActivityExceptionsEnum.*;
import java.io.StringWriter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QNAException extends ActivityException {
	

	
	
	private static final long serialVersionUID = 1L;
	
	private String message=null;

	public QNAException()
	{
		super();
	}

	public QNAException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public QNAException(String message, Throwable cause)
	{
		super(message, cause);
		
	}

	public QNAException(String message)
	{
		super(message);
		message=message;
	}

	public QNAException(Throwable cause)
	{
		super(cause);
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
		return ActivityExceptionsEnum.QNASERVICEEXCEPTION;
	}
	


}
