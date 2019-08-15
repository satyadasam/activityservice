package com.  .activity.exception;

import com.  .activity.qna.exception.dataaccess.QNADataAccessException;

public class PopulateExceptionHandler {
	
	
	public static PopulateExceptionHandler getIntance(){
		
		
		return new PopulateExceptionHandler();
	}
	
	
	public void handleExceptons(Exception ex) throws QNADataAccessException, ProcessorException{
		
		
		if(ex instanceof AdapterException){
			
			throw (AdapterException)ex;
		}
		else if( ex instanceof QNADataAccessException){
			
			throw new QNADataAccessException("Unable to save or retrives from activity tables");
		}
		else {
			
			throw new ProcessorException("Exception while processing request");
		}
		
	}
	

}
