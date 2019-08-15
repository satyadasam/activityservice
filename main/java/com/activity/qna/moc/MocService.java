package com.  .activity.qna.moc;

import com.  .activity.qna.moc.entity.MocActivity;
import com.  .activity.qna.model.DBActivityResult;

/**
 * Interface for MOC activity processing
 * @author pellington
 */
public interface MocService {	
	
	public boolean checkIfMOCActivity(int activityId);

	public boolean checkIfQuestionnaireIsMOC(int questionnaireId);

	public MocActivity getMocActivity(int qnaId);
	
	public boolean saveMocActivity(DBActivityResult activity);
	
	public void saveMocActivity(int qnaId, long guid, int sessionID, String userAgent);
	
}
