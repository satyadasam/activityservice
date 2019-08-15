package com.  .activity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.  .activity.exception.InvalidObjectException;
import com.  .activity.processor.FormProcessor;
import com.  .activity.qna.model.UserResponse;
import com.  .registration.core.User;


@JsonIgnoreProperties
public class UserActivity extends UserResponse {
	
	@JsonIgnore
	private String formType;
	// Eval form user submits credits earned....
	private float creditsEarned;
	@JsonIgnore
	private FormProcessor formProcessor;
	@JsonIgnore
	private String userAgent;
	@JsonIgnore
	private User user;

	public UserActivity(){
		
	}	

	public void validate() throws InvalidObjectException{}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		
	}

	public FormProcessor getFormProcessor() {
		return formProcessor;
	}

	public void setFormProcessor(FormProcessor formProcessor) {
		this.formProcessor = formProcessor;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public float getCreditsEarned() {
		return creditsEarned;
	}

	public void setCreditsEarned(float creditsEarned) {
		this.creditsEarned = creditsEarned;
	}
	
}
