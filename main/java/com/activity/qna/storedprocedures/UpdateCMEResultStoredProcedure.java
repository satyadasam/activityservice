/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/

package com.  .activity.qna.storedprocedures;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .prof_utilities.util.AppLogger;
import com.  .prof_utilities.util.db.OracleStoredProcedure;



/**
 * This class is the stored procedure invocation class.<UpdateCMEResultStoredProcedure>
 * The Class extends org.springframework.jdbc.object.StoredProcedure
 * It executes the Stored procedure to Update the CME Results.
 * The execute() method in the class calls the execute() method in the 
 * super class i.e. org.springframework.jdbc. object.StoredProcedure class.
 * 
 * @author Vikas Gupta
 * @version 1.0 
 */
public class UpdateCMEResultStoredProcedure extends OracleStoredProcedure {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Private static members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private static final String PARAM_QUESTIONNAIRE_ID = "PV_QNA_ID";
	private static final String PARAM_GUID = "PV_GUID";
	private static final String PARAM_TYPE_FIELD_TBL_ARRAY = "PV_FIELD";
	private static final String PARAM_PV_SESSION_ID = "PV_SESSION_ID";
    private static final String PARAM_PV_USER_AGENT = "PV_USER_AGENT";

    private static final String PARAM_UPDATE_CME_RESULT_INDICATOR = "PV_IND";
	
	private static final String PV_TYPE_FIELD_TBL_FIELD_TYPE = "TYPE_FIELD_TBL";
	
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Private static members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	public static final String TYPE_FIELD_OBJECT_TYPE = "TYPE_FIELD";

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
	public UpdateCMEResultStoredProcedure(DataSource dataSource,
			String storedProcedureName) {

		this.setDataSource(dataSource);
		this.setSql(storedProcedureName);

		// Declaring IN Parameters
		declareParameter(new SqlParameter(
				UpdateCMEResultStoredProcedure.PARAM_QUESTIONNAIRE_ID,
				Types.INTEGER));
		declareParameter(new SqlParameter(
				UpdateCMEResultStoredProcedure.PARAM_GUID, Types.NUMERIC));
		declareParameter(new SqlParameter(UpdateCMEResultStoredProcedure.PARAM_TYPE_FIELD_TBL_ARRAY,
				Types.ARRAY));
		declareParameter(new SqlParameter(
				UpdateCMEResultStoredProcedure.PARAM_PV_SESSION_ID, Types.INTEGER));
        declareParameter(new SqlParameter(
                UpdateCMEResultStoredProcedure.PARAM_PV_USER_AGENT, Types.VARCHAR));

		// Declaring OUT Parameters
		declareParameter(new SqlOutParameter(
				UpdateCMEResultStoredProcedure.PARAM_UPDATE_CME_RESULT_INDICATOR, Types.INTEGER));

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
	 * This method executes the stored procedure and updates the Activity Result table
	 * object Model
	 * 
	 * @param UserResponse
	 * @return int
	 */
	public int execute( int qnaId, long guid, int sessionID, List fieldList, String userAgent )
			throws QNADataAccessException {
		
		AppLogger.debug( UpdateCMEResultStoredProcedure.class, "execute", "--- questionnaireID = " + qnaId );
		AppLogger.debug( UpdateCMEResultStoredProcedure.class, "execute", "--- guid = " + guid );
		
		if( null != fieldList )
			AppLogger.debug( UpdateCMEResultStoredProcedure.class, "execute", "--- fieldList Size = " + fieldList.size() );
		
		Connection conn = null;
		
		try {
			
			// Getting the connection from JDBC template
			conn = getJdbcTemplate().getDataSource().getConnection();

			Object fieldArray = createArrayParameter(UpdateCMEResultStoredProcedure.PV_TYPE_FIELD_TBL_FIELD_TYPE, conn, fieldList.toArray());
			// Creating the array descriptor
			
			Map inParams = new HashMap();
			inParams.put(PARAM_QUESTIONNAIRE_ID, new Integer(qnaId));
			inParams.put(PARAM_GUID, new Long(guid));			
			inParams.put(PARAM_TYPE_FIELD_TBL_ARRAY, fieldArray);
			inParams.put(PARAM_PV_SESSION_ID, new Integer(sessionID));
            inParams.put(PARAM_PV_USER_AGENT, userAgent);

			Map map = super.execute(inParams);
			
			Integer result = (Integer)map.get(PARAM_UPDATE_CME_RESULT_INDICATOR);

			return result.intValue();

		} catch (DataAccessException dae) {

			throw new QNADataAccessException(
					" --DataAccessException In Updating CME Result Info Into DB in METHOD == execute(int qnaId, long guid, List fieldList), CLASS == UpdateCMEResultStoredProcedure-- ",
					dae);

		} catch (Exception e) {
			
			throw new QNADataAccessException(
					" --General Exception In Updating CME Result Info Into DB in METHOD == execute(int qnaId, long guid, List fieldList), CLASS == UpdateCMEResultStoredProcedure-- ",
					e);
		} finally {

			// Closing the connection
			try {

				if (null != conn)
					conn.close();

			} catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}
		}
	}
}
