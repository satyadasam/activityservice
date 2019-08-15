package com.  .activity.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnAuthorizedUserExcepton extends Exception {
	
	
public UnAuthorizedUserExcepton(String msg){
	
	super(msg);
}

 @Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

}
