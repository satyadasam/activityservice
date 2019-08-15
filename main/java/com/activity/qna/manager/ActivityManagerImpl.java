/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/

package com.  .activity.qna.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.  .activity.qna.dao.JDBCActivityDAO;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.exception.manager.QNAManagerException;
import com.  .activity.qna.moc.MocServiceImpl;
import com.  .activity.qna.model.ActivityResult;
import com.  .activity.qna.model.DBActivityResult;
import com.  .activity.qna.model.DBUpdateActivityResult;
import com.  .prof_utilities.util.AppLogger;
import com.  .activity.qna.messaging.KafkaMessageProducer;
import com.  .registration.core.Address;
import com.  .registration.core.Profession;
import com.  .registration.core.User;


/**
 * This Class serves as a Manager For Activity Object. The responsibilities of
 * this class are (but not limited to) 1. Get the ActivityResult Object from DB.
 * 2. Create Activity Result Row
 *
 * @author vagupta
 */

public class ActivityManagerImpl implements IActivityManager {

	public ActivityManagerImpl() {}

	private JDBCActivityDAO jdbcActivityDAO;
	private MocServiceImpl mocServiceImpl;
	private KafkaMessageProducer kafkaMessageProducer;



	/*
	 * (non-Javadoc)
	 * @see com.  .qna.core.manager.IActivityManager#getActivityResult(int, int)
	 */
	public ActivityResult getActivityResult(long guid, int qnaId) throws QNAManagerException {

		AppLogger.debug( ActivityManagerImpl.class, "getActivityResult", "--- questionnaireID = " + qnaId );
		AppLogger.debug( ActivityManagerImpl.class, "getActivityResult", "--- guid = " + guid );
		ActivityResult activityResult;

		try {
			activityResult = jdbcActivityDAO.getCMEResult(qnaId, guid);
			if( null != activityResult ){
				AppLogger.debug( ActivityManagerImpl.class, "getActivityResult", "--- Returning ActivityResult = " + activityResult.toString());
			}
			return activityResult;

		} catch (QNADataAccessException qdae) {
			throw new QNAManagerException(
					" --Exception while retrieving the CME Result record from DB in METHOD == getActivityResult(long guid, int qnaId), CLASS == ActivityManagerImpl ",
					qdae);
		}catch( Exception e ){
			throw new QNAManagerException(
					" --General Exception while retrieving the CME Result record from DB in METHOD == getActivityResult(long guid, int qnaId), CLASS == ActivityManagerImpl ",
					e);
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.  .qna.core.manager.IActivityManager#saveActivityResult(int,
	 * int, User)
	 */
	public int saveActivityResult( int qnaId, int pass, int profileComplete, int sessionID, User user, String userAgent) throws QNAManagerException {

		AppLogger.debug( ActivityManagerImpl.class, "saveActivityResult", "--- questionnaireID = " + qnaId );
		AppLogger.debug( ActivityManagerImpl.class, "saveActivityResult", "--- profileComplete = " + profileComplete );

		DBActivityResult dbActivityResult = new DBActivityResult();

		dbActivityResult.setQnaId( qnaId );
		dbActivityResult.setPass( pass );
		dbActivityResult.setProfileComplete( Integer.toString(profileComplete) );
		dbActivityResult.setSessionID(sessionID);
		dbActivityResult.setUserAgent(userAgent);

		try {

			if( null != user ){

				dbActivityResult.setGuid( user.getGlobalUserID() );
				Profession profession = user.getProfession();
				Object addressObject = user.getAddress().get(0);

				if( null != addressObject ){
					Address address = (Address)addressObject;
					dbActivityResult.setCountryAbbrev( address.getCountryAbbreviation() );
				}

				if( null != profession ){
					dbActivityResult.setDegreeId( profession.getDegreeId() );
					Object occupationIdObject = profession.getOccupationId();

					if( null != occupationIdObject ){
						dbActivityResult.setOccupationId( ((Integer)occupationIdObject).intValue() );
					}

					dbActivityResult.setProfessionId( profession.getProfessionId() );
					dbActivityResult.setSpecialtyId(profession.getSpecialtyId());
				}
			}

			AppLogger.debug( ActivityManagerImpl.class, "saveActivityResult", "--- DBActivityResult = " + dbActivityResult.toString() );
			return jdbcActivityDAO.saveCMEResult( dbActivityResult );

		} catch (QNADataAccessException qdae) {

			throw new QNAManagerException(
					" --QNADataAccessException while Saving the CME Result record Into DB in METHOD == getActivityResult(long guid, int profileComplete, User user), CLASS == ActivityManagerImpl-- ",
					qdae);
		} catch (Exception e){
			throw new QNAManagerException(
					" --General Exception while Saving the CME Result record Into DB in METHOD == getActivityResult(long guid, int profileComplete, User user), CLASS == ActivityManagerImpl-- ",
					e);
		}

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.  .qna.core.manager.IActivityManager#saveActivityResult(int,
	 * int, User)
	 */
	public int saveActivityResult(int qnaId, int pass, int profileComplete, int sessionID, User user, String userAgent, boolean isMocEligible) throws QNAManagerException {

		DBActivityResult dbActivityResult = new DBActivityResult();
		dbActivityResult.setQnaId( qnaId );
		dbActivityResult.setPass( pass );
		dbActivityResult.setProfileComplete( Integer.toString(profileComplete) );
		dbActivityResult.setSessionID(sessionID);
		dbActivityResult.setUserAgent(userAgent);

		//Create DB Activity obj but pass processing to other saveActivityResult method
		//ON return, process MOC
		if( null != user ){
			dbActivityResult.setGuid( user.getGlobalUserID() );
			Profession profession = user.getProfession();
			Object addressObject = user.getAddress().get(0);
			if( null != addressObject ){
				Address address = (Address)addressObject;
				dbActivityResult.setCountryAbbrev( address.getCountryAbbreviation() );
			}
			if( null != profession ){
				int degreeId = profession.getDegreeId();
				if (degreeId < 0)//sometimes if the user did not enter degreeid, reg_core could return -1 as degree id. In that case changing it to 0.
					degreeId = 0;
				dbActivityResult.setDegreeId(degreeId);
				Object occupationIdObject = profession.getOccupationId();
				if( null != occupationIdObject ){
					dbActivityResult.setOccupationId( ((Integer)occupationIdObject).intValue() );
				}
				dbActivityResult.setProfessionId( profession.getProfessionId() );
				dbActivityResult.setSpecialtyId(profession.getSpecialtyId());
			}
		}

		int result = saveActivityResult(qnaId, pass, profileComplete, sessionID, user, userAgent);


		if (isMocEligible) {
			mocServiceImpl.saveMocActivity(dbActivityResult);
		}
		if (pass >= 1) {//submit iff user passed the test.
			sendMessageToPars(new Long(user.getGlobalUserID()), qnaId);
		}

		return result;



	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.  .qna.core.manager.IActivityManager#getActivityResult(int,
	 * int)
	 */
	public int updateActivityResult( int qnaId, long guid, int sessionID, Map fieldMap, String userAgent) throws QNAManagerException {

		AppLogger.debug( ActivityManagerImpl.class, "updateActivityResult", "--- questionnaireID = " + qnaId );
		AppLogger.debug( ActivityManagerImpl.class, "updateActivityResult", "--- guid = " + guid );
		List<DBUpdateActivityResult> fieldList = new ArrayList<DBUpdateActivityResult>();

		if( null != fieldMap ){
			Set fieldKeySet = fieldMap.keySet();
			Iterator iterator = fieldKeySet.iterator();
			// Iterate over keyset to get values for DBUpdateActivityResult obj
			while( iterator.hasNext() ){

				String key = (String)iterator.next();
				String value = (String)fieldMap.get(key);

				// Create a new DBUpdateActivityResult object
				DBUpdateActivityResult dbUpdateActivityResult = new DBUpdateActivityResult();
				dbUpdateActivityResult.setFieldName(key);
				dbUpdateActivityResult.setFieldValue(value);
				AppLogger.debug( ActivityManagerImpl.class, "updateActivityResult", "--- DBUpdateActivityResult = " + dbUpdateActivityResult.toString() );

				// Add DBUpdateActivityResult to the List
				fieldList.add(dbUpdateActivityResult);
			}
		}

		try {
			return jdbcActivityDAO.updateCMEResult(qnaId, guid, sessionID, fieldList, userAgent);

		} catch (QNADataAccessException qdae) {
			throw new QNAManagerException(
					" --Exception while updating the CME Result record from DB in METHOD == updateActivityResult(int qnaId, long guid, Map fieldMap), CLASS == ActivityManagerImpl ",
					qdae);
		}catch( Exception e ){
			throw new QNAManagerException(
					" --General Exception while updating the CME Result record from DB in METHOD == updateActivityResult(int qnaId, long guid, Map fieldMap), CLASS == ActivityManagerImpl ",
					e);
		}

	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.  .qna.core.manager.IActivityManager#getActivityResult(int,
	 * int)
	 */
	public int updateActivityResult( int qnaId, long guid, int sessionID, Map fieldMap, String userAgent, boolean isMocEligible) throws QNAManagerException {

		String passVal = "0";
		passVal = fieldMap != null && fieldMap.get("pass") != null ? fieldMap.get("pass").toString() : passVal;
		fieldMap.remove("pass");

		//Call original updateActivityResult
		int result = updateActivityResult(qnaId, guid, sessionID, fieldMap, userAgent);

		//Then process MOC
		if(isMocEligible){
			mocServiceImpl.saveMocActivity(qnaId, guid, sessionID, userAgent);
		}

		if (passVal != null && new Integer(passVal) >= 1) {//submit iff user passed the test.
			sendMessageToPars(guid, qnaId);
		}

		return result;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.  .qna.core.manager.IActivityManager#getActivityResult(int,
	 * int)
	 */
	public HashMap getActivityEligibilities( int activityId )
			throws QNAManagerException {

		AppLogger.debug( ActivityManagerImpl.class, "getActivityEligibilities", "--- activityId = " + activityId );

		HashMap activityEligibilities;

		try {

			activityEligibilities = jdbcActivityDAO.getActivityEligibilities(activityId);

			return activityEligibilities;

		} catch (QNADataAccessException qdae) {

			throw new QNAManagerException(
					" --Exception while retrieving the ActivityEligibilities record from DB in METHOD == getActivityEligibility(int activityId), CLASS == ActivityManagerImpl ",
					qdae);
		}catch( Exception e ){

			throw new QNAManagerException(
					" --General Exception while retrieving the ActivityEligibilities Result record from DB in METHOD == getActivityEligibility(int activityId), CLASS == ActivityManagerImpl ",
					e);
		}

	}

	public HashMap getActivityEligibilitiesByArticleId( int articleId )	throws QNAManagerException {

		AppLogger.debug( ActivityManagerImpl.class, "getActivityEligibilities", "--- articleId = " + articleId );
		try {
			return jdbcActivityDAO.getActivityEligibilitiesByArticleId(articleId);
		} catch (QNADataAccessException qdae) {

			throw new QNAManagerException(
					" --Exception while retrieving the ActivityEligibilities record from DB in METHOD == getActivityEligibility(int activityId), CLASS == ActivityManagerImpl ",
					qdae);
		}catch( Exception e ){

			throw new QNAManagerException(
					" --General Exception while retrieving the ActivityEligibilities Result record from DB in METHOD == getActivityEligibility(int activityId), CLASS == ActivityManagerImpl ",
					e);
		}

	}


	public HashMap getActivityEligibilitiesByQuestionnaireId( int questionnaireId )	throws QNAManagerException {

		AppLogger.debug( ActivityManagerImpl.class, "getActivityEligibilities", "--- articleId = " + questionnaireId );
		try {
			return jdbcActivityDAO.getActivityEligibilitiesByQuestionnaireId(questionnaireId);
		} catch (QNADataAccessException qdae) {

			throw new QNAManagerException(
					" --Exception while retrieving the ActivityEligibilities record from DB in METHOD == getActivityEligibility(int activityId), CLASS == ActivityManagerImpl ",
					qdae);
		}catch( Exception e ){

			throw new QNAManagerException(
					" --General Exception while retrieving the ActivityEligibilities Result record from DB in METHOD == getActivityEligibility(int activityId), CLASS == ActivityManagerImpl ",
					e);
		}

	}


	public JDBCActivityDAO getJdbcActivityDAO() {
		return jdbcActivityDAO;
	}
	public void setJdbcActivityDAO(JDBCActivityDAO jdbcActivityDAO) {
		this.jdbcActivityDAO = jdbcActivityDAO;
	}
	public void setMocServiceImpl(MocServiceImpl mocServiceImpl) {
		this.mocServiceImpl = mocServiceImpl;
	}

	public KafkaMessageProducer getKafkaMessageProducer() {
		return kafkaMessageProducer;
	}

	public void setKafkaMessageProducer(KafkaMessageProducer kafkaMessageProducer) {
		this.kafkaMessageProducer = kafkaMessageProducer;
	}

	private void sendMessageToPars(Long globalUserID, int qnaId) throws QNAManagerException {
		ActivityResult activityResult = getActivityResult(globalUserID, qnaId);
		String jsonString = "{\"activityId\":\""+activityResult.getActivityId()+"\",\"globalUserId\":\""+globalUserID.toString()+"\"}";
		kafkaMessageProducer.sendMessage("pars", jsonString);
	}


}
