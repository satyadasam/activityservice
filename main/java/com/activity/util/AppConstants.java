package com.  .activity.util;


public class AppConstants {
	
	public final static String ERROR_UNANSWERED_QUESTIONS_CODE="-10";
	public final static String ERROR_CREDIT_EARNED_RESPONSE_CODE="-20";
	public final static String ERROR_MAX_ATTEMPTS_MET_RESPONSE_CODE="-30";
	public final static String ERROR_ACTIVITY_RESULT_NOT_FOUND_CODE="-40";
	public final static String ERROR_ACTIVITY_EXPIRED_CODE="-60";
	public final static String  GEN_ERR_CODE="-50";
	
	public final static String  GEN_PASS_CODE="0";
	public static String  GEN_PASS_MSG="Susses";	
	public static String  GEN_ERR_MSG="Please contact Admin";
	public static String SUFFIX_REQUESTPROCESSOR="formprocessor";	
	public static String SUCCESS_RESPONSE="SUCCESS_RESPONSE";
	public static String UNANSWERED_QUESTIONS="UNANSWERED_QUESTIONS";
	public static String ERROR_CREDIT_EARNED_RESPONSE="CREDIT_ALREADY_EARNED";
	public static String ERROR_MAX_ATTEMPTS_MET_RESPONSE="MAX_ATTEMPTS_MET";
	public static String ERROR_ACTIVITY_RESULT_NOT_FOUND="ACTIVITY_RESULT_NOT_FOUND";
	public static String ERROR_ACTIVITY_EXPIRED="ACTIVITY_EXPIRED";
	public static String USER_AGENT="User-Agent";
	public static final int ACTIVITY_RESULT_FAIL	= 0;	//Activity Result
	public static final int ACTIVITY_RESULT_SUCCEED	= 1;
	public static final String ACTIVITY_TITLE	= "activityTitle";	
	public static final String ACTIVITY_MAX_ATTEMPT	= "activityMaxAttempt";
	
	public static String ERROR_UNANSWERED_QUESTIONS_MSG="UNANSWERED_QUESTIONS";
	public static String ERROR_UNANSWERED_QUESTIONS_VIEWNEXTPAGE="UNANSWERED_QUESTIONS";
	

	public static String SUCCESS_RESPONSE_MSG="SUCCESS";
	public static String SUCCESS_RESPONSE_VIEWNEXTPAGE="";
	public static final String  SUCCESS_RESPONSE_CODE="1";
	
	public static String ERROR_CREDIT_EARNED_RESPONSE_MSG="CREDIT_ALREADY_EARNED";
	public static String ERROR_CREDIT_EARNED_RESPONSE_VIEWNEXTPAGE="CREDIT_ALREADY_EARNED";

	
	public static String ERROR_MAX_ATTEMPTS_MET_RESPONSE_MSG="MAX_ATTEMPTS_MET";
	public static String ERROR_MAX_ATTEMPTS_MET_RESPONSE_VIEWNEXTPAGE="MAX_ATTEMPTS_MET";

	
	public static String ERROR_ACTIVITY_RESULT_NOT_FOUND_MSG="ACTIVITY_RESULT_NOT_FOUND";
	public static String ERROR_ACTIVITY_RESULT_NOT_FOUND_VIEWNEXTPAGE="";
	

	public static String ERROR_ACTIVITY_RESULT_UPDATE="ACTIVITY_RESULT_UPDATE_FAILED";
	public static String ERROR_ACTIVITY_RESULT_UPDATE_VIEWNEXTPAGE="";
	public static String ERROR_ACTIVITY_RESULT_UPDATE_CODE="0";


	
	public static final String ACTIVITY_RESULT_FIELD_EVAL_COMPLETE	= "EVAL_COMPLETE";
	public static final String ACTIVITY_RESULT_FIELD_CREDITS_EARNED	= "CREDITS_EARNED";	
	public static final int ACTIVITY_RESULT_EVALUATION_COMPLETE	= 1;
	public static final int ACTIVITY_RESULT_EVALUATION_INCOMPLETE		= 1;
	public static final int PHYSICIAN_CME = 1;
	public static final int NURSE_CE = 2;
	public static final int PHARMACIST_CE = 3;
	public static final String PSYCHOLOGIST_CE = "4";
	public static final String MEDICAL_LABORATOR_CE = "5";
	public static final int LOC_CREDIT_TYPE = 6;
	public static final int NURSE_PRACTITIONER_NP = 7;
	public static final int PHYSICIAN_ASSISTANT_PA = 8;
	
	public static final int PROFESSION_PHARMACIST=8;
	public static final int PROFESSION_PHYSICIAN=10;
	public static final int PROFESSION_PHYSICIAN_ASSISTANT=11;
	public static final int PROFESSION_NURSE=12;
	public static final int PROFESSION_MEDICAL_LABORATOR=13;
	public static final int PROFESSION_PSYCHOLOGIST=23;
	
	public static final String SITE_NAME_MEDSCAPE_WWW = "Medscape-www";   	//  2001=Medscape-www,  2003=Medscape-CME
	public static final int SITE_ID_MEDSCAPE_WWW = 2001;					//  2001=Medscape-www,  2003=Medscape-CME
	public static final int SITE_ID_FOR_UNKNOWN_SITE_NAME = -1;  

	
	/*public static final FormSubmitResponse ERROR_VALIDATION_RESPONSE = new FormSubmitResponse(0, VALIDATION);
	public static final FormSubmitResponse ERROR_SAVE_RESPONSE = new FormSubmitResponse(0, SAVE);
	public static final FormSubmitResponse ERROR_CREDIT_ALREADY_EARNED_RESPONSE = new FormSubmitResponse(0, CREDIT_ALREADY_EARNED);
	public static final FormSubmitResponse ERROR_MAX_ATTEMPTS_MET_RESPONSE = new FormSubmitResponse(0, MAX_ATTEMPTS_MET);
	public static final FormSubmitResponse ERROR_DATA_RESPONSE = new FormSubmitResponse(0, DATA)*/


}
