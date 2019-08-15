/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/

package com.  .activity.qna.model;

import java.sql.Date;

/**
 * This class defines the DataModel for Activity Result. It corresponds to
 * 'activity_result' table. The class defines the required parameters and their
 * corrosponding Getters and Setters.
 * 
 * @author Vikas Gupta
 * @version 1.0
 */
public class ActivityResult {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * data members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	private int activityResultId;
	private int globalUserId;
	private int activityId;
	private int providerId;
	private int creditTypeId;
	private double creditsEarned;
	private int profileComplete;
	private int professionid;
	private int specialtyid;
	private int occupationid;
	private Date dateCompleted;
	private Date createDate;
	private Date lastUpdatedDate;
	private int evaluationComplete;
	private int timespentComplete;
	private int accountStatusId;
	private int evaluationRequired;
	private int sessionID;
	private int attemptsNum;
    private String userAgent;

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Public methods ( Getters and Setters )
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public int getAttemptsNum() {
		return attemptsNum;
	}

	public void setAttemptsNum(int attemptsNum) {
		this.attemptsNum = attemptsNum;
	}

	public int getSpecialtyid() {
		return specialtyid;
	}

	public void setSpecialtyid(int specialtyid) {
		this.specialtyid = specialtyid;
	}

	public int getOccupationid() {
		return occupationid;
	}

	public void setOccupationid(int occupationid) {
		this.occupationid = occupationid;
	}

	public int getActivityResultId() {
		return activityResultId;
	}

	public void setActivityResultId(int activityResultId) {
		this.activityResultId = activityResultId;
	}

	public int getGlobalUserId() {
		return globalUserId;
	}

	public void setGlobalUserId(int globalUserId) {
		this.globalUserId = globalUserId;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public int getCreditTypeId() {
		return creditTypeId;
	}

	public void setCreditTypeId(int creditTypeId) {
		this.creditTypeId = creditTypeId;
	}

	public double getCreditsEarned() {
		return creditsEarned;
	}

	public void setCreditsEarned(double creditsEarned) {
		this.creditsEarned = creditsEarned;
	}

	public int getProfileComplete() {
		return profileComplete;
	}

	public void setProfileComplete(int profileComplete) {
		this.profileComplete = profileComplete;
	}

	public int getProfessionid() {
		return professionid;
	}

	public void setProfessionid(int professionid) {
		this.professionid = professionid;
	}

	public Date getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(Date dateCompleted) {
		this.dateCompleted = dateCompleted;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public int getEvaluationComplete() {
		return evaluationComplete;
	}

	public void setEvaluationComplete(int evaluationComplete) {
		this.evaluationComplete = evaluationComplete;
	}

	public int getAccountStatusId() {
		return accountStatusId;
	}

	public void setAccountStatusId(int accountStatusId) {
		this.accountStatusId = accountStatusId;
	}

	public int getEvaluationRequired() {
		return evaluationRequired;
	}

	public void setEvaluationRequired(int evaluationRequired) {
		this.evaluationRequired = evaluationRequired;
	}

	public int getTimespentComplete() {
		return timespentComplete;
	}

	public void setTimespentComplete(int timespentComplete) {
		this.timespentComplete = timespentComplete;
	}

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String toString() {

		StringBuffer sb = new StringBuffer();
		sb.append("-------------- ACTIVITY RESULT -------------------");
		sb.append("activityResultId = " + activityResultId);
		sb.append("::");
		sb.append("globalUserId = " + globalUserId);
		sb.append("::");
		sb.append("activityId = " + activityId);
		sb.append("::");
		sb.append("providerId = " + providerId);
		sb.append("::");
		sb.append("creditTypeId = " + creditTypeId);
		sb.append("::");
		sb.append("creditsEarned = " + creditsEarned);
		sb.append("::");
		sb.append("profileComplete = " + profileComplete);
		sb.append("::");
		sb.append("professionid = " + professionid);
		
		sb.append("::");
		sb.append("specialtyid = " + specialtyid);
		sb.append("::");
		sb.append("occupationid = " + occupationid);
		sb.append("::");
		sb.append("evaluationComplete = " + evaluationComplete);
		sb.append("::");
		sb.append("timespentComplete = " + timespentComplete);
		sb.append("::");
		sb.append("accountStatusId = " + accountStatusId);
		sb.append("::");
		sb.append("evaluationRequired = " + evaluationRequired);
        sb.append("::");
        sb.append("userAgent = " + userAgent);

		sb.append("----------------------------------------------------------");

		return sb.toString();

	}

}
