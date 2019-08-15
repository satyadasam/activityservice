package com.  .activity.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidObjectException extends Exception {
	
	List<ValidationError> errors= null;
	Map<String,List<ValidationError>> errorMap=null ;
	
	public InvalidObjectException(List<ValidationError> errors){
		
		this.errors=errors;
	}
	
	public Map<String,List<ValidationError>> getErrorMessages(){
		
		  Map<String,List<ValidationError>> errorMap= new HashMap();
		
		  errorMap.put("errors", errors);
		  return errorMap;
	}
	
	
	@Override
	public String getMessage() {
		
	return	 errors.toString();
	}
	

}
