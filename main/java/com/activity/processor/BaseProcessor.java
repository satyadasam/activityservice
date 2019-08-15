package com.  .activity.processor;
import static com.  .activity.qna.constants.FormTypeConstant.POST;
import static com.  .activity.qna.constants.FormTypeConstant.INTERNAL;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.  .activity.adaptor.QNAAdaptor;
import com.  .activity.exception.ProcessorException;
import com.  .activity.exception.QNAException;
import com.  .activity.model.AppResponse;
import com.  .activity.model.UserActivity;
import com.  .qnaservice.entity.QuestionForm;
import com.  .qnaservice.entity.Questionnaire;
import com.  .qnaservice.entity.Status;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.exception.manager.QNAManagerException;
import com.  .activity.qna.exception.rule.QNARuleException;
import com.  .activity.qna.manager.IQNAManager;
import com.  .activity.qna.moc.MocService;
import com.  .activity.qna.service.ValidationRuleService;
import com.  .activity.util.AppConstants;
import com.  .activity.util.ControllerUtils;
import com.  .activity.util.QuestionnaireUtils;
import com.  .registration.core.User;

public class BaseProcessor implements FormProcessor {

	
	
	@Inject
	protected IQNAManager qnaManagerImpl;
	@Inject
	protected ValidationRuleService validationRuleService;
	@Autowired
    QNAAdaptor qnaAdaptor;
/*	@Inject
	protected QNABusinessRuleService qnaBusinessRuleService;*/
	@Autowired
	protected MocService mocService;	
	@Override
	public ResponseEntity<AppResponse> processRequest(UserActivity request, Questionnaire questionnaire)
			 throws ProcessorException,QNADataAccessException {
		return null;
	}	
	
	protected boolean isActivityValidForTest(Questionnaire questionnaire) {
		Date activityExpirationDate = questionnaire.getActivityInfo().getActivityExpirationDate();
		return null != activityExpirationDate && !isActivityExpired(activityExpirationDate) && !questionnaire.isTestLessActivity();
	}
	
	protected boolean isPostFormPresent( Questionnaire questionnaire ) throws QNAManagerException {
		List<QuestionForm> postFormList = QuestionnaireUtils.getQuestionFormByFormTypeId(questionnaire, POST.getFormTypeId());
		return null != postFormList ? ( postFormList.isEmpty() ? false : true ) : false;
	}

	 protected boolean testPassed(UserActivity activity,Questionnaire questionnaire, User user) throws QNARuleException {
		
		boolean areAllForScoreAnsweredForFormType = validationRuleService.areForScoreQuestionsAnsweredByFormType( questionnaire, user.getGlobalUserID(),INTERNAL.getFormTypeId(),activity);
		return areAllForScoreAnsweredForFormType && validationRuleService.hasUserPassedTheTest(questionnaire, user,activity);
	}
	
	protected void saveActivityResult(User user, int questionnaireId, int sessionID, int pass, String userAgent, boolean isMocEligible) throws QNAManagerException {
		qnaManagerImpl.saveActivityResult(questionnaireId, pass, validationRuleService.isUserCMEProfileComplete(user) ? 1 : 0, sessionID, user, userAgent, isMocEligible);
	//	qnaManagerImpl.saveActivityResult(questionnaireId, pass, 1, sessionID, user, userAgent, isMocEligible);
	}
	
	/*protected ResponseEntity<AppResponse> saveUserResponse(UserActivity userActivity,String guid,Class classz){		
			
		 Status status=qnaAdaptor.saveUserResponse(userActivity,String.valueOf(userActivity.getGuid()),classz);
		 
		 if(!status.isOk()){
			 
			 return ControllerUtils.getResponse(status.getErrorMessage(),  HttpStatus.INTERNAL_SERVER_ERROR);
		 }
		  else return null;
	}*/
	
	protected int getCreditTypeId(User user) throws QNAException {

		String professionID = String.valueOf(user.getProfession().getProfessionId());
		String creditType = validationRuleService.getCreditTypeMap().get(professionID);
		int creditTypeID = 0;
		int occupationID = 0;
		if( null != user.getProfession().getOccupationId()){
			occupationID = user.getProfession().getOccupationId().intValue();
		}
		if(creditType==null){
			
			  if (occupationID == 18) {
					
					/*
					 * Credit type: 4 - Psychologist CE
					 * 
					 * All Users with "psychologist" as their occupation (ID=18)
					 */
					
					creditType = AppConstants.PSYCHOLOGIST_CE;
					
				} 
			  	if (occupationID == 16) {
					
					/*
					 * Credit type: 5 - Medical Laboratory CE
					 * 
					 * All Users with "medical technician" as their occupation (ID=16)
					 */
					creditType = AppConstants.MEDICAL_LABORATOR_CE;
					
				} 
				
				
		}
		
		if (creditType != null) {
			creditTypeID = Integer.valueOf(creditType);
		} else {

			creditTypeID = AppConstants.LOC_CREDIT_TYPE;
			
		}
		return creditTypeID;
	}
	
	/*
	 * Check if the activity is expired
	 */
	private static boolean isActivityExpired( Date activityExpirationDate ) {

		Calendar activityExpiration = new GregorianCalendar();
		activityExpiration.setTimeInMillis(activityExpirationDate.getTime());
		
		long currentTimeInMilliSeconds = System.currentTimeMillis();
		Calendar currentDate = new GregorianCalendar();
		currentDate.setTimeInMillis(currentTimeInMilliSeconds);
		
	//	AppLogger.debug(ActivityController.class, "isActivityExpired( Date activityExpirationDate )"," activityExpiration = " + activityExpiration.toString());
	//	AppLogger.debug(ActivityController.class, "isActivityExpired( Date activityExpirationDate )"," currentDate = " + currentDate.toString());
		
		if (activityExpirationDate != null && activityExpiration.before(currentDate)) {
		//	AppLogger.debug(ActivityController.class, "isActivityExpired( Date activityExpirationDate )"," ***Expired Activity***  ");
			return true;
		}
		//AppLogger.debug(ActivityController.class, "isActivityExpired( Date activityExpirationDate )"," ***Not Expired Activity***  ");
		return false;
	}

}
