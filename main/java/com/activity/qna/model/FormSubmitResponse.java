package com.  .activity.qna.model;

public class FormSubmitResponse {
	public static final String SUCCESS = "SUCCESS";
	public static final String UNANSWERED_QUESTIONS = "UNANSWERED_QUESTIONS";
	public static final String VALIDATION = "VALIDATION";
	public static final String DATA = "DATA";
	public static final String SAVE = "SAVE";
	public static final String CREDIT_ALREADY_EARNED = "CREDIT_ALREADY_EARNED";
	public static final String MAX_ATTEMPTS_MET = "MAX_ATTEMPTS_MET";
	
	public static final FormSubmitResponse SUCCESS_RESPONSE = new FormSubmitResponse(1, SUCCESS);
	public static final FormSubmitResponse ERROR_UNANSWERED_QUESTIONS_RESPONSE = new FormSubmitResponse(0, UNANSWERED_QUESTIONS);
	public static final FormSubmitResponse ERROR_VALIDATION_RESPONSE = new FormSubmitResponse(0, VALIDATION);
	public static final FormSubmitResponse ERROR_SAVE_RESPONSE = new FormSubmitResponse(0, SAVE);
	public static final FormSubmitResponse ERROR_CREDIT_ALREADY_EARNED_RESPONSE = new FormSubmitResponse(0, CREDIT_ALREADY_EARNED);
	public static final FormSubmitResponse ERROR_MAX_ATTEMPTS_MET_RESPONSE = new FormSubmitResponse(0, MAX_ATTEMPTS_MET);
	public static final FormSubmitResponse ERROR_DATA_RESPONSE = new FormSubmitResponse(0, DATA);
	
	public FormSubmitResponse (int status, String code) {
		setStatus(status);
		setCode(code);
	}
	private int status;
	private String code;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
