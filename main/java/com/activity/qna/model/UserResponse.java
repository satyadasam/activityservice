/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/


package com.  .activity.qna.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.  .activity.util.AppConstants;



/**
 * This class defines the DataModel for UserResponse. 
 * The class defines the required parameters and 
 * their corrosponding Getters and Setters.
 * 
 * @author Vikas Gupta
 * @version 1.0
 */
public class UserResponse implements Response{
	

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * data members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	@JsonIgnore
	private long guid;
	private int siteId ; // if not set, use default value for 'Medscape-www'
	@NotNull
	private Integer questionnaireId;
	private List<QuestionResponse> questionResponses;
	@JsonIgnore
	private int specialtyId;
	@JsonIgnore
	private int professionId;
	@NotNull
	private Integer formId;
	
	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Constructors.
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	public UserResponse(){
		this.questionResponses = new ArrayList<QuestionResponse>();
	}

	 /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Public methods ( Getters and Setters )
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
	public long getGuid() {
		return guid;
	}

	public void setGuid(long guid) {
		this.guid = guid;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteid) {
		this.siteId = siteid;
		if(siteid==0){
			
			this.siteId=AppConstants.SITE_ID_MEDSCAPE_WWW;
		}
		
	}
	
	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	
	public int getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(int specialtyId) {
		this.specialtyId = specialtyId;
	}

	public int getProfessionId() {
		return professionId;
	}

	public void setProfessionId(int professionId) {
		this.professionId = professionId;
	}
	public List<QuestionResponse> getQuestionResponses() {
		return questionResponses;
	}

	public void setQuestionResponses(List<QuestionResponse> questionResponses) {
		this.questionResponses = questionResponses;
	}
	
	 /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Helper methods
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */



	/**
	 * This methods returns the question response object.
	 * 
	 * @param questionid
	 * @return List of QuestionResponse or NULL instead
	 * 
	 */
	public List<QuestionResponse> getQuestionResponseList( int questionid ){
		
		List<QuestionResponse> responseList = new ArrayList<QuestionResponse>();
		
		// Verifying the Method Contract
		// Question Id cannot be 0
		if( questionid == 0 )
			
			// Contract failed. Return NULL.
			return null;
		
		
		// Iterating over the Question Response List
		for( int i = 0; i < questionResponses.size(); i++ ){
			
			// Getting the question response object
			QuestionResponse qr = questionResponses.get(i);
			
			if( qr.getQuestionId() == questionid )
				
				responseList.add(qr);
				
				// To support 'multi-selected choices', do not return list here
				//return responseList;
			
		}
		
		// no question response found with that question id.
		// means either user have not responded to this question or
		// this question is not a part of form at all.
		return responseList;
	}

    @Override
    public String toString() {
        return "UserResponse{" +
                "guid=" + guid +
                ", siteid=" + siteId +
                ", questionnaireId=" + questionnaireId +
                ", questionResponseList=" + questionResponses +
                '}';
    }

	/*
		The below methods are used by CME PULSE mobile app , to support legacy mobile apps these setter methods
		has to be retained which passes over the values to siteId and questionResponses instance variables
	*/
	// DO NOT modify this method
	public void setSiteid(int siteid) {
		this.siteId = siteid;
	}
	// DO NOT modify this method
	public void setQuestionResponseList(List<QuestionResponse> questionResponseList) {
		this.questionResponses = questionResponseList;
	}


}
