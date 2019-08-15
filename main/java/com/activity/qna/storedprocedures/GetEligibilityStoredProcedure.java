/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/

package com.  .activity.qna.storedprocedures;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameter;

import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .prof_utilities.util.db.OracleStoredProcedure;


public class GetEligibilityStoredProcedure extends OracleStoredProcedure {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Private static members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	protected static final String PARAM_ACTIVITY_ID = "PV_ID";
	
	protected static final String PARAM_ELIG_RESULT_SET = "PV_RESULT";
	


	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Constructors
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	/**
	 * 
	 * @param dataSource
	 */
	public GetEligibilityStoredProcedure(DataSource dataSource, String storedProcedureName, ResultSetExtractor eligibilityResultSetExtractor ) {

		this.setDataSource(dataSource);
		this.setSql(storedProcedureName);

		declareParameter(new SqlParameter(PARAM_ACTIVITY_ID, Types.INTEGER));
		declareOutCursorParameter(GetEligibilityStoredProcedure.PARAM_ELIG_RESULT_SET, eligibilityResultSetExtractor);		

		compile();
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Public methods
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	/**
	 * This method executes the stored procedure and returns the Questionnaire Object object Model
	 * 
	 * @param qnaId
	 * @param formId
	 * @return Questionnaire
	 */
	public HashMap execute(int activityId) throws QNADataAccessException {
		
		try {

			Map inParams = new HashMap();
			inParams.put(PARAM_ACTIVITY_ID, new Integer(activityId));
			
			Map map = super.execute(inParams);

			HashMap eligibilityMap = (HashMap) map.get(GetEligibilityStoredProcedure.PARAM_ELIG_RESULT_SET);
			
			return eligibilityMap;
			

		} catch (DataAccessException dae) {
			throw new QNADataAccessException(" --DataAccessException In Getting Questionnaire Object Info From DB in METHOD == execute(int qnaId, int formId), CLASS == GetEligibilityStoredProcedure-- ",	dae);
		} catch (Exception e) {
			throw new QNADataAccessException( " --General Exception In METHOD == execute(int qnaId, int formId), CLASS = GetEligibilityStoredProcedure-- ", e);
		}
	}

}
