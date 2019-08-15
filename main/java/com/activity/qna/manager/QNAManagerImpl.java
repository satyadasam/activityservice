/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/

package com.  .activity.qna.manager;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.  .activity.qna.exception.manager.QNAManagerException;
import com.  .activity.qna.model.ActivityResult;
import com.  .prof_utilities.util.AppLogger;
import com.  .registration.core.User;




/**
 * QNAManagerImpl implements IQNAManager.
 *
 * @author vagupta
 * @version 1.0
 */
public class QNAManagerImpl implements IQNAManager {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * Private Data Members
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	/*private IQuestionnaireManager questionnaireManagerImpl;
	private IUserResponseManager userResponseManagerImpl;*/
	private IActivityManager activityManagerImpl;

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * Constructor
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	public QNAManagerImpl() {
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * Getters and Setters
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	/*public IQuestionnaireManager getQuestionnaireManagerImpl() {
		return questionnaireManagerImpl;
	}

	public void setQuestionnaireManagerImpl(
			IQuestionnaireManager questionnaireManagerImpl) {
		this.questionnaireManagerImpl = questionnaireManagerImpl;
	}

	public IUserResponseManager getUserResponseManagerImpl() {
		return userResponseManagerImpl;
	}

	public void setUserResponseManagerImpl(
			IUserResponseManager userResponseManagerImpl) {
		this.userResponseManagerImpl = userResponseManagerImpl;
	}*/

	public IActivityManager getActivityManagerImpl() {
		return activityManagerImpl;
	}

	public void setActivityManagerImpl(IActivityManager activityManagerImpl) {
		this.activityManagerImpl = activityManagerImpl;
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 *
	 * Delegation To IActivityManager
	 *
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	public ActivityResult getActivityResult(long guid, int qnaId) throws QNAManagerException{
		
		AppLogger.debug( QNAManagerImpl.class, "getActivityResult", "--- questionnaireID = " + qnaId );
		AppLogger.debug( QNAManagerImpl.class, "getActivityResult", "--- guid = " + guid );
		
		return activityManagerImpl.getActivityResult(guid, qnaId);
	}

	public int saveActivityResult( int qnaId, int pass, int profileComplete, int sessionID, User user, String userAgent, boolean isMocActivity )throws QNAManagerException{
		
		AppLogger.debug( QNAManagerImpl.class, "saveActivityResult", "--- questionnaireID = " + qnaId );
		AppLogger.debug( QNAManagerImpl.class, "saveActivityResult", "--- profileComplete = " + profileComplete );
		
		return activityManagerImpl.saveActivityResult(qnaId, pass, profileComplete, sessionID, user, userAgent, isMocActivity);

	}
	
	public int saveActivityResult( int qnaId, int pass, int profileComplete, int sessionID, User user, String userAgent )throws QNAManagerException{
		
		AppLogger.debug( QNAManagerImpl.class, "saveActivityResult", "--- questionnaireID = " + qnaId );
		AppLogger.debug( QNAManagerImpl.class, "saveActivityResult", "--- profileComplete = " + profileComplete );
		
		return activityManagerImpl.saveActivityResult(qnaId, pass, profileComplete, sessionID, user, userAgent);

	}

	public int updateActivityResult( int qnaId, long guid, int sessionID, Map fieldMap, String userAgent) throws QNAManagerException{
		
		AppLogger.debug( QNAManagerImpl.class, "updateActivityResult", "--- questionnaireID = " + qnaId );
		AppLogger.debug( QNAManagerImpl.class, "updateActivityResult", "--- guid = " + guid );
		
		return activityManagerImpl.updateActivityResult(qnaId, guid, sessionID, fieldMap, userAgent);
	}


	public int updateActivityResult( int qnaId, long guid, int sessionID, Map fieldMap, String userAgent, boolean isMocEligible) throws QNAManagerException{
		
		AppLogger.debug( QNAManagerImpl.class, "updateActivityResult", "--- questionnaireID = " + qnaId );
		AppLogger.debug( QNAManagerImpl.class, "updateActivityResult", "--- guid = " + guid );
		
		return activityManagerImpl.updateActivityResult(qnaId, guid, sessionID, fieldMap, userAgent, isMocEligible);
	}
	
	public HashMap getActivityEligibilities( int activityId ) throws QNAManagerException{
		
		AppLogger.debug( QNAManagerImpl.class, "getActivityEligibilities", "--- activityId = " + activityId );
		
		return activityManagerImpl.getActivityEligibilities(activityId);
	}
	
	public HashMap getActivityEligibilitiesByArticleId( int articleId ) throws QNAManagerException{
		
		AppLogger.debug( QNAManagerImpl.class, "getActivityEligibilities", "--- articleId = " + articleId );
		
		return activityManagerImpl.getActivityEligibilitiesByArticleId(articleId);
	}
	
	public HashMap getActivityEligibilitiesByQuestionnaireId( int questionnaireId ) throws QNAManagerException{
		
		AppLogger.debug( QNAManagerImpl.class, "getActivityEligibilities", "--- articleId = " + questionnaireId );
		
		return activityManagerImpl.getActivityEligibilitiesByQuestionnaireId(questionnaireId);
	}
	


}
