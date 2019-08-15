package com.  .activity.processor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.  .activity.exception.AdapterException;
import com.  .activity.exception.AdapterExceptionHandler;
import com.  .activity.exception.PopulateExceptionHandler;
import com.  .activity.exception.ProcessorException;
import com.  .activity.exception.UnAuthorizedUserExcepton;
import com.  .activity.model.AppResponse;
import com.  .activity.model.AppResponseMsg;
import com.  .activity.model.UserActivity;
import com.  .activity.util.AppConstants;
import com.  .activity.util.ControllerUtils;
import com.  .activity.util.ExceptionUtils;
import com.  .activity.util.QNARuntimeUtil;
import com.  .qnaservice.entity.Questionnaire;
import com.  .qnaservice.entity.Status;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.model.ActivityResult;
@Component("postformprocessor")
public class PostFormProcessor extends BaseProcessor implements FormProcessor {

	private static Logger log = LoggerFactory.getLogger(PostFormProcessor.class);

	@Override
	public ResponseEntity<AppResponse> processRequest(UserActivity userActivity, Questionnaire questionnaire) throws ProcessorException,QNADataAccessException {
		
		Map<String,Object>  data=null; 
		
		try{
			
				ActivityResult activityResult = qnaManagerImpl.getActivityResult(userActivity.getGuid(), userActivity.getQuestionnaireId());
				
				
				int maxAttempt = QNARuntimeUtil.getMaxAttemptFromDB(userActivity.getUser(),questionnaire,getCreditTypeId(userActivity.getUser()));
				 if( null != activityResult && activityResult.getCreditsEarned() != 0.0 ) {
				
					return ControllerUtils.getResponse(AppConstants.ERROR_CREDIT_EARNED_RESPONSE, HttpStatus.BAD_REQUEST,data);
					
				}		
				
				if(QNARuntimeUtil.hasUserReachedMaxAttemptsInTheDay(questionnaire, null, userActivity.getUser(),activityResult,getCreditTypeId(userActivity.getUser()))){
					return ControllerUtils.getResponse(AppConstants.ERROR_MAX_ATTEMPTS_MET_RESPONSE,  HttpStatus.BAD_REQUEST,data);
				}
				if(!isActivityValidForTest(questionnaire)){
					
					return ControllerUtils.getResponse(AppConstants.ERROR_ACTIVITY_EXPIRED,  HttpStatus.BAD_REQUEST,data);
				}
			
				boolean	isValidated = validationRuleService.areForScoreQuestionsAnswered(questionnaire, userActivity);
				Status status=null;
				if( isValidated ){				
				     
					  status=qnaAdaptor.saveUserResponse(userActivity,String.valueOf(userActivity.getGuid()),Status.class);
					  
					  if(status.getData()!=null){
						  
						  data=status.getData();
					  }		  
				
					 if(!status.isOk()){
						 return ControllerUtils.getResponse(status.getErrorMessage(),  HttpStatus.INTERNAL_SERVER_ERROR,data);
					 }
				
				 boolean  hasUserPassedTheTest = validationRuleService.hasUserPassedTheTest( questionnaire, userActivity.getUser() ,userActivity);
				   //hasUserPassedTheTest=false;
		        //Check if this is an MOC activity and if the user is MOC eligible.  If so AND IF USER HAS PASSED TEST, set flag
				//so we can send message to PARS/ABIM
				boolean	isMocEligible = false;
				//boolean isMocActivity =false;
				boolean isMocActivity = mocService.checkIfQuestionnaireIsMOC(questionnaire.getQuestionnaireId());
				
		        if(userActivity.getUser().isMocEligible() && isMocActivity){
		        			isMocEligible = true;
		        }
	
				if(hasUserPassedTheTest){
					
					if (activityResult != null){
						
						Map inParams = new HashMap();
						inParams.put("pass","1");
						qnaManagerImpl.updateActivityResult(userActivity.getQuestionnaireId(), userActivity.getUser().getGlobalUserID(),0, inParams, userActivity.getUserAgent(), isMocEligible);
					}		
					else 
						saveActivityResult(userActivity.getUser(), userActivity.getQuestionnaireId(), 0, AppConstants.ACTIVITY_RESULT_SUCCEED, userActivity.getUserAgent(), isMocEligible);
	
				} 
				else {					
							int numOfAttempts = QNARuntimeUtil.getAttemptsNumInTheDayFromDB(activityResult);					
							if(null != activityResult && 0 != maxAttempt && numOfAttempts <= maxAttempt ){								
								Map inParams = new HashMap();
								inParams.put("pass","1");
								qnaManagerImpl.updateActivityResult(userActivity.getQuestionnaireId(),userActivity.getUser().getGlobalUserID(),QNARuntimeUtil.getCurrentDateInNumericFormat(), inParams, userActivity.getUserAgent());					
							}							
							else if ( activityResult == null && maxAttempt > 0 )
							saveActivityResult(userActivity.getUser(), userActivity.getQuestionnaireId(), QNARuntimeUtil.getCurrentDateInNumericFormat(), AppConstants.ACTIVITY_RESULT_FAIL, userActivity.getUserAgent(), isMocEligible);						
							else if ( activityResult == null )							
							saveActivityResult(userActivity.getUser(), userActivity.getQuestionnaireId(), 0, AppConstants.ACTIVITY_RESULT_FAIL, userActivity.getUserAgent(), isMocEligible);					
				      }				
		    }
			else{
				
		    		return ControllerUtils.getResponse(AppConstants.UNANSWERED_QUESTIONS,  HttpStatus.BAD_REQUEST,data);
		    
		    }
		    	
			return ControllerUtils.getResponse(AppConstants.SUCCESS_RESPONSE,  HttpStatus.CREATED,data);

			
		}			
		catch(Exception ex)	{
			log.error("Exception "+ExceptionUtils.getStackTrace(ex));
			
			PopulateExceptionHandler.getIntance().handleExceptons(ex);		
			

	}
		return null;
  }
}
