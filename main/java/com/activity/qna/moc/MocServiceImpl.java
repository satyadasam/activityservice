package com.  .activity.qna.moc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.  .activity.qna.messaging.KafkaMessageProducer;
import com.  .activity.qna.moc.dao.MocActivityDAOImpl;
import com.  .activity.qna.moc.entity.MocActivity;
import com.  .activity.qna.moc.exception.MocException;
import com.  .activity.qna.model.DBActivityResult;




/**
 * Implementation class for MOC activity processing
 * @author pellington
 */
public class MocServiceImpl implements MocService {

	private static final Log log = LogFactory.getLog(MocServiceImpl.class);
	
	MocActivityDAOImpl mocActivityDAOImpl;
	
	/**
	 * Looks up an activity to determine if it is an MOC activity
	 * @param activityId
	 * @return
	 */
	public boolean checkIfMOCActivity(int activityId) {
		return mocActivityDAOImpl.checkIfMOCActivity(activityId);
	}
	
	
	/**
	 * Looks up an activity to determine if it is an MOC activity
	 * @param questionnaireId
	 * @return
	 */
	public boolean checkIfQuestionnaireIsMOC(int questionnaireId) {
		return mocActivityDAOImpl.checkIfQuestionnaireIsMOC(questionnaireId);
	}

	/**
	 * Saves record to ACTIVITY_RESULT table with MOC credit ID (9) and status ID 9
	 * Then sends message off to kafka queue to send activity info to PARS/ABIM
	 * @param
	 */
	@Override
	public boolean saveMocActivity(DBActivityResult activity) {
		
		MocActivity mocActivityInfo = null;
		try {
			//1. Get activityID
			mocActivityInfo = mocActivityDAOImpl.getActivityInfo(activity.getQnaId());
			
			//2. Save record
			mocActivityDAOImpl.saveMocActivityResult(mocActivityInfo, activity);

			//3. Send message to PARS Kafka topic
			//commenting below logic , the msg to KAKFKA is now sent by activityManagerImpl as going forward this is not a MOC specfic feature
			//all completed cme activity will be sent to kafka
			/*if(sendToPars){
				String jsonString = "{\"activityId\":\""+mocActivityInfo.getActivityId()+"\",\"globalUserId\":\""+new Long(activity.getGuid()).toString()+"\"}";
				kafkaMessageProducer.sendMessage("pars", jsonString);
			}*/
			return true;
		} catch (MocException e) {
			log.error("Exception saving MOC activity to DB: "+ e.getMessage());
			//TODO how do we handle this
			return false;
		}
		
	}
	

	/**
	 * Saves an Moc activity to the ACTIVITY_RESULT table during the UPDATE activity work flow.  
	 * We don't insert an MOC record into ACTIVITY_RESULTS unless the test has been passed.  
	 * So if the user got questions wrong, we only add the CME credit that is in progress.
	 * When the user re-submits answers to test and passes, we then insert an MOC record.  
	 * This ensures backwards compatibility, maintaining only 1 record while in-progress
	 * 
	 * This method gets all info and passes to saveMocActivity(DBActivityResult) record to process
	 * @param
	 */
	@Override
	public void saveMocActivity(int qnaId, long guid, int sessionID, String userAgent) {
		
		try {
			DBActivityResult activity = mocActivityDAOImpl.getInProgressActivityResult(qnaId, guid);
			activity.setUserAgent(userAgent);
			activity.setSessionID(sessionID);
			activity.setPass(1);//it is being hard coded because this flow would be invoked only when the user passes the test.
			saveMocActivity(activity);
			
		} catch (MocException e) {
			log.error("Exception getting in-progress activity result record: "+ e.getMessage());
			//TODO how do we handle this
		}
	}

	/**
	 * Gets MocActivity info
	 * @param
	 * @return mocActivity
	 */
	@Override
	public MocActivity getMocActivity(int qnaId) {
		MocActivity activity = null;
		try {
			activity = mocActivityDAOImpl.getActivityInfo(qnaId);
		} catch (MocException e) {
			log.error("Exception getting MOC activity info: "+ e.getMessage());
			//TODO how do we handle this
		}
		return activity;
	}	


	public void setMocActivityDAOImpl(MocActivityDAOImpl mocActivityDAOImpl) {
		this.mocActivityDAOImpl = mocActivityDAOImpl;
	}

	
}
