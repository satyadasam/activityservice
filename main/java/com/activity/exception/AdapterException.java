package com.  .activity.exception;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
public class AdapterException extends  RuntimeException {

    private HttpStatus statusCode;
    private String body;

    public AdapterException(String msg) {
        super(msg);
    }

    public AdapterException(HttpStatus statusCode, String body, String msg) {
        super(msg);
        this.statusCode = statusCode;
        this.body = body;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }
    
    public ActivityExceptionsEnum getType(){
    	
    	return ActivityExceptionsEnum.ADAPTOREXCEPTION;
    }
}
