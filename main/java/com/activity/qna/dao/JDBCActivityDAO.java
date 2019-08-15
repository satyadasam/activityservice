/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/

package com.  .activity.qna.dao;

import java.util.HashMap;
import java.util.List;

import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.model.ActivityResult;
import com.  .activity.qna.model.DBActivityResult;
import com.  .activity.qna.model.DBUpdateActivityResult;
import com.  .activity.qna.storedprocedures.GetCMEResultStoredProcedure;
import com.  .activity.qna.storedprocedures.GetEligibilityByArticleIdStoredProcedure;
import com.  .activity.qna.storedprocedures.GetEligibilityByQuestionnaireIdStoredProcedure;
import com.  .activity.qna.storedprocedures.GetEligibilityStoredProcedure;
import com.  .activity.qna.storedprocedures.SaveCMEResultStoredProcedure;
import com.  .activity.qna.storedprocedures.UpdateCMEResultStoredProcedure;
import com.  .prof_utilities.util.AppLogger;

/**
 * This class is the DAO class for Activity and extends JDBCBaseDAO
 * The class invokes various stored procedure ranging from
 * 1. Getting Activity Data from activity result table
 * 2. Saving activity data in activity result table
 * 3. updating activity data in activity result table
 * 
 * @author Vikas Gupta
 * @version 1.0
 */
public class JDBCActivityDAO extends JDBCBaseDAO{

	/*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Private data members
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
	/**
	 * Injected using Spring
	 * 
	 */
	
	private GetCMEResultStoredProcedure getCMEResultStoredProcedure;
	private SaveCMEResultStoredProcedure saveCMEResultStoredProcedure;
	private UpdateCMEResultStoredProcedure updateCMEResultStoredProcedure;
	private GetEligibilityStoredProcedure getEligibilityStoredProcedure;
	private GetEligibilityByArticleIdStoredProcedure getEligibilityByArticleIdStoredProcedure;
	private GetEligibilityByQuestionnaireIdStoredProcedure getEligibilityByQuestionnaireIdStoredProcedure;
	
	/*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Public methods
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */




	/**
	 * This method will return the ActivityResult object
	 * @param questionnaireID
	 * @param guid
	 * @return ActivityResult
	 * 
	 * @throws QNADataAccessException
	 */
	public ActivityResult getCMEResult( int questionnaireID, long guid ) throws QNADataAccessException{
		
		AppLogger.debug( JDBCActivityDAO.class, "getCMEResult", "--- questionnaireID = " + questionnaireID );
		AppLogger.debug( JDBCActivityDAO.class, "getCMEResult", "--- guid = " + guid );
		
		return getCMEResultStoredProcedure.execute( questionnaireID, guid );		
	}
	
	
	/**
	 * This method will Save the ActivityResult object
	 * @param DBActivityResult
	 * @return int
	 * 
	 * @throws QNADataAccessException
	 */
	public int saveCMEResult( DBActivityResult dbActivityResult ) throws QNADataAccessException{
		
		AppLogger.debug( JDBCActivityDAO.class, "saveCMEResult", "--- DBActivityResult = " + dbActivityResult.toString() );
		
		return saveCMEResultStoredProcedure.execute( dbActivityResult );
			
	}
	
	
	/**
	 * This method will update the ActivityResult object
	 * @param qnaId
	 * @param guid
	 * @param fieldList
	 * 
	 * @return int
	 * 
	 * @throws QNADataAccessException
	 */
	public int updateCMEResult( int qnaId, long guid, int sessionID, List fieldList, String userAgent ) throws QNADataAccessException{
		
		AppLogger.debug( JDBCActivityDAO.class, "updateCMEResult", "--- qnaId = " + qnaId );
		AppLogger.debug( JDBCActivityDAO.class, "updateCMEResult", "--- guid = " + guid );
		
		for( int i = 0; i < fieldList.size(); i++ ){			
			DBUpdateActivityResult dbUpdateActivityResult = ( DBUpdateActivityResult )fieldList.get(i);
			AppLogger.debug( JDBCActivityDAO.class, "updateCMEResult", "--- dbUpdateActivityResult = " + dbUpdateActivityResult.toString() );
		}
		
		return updateCMEResultStoredProcedure.execute( qnaId,guid,sessionID, fieldList, userAgent );
			
	}
	
	/**
	 * This method will return the ActivityEligibility object
	 * @param activityId
	 * @return HashMap
	 * 
	 * @throws QNADataAccessException
	 */
	public HashMap getActivityEligibilities( int activityId ) throws QNADataAccessException{
		
		AppLogger.debug( JDBCActivityDAO.class, "getActivityEligibilities", "--- activityId = " + activityId );
		
		return getEligibilityStoredProcedure.execute( activityId );		
	}

	public HashMap getActivityEligibilitiesByArticleId( int articleId ) throws QNADataAccessException{
		
		AppLogger.debug( JDBCActivityDAO.class, "getActivityEligibilities", "--- articleId = " + articleId );
		
		return getEligibilityByArticleIdStoredProcedure.execute( articleId );		
	}
	
	public HashMap getActivityEligibilitiesByQuestionnaireId( int questionnaireId ) throws QNADataAccessException{
		
		AppLogger.debug( JDBCActivityDAO.class, "getActivityEligibilities", "--- articleId = " + questionnaireId );
		
		return getEligibilityByQuestionnaireIdStoredProcedure.execute( questionnaireId );		
	}

	/*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Getters and Setters
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
	
	public GetCMEResultStoredProcedure getGetCMEResultStoredProcedure() {
		return getCMEResultStoredProcedure;
	}

	public void setGetCMEResultStoredProcedure(
			GetCMEResultStoredProcedure getCMEResultStoredProcedure) {
		this.getCMEResultStoredProcedure = getCMEResultStoredProcedure;
	}
	
	public SaveCMEResultStoredProcedure getSaveCMEResultStoredProcedure() {
		return saveCMEResultStoredProcedure;
	}


	public void setSaveCMEResultStoredProcedure(
			SaveCMEResultStoredProcedure saveCMEResultStoredProcedure) {
		this.saveCMEResultStoredProcedure = saveCMEResultStoredProcedure;
	}
	
	public UpdateCMEResultStoredProcedure getUpdateCMEResultStoredProcedure() {
		return updateCMEResultStoredProcedure;
	}


	public void setUpdateCMEResultStoredProcedure(
			UpdateCMEResultStoredProcedure updateCMEResultStoredProcedure) {
		this.updateCMEResultStoredProcedure = updateCMEResultStoredProcedure;
	}
	
	public GetEligibilityStoredProcedure getGetEligibilityStoredProcedure() {
		return getEligibilityStoredProcedure;
	}


	public void setGetEligibilityStoredProcedure(
			GetEligibilityStoredProcedure getEligibilityStoredProcedure) {
		this.getEligibilityStoredProcedure = getEligibilityStoredProcedure;
	}


	public GetEligibilityByArticleIdStoredProcedure getGetEligibilityByArticleIdStoredProcedure() {
		return getEligibilityByArticleIdStoredProcedure;
	}


	public void setGetEligibilityByArticleIdStoredProcedure(
			GetEligibilityByArticleIdStoredProcedure getEligibilityByArticleIdStoredProcedure) {
		this.getEligibilityByArticleIdStoredProcedure = getEligibilityByArticleIdStoredProcedure;
	}


	public GetEligibilityByQuestionnaireIdStoredProcedure getGetEligibilityByQuestionnaireIdStoredProcedure() {
		return getEligibilityByQuestionnaireIdStoredProcedure;
	}


	public void setGetEligibilityByQuestionnaireIdStoredProcedure(
			GetEligibilityByQuestionnaireIdStoredProcedure getEligibilityByQuestionnaireIdStoredProcedure) {
		this.getEligibilityByQuestionnaireIdStoredProcedure = getEligibilityByQuestionnaireIdStoredProcedure;
	}
	
}
