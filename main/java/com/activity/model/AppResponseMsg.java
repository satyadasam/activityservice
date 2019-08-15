package com.  .activity.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.  .activity.util.AppConstants;


/**
 * @author SSangapalli
 *
 */
@JsonIgnoreProperties
public class AppResponseMsg {
	

	public String code = AppConstants.GEN_PASS_CODE;

	public String message = AppConstants.GEN_PASS_MSG;
	
	public String nextViewPage;
	
	public Map<String,Object> data;

	public Map<String,Object> getData() {
		return data;
	}

	public void setData(Map<String,Object> data) {
		this.data = data;
	}

	public AppResponseMsg() {
		super();
	}

	public AppResponseMsg(String code, String message,String nextViewPage) {
		super();
		this.code = code;
		this.message = message;
		this.nextViewPage=nextViewPage;
	}

	public AppResponseMsg(String msgType) {
		super();
		switch (MSGTYPE.toMsgType(msgType)) {
		
		case PASS:
			this.code = AppConstants.GEN_PASS_CODE;
			this.message = AppConstants.GEN_PASS_MSG;
			break;
		case FAIL:
			this.code = AppConstants.GEN_ERR_CODE;
			this.message = AppConstants.GEN_ERR_MSG;
			break;
		case SUCCESS_RESPONSE:
			this.code = AppConstants.SUCCESS_RESPONSE_CODE;
			this.message = AppConstants.SUCCESS_RESPONSE_MSG;
			this.nextViewPage = AppConstants.SUCCESS_RESPONSE_VIEWNEXTPAGE;
			break;
		case UNANSWERED_QUESTIONS:
			this.code = AppConstants.ERROR_UNANSWERED_QUESTIONS_CODE;
			this.message = AppConstants.ERROR_UNANSWERED_QUESTIONS_MSG;
			this.nextViewPage = AppConstants.ERROR_UNANSWERED_QUESTIONS_VIEWNEXTPAGE;
			break;
		case CREDIT_ALREADY_EARNED :
			
			this.code = AppConstants.ERROR_CREDIT_EARNED_RESPONSE_CODE;
			this.message = AppConstants.ERROR_CREDIT_EARNED_RESPONSE_MSG;
			this.nextViewPage = AppConstants.ERROR_CREDIT_EARNED_RESPONSE_VIEWNEXTPAGE;	
			break;
		case MAX_ATTEMPTS_MET :		
			this.code = AppConstants.ERROR_MAX_ATTEMPTS_MET_RESPONSE_CODE;
			this.message = AppConstants.ERROR_MAX_ATTEMPTS_MET_RESPONSE_MSG;
			this.nextViewPage = AppConstants.ERROR_MAX_ATTEMPTS_MET_RESPONSE_VIEWNEXTPAGE;
			break;
			
		case ACTIVITY_RESULT_NOT_FOUND :
			this.code = AppConstants.ERROR_ACTIVITY_RESULT_NOT_FOUND_CODE;
			this.message = AppConstants.ERROR_ACTIVITY_RESULT_NOT_FOUND_MSG;
			this.nextViewPage = AppConstants.ERROR_ACTIVITY_RESULT_NOT_FOUND_VIEWNEXTPAGE;
			break;
		case ACTIVITY_EXPIRED :
			
			this.code = AppConstants.ERROR_ACTIVITY_EXPIRED_CODE;
			this.message = AppConstants.ERROR_ACTIVITY_EXPIRED;
			this.nextViewPage = AppConstants.ERROR_ACTIVITY_RESULT_NOT_FOUND_VIEWNEXTPAGE;
			break;
			
		default:{
				
				this.message=msgType;
				this.code="-50";
			}

		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	protected static enum MSGTYPE {		
		PASS, FAIL,SUCCESS_RESPONSE,UNANSWERED_QUESTIONS,CREDIT_ALREADY_EARNED,MAX_ATTEMPTS_MET,ACTIVITY_RESULT_NOT_FOUND,ACTIVITY_EXPIRED,DEFAULT;
		public static MSGTYPE toMsgType(String in) {
			return valueOf(in.toUpperCase());
		}
	}

}
