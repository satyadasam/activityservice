package com.  .activity.util;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.  .activity.model.AppResponse;
import com.  .activity.model.AppResponseMsg;

public class ControllerUtils {
	
	
	public static ResponseEntity<AppResponse> 	getResponse(String responseMsg, HttpStatus status ,Map<String,Object> data){
		AppResponse response = new AppResponse();
		AppResponseMsg appMessage= new AppResponseMsg(responseMsg);
		appMessage.setData(data);
		response.setResponseMsg(appMessage);		
		return new ResponseEntity<>(response,status);
	}
		
	public static ResponseEntity<AppResponse> getResponse(String responseMsg, String code, HttpStatus status){
		AppResponse response = new AppResponse();
		AppResponseMsg appMessage= new AppResponseMsg(code,responseMsg,null);
		response.setResponseMsg(appMessage);
		return new ResponseEntity<>(response,status);
	}

}
 