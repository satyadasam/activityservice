package com.  .activity.qna.moc.dao;

import com.  .activity.qna.moc.entity.MocActivity;
import com.  .activity.qna.moc.exception.MocException;
import com.  .activity.qna.model.DBActivityResult;

public interface iMocActivityDAO {
	
	public boolean checkIfMOCActivity(int activityId);
	
	public boolean checkIfQuestionnaireIsMOC(int questionnaire);
	
	public boolean checkIfUserhasAbim(int guid);

	public MocActivity getActivityInfo(int qnaId) throws MocException;
	
	public int saveMocActivityResult(MocActivity mocActivity, DBActivityResult activityInfo) throws MocException;
	
	public DBActivityResult getInProgressActivityResult(int qnaId, long guid) throws MocException;
}
