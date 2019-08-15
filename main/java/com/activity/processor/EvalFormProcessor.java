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
import com.  .qnaservice.entity.Questionnaire;
import com.  .qnaservice.entity.Status;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.model.ActivityResult;
import com.  .activity.util.AppConstants;
import com.  .activity.util.ControllerUtils;
import com.  .activity.util.ExceptionUtils;


@Component("evalformprocessor")
public class EvalFormProcessor extends BaseProcessor  implements FormProcessor{

	private static Logger log = LoggerFactory.getLogger(EvalFormProcessor.class);
	@Override
	public ResponseEntity<AppResponse> processRequest(UserActivity userActivity, Questionnaire questionnaire) throws ProcessorException, QNADataAccessException {
		Map<String,Object>  data=null; 
		try{
					ActivityResult activityResult = qnaManagerImpl.getActivityResult(userActivity.getGuid(), userActivity.getQuestionnaireId());
					
					if(activityResult==null) {
						
						 return ControllerUtils.getResponse(AppConstants.ERROR_ACTIVITY_RESULT_NOT_FOUND,  HttpStatus.BAD_REQUEST,data);
						
					}
					
					if((1 == activityResult.getEvaluationRequired()) && !validationRuleService.isQuestionFormAnswered(questionnaire, userActivity.getFormId(),userActivity.getGuid(),userActivity)){
						
						return ControllerUtils.getResponse(AppConstants.UNANSWERED_QUESTIONS,  HttpStatus.BAD_REQUEST,data);
					}
					Status status=null;
					status=qnaAdaptor.saveUserResponse(userActivity,String.valueOf(userActivity.getGuid()),Status.class);
				    if(status.getData()!=null){
					  
					  data=status.getData();
				    }				  
			
				    if(!status.isOk()){
					 return ControllerUtils.getResponse(status.getErrorMessage(),  HttpStatus.INTERNAL_SERVER_ERROR,data);
				   }
				//
		
			    // Removed other two conditions
			
				Map<String, String> inParams = new HashMap<String, String>();
	            inParams.put(AppConstants.ACTIVITY_RESULT_FIELD_EVAL_COMPLETE, String.valueOf(AppConstants.ACTIVITY_RESULT_EVALUATION_COMPLETE));
	            int creditTypeId= getCreditTypeId(userActivity.getUser());
	            if (creditTypeId == AppConstants.PHYSICIAN_CME) 
	                inParams.put(AppConstants.ACTIVITY_RESULT_FIELD_CREDITS_EARNED, String.valueOf(userActivity.getCreditsEarned()));
	            //Not test, do NOT save MOC record & send message to PARS/ABIM
	            qnaManagerImpl.updateActivityResult( userActivity.getQuestionnaireId(), userActivity.getGuid(), 0, inParams, userActivity.getUserAgent());			
			    return ControllerUtils.getResponse(AppConstants.SUCCESS_RESPONSE,  HttpStatus.CREATED,data);
			
		}catch(Exception ex){
			log.error("Exception "+ExceptionUtils.getStackTrace(ex));
			PopulateExceptionHandler.getIntance().handleExceptons(ex);		
		}
		return null;
		
		
	}

}
