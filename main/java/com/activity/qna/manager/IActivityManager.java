/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/


package com.  .activity.qna.manager;

import java.util.HashMap;
import java.util.Map;

import com.  .activity.qna.exception.manager.QNAManagerException;
import com.  .activity.qna.model.ActivityResult;
import com.  .registration.core.User;

/**
 * Activity Manager Interface Class.
 *
 * This interface has the method definitions for Saving/Retriving Activity Info.
 * 
 * @author vagupta
 * @version 1.0
 */
public interface IActivityManager {

	/**
	 * This Method returns ActivityResult Object
	 * 
	 * @param guid
	 * @param questionnaireID
	 * @return ActivityResult
	 */
	public abstract ActivityResult getActivityResult(long guid, int qnaId) throws QNAManagerException;
	
	/**
	 * This Method Saves ActivityResult Object
	 * 
	 * @param guid
	 * @param profileComplete
	 * @param User 
	 * @return int
	 */
	public int saveActivityResult( int qnaId, int pass, int profileComplete, int sessionID, User user, String userAgent) throws QNAManagerException;
	
	/**
	 * This Method Saves ActivityResult Object & processes MOC activities
	 * 
	 * @param guid
	 * @param profileComplete
	 * @param User 
	 * @return int
	 */
	public int saveActivityResult( int qnaId, int pass, int profileComplete, int sessionID, User user, String userAgent, boolean isMocActivity) throws QNAManagerException;
	
	/**
	 * This Method Updates ActivityResult Object
	 * 
	 * @param qnaId
	 * @param guid
	 * @param Map <fieldMap> 
	 * @return int
	 */
	public int updateActivityResult( int qnaId, long guid, int sessionID, Map fieldMap, String userAgent )
	throws QNAManagerException;
	
	
	/**
	 * This Method Updates ActivityResult Object & processes moc Activities
	 * 
	 * @param qnaId
	 * @param guid
	 * @param Map <fieldMap> 
	 * @return int
	 */
	public int updateActivityResult( int qnaId, long guid, int sessionID, Map fieldMap, String userAgent, boolean isMocActivity )
	throws QNAManagerException;

	/**
	 * This Method returns a map of ActivityEligibility Object
	 * 
	 * @param activityId
	 * @return HashMap
	 */
	public abstract HashMap getActivityEligibilities( int activityId ) throws QNAManagerException;
	
	public abstract HashMap getActivityEligibilitiesByArticleId( int articleId ) throws QNAManagerException;
	
	public abstract HashMap getActivityEligibilitiesByQuestionnaireId( int questionnaireId ) throws QNAManagerException;
}
