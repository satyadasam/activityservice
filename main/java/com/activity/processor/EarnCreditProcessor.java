package com.  .activity.processor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.  .activity.exception.PopulateExceptionHandler;
import com.  .activity.exception.ProcessorException;
import com.  .activity.model.AppResponse;
import com.  .activity.model.UserActivity;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.model.ActivityResult;
import com.  .activity.util.AppConstants;
import com.  .activity.util.ControllerUtils;
import com.  .activity.util.ExceptionUtils;
import com.  .qnaservice.entity.Questionnaire;
import com.  .qnaservice.entity.Status;

@Component("earnCreditProcessor")
public class EarnCreditProcessor extends BaseProcessor {
	


	private static Logger log = LoggerFactory.getLogger(EarnCreditProcessor.class);
	@Override
	public ResponseEntity<AppResponse> processRequest(UserActivity userActivity, Questionnaire questionnaire) throws ProcessorException, QNADataAccessException {
		Map<String,Object>  data=null; 
		try{
				
			boolean	isMocEligible = false;
			//boolean isMocActivity =false;
			boolean isMocActivity = mocService.checkIfQuestionnaireIsMOC(questionnaire.getQuestionnaireId());
			
	        if(userActivity.getUser().isMocEligible() && isMocActivity){
	        			isMocEligible = true;
	        }
	        
			saveActivityResult(userActivity.getUser(), userActivity.getQuestionnaireId(), 0, AppConstants.ACTIVITY_RESULT_SUCCEED, userActivity.getUserAgent(), isMocEligible);
			return ControllerUtils.getResponse(AppConstants.SUCCESS_RESPONSE,  HttpStatus.CREATED,data);
			
		}catch(Exception ex){
			log.error("Exception "+ExceptionUtils.getStackTrace(ex));
			PopulateExceptionHandler.getIntance().handleExceptons(ex);		
		}
		return null;
		
		
	}



}
