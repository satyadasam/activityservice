package com.  .activity.model;



import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.  .activity.processor.FormProcessor;
import com.  .activity.qna.model.UserResponse;
import com.  .registration.core.User;


/**
 * Composite Request communication object to hold request-relevant objects.
 *
 * @author SSangapalli
 *
 */
@JsonIgnoreProperties
public class AppRequest extends UserResponse {

	private HttpServletRequest request;
	private MultipartHttpServletRequest multipartRequest;	
	private String formType;	
	private int formId;
	private float creditsEarned;	
	private User user;
	
	

	public FormProcessor formProcessor;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public MultipartHttpServletRequest getMultipartRequest() {
		return multipartRequest;
	}

	public void setMultipartRequest(MultipartHttpServletRequest multipartRequest) {
		this.multipartRequest = multipartRequest;
	}	

	public FormProcessor getFormProcessor() {
		return formProcessor;
	}

	public void setFormProcessor(FormProcessor formProcessor) {
		this.formProcessor = formProcessor;
	}
	
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}
	

	public float getCreditsEarned() {
		return creditsEarned;
	}

	public void setCreditsEarned(float creditsEarned) {
		this.creditsEarned = creditsEarned;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
