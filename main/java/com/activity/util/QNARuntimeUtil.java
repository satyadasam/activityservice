package com.  .activity.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.exception.manager.QNAManagerException;
import com.  .activity.qna.model.ActivityResult;
import com.  .qnaservice.entity.Eligibility;
import com.  .qnaservice.entity.Questionnaire;
import com.  .registration.core.User;



public class QNARuntimeUtil {
	
	/*
	 *  This method is to determine if the user has reached the MaxAttempts allowed
	 */
	public static boolean hasUserReachedMaxAttemptsInTheDay(Questionnaire questionnaire, HttpServletRequest request,
			User user, ActivityResult activityResult,int creditTypeId) throws QNADataAccessException, QNAManagerException {
		
		boolean reachMaxAttempts = false;
		
		// Retrieve 'attemptsNum' from DB
		int attemptsNum = QNARuntimeUtil.getAttemptsNumInTheDayFromDB( activityResult );
		// Retrieve 'MaxAttempt' from DB
		int maxAttempt = getMaxAttemptFromDB(user, questionnaire,creditTypeId);
		
		if(0 != maxAttempt && attemptsNum == maxAttempt){			
			reachMaxAttempts = true;
		}		
		
		return reachMaxAttempts;
	}
	
	/*
	 * retrieve "AttemptsNum" from DB
	 */
	public static int getAttemptsNumInTheDayFromDB(ActivityResult activityResult) throws QNAManagerException{
		
		int numOfTimesTaken = 0;
		
		if(activityResult != null) {
			
			// Get latest test date if exist
			int lastTestDateInNumericFormat = 0;
			lastTestDateInNumericFormat = activityResult.getSessionID();		
						
			if(lastTestDateInNumericFormat>0 && 
					(lastTestDateInNumericFormat == getCurrentDateInNumericFormat())){
				
				numOfTimesTaken = activityResult.getAttemptsNum();
			}
		}
		
		return numOfTimesTaken;
	}
	

	/*
	 * Get Max Attempt from DB ('Max Attempt' is activity and credit type specific.)
	 */
	public static int getMaxAttemptFromDB(User user, Questionnaire questionnaire, int creditTypeId)  throws QNADataAccessException, QNAManagerException {

		int maxAttempt = 0;

	
		
		if (null != user) {

			// Find out the creditType id for the user
		//	int creditTypeId = QNASharedUtil.getUserCreditType(user);

			if (!QuestionnaireUtils.isCreditTypeAssociated(creditTypeId, questionnaire)) {
				creditTypeId = AppConstants.LOC_CREDIT_TYPE;
			}
			
			// Get Eligibilities for the activity associated with the given Questionnaire
			Map<Integer, Eligibility> eligibilityMap  = questionnaire.getActivityInfo().getEligibilityMap();
			
			// Get Eligibilities for the given credittype
			Eligibility eligibility = (Eligibility)eligibilityMap.get(new Integer(creditTypeId));

			if(null != eligibility)
				maxAttempt = eligibility.getMaxAttempts();
		}

		return maxAttempt;
	}
	

	/*
	 * This method is to get current date in numeric format, e.g. 20110701
	 */
	public static int getCurrentDateInNumericFormat(){
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
				
		int sessionID = year*10000 + month*100 + day;		
		
		return sessionID;
	}
	
	
}

