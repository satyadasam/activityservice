/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/

package com.  .activity.qna.db.jdbc.rowmappers;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.  .activity.qna.model.ActivityResult;


public class CMEResultRowMapper implements RowMapper {
	
	/**
	 * Default Method Implementation defined in the RowMapper class. 
	 */
	public Object mapRow(java.sql.ResultSet rs, int arg1)throws SQLException {
		
		// Initializing and instantiating ActivityResult
		ActivityResult activityResult = new ActivityResult();
		
		// Setting Values
		activityResult.setActivityResultId( rs.getInt("ACTIVITY_RESULT_ID") );
		activityResult.setGlobalUserId( rs.getInt("GLOBAL_USER_ID") );
		activityResult.setActivityId( rs.getInt("ACTIVITY_ID") );
		activityResult.setProviderId( rs.getInt("PROVIDER_ID") );
		activityResult.setCreditTypeId( rs.getInt("CREDIT_TYPE_ID") );
		activityResult.setCreditsEarned( rs.getDouble("CREDITS_EARNED") );
		activityResult.setProfileComplete( rs.getInt("PROFILE_COMPLETE") );
		activityResult.setDateCompleted( rs.getDate("DATE_COMPLETED") );
		activityResult.setEvaluationComplete(rs.getInt("EVAL_COMPLETE")); //EVAL_COMPLETE
		activityResult.setAccountStatusId( rs.getInt("ACCOUNT_STATUS_ID") );
		activityResult.setEvaluationRequired( rs.getInt("EVAL_REQUIRED") );
		activityResult.setTimespentComplete( rs.getInt("TIMESPENT_COMPLETE") );		
		
		activityResult.setSessionID( rs.getInt("SESSION_ID") );
		activityResult.setAttemptsNum( rs.getInt("ATTEMPTS_NUM") );
        activityResult.setUserAgent( rs.getString("USER_AGENT") );
        
		// returning ActivityInfo
		return activityResult;
	}
}

