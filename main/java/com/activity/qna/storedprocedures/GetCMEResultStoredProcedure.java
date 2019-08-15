/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/

package com.  .activity.qna.storedprocedures;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;

import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.model.ActivityResult;
import com.  .prof_utilities.util.AppLogger;
import com.  .prof_utilities.util.db.OracleStoredProcedure;




/**
 * The Class extends org.springframework.jdbc.object.StoredProcedure
 * It executes the Stored procedure to Get the CME Result stored in
 * activity result table. The execute() method in the class calls the
 * execute() method in the super class i.e. org.springframework.jdbc.
 * object.StoredProcedure class. The result of the stored procedure is 
 * read using org.springframework.jdbc.core.RowMapper. 
 * 
 * @author vagupta
 * 
 * @version 1.0
 *
 */
public class GetCMEResultStoredProcedure extends OracleStoredProcedure {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Private static members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private static final String PARAM_QUESTIONNAIRE_ID = "PV_QNA_ID";
	private static final String PARAM_GUID = "PV_GUID";
	private static final String PARAM_CME_RESULT_RESULT_SET = "PV_RESULT";

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
	public GetCMEResultStoredProcedure(DataSource dataSource,
			String storedProcedureName, RowMapper cmeResultRowMapper) {

		this.setDataSource(dataSource);
		this.setSql(storedProcedureName);

		// Declaring IN Parameters
		declareParameter(new SqlParameter(
				GetCMEResultStoredProcedure.PARAM_GUID, Types.NUMERIC));
		declareParameter(new SqlParameter(
				GetCMEResultStoredProcedure.PARAM_QUESTIONNAIRE_ID,
				Types.INTEGER));
		

		// Declaring OUT Parameters
		declareOutCursorParameter(
				GetCMEResultStoredProcedure.PARAM_CME_RESULT_RESULT_SET,
				cmeResultRowMapper);

		// Compile the procedure
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
	 * This method executes the stored procedure and returns the Activity Result
	 * object Model
	 * 
	 * @param qnaId
	 * @param guid
	 * @return ActivityResult
	 */
	public ActivityResult execute(int qnaId, long guid)
			throws QNADataAccessException {

		AppLogger.debug( GetCMEResultStoredProcedure.class, "execute", "--- questionnaireID = " + qnaId );
		AppLogger.debug( GetCMEResultStoredProcedure.class, "execute", "--- guid = " + guid );
		
		try {
			
			Map inParams = new HashMap();
			inParams.put(PARAM_GUID, new Long(guid));
			inParams.put(PARAM_QUESTIONNAIRE_ID, new Integer(qnaId));
			

			Map map = super.execute(inParams);

			// Get the questionResponseList List information
			List cmeResultList = (List) map
					.get(GetCMEResultStoredProcedure.PARAM_CME_RESULT_RESULT_SET);

			int count = (cmeResultList != null) ? cmeResultList.size() : 0;

			if (count > 1) {
				/*
				 * no records or more than one record is found for input
				 * legacyid and siteStatus
				 */
				throw new RuntimeException(
						count
								+ " record(s) found In Activity Result. Expected 1. qnaId="
								+ qnaId + " guid=" + guid);
				
			} else if (count == 0){
				
				// No Records Found
				return null;
				
			}else{	

				// one and only record is found.
				return (ActivityResult) cmeResultList.get(0);
			}

		} catch (DataAccessException dae) {

			throw new QNADataAccessException(
					" --DataAccessException In Getting CME Result Info From DB in METHOD == execute( int qnaId, int formId ), CLASS == GetCMEResultStoredProcedure-- ",
					dae);

		} catch (Exception e) {

			throw new QNADataAccessException(
					" --General Exception In Assembling CME Result Object in METHOD == execute( int qnaId, int formId ), CLASS == GetCMEResultStoredProcedure-- ",
					e);
		}
	}
}
