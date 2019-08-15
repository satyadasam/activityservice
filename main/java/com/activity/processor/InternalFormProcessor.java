package com.  .activity.processor;

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
import com.  .qnaservice.entity.Questionnaire;
import com.  .qnaservice.entity.Status;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.model.ActivityResult;
import com.  .activity.util.AppConstants;
import com.  .activity.util.ControllerUtils;
import com.  .activity.util.ExceptionUtils;


@Component("internalformprocessor")
public class InternalFormProcessor extends BaseProcessor  implements FormProcessor{

	private static Logger log = LoggerFactory.getLogger(InternalFormProcessor.class);
	@Override
	public ResponseEntity<AppResponse> processRequest(UserActivity userActivity, Questionnaire questionnaire) throws ProcessorException, QNADataAccessException {
		Map<String,Object>  data=null; 
		try{
			
				 ActivityResult activityResult = qnaManagerImpl.getActivityResult(userActivity.getGuid(), userActivity.getQuestionnaireId());	
				 
				 if( null != activityResult && activityResult.getCreditsEarned() != 0.0 ) {
						
						return ControllerUtils.getResponse(AppConstants.ERROR_CREDIT_EARNED_RESPONSE,  HttpStatus.BAD_REQUEST,data);
						
					}		
				 boolean areRequiredQuestionsAnswered = validationRuleService.areRequiredQuestionsAnsweredByFormId(questionnaire, userActivity.getFormId(), userActivity);
				 if(areRequiredQuestionsAnswered) {						
					boolean isQuestionFormAnswered = validationRuleService.isQuestionFormAnswered(questionnaire, userActivity.getFormId(), userActivity.getGuid(),userActivity);
		            //Check if this is an MOC activity and if the user is MOC eligible.  If so, push message onto queue
					boolean	isMocEligible = false;
					boolean isMocActivity = mocService.checkIfQuestionnaireIsMOC(questionnaire.getQuestionnaireId());;						
		            if(userActivity.getUser().isMocEligible() && isMocActivity && testPassed(userActivity,questionnaire, userActivity.getUser())){
		            	isMocEligible = true;
		            }
		            Status status=null; 		            
		            ResponseEntity<AppResponse> responseEntity=null;
		        	//isQuestionFormAnswered=false;
					if( isQuestionFormAnswered && null == activityResult){
						
						  status=qnaAdaptor.saveUserResponse(userActivity,String.valueOf(userActivity.getGuid()),Status.class);
						  
						  if(status.getData()!=null){
							  
							  data=status.getData();
						  }				  
					
						 if(!status.isOk()){
							 return ControllerUtils.getResponse(status.getErrorMessage(),  HttpStatus.INTERNAL_SERVER_ERROR,data);
						 }						
					}				
					else if ( !isQuestionFormAnswered ){
						
						  status=qnaAdaptor.saveUserResponse(userActivity,String.valueOf(userActivity.getGuid()),Status.class);
						  if(data!=null){
							  
							  data=status.getData();
						  }					  
					
						 if(!status.isOk()){
							 return ControllerUtils.getResponse(status.getErrorMessage(),  HttpStatus.INTERNAL_SERVER_ERROR,data);
						 }
					}						
					 if(responseEntity!=null){
						 
						 return responseEntity;
					 }
					
					// Check this code latter
					// activityResult=null;
					if (null == activityResult && questionnaire.getActivityInfo()!=null && isActivityValidForTest(questionnaire) && !isPostFormPresent(questionnaire) && testPassed(userActivity, questionnaire, userActivity.getUser())) {
						
						 		saveActivityResult(userActivity.getUser(), userActivity.getQuestionnaireId(), 0, AppConstants.ACTIVITY_RESULT_SUCCEED,userActivity.getUserAgent(),isMocEligible) ; 
							/*	qnaManagerImpl.saveActivityResult(userActivity.getQuestionnaireId(),AppConstants.ACTIVITY_RESULT_SUCCEED, qnaBusinessRuleService.isUserCMEProfileComplete(userActivity.getUser()) ? 1 : 0, 0, userActivity.getUser(),
								userActivity.getUserAgent(), isMocEligible);*/
						      
					}
					
				} else {
					log.error("Required questions are unanswered.");				
					return ControllerUtils.getResponse(AppConstants.UNANSWERED_QUESTIONS, HttpStatus.BAD_REQUEST,data);
				}
		
			  return ControllerUtils.getResponse(AppConstants.SUCCESS_RESPONSE, HttpStatus.CREATED,data);
			
		}catch(Exception ex){
			
			log.error("Exception "+ExceptionUtils.getStackTrace(ex));
			PopulateExceptionHandler.getIntance().handleExceptons(ex);		
			
		}
		return null;
		
		
	}

}
