package com.  .activity.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.  .activity.adaptor.QNAAdaptor;
import com.  .activity.exception.ProcessorException;
import com.  .activity.exception.ProfileServiceException;
import com.  .activity.exception.UnAuthorizedUserExcepton;
import com.  .activity.model.AppRequest;
import com.  .activity.model.AppResponse;
import com.  .activity.model.UserActivity;
import com.  .activity.processor.FormProcessor;
import com.  .activity.processor.FormProcessorFactory;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.service.ActivityService;
import com.  .activity.util.AppConstants;
import com.  .activity.util.ExceptionUtils;
import com.  .auth.unmarshal.UnmarshalException;
import com.  .profile_service.adapter.ProfileServiceAdapter;
import com.  .profile_service.adapter.UserUnmarshaller;
import com.  .profile_service.adapter.exception.ProfileServiceAdapterException;
import com.  .qnaservice.entity.Questionnaire;
import com.  .registration.core.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;

public class AbstractController {
	
	private static Logger log = LoggerFactory.getLogger(AbstractController.class);

	@Inject
	FormProcessorFactory processorFactory;

	@Autowired
	ProfileServiceAdapter profileServiceAdapter;

	@Autowired
	protected QNAAdaptor qnaAdaptor;

	@Autowired
	ActivityService activityService;


	@ModelAttribute(value="guid")
	protected  Long getGuid(@RequestHeader(value = "X-User-Guid", required = false) String guidStr){
		
		Long guid = null;		
		if(StringUtils.isNotBlank(guidStr) &&!guidStr.contains("AN")) {
			try{
				
				guid = Long.valueOf(guidStr);
			}
			catch(NumberFormatException ex){
				
				log.error("Exception happend while converting to Long {} ", guidStr);
			}
		}
		
		return guid;
	}


	public ResponseEntity<AppResponse> processAppRequest(UserActivity appRequest,Questionnaire questionnaire) throws ProcessorException, QNADataAccessException {
		log.debug("invoking processor name: {}", appRequest.getFormProcessor().getClass().getName());
		return appRequest.getFormProcessor().processRequest(appRequest,questionnaire);

	}
	/**
	 * Prepares {@link AppRequest} for downstream use.
	 *
	 * @return
	 * @throws UnmarshalException
	 * @throws UnAuthorizedUserExcepton
	 * @throws ProfileServiceAdapterException
	 */
	protected void prepareUserActivityRequest( UserActivity appRequest,String guid) throws ProfileServiceException, UnmarshalException, UnAuthorizedUserExcepton  {

		log.debug("store HttpServletRequest in AppRequest");
	

		if(guid==null){

			throw new UnAuthorizedUserExcepton(" GUID can't be blank ");
		}
		User user = getUser(appRequest,guid);

		//Set specialty & profession of the user.  For now US ONLY
		if(user!=null&&(user.getUserName()!=null)){

			appRequest.setUser(user);
			appRequest.setGuid(user.getGlobalUserID());
			appRequest.setProfessionId(user.getProfession().getProfessionId());
			appRequest.setSpecialtyId(user.getProfession().getSpecialtyId());

		}else{

			throw new UnAuthorizedUserExcepton("User Not found for GUID :  "+guid);
		}

		log.debug("determine request processor.");
	}

	protected User getUser(UserActivity appRequest,String guid) throws ProfileServiceException, UnmarshalException  {
		try{

			String json = profileServiceAdapter.getProfile(guid);
			User user = UserUnmarshaller.unmarshal(json);
			return user;
		}catch(ProfileServiceAdapterException | UnmarshalException ex){
			log.error("Exception " + ExceptionUtils.getStackTrace(ex));
			if(ex instanceof ProfileServiceAdapterException){

				throw new ProfileServiceException("Unable to get Response from Profile Service");
			}
			else{

				throw  new UnmarshalException("Unable to parse json from ProfileService");
			}
		}

	}

	/**
	 * Uses factory pattern supported by
	 * {@link org.springframework.beans.factory.config.ServiceLocatorFactoryBean}
	 *
	 * @param userActivity
	 * @return
	 */
	protected void createAppRequestProcessor(UserActivity userActivity) {
		FormProcessor formProcessor = null;

		String processorName = new StringBuilder(userActivity.getFormType().toLowerCase()).append(AppConstants.SUFFIX_REQUESTPROCESSOR).toString();
		log.debug("incoming request resolved to processor name: {}", processorName);
		formProcessor = processorFactory.getFormProcessor(processorName);
		userActivity.setFormProcessor(formProcessor);

	}

}
