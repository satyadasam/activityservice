package com.  .activity.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.  .activity.model.AppResponse;
import com.  .activity.util.ControllerUtils;

public enum ActivityExceptionsEnum {
	
	
	PROFILEEXCEPTIONTYPE("-100","PROFILE_SERVICE_ERROR"),
	PROCESSOREXCEPTION("-101","INTERNAL_SERVER_EXCEPTION"  ),
	ACTIVITYEXCEPTION("-102,","ACTIVIY_NOT_SAVED"),
	QNADATAACESSEXCEPTION("-104,","DATA_ACCESS_EXCEPTION"),
	QNASERVICEEXCEPTION("-105","QNA_SERVICE_EXCEPTION"),
	ADAPTOREXCEPTION("-106","EXTERNAL_SERVICE_EXCEPTION"),
	INVALIDOBJECTEXCEPTION("-107","INPUT DATA NOT VALID"),
	ILLEGALARGUMENTEXCEPTION("-108","ARGUMENTS OR NOT VALID"),
	IOEXCEPTION("-109","IOEXCEPTION"),
	UNAUTHORIZEDUSEREXCEPTION("-110","USER_NOT_AUTHORIZED"),
	METHODARGUMENTNOTVALIDEXCEPTION("-111","MANDATORY_FIELDS_MISSING"),
	HTTPMESSAGENOTREADABLEEXCEPTION("-112","JSON_REQUEST_MESSAGE_FORMAT_ERROR"),
	UNMARSHALEXCEPTION("-113","UNABLE_TO_CONVERT_JSON_RESPONSE");
	
	
	
	private String errorCode;
	
	 private  String errorMsg;
	
	
	private ActivityExceptionsEnum(String code, String message){
	
		errorCode=code;
		errorMsg=message;
	}
	
	 public String getErrorCode() {
			return errorCode;
		}


		public String getErrorMsg() {
			return errorMsg;
		}




}
