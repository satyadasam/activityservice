package com.  .activity.qna.model;

import java.util.List;


/**
 * Abstracting the responses since we are now allowing for anonymous responses
 * @author pellington
 *
 */
public interface Response {

	public int getSiteId();
	public void setSiteId(int siteid);	
	public Integer getQuestionnaireId();
	public void setQuestionnaireId(Integer questionnaireId);
	public List<QuestionResponse> getQuestionResponses();
	public void setQuestionResponses(List<QuestionResponse> questionResponseList);
	public List<QuestionResponse> getQuestionResponseList( int questionid );
	
}
