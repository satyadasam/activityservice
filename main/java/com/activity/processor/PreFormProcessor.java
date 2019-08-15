package com.  .activity.processor;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.  .activity.exception.AdapterException;
import com.  .activity.exception.PopulateExceptionHandler;
import com.  .activity.exception.ProcessorException;
import com.  .activity.model.AppResponse;
import com.  .activity.model.UserActivity;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .qnaservice.entity.Questionnaire;
import com.  .qnaservice.entity.Status;
import com.  .activity.util.AppConstants;
import com.  .activity.util.ControllerUtils;
import com.  .activity.util.ExceptionUtils;


@Component("preformprocessor")
public class PreFormProcessor extends BaseProcessor  implements FormProcessor{

	private static Logger log = LoggerFactory.getLogger(PreFormProcessor.class);
	@Override
	public ResponseEntity<AppResponse> processRequest(UserActivity userActivity, Questionnaire questionnaire) throws ProcessorException, QNADataAccessException {
		Map<String,Object>  data=null; 
		try{
			
		boolean	isValidated = validationRuleService.areRequiredQuestionsAnsweredByFormId(questionnaire, userActivity.getFormId(), userActivity);
		
		Status status=null;	
		
			if( isValidated ) {
				
				  status=qnaAdaptor.saveUserResponse(userActivity,String.valueOf(userActivity.getGuid()),Status.class);
				  if(status.getData()!=null){
					  
					  data=status.getData();
				  }				  

					 if(!status.isOk()){
						 
						 return ControllerUtils.getResponse(status.getErrorMessage(),  HttpStatus.INTERNAL_SERVER_ERROR,data);
					 }
					 else{
						 
						 return ControllerUtils.getResponse(AppConstants.SUCCESS_RESPONSE, HttpStatus.CREATED,data);
					 }
				
			
			 
		}else{
			
			log.error("Required questions are unanswered.");				
			return ControllerUtils.getResponse(AppConstants.UNANSWERED_QUESTIONS,  HttpStatus.BAD_REQUEST,data);
		}
					
		}catch(Exception ex){
			log.error("Exception "+ExceptionUtils.getStackTrace(ex));
			PopulateExceptionHandler.getIntance().handleExceptons(ex);		
		}
		return null;		
		
	}

}
