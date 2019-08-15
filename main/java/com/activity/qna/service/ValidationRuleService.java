package com.  .activity.qna.service;


import static com.  .activity.qna.constants.FormTypeConstant.POST;
import static com.  .activity.qna.constants.FormTypeConstant.INTERNAL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.  .activity.adaptor.QNAAdaptor;
import com.  .activity.model.UserActivity;
import com.  .qnaservice.entity.Eligibility;
import com.  .qnaservice.entity.Question;
import com.  .qnaservice.entity.QuestionForm;
import com.  .qnaservice.entity.Questionnaire;
import com.  .activity.qna.constants.DisplayTypeConstant;
import com.  .activity.qna.constants.QuestionTypeConstant;
import com.  .activity.qna.exception.dataaccess.QNADataAccessException;
import com.  .activity.qna.exception.manager.QNAManagerException;
import com.  .activity.qna.exception.rule.QNARuleException;
import com.  .activity.qna.manager.IQNAManager;
import com.  .activity.qna.model.AnonymousResponse;
import com.  .activity.qna.model.QuestionResponse;
import com.  .activity.qna.model.Response;
import com.  .activity.qna.model.UserResponse;
import com.  .activity.util.AppConstants;
import com.  .activity.util.QuestionnaireUtils;
import com.  .prof_utilities.util.AppLogger;
import com.  .registration.core.Address;
import com.  .registration.core.CMEInfo;
import com.  .registration.core.Profession;
import com.  .registration.core.User;


public class ValidationRuleService {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * private data members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	/**
	 * Injected Using Spring
	 */
	@Inject
	private IQNAManager qnaManagerImpl;
	
	@Autowired	
	private QNAAdaptor qnaAdaptor;
	
	//@Autowired
	//@Qualifier("creditMap")
	private Map<String,String> creditTypeMap;
	

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Getters and Setters
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public Map<String, String> getCreditTypeMap() {
		return creditTypeMap;
	}

	public void setCreditTypeMap(Map<String, String> creditTypeMap) {
		this.creditTypeMap = creditTypeMap;
	}

	public IQNAManager getQnaManagerImpl() {
		return qnaManagerImpl;
	}

	public void setQnaManagerImpl(IQNAManager qnaManagerImpl) {
		this.qnaManagerImpl = qnaManagerImpl;
	}


	public boolean isQuestionFormAnswered(Questionnaire questionnaire, int formId, long guid, UserResponse response)
			throws QNARuleException {

		
		
		AppLogger.debug(ValidationRuleService.class, "isQuestionFormAnswered",
				"--- qnaId = " + questionnaire.getQuestionnaireId());
		AppLogger.debug(ValidationRuleService.class, "isQuestionFormAnswered",
				"--- formId = " + formId);
		AppLogger.debug(ValidationRuleService.class, "isQuestionFormAnswered",
				"--- guid = " + guid);

		try {

		

		//	QuestionForm qForm= qnaAdaptor.getQuestionnaireByFormId(qnaId,formId, null,false,QuestionForm.class);
			QuestionForm qForm=QuestionnaireUtils.getQuestionFormForId(questionnaire, formId);			
			return areAllQuestionsAnswered(qForm.getQuestions(),response);
		} catch (Exception e) {

			throw new QNARuleException(
					"General Exception in METHOD == isRequiredEvalFormComplete(int qnaId, int formId, UserResponse userResponse), CLASS == RuleHelper",
					e);
		}
	}

	

	/**
	 * This Method Validates whether user response is valid	 * 
	 * @param questionnaireID
	 * @param formID
	 * @param userResponse  (created using the user responses from the FORM )
	 * @return boolean
	 * @throws QNARuleException 
	 */
	public boolean areRequiredQuestionsAnsweredByFormId(Questionnaire questionnaire, int formID, Response userResponse) throws QNARuleException {

		AppLogger.debug(ValidationRuleService.class, "areRequiredQuestionsAnsweredByFormId", "--- questionnaireID = " + questionnaire.getQuestionnaireId());
		AppLogger.debug(ValidationRuleService.class, "areRequiredQuestionsAnsweredByFormId", "--- formId = " + formID);
		QuestionForm qForm = null;
		boolean areRequiredQuestionsAnswered = false;
		try {
			
			

			//Validate whether all required questions are answered for current form
		//	 qForm = qnaManagerImpl.getQuestionFormByFormId(questionnaireID, formID);
			   qForm= QuestionnaireUtils.getQuestionFormForId(questionnaire, formID);
			if (null != qForm) {
				// Validate whether user answer all required questions by using UserResponse
			areRequiredQuestionsAnswered = allRequiredQuestionsAnswered(qForm.getQuestions(),userResponse);
			}
			return areRequiredQuestionsAnswered;

		} catch (Exception e) {
			throw new QNARuleException("General Exception in method isValidUserResponse(int questionnaireID, int formID, UserResponse userResponse), Class = RuleHelper",e);
		}
		
	}
	
	/**
	 * This Method finds out, if all the for score questions has been answered
	 * by the user for a particular form
	 * 
	 * @param questionnaireID
	 * @param guid
	 * @param formTypeID
	 * 
	 * @return boolean
	 * 
	 * @throws QNARuleException
	 * 
	 */
	public boolean areForScoreQuestionsAnsweredByFormType(Questionnaire questionnaire, long guid,
			int formTypeID, UserResponse response) throws QNARuleException {

		AppLogger.debug(ValidationRuleService.class,
				"areForScoreQuestionsAnsweredByFormType", "--- qnaid = "
						+ questionnaire.getQuestionnaireId());
		AppLogger.debug(ValidationRuleService.class,
				"areForScoreQuestionsAnsweredByFormType", "--- guid = " + guid);
		AppLogger.debug(ValidationRuleService.class,
				"areForScoreQuestionsAnsweredByFormType", "--- formTypeID = "
						+ formTypeID);

		try {

				

			// Checking for contract
			if (null != questionnaire) {

				return areForScoreQuestionsAnsweredByFormType(questionnaire,
						formTypeID,response);

			} else {
				// else return false. Better safe than Sorry :-)
				return false;
			}

		} catch (Exception qdae) {

			throw new QNARuleException(
					"-- QNADataAccessException in METHOD == areAllForScoreAnswered(int qnaid, long guid, int formTypeID), CLASS == ValidationRuleService",
					qdae);
		}
	}

	/**
	 * This Method finds out, if all the for score questions has been answered
	 * for the specific formtype by the user
	 * 
	 * @param questionnaireID
	 * @param formTypeID
	 * @param userResponse
	 * 
	 * @return boolean
	 * 
	 */
	public boolean areForScoreQuestionsAnsweredByFormType(Questionnaire questionnaire, int formTypeID,UserResponse userResponse) {

		if (null != questionnaire)
			AppLogger.debug(ValidationRuleService.class,"areForScoreQuestionsAnsweredByFormType","--- questionnaireID = "+ questionnaire.getQuestionnaireId());

			AppLogger.debug(ValidationRuleService.class,"areForScoreQuestionsAnsweredByFormType", "--- formTypeID = "+ formTypeID);
		/*
		 * flag to show whether all for score questions are answered for the
		 * specific formtype
		 */
		boolean areAllForScoreAnswered = true;
		
		/*List formSetList = questionnaire.getQuestionForms();

		for (int i = 0; i < formSetList.size(); i++) {

			QuestionForm form = (QuestionForm) formSetList.get(i);

			if (form.getFormTypeId() == formTypeID) {
				

				if (formTypeID == INTERNAL.getFormTypeId()
						|| formTypeID == POST.getFormTypeId()) {
					
					// Get the question Form List
					
				
						
						// Delagate to Question Form and ask the $100,000,0 question :-)
						boolean result = areAllForScoreAnswered(form.getQuestions());

						if (!result) {
							return result;
						}

					}
				}
				
				
			}*/
		
		
			/*
			 * Validate whether user has answered all the for score questions for
			 * the specific formtype*/
			 
		List formSetList = questionnaire.getQuestionForms();	
		Iterator<QuestionForm> questionFormItr = formSetList.stream().iterator();

		while (questionFormItr.hasNext()) {

			QuestionForm questionForm = questionFormItr.next();

			if (questionForm.getFormTypeId() == formTypeID) {

				if (formTypeID == INTERNAL.getFormTypeId() || formTypeID == POST.getFormTypeId()) {

					Iterator<Question> questioniter = questionForm.getQuestions().stream().iterator();
					Question question = null;
					List<QuestionResponse> qestionResponse = null;
					while (questioniter.hasNext()) {						
						question = questioniter.next();
						qestionResponse = userResponse.getQuestionResponseList(question.getQuestionId());
						if (question.isScore()) {
							if (match(qestionResponse, getQuestionResponseListPredicate(qestionResponse))) {
								return false;
							}
						}

					}

				}

			}

		}
		
		
		AppLogger.debug(ValidationRuleService.class,"areForScoreQuestionsAnsweredByFormType","--- Returned areAllForScoreAnswered = "+ areAllForScoreAnswered);

		return areAllForScoreAnswered;
		}

		
	/**
	 * This Method finds out, if all the for score questions has been answered
	 * by the user for all forms including current form .
	 * 
	 * @param questionnaireID
	 * @param userResponse
	 * 
	 * @return boolean
	 * 
	 */
	public boolean areForScoreQuestionsAnswered(Questionnaire questionnaire, UserResponse userResponse) {

		
			AppLogger.debug(ValidationRuleService.class,
					"areForScoreQuestionsAnswered(Questionnaire questionnaire, UserResponse userResponse)",
					"--- questionnaireID = " + questionnaire.getQuestionnaireId());

		if (null != userResponse)
			AppLogger.debug(ValidationRuleService.class,
					"areForScoreQuestionsAnswered(Questionnaire questionnaire, UserResponse userResponse)",
					"--- userResponse.getGuid() = " + userResponse.getGuid());

		/*
		 * flag to show whether all for score questions are answered
		 */
		 boolean areAllForScoreAnswered = true;
		 
		QuestionForm currentForm= QuestionnaireUtils.getQuestionFormForId(questionnaire, userResponse.getFormId());
		
		// validate current form with userResponse for post form
		
		Iterator<Question> questioniter = currentForm.getQuestions().stream().iterator();		
		Question question = null;
		List<QuestionResponse> qestionResponse = null;
		while (questioniter.hasNext()) {			
			question = questioniter.next();
			qestionResponse = userResponse.getQuestionResponseList(question.getQuestionId());	
			if(question.isScore()){	
				if (match(qestionResponse, getQuestionResponseListPredicate(qestionResponse))) {
					return false;
				}
			}
				
		}
		
		
		// validate other forms for answers
		
		 List formSetList = questionnaire.getQuestionForms();
		
		

		for (int i = 0; i < formSetList.size(); i++) {

			QuestionForm form = (QuestionForm) formSetList.get(i);
				if (form.getFormId()!=currentForm.getFormId() && ( form.getFormType().getId() == INTERNAL.getFormTypeId()|| form.getFormType().getId()  == POST.getFormTypeId())) {				
					
						boolean result = areAllForScoreAnswered(form.getQuestions());

						if (!result) {
							return result;
						}

					}
			}

		

		AppLogger.debug(ValidationRuleService.class,
				"areForScoreQuestionsAnswered(Questionnaire questionnaire, UserResponse userResponse)",
				"--- areAllForScoreAnswered = " + areAllForScoreAnswered);

		return areAllForScoreAnswered;
	}

	
	

	
	/**
	 * This Method finds out, if user has passed the test
	 * 
	 * @param questionnaireID
	 * @param user
	 * 
	 * @return boolean
	 * 
	 * @throws QNARuleException
	 * 
	 */
	public boolean hasUserPassedTheTest(Questionnaire questionnaire, User user, UserResponse userReponse)
			throws QNARuleException {

		AppLogger.debug(ValidationRuleService.class,
				"hasUserPassedTheTest( int qnaId, User user )", "--- qnaId = "
						+ questionnaire.getQuestionnaireId());
        int creditTypeId=0;
		try {

			// Logic to find the Users Credit Type Id
			// Check to see, if user object is not NULL
			if (null != user) {
				
                 // Commented to remove profile cache dependency
				//int creditTypeId = QNASharedUtil.getUserCreditType(user);
				// Find out the creditType id for the user
				String professionID=String.valueOf(user.getProfession().getProfessionId());
				String creditType =creditTypeMap.get(professionID);
				if(creditType!=null){
					
					creditTypeId=Integer.valueOf(creditType);
				}
				else{
					
					creditTypeId= AppConstants.LOC_CREDIT_TYPE;
				}
				

		
				// As per Leah, if creditType is not associated with this
				// questionnaire/activity, then default credittype(LOC) is
				// assigned to creditType
				if (!QuestionnaireUtils.isCreditTypeAssociated(creditTypeId,
						questionnaire)) {
					creditTypeId =AppConstants.LOC_CREDIT_TYPE;
				}

				// with the creditTypeid calulated above, find the users passing
				// score
				int passingScore = getPassingScore(questionnaire,creditTypeId);

				// Since Passing score is in %ages of 100, for example a passing
				// score of 70 means
				// a 70% score is required for the user to pass the test.

				// So we would need to find out the following information
				// 1. How many for score questions are there? i.e. Total For
				// Score Questions
				// 2. How many for score questions user has answered correctly?
				// i.e. Total Questions answered correctly
				// 3. apply the formula -- Total Questions answered
				// correctly/Total For Score Questions * 100
				// 4. Take the int value of the formula result and
				// 5. compare it with passing score.
				// 6. If the formula rsult is more or equal to passing score,
				// then user has passed the test
				// 7. Fail otherwise.

				// 1. How many for score questions are there? i.e. Total For
				// Score Questions
				int totalForScoreQuestions = getTotalForScoreQuestions(questionnaire);
						
						

				// 2. How many for score questions user has answered correctly?
				// i.e. Total Questions answered correctly
				
				//UserResponse ur = qnaManagerImpl.getResponse(user.getGlobalUserID(), qnaId);
				int totalForScoreQuestionsAnsweredCorrectly = getTotalForScoreQuestionsAnsweredCorrectly(questionnaire,userReponse);

				// 3. apply the formula -- Total Questions answered
				// correctly/Total For Score Questions * 100
				Float totalForScoreQuestionsFloatValue = new Float(
						totalForScoreQuestions);

				Float totalForScoreQuestionsAnsweredCorrectlyFloatValue = new Float(
						totalForScoreQuestionsAnsweredCorrectly);

				float userScore = (totalForScoreQuestionsAnsweredCorrectlyFloatValue
						.floatValue() / totalForScoreQuestionsFloatValue
						.floatValue()) * 100;

				// 4. Take the int value of the formula result and
				int score = (int) userScore;

				// 5. compare it with passing score.
				if (score >= passingScore) {

					// 6. If the formula rsult is more or equal to passing
					// score, then user has passed the test
					return true;
				}

				// 7. Fail otherwise.
				return false;

			} else {
				// throw exception, as User cannot be NULL
				throw new QNARuleException(
						"-- Exception in as User Object is NULL in METHOD == hasUserPassedTheTest(int qnaid, User user), CLASS == ValidationRuleService--");
			}

		}  catch (Exception e) {

			throw new QNARuleException(
					"-- General Exception in METHOD == hasUserPassedTheTest(int qnaid, User user), CLASS == ValidationRuleService--",
					e);
		}
	}
	
	
	
	/**
	 * This method computes whether users CME profile is complete or not.
	 * 
	 * @param User user
	 * @return boolean
	 */
	public boolean isUserCMEProfileComplete( User user ){
		
		boolean isUserCMEProfileComplete = true;
		
		boolean validateUs = false;
		boolean validLicense = true;
     
		// Checking Methods Contract
		if(user != null){
			
			// Checking for validity of global user id
			if(user.getGlobalUserID() == -1){
				
				// Setting the isUserCMEProfileComplete to false
				isUserCMEProfileComplete = false;
				
				// return from method.
				return isUserCMEProfileComplete;
				
			}

			
			/*
			 * Validate User Address Routine
			 */
			List addresses = user.getAddress();
			Address userAddress = new Address();

			if(addresses != null && addresses.size() != 0){
				
				// get User's Address
				for(int i=0; i<addresses.size(); i++){
					
					Address address = (Address)addresses.get(i);

					if(address.getContactType() == Address.CONTACT_HOME){
						userAddress = address;
					}
				}

				String country = userAddress.getCountryAbbreviation();
				
				if(!QuestionnaireUtils.isValidString(country)){
					isUserCMEProfileComplete = false;
					return isUserCMEProfileComplete;
				}

				// Whether US Address
				if(country.equalsIgnoreCase("US")){
					validateUs=true;
				}

				// Street
				if(!QuestionnaireUtils.isValidString(userAddress.getStreet1()) && !QuestionnaireUtils.isValidString(userAddress.getStreet2())){
					isUserCMEProfileComplete = false;
					return isUserCMEProfileComplete;
				}

				// City
				if(!QuestionnaireUtils.isValidString(userAddress.getCity())){
					isUserCMEProfileComplete = false;
					return isUserCMEProfileComplete;
				}

				// State
				if(!QuestionnaireUtils.isValidString(userAddress.getStateAbbreviation())){
					// if US address
					if(validateUs){
						isUserCMEProfileComplete = false;
						return isUserCMEProfileComplete;
					}
				}

				// ZipCode
				if(QuestionnaireUtils.isValidString(userAddress.getZipCode())){
           			String tempZipCode = userAddress.getZipCode();
					if(tempZipCode.equals("99999")){
						// if US address
						if(validateUs){
							isUserCMEProfileComplete = false;
							return isUserCMEProfileComplete;
						}
					}
				}else{
					// if US address
					if(validateUs){
						isUserCMEProfileComplete = false;
						return isUserCMEProfileComplete;
					}
				}

			}else{
				
				isUserCMEProfileComplete = false;
				return isUserCMEProfileComplete;
			}
			

			/*
			 * Validate User Profession
			 */
			Profession prof = user.getProfession();
			boolean licenseReqd = false;

			if(prof != null){

				int degreeid = 0;
				int occupationid = 0;
				int professionid = 0;
				
				if (prof.getDegreeId() != -1) {
					degreeid = prof.getDegreeId();
				}
				
				if (prof.getOccupationId() != null) {
					occupationid = prof.getOccupationId().intValue();
				}
				
				if (prof.getProfessionId() != -1) {
					professionid = prof.getProfessionId();					
				} 

				if(degreeid != -1 && occupationid != -1 && professionid != -1 ){
					
					/*
					 * TODO: Need a DAO to retrieve 'licenseReqd'
					 * 
					 * 		 "select license_reqd from activity_profile_credit where PROFESSION_ID=? and DEGREE_ID=? and OCCUPATION_ID=? ";
					 * 
					 */
					//ValidatorDAO validator = ValidatorDAOFactory.getValidatorDAO();
					//licenseReqd = validator.validateUserLicense( degreeid, occupationid, professionid);
					
				}else{
					
					isUserCMEProfileComplete = false;
					return isUserCMEProfileComplete;
				}
			 

			}else{ //end prof == null
				
				isUserCMEProfileComplete = false;
				return isUserCMEProfileComplete;
			}

			
			/*
			 * Validate User CMEInfo
			 */
			CMEInfo cmeInfo = user.getCMEInfo();

			if(cmeInfo != null){
				
				if(!QuestionnaireUtils.isValidString(cmeInfo.getLicenseId())){
					validLicense = false;
				}
			}

			// if license requried, but not a valid license
			if(licenseReqd && !validLicense){
				isUserCMEProfileComplete = false;
				return isUserCMEProfileComplete;
			}
	
			
		}else{//end user == null
				
			isUserCMEProfileComplete = false;
		}
	 
		
		return isUserCMEProfileComplete;	
	}



	
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Private Methods Methods 
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	

	/**
	 * 
	 * Helper method to find out, if all the questions associated to this
	 * QuestionForm are answered. 	
	 * 
	 * @param userResponse
	 * @return boolean
	 */
	private boolean areAllQuestionsAnswered(List<Question> questionList, UserResponse response) {

		Result<Boolean> result = Result.of(false);		
		Iterator<Question> subQuestionItr = null;		
		List<QuestionResponse> qestionResponse = null;		
		Iterator<Question> questioniter = questionList.stream().iterator();		
		Question question = null;		
		while (questioniter.hasNext()) {			
			question = questioniter.next();			
			if (match(question, getQuestionTypePredicate(question))) {				
					subQuestionItr = question.getMatrixQuestions().iterator();					
					while (subQuestionItr.hasNext()) {						
							Question subQuestion = subQuestionItr.next();							
							qestionResponse = response.getQuestionResponseList(subQuestion.getQuestionId());						
							if (match(qestionResponse, getQuestionResponseListPredicate(qestionResponse))) {
								return false;
							}
					 }
			} 
		    else {

					qestionResponse = response.getQuestionResponseList(question.getQuestionId());					
					if (match(qestionResponse, getQuestionResponseListPredicate(qestionResponse))) {
						return false;
					}
			}
		}

		return true;
	}

	
	private  boolean allRequiredQuestionsAnswered(List<Question> questionList, Response userResponse ) {	
		
		Result<Boolean> result= Result.of(true);
		questionList.stream().forEach(question-> {			
			if(match(question,getQuestionTypePredicate(question))){				
				question.getMatrixQuestions().stream().forEach(subQuestion->{					
					List<QuestionResponse> qestionResponse= userResponse.getQuestionResponseList(subQuestion.getQuestionId());					 
					if(!match(qestionResponse,question,getRequiredQuestionPredicate(qestionResponse,subQuestion))){					
						result.add(false);
					}
					
				});
				
			}
			else{
				
				List<QuestionResponse> qestionResponse= userResponse.getQuestionResponseList(question.getQuestionId());
				if(!match(qestionResponse,question,getRequiredQuestionPredicate(qestionResponse,question))){					
					result.add(false);
				}
			}
				
			
		});
		
		return result.get();
		}
	
	
	private  <T>  boolean match(T t, Predicate<T> predicate){	
		  return predicate.test(t);
		
	}
	
	
	private  <R,Q> boolean match(R r,Q q, BiPredicate<R,Q> biPredicate){	
		  return biPredicate.test(r,q);
		
	}
	
	private BiPredicate<List<QuestionResponse>,Question> getRequiredQuestionPredicate(List<QuestionResponse> questionResponseList,Question quesiton){
		/**
		 * Logic Explained: 1. If Question is NOT Required 2. If Question is
		 * Required and questionResponseList is NULL return false ( i.e. user
		 * has to answer this mendatory question )
		 */
		BiPredicate<List<QuestionResponse>,Question> biPredicate= (r,q)->{
			
			if (!q.required) {
				return true;
			}
			QuestionResponse resonse =null;
			if (r != null) {
				Iterator<QuestionResponse> responseItr = r.stream().iterator();
				while (responseItr.hasNext()) {
					 resonse = responseItr.next();
					if (match(q, getQuestionDisplayType(q))) {
						
						if (match(resonse, getResponsePredicate(resonse))) {
							return false;
						} else {	
							return true;
						}
					} else {
	
						if (match(resonse, getResponseChoice(resonse))) {
							return true;
						}
	
					}
	
				}
				
			}
			return false;
			
		};
		return biPredicate;
	}
	
	private Predicate<Question> getQuestionTypePredicate(Question quesiton){
		
		return (t)->t.getQuestionTypeId()==QuestionTypeConstant.MATRIX.getQuestionTypeId();
		
	}
	
	private Predicate<List<QuestionResponse>> getQuestionResponseListPredicate(List<QuestionResponse> qestionResponse){
		
		return (responseList) -> (responseList==null ||(responseList!=null&&responseList.isEmpty()));
		
		
	}
	private Predicate<Question> getQuestionDisplayType(Question quesiton){
			
			return (ques) -> (ques.getDisplayType() == DisplayTypeConstant.TEXTBOX.getDisplayTypeId()
					|| ques.getDisplayType() == DisplayTypeConstant.TEXTAREA.getDisplayTypeId());
			
			
		}
	
	private Predicate<QuestionResponse> getResponsePredicate(QuestionResponse response){
		
		return (resp) -> resp.getResponseText()==null ||(resp.getResponseText()!=null && "".equals(resp.getResponseText().isEmpty()));
		
		
	}
	private Predicate<QuestionResponse> getResponseChoice(QuestionResponse response){
		
		return (resp) -> resp.getChoiceId() > 0;
		
		
	}


	
	
	/**
	 * This method validates if the  question has been answered.
	 * 
	 * @param userResponse
	 * @param question
	 * @return boolean
	 */
	private  boolean isQuestionsAnswered(Response userResponse, Question question) { 
		int questionId = question.getQuestionId();

		// The list below could come empty, if the user have not responded to this question
		List questionResponseList = userResponse.getQuestionResponseList(questionId);
		
		boolean result = false;

		
		if (null != questionResponseList) {
			for (int j = 0; j < questionResponseList.size(); j++) {
				QuestionResponse qr = (QuestionResponse) questionResponseList.get(j);

				int choiceId = qr.getChoiceId();
				String responseText = qr.getResponseText();

				result = isRequiredAndAnswered(question,choiceId);
			}
		}
		return result;
	}
	
	
	/*
	 * Helper method to find out, this  question is answered or not .
	 * 
	 * @param choiceId
	 * @param responseText
	 * @return boolean
	 */
	private boolean isRequiredAndAnswered(Question question,int choiceId) {

		

			if (question.getDisplayType() == DisplayTypeConstant.TEXTBOX.getDisplayTypeId()
					|| question.getDisplayType() == DisplayTypeConstant.TEXTAREA
							.getDisplayTypeId()) {

				if (null != question.getResponseText()) {
					if (!question.getResponseText().equals("")) {
						return true;
					}
				} else {
					return false;
				}
			} else {
				// Comparing against greater then 0 to avaoid -1 conditions
				// as choiceId can never be 0 or less then 0
				if (choiceId > 0) {
					return true;
				}
			}
		
		return false;
	}


	
	public boolean areAllForScoreAnswered(List<Question> QuestionsLs) {

		// Get the question List
	

		// Itarate over the question List
		for (int i = 0; i < QuestionsLs.size(); i++) {

			// Get the question
			Question question = (Question) QuestionsLs.get(i);

			// Make sure question is a for score question
			if (question.isScore()) {

				// If yes, get the question id
				int questionId = question.getQuestionId();

				// Get the user questionResponseList
			

				if (!question.isAnswered()) {
					// means it is a for score question and user has not
					// answered
					return false;

				}
				// else, it's a for score question and user has answered this
				// question.
			}
		}

		// The control will reach to this point
		// when the whole questionList is being iterated through
		return true;
	}
	
	
	private int getPassingScore(Questionnaire questionnaire, int creditTypeId ){
		
		Map<Integer, Eligibility> eligibilityMap=questionnaire.getActivityInfo().getEligibilityMap();
		Eligibility eligibility =eligibilityMap.get(new Integer(creditTypeId) );		
		if( null != eligibility ){
			return eligibility.getPassingScore();
		}
		return 0;
	}
	
	private int getTotalForScoreQuestions(Questionnaire questionnaire) {

		int total = 0;

		List<QuestionForm> formSetList = questionnaire.getQuestionForms();

		for (int i = 0; i < formSetList.size(); i++) {

			QuestionForm questionForm = (QuestionForm) formSetList.get(i);
			
			for(Question question: questionForm.getQuestions()){
						
						if(question.isScore()){
							
							total = total + 1;
						}
				
			}
		}

		return total;
	}
	
	private int getTotalForScoreQuestionsAnsweredCorrectly(Questionnaire questionnaire,UserResponse userResponse) {

		int totalForScoreQuestionsAnsweredCorrectly = 0;	
		
		
		// Populate current form with userResponse for post form
		QuestionForm currentForm= QuestionnaireUtils.getQuestionFormForId(questionnaire, userResponse.getFormId());		
		populateTotalForScoreQuestionsAnsweredCorrectly(userResponse,currentForm);
		
		List<QuestionForm> formSetList = questionnaire.getQuestionForms();
		for (int i = 0; i < formSetList.size(); i++) {

			QuestionForm questionForm = (QuestionForm) formSetList.get(i);
			
			for(Question question: questionForm.getQuestions()){
				
				if(question.isScore()){
						
						if(question.answeredCorrectly){
							
							totalForScoreQuestionsAnsweredCorrectly = totalForScoreQuestionsAnsweredCorrectly + 1;						
						
					}					
					
				}
				
			}
		}

		return totalForScoreQuestionsAnsweredCorrectly;
	}	
	
	
	private void populateTotalForScoreQuestionsAnsweredCorrectly(UserResponse userResponse, QuestionForm currentForm) {

		Iterator<Question> questioniter = currentForm.getQuestions().stream().iterator();
		Question question = null;
		List<QuestionResponse> qestionResponse = null;
		Iterator<QuestionResponse> responseItr=null;
		QuestionResponse questionResponse=null;
		while (questioniter.hasNext()) {
			question = questioniter.next();
			qestionResponse = userResponse.getQuestionResponseList(question.getQuestionId());
			responseItr=qestionResponse.stream().iterator();
			while(responseItr.hasNext()){				
				questionResponse=responseItr.next();
				populateUserScore(question, questionResponse.getChoiceId(), questionResponse.getResponseText());

			}
		
		}

	}
	
	
	private void populateUserScore(Question question, int choiceId, String responseText) {

		if (question.isScore()) {

				if (match(question, getQuestionDisplayType(question))) {
					
						question.setResponseText(responseText);					
						question.setAnswered(true);
						// since it is a text or textarea question, setting that the
						// question is answered correctly
						question.setAnsweredCorrectly(true);

			} 
			else {

				// Comparing against greater then 0 to avaoid -1 conditions
				// as choiceId can never be 0 or less then 0
					if (choiceId > 0) {	
						
						question.getChoices().stream().forEach(choice -> {	
							if (choiceId == choice.getChoiceId()) {
								question.setAnswered(true);
								choice.setSelected(true);
								if (choice.isCorrect()) {
									question.setAnsweredCorrectly(true);	
								}
	
							}
	
						});
	
					}
					else {
						
						question.setAnsweredCorrectly(true);		
					}
			}// Else
		}// isScore

	}
	
	/**
	 * 
	 * Helper method to find out, if all the questions associated to this
	 * QuestionForm are answered. The method call flow is based on DELAGATION
	 * pattern. This method does not distinguish between REQUIRED, FOR SCORE or
	 * GENERAL QUESTIONS.
	 * 
	 * @param userResponse
	 * @return boolean
	 */
	/*private boolean areAllQuestionsAnswered(List<Question> questionList, UserResponse response) {

		Result<Boolean> result= Result.of(false);
		questionList.stream().forEach(question-> {
			
			if(match(question,getQuestionTypePredicate(question))){
				
				question.getMatrixQuestions().stream().forEach(subQuestion->{
					
					List<QuestionResponse> qestionResponse= response.getQuestionResponseList(subQuestion.getQuestionId());
					 
					 if(match(qestionResponse,getQuestionResponseListPredicate(qestionResponse))){
						 
						
					 }
					
				});
				
			}
			else{
				
				result.add(isQuestionsAnswered(response,question));
			}
				
			
		});
			
	
		
	
		return result.get();
	}*/



}
