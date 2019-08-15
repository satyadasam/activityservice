/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/


package com.  .activity.qna.model;

import java.util.ArrayList;
import java.util.List;
import com.  .activity.util.AppConstants;


/**
 * This class defines the DataModel for AnonymousResponse. 
 * The class defines the required parameters and 
 * their corrosponding Getters and Setters.
 * 
 * @version 1.0
 */
public class AnonymousResponse implements Response{

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * data members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private String sessionId;
	private int siteId = AppConstants.SITE_ID_MEDSCAPE_WWW; // if not set, use default value for 'Medscape-www'
	private Integer questionnaireId;
	private List questionResponseList;
	
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Constructors.
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	public AnonymousResponse(){
		this.questionResponseList = new ArrayList();
	}


	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteid) {
		this.siteId = siteid;
	}
	
	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public List getQuestionResponses() {
		return questionResponseList;
	}

	public void setQuestionResponses(List questionResponseList) {
		this.questionResponseList = questionResponseList;
	}
	
	
	
	/**
	 * This methods returns the question response object.
	 * 
	 * @param questionid
	 * @return List of QuestionResponse or NULL instead
	 * 
	 */
	public List getQuestionResponseList(int questionid){
		
		List responseList = new ArrayList();		
		// Verifying the Method Contract - Question Id cannot be 0
		if( questionid == 0 ){
			return null;
		}
		
		// Iterating over the Question Response List
		for( int i = 0; i < questionResponseList.size(); i++ ){			
			// Getting the question response object
			QuestionResponse qr = (QuestionResponse)questionResponseList.get(i);			
			if( qr.getQuestionId() == questionid ){				
				responseList.add(qr);				
				// To support 'multi-selected choices', do not return list here
				//return responseList;
			}
		}
		
		// no question response found with that question id. means either user have not responded to this question or
		// this question is not a part of form at all.
		return responseList;
	}

    @Override
    public String toString() {
        return "UserResponse{" +
                "sessionId=" + sessionId +
                ", siteid=" + siteId +
                ", questionnaireId=" + questionnaireId +
                ", questionResponseList=" + questionResponseList +
                '}';
    }
}
