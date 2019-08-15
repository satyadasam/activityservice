package com.  .activity.model;

import com.  .activity.util.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by thatikonda on 10/24/16.
 */
public class LegacyResponse {

    public static final String SUCCESS = "SUCCESS";
    public static final String UNANSWERED_QUESTIONS = "UNANSWERED_QUESTIONS";
    public static final String VALIDATION = "VALIDATION";
    public static final String DATA = "DATA";
    public static final String SAVE = "SAVE";
    public static final String CREDIT_ALREADY_EARNED = "CREDIT_ALREADY_EARNED";
    public static final String MAX_ATTEMPTS_MET = "MAX_ATTEMPTS_MET";

    public static final LegacyResponse SUCCESS_RESPONSE = new LegacyResponse(1, SUCCESS);
    public static final LegacyResponse ERROR_UNANSWERED_QUESTIONS_RESPONSE = new LegacyResponse(0, UNANSWERED_QUESTIONS);
    public static final LegacyResponse ERROR_VALIDATION_RESPONSE = new LegacyResponse(0, VALIDATION);
    public static final LegacyResponse ERROR_SAVE_RESPONSE = new LegacyResponse(0, SAVE);
    public static final LegacyResponse ERROR_CREDIT_ALREADY_EARNED_RESPONSE = new LegacyResponse(0, CREDIT_ALREADY_EARNED);
    public static final LegacyResponse ERROR_MAX_ATTEMPTS_MET_RESPONSE = new LegacyResponse(0, MAX_ATTEMPTS_MET);
    public static final LegacyResponse ERROR_DATA_RESPONSE = new LegacyResponse(0, DATA);


    public LegacyResponse (int status, String code) {
        setStatus(status);
        setCode(code);
    }
    public LegacyResponse () {
    }

    private int status;
    private String code;

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }



    public LegacyResponse transformResponse(AppResponseMsg msg){

        switch (msg.getCode()){
            case AppConstants.SUCCESS_RESPONSE_CODE :
                return SUCCESS_RESPONSE;

            case AppConstants.ERROR_UNANSWERED_QUESTIONS_CODE :
                return ERROR_UNANSWERED_QUESTIONS_RESPONSE;

            case AppConstants.GEN_ERR_CODE :
                return ERROR_SAVE_RESPONSE;

            case AppConstants.ERROR_CREDIT_EARNED_RESPONSE_CODE :
                return ERROR_CREDIT_ALREADY_EARNED_RESPONSE;

            case AppConstants.ERROR_MAX_ATTEMPTS_MET_RESPONSE_CODE :
                return ERROR_MAX_ATTEMPTS_MET_RESPONSE;

            /*case AppConstants.GEN_ERR_MSG :
                return ERROR_DATA_RESPONSE;
             */
            default:{
                return new LegacyResponse(-50, msg.getMessage());
            }
        }
    }


    public static ResponseEntity<LegacyResponse> getResponse(String responseMsg , String code, HttpStatus status){
        return new ResponseEntity<>(new LegacyResponse(Integer.parseInt(code),responseMsg),status);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
