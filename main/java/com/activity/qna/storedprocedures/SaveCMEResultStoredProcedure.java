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
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.model.DBActivityResult;
import com.  .prof_utilities.util.AppLogger;



/**
 * This class is the stored procedure invocation class.<SaveCMEResultStoredProcedure>
 * The Class extends org.springframework.jdbc.object.StoredProcedure
 * It executes the Stored procedure to Save the CME Results.
 * The execute() method in the class calls the execute() method in the 
 * super class i.e. org.springframework.jdbc. object.StoredProcedure class.
 * 
 * @author Vikas Gupta
 * @version 1.0 
 */
public class SaveCMEResultStoredProcedure extends StoredProcedure {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Private static members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */																	
																						// DB TYPES
	private static final String PARAM_PV_QNA_ID = "PV_QNA_ID"; 							// NUMBER
	private static final String PARAM_PV_GUID = "PV_GUID";								// NUMBER
	private static final String PARAM_PV_PROFILE = "PV_PROFILE";						// VARCHAR
	private static final String PARAM_PV_PROF_ID = "PV_PROF_ID";						// NUMBER
	private static final String PARAM_PV_OCP_ID = "PV_OCP_ID";							// NUMBER
	private static final String PARAM_PV_DEGREE_ID = "PV_DEGREE_ID";					// NUMBER
	private static final String PARAM_PV_SPECILTY_ID = "PV_SPECILTY_ID";				// NUMBER
	private static final String PARAM_PV_COUNTRY = "PV_COUNTRY";						// NUMBER
	private static final String PARAM_PV_PASS = "PV_PASS";								// NUMBER
	private static final String PARAM_PV_SESSION_ID = "PV_SESSION_ID";					// NUMBER
    private static final String PARAM_PV_USER_AGENT = "PV_USER_AGENT";					// VARCHAR

	private static final String PARAM_SAVE_CME_RESULT_RESPONSE_INDICATOR = "PV_IND"; 	// NUMBER
	
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
	public SaveCMEResultStoredProcedure(DataSource dataSource,
			String storedProcedureName) {

		this.setDataSource(dataSource);
		this.setSql(storedProcedureName);

		// Declaring IN Parameters
		declareParameter(new SqlParameter(
				SaveCMEResultStoredProcedure.PARAM_PV_QNA_ID,
				Types.INTEGER));
		declareParameter(new SqlParameter(
				SaveCMEResultStoredProcedure.PARAM_PV_GUID, Types.INTEGER));
		declareParameter(new SqlParameter(
				SaveCMEResultStoredProcedure.PARAM_PV_PROFILE, Types.VARCHAR));		
		declareParameter(new SqlParameter(
				SaveCMEResultStoredProcedure.PARAM_PV_PROF_ID, Types.INTEGER));
		declareParameter(new SqlParameter(
				SaveCMEResultStoredProcedure.PARAM_PV_OCP_ID, Types.INTEGER));
		declareParameter(new SqlParameter(
				SaveCMEResultStoredProcedure.PARAM_PV_DEGREE_ID, Types.INTEGER));
		declareParameter(new SqlParameter(
				SaveCMEResultStoredProcedure.PARAM_PV_SPECILTY_ID, Types.INTEGER));
		declareParameter(new SqlParameter(
				SaveCMEResultStoredProcedure.PARAM_PV_COUNTRY, Types.VARCHAR));
		declareParameter(new SqlParameter(
				SaveCMEResultStoredProcedure.PARAM_PV_PASS, Types.INTEGER));
		declareParameter(new SqlParameter(
				SaveCMEResultStoredProcedure.PARAM_PV_SESSION_ID, Types.INTEGER));
        declareParameter(new SqlParameter(
                PARAM_PV_USER_AGENT, Types.VARCHAR));


        // Declaring OUT Parameters
		declareParameter(new SqlOutParameter( SaveCMEResultStoredProcedure.PARAM_SAVE_CME_RESULT_RESPONSE_INDICATOR, Types.INTEGER));

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
	 * This method executes the stored procedure and saves the ActivityResult
	 * object Model
	 * 
	 * @param DBActivityResult
	 * @return int
	 */
	public int execute( DBActivityResult dbActivityResult )
			throws QNADataAccessException {
		
		AppLogger.debug( SaveCMEResultStoredProcedure.class, "execute", "--- DBActivityResult = " + dbActivityResult.toString() );
		
		try {
			
			Map inParams = new HashMap();
			inParams.put(PARAM_PV_QNA_ID, new Integer(dbActivityResult.getQnaId()));
			inParams.put(PARAM_PV_GUID, new Long(dbActivityResult.getGuid()));
			inParams.put(PARAM_PV_PROFILE, dbActivityResult.getProfileComplete());			
			inParams.put(PARAM_PV_PROF_ID, new Integer(dbActivityResult.getProfessionId()));
			inParams.put(PARAM_PV_OCP_ID, new Integer(dbActivityResult.getOccupationId()));			
			inParams.put(PARAM_PV_DEGREE_ID, new Integer(dbActivityResult.getDegreeId()));
			inParams.put(PARAM_PV_SPECILTY_ID, new Integer(dbActivityResult.getSpecialtyId()));
			inParams.put(PARAM_PV_COUNTRY, dbActivityResult.getCountryAbbrev());
			inParams.put(PARAM_PV_PASS, new Integer(dbActivityResult.getPass()));
			inParams.put(PARAM_PV_SESSION_ID, new Integer(dbActivityResult.getSessionID()));
            inParams.put(PARAM_PV_USER_AGENT, dbActivityResult.getUserAgent());

			Map map = super.execute(inParams);
			
			Integer result = (Integer)map.get(PARAM_SAVE_CME_RESULT_RESPONSE_INDICATOR);

			return result.intValue();

		} catch (DataAccessException dae) {

			throw new QNADataAccessException(
					" DataAccessException In Saving Activity Result Info Into DB in METHOD == execute(DBActivityResult dbActivityResult), CLASS == SaveCMEResultStoredProcedure",
					dae);

		} catch (Exception e) {
			
			throw new QNADataAccessException(
					" General Exception In Saving Activity Result Info Into DB in METHOD == execute(DBActivityResult dbActivityResult), CLASS == SaveCMEResultStoredProcedure",
					e);
		} 
	}
}
