package com.  .activity.processor;

import org.springframework.http.ResponseEntity;

import com.  .activity.exception.ProcessorException;
import com.  .activity.model.AppResponse;
import com.  .activity.model.UserActivity;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .qnaservice.entity.Questionnaire;




/**
 * @author SSangapalli
 * 
 * All FormProcessors will implement this interface
 */
public interface FormProcessor {
	
	
 public ResponseEntity<AppResponse> processRequest(UserActivity request,Questionnaire questionnaire) throws ProcessorException,QNADataAccessException;

}
