package com.  .activity.controller;

import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.  .activity.model.*;
import com.  .activity.qna.model.UserResponse;
import com.  .activity.service.ActivityService;
import com.  .activity.util.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.  .activity.adaptor.QNAAdaptor;
import com.  .activity.exception.InvalidObjectException;
import com.  .activity.exception.ProcessorException;
import com.  .activity.exception.ProfileServiceException;
import com.  .activity.exception.QNAException;
import com.  .activity.exception.UnAuthorizedUserExcepton;
import com.  .activity.processor.FormProcessor;
import com.  .activity.processor.FormProcessorFactory;
import com.  .activity.qna.constants.FormTypeConstant;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.util.AppConstants;
import com.  .activity.util.ExceptionUtils;
import com.  .auth.unmarshal.UnmarshalException;
import com.  .profile_service.adapter.ProfileServiceAdapter;
import com.  .profile_service.adapter.UserUnmarshaller;
import com.  .profile_service.adapter.exception.ProfileServiceAdapterException;
import com.  .qnaservice.entity.QuestionForm;
import com.  .qnaservice.entity.Questionnaire;
import com.  .registration.core.User;

/**
 * @author SSangapalli  
 * Enpoints for all form types
 */

/**
 * @author SSangapalli
 *
 */
/**
 * @author SSangapalli
 *
 */
@RestController
public class ActivityController extends AbstractController {
	
	private static Logger log = LoggerFactory.getLogger(ActivityController.class);


	/***
	 * Common method for middleform/internalform/Evalfrom
	 * 1)
	 * 
	 * @param userActivity
	 * @param guid
	 * @param request
	 * @param userAgent
	 * @return
	 * @throws ProcessorException 
	 * @throws QNAException 
	 * @throws InvalidObjectException 
	 * @throws UnmarshalException 
	 * @throws ProfileServiceAdapterException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/save/useractivity", method = { RequestMethod.POST })
	ResponseEntity<AppResponse> saveUserActivity(@Valid @RequestBody UserActivity userActivity, HttpServletRequest request,
			@NotNull @ModelAttribute("guid") String guid, @RequestHeader("User-Agent") String userAgent)
					throws UnAuthorizedUserExcepton, ProcessorException, QNAException, InvalidObjectException, ProfileServiceException, UnmarshalException,QNADataAccessException {

		userActivity.setUserAgent(userAgent);
		prepareUserActivityRequest(userActivity, guid);
		
		Questionnaire questionnaire = null;
		
		
		try {
			questionnaire = qnaAdaptor.getQuestionnaireById(userActivity.getQuestionnaireId(), guid, userActivity.getSiteId(), false, Questionnaire.class);
		} catch (Exception ex) {
			log.error("Exception " + ExceptionUtils.getStackTrace(ex));
			throw new QNAException(ex.getMessage() +",  QuestionnaireId "+userActivity.getQuestionnaireId());
		}
		
		if((getFromType(questionnaire,userActivity.getFormId())!=null)){
			
			String formType=getFromType(questionnaire,userActivity.getFormId());				
			userActivity.setFormType(formType);
			this.createAppRequestProcessor(userActivity); 	
			return processAppRequest(userActivity, questionnaire);	

		}
		else{
			
			throw new QNAException("Unable to find Form in Questionnaire for FormId "+userActivity.getFormId());
			
		}		
	}
	
	
	
	
	
	/***
	 * EndPoint for PostForm
	 * 1)
	 * @param userActivity
	 * @param guid
	 * @param request
	 * @param userAgent
	 * @return
	 * @throws ProcessorException 
	 * @throws QNAException 
	 * @throws InvalidObjectException 
	 * @throws UnmarshalException 
	 * @throws ProfileServiceAdapterException 
	 * @throws UnAuthorizedUserExcepton, ProcessorException
	 */
		@RequestMapping(value = "/save/postUseractivity", method = { RequestMethod.POST})	
		ResponseEntity<AppResponse> savePostUserActivity(@Valid
				@RequestBody UserActivity userActivity, @RequestHeader("User-Agent") String userAgent, HttpServletRequest request, @NotNull @ModelAttribute("guid") String guid) throws UnAuthorizedUserExcepton, ProcessorException, QNAException, InvalidObjectException, ProfileServiceException, UnmarshalException,QNADataAccessException {
			
						
				userActivity.setUserAgent(userAgent);
				this.prepareUserActivityRequest(userActivity,guid);
				Questionnaire questionnaire = null;
				
				try{
					
					questionnaire=qnaAdaptor.getQuestionnaireById(userActivity.getQuestionnaireId(), String.valueOf(userActivity.getGuid()), userActivity.getSiteId(), false, Questionnaire.class);
				}
				catch(Exception ex){
					
					log.error("Exception "+ExceptionUtils.getStackTrace(ex));
					throw new QNAException(ex.getMessage() +",  QuestionnaireId "+userActivity.getQuestionnaireId());
				}
				if((getFromType(questionnaire,userActivity.getFormId())!=null)){
					
					String formType=getFromType(questionnaire,userActivity.getFormId());				
					userActivity.setFormType(formType);
					this.createAppRequestProcessor(userActivity); 	
					return processAppRequest(userActivity, questionnaire);	
		
				}
				else{
					
					throw new QNAException("Unable to find Form in Questionnaire for FormId "+userActivity.getFormId());
					
				}
				
	}
		
	
		
		/**
		 * used for earing credits
		 * @param questionnaireId
		 * @param guid
		 * @return
		 * @throws UnAuthorizedUserExcepton
		 * @throws ProcessorException
		 * @throws QNAException
		 * @throws InvalidObjectException
		 * @throws ProfileServiceException
		 * @throws UnmarshalException
		 * @throws QNADataAccessException
		 */
		@RequestMapping(value = "/get/earncredits/{questionnaireId}", method = { RequestMethod.GET })
		ResponseEntity<AppResponse> getEarnCreditsForTestLessActivity(@PathVariable(value ="questionnaireId") int questionnaireId,
				@NotNull @ModelAttribute("guid") String guid)
						throws UnAuthorizedUserExcepton, ProcessorException, QNAException, InvalidObjectException, ProfileServiceException, UnmarshalException,QNADataAccessException {

			
			Questionnaire questionnaire = null;
			
			
			try {
				
				questionnaire = qnaAdaptor.getQuestionnaireById(questionnaireId, guid, 2001, false, Questionnaire.class);
			} catch (Exception ex) {
				log.error("Exception " + ExceptionUtils.getStackTrace(ex));
				throw new QNAException(ex.getMessage() +",  QuestionnaireId "+questionnaireId);
			}
			
			if((questionnaire.isTestLessActivity())){				
				
				User user=getUser(null,guid);
				FormProcessor formProcessor = processorFactory.getFormProcessor("earnCreditProcessor");
				UserActivity userActivity=new UserActivity();
				userActivity.setUser(user);
				userActivity.setQuestionnaireId(questionnaire.getQuestionnaireId());
				return formProcessor.processRequest(userActivity, questionnaire);
				

			}
			else{
				
				throw new QNAException("Activity is not a Test Less Activity  "+questionnaireId);
				
			}		
		}



		///////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////// PRIVATE
	/////////////////////////////////////////////////////////////////////////////////////////// METHODS///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////


	
	 private String getFromType(Questionnaire questionnaire, int formId){		 
		
		 Optional<QuestionForm> optional  = questionnaire.getQuestionForms().stream().filter( formset->( formset.getFormId() == formId) ).findFirst();
		 
		 if(optional.isPresent()){
			 
			 if(FormTypeConstant.getDisplayTypeConstantById(optional.get().getFormTypeId())!=null){
			    	
			    	return String.valueOf(FormTypeConstant.getDisplayTypeConstantById(optional.get().getFormTypeId()).getFormName());
			   }
		 }
	    
	    return null;
		
	}
	



	/**
	 *
	 * @param activityResultUpdate
	 * @return
	 * @throws QNADataAccessException 
	 */


 	//this endpoint is to update the activity result show flag. The flag is determine is the activity has to be suppressed in the cme traker
	//PPE-84800
	@RequestMapping(value = "/update/activityresult/userattribute", method = { RequestMethod.POST}, headers = {"Accept=application/json"})
	public @ResponseBody AppResponse update(@Valid @RequestBody ActivityResultUpdate activityResultUpdate){
		 if(activityService.updateUserActivityInfo(activityResultUpdate))
			 return ControllerUtils.getResponse(AppConstants.SUCCESS_RESPONSE, HttpStatus.OK,null).getBody();
			else
			 return ControllerUtils.getResponse(AppConstants.ERROR_ACTIVITY_RESULT_UPDATE, HttpStatus.OK,null).getBody();
	}



}
