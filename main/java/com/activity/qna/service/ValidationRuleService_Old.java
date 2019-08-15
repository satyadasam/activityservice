package com.  .activity.qna.service;


import static com.  .activity.qna.constants.FormTypeConstant.POST;
import static com.  .activity.qna.constants.FormTypeConstant.INTERNAL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.  .activity.adaptor.QNAAdaptor;
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
import com.  .registration.core.User;


/**
 * ValidationRuleService class has methods validates various forms. which would
 * include
 * <ul>
 * <li>Validating Form with User Response</li>
 * <li>Validating For Score Questions</li>
 * <li>Whether user responded to a Form</li>
 * etc.
 * </ul>
 * 
 * @author Vikas Gupta
 * @version 1.0
 */
public class ValidationRuleService_Old {

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

	/**
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Public Methods ( Check User Response Validity )
	 * 
	 * public boolean areRequiredQuestionsAnsweredByFormId(int
	 * questionnaireID,int formID, UserResponse userResponse) throws
	 * QNARuleException
	 * 
	 * public boolean areRequiredQuestionsAnsweredByQuestionIdList(int
	 * questionnaireID, List questionIdList, UserResponse userResponse) throws
	 * QNARuleException
	 * 
	 * public boolean areRequiredQuestionsAnsweredByFormId(Questionnaire
	 * questionnaire, int formID, UserResponse userResponse) throws
	 * QNARuleException
	 * 
	 * 
	 * Public Methods ( Check User Response Validity For For Score Questions )
	 * 
	 * public boolean areForScoreQuestionsAnswered(int
	 * questionnaireID,UserResponse userResponse) throws QNARuleException
	 * 
	 * public boolean areForScoreQuestionsAnswered(Questionnaire
	 * questionnaire,UserResponse userResponse)
	 * 
	 * public boolean areForScoreQuestionsAnsweredByFormType(int qnaid, int
	 * guid, int formTypeID) throws QNARuleException
	 * 
	 * public boolean areForScoreQuestionsAnsweredByFormType( Questionnaire
	 * questionnaire, int formTypeID, UserResponse userResponse)
	 * 
	 * 
	 * Public Methods ( General Rules )
	 * 
	 * public boolean hasUserPassedTheTest(Questionnaire questionnaire,
	 * UserResponse userResponse)
	 * 
	 * public boolean hasUserPassedTheTest( int qnaId, User user ) throws
	 * QNARuleException
	 * 
	 * public int getUserCreditType( int professionID, int occupationID )
	 * 
	 * public boolean isFormTypeAssocaitedWithQuestionnaire(int questionnaireID,
	 * int formTypeID) throws QNAManagerException
	 * 
	 * public boolean hasUserResponded(long guid, int qnaId, int formId) throws
	 * QNARuleException
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Public Methods ( Check User Response Validity )
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

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
			areRequiredQuestionsAnswered = areAllRequiredQuestionsAnswered(qForm.getQuestions(),userResponse);
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
			int formTypeID) throws QNARuleException {

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
						formTypeID);

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
	public boolean areForScoreQuestionsAnsweredByFormType(
			Questionnaire questionnaire, int formTypeID) {

		if (null != questionnaire)
			AppLogger.debug(
					ValidationRuleService.class,
					"areForScoreQuestionsAnsweredByFormType",
					"--- questionnaireID = "
							+ questionnaire.getQuestionnaireId());

		AppLogger.debug(ValidationRuleService.class,
				"areForScoreQuestionsAnsweredByFormType", "--- formTypeID = "
						+ formTypeID);

		

		/*
		 * flag to show whether all for score questions are answered for the
		 * specific formtype
		 */
		boolean areAllForScoreAnswered = true;

		/*
		 * Validate whether user has answered all the for score questions for
		 * the specific formtype
		 */
		List formSetList = questionnaire.getQuestionForms();

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
				
				
			}
		AppLogger.debug(ValidationRuleService.class,"areForScoreQuestionsAnsweredByFormType","--- Returned areAllForScoreAnswered = "+ areAllForScoreAnswered);

		return areAllForScoreAnswered;
		}

		
	/**
	 * This Method finds out, if all the for score questions has been answered
	 * by the user
	 * 
	 * @param questionnaireID
	 * @param userResponse
	 * 
	 * @return boolean
	 * 
	 */
	public boolean areForScoreQuestionsAnswered(Questionnaire questionnaire, UserResponse userResponse) {

		if (null != questionnaire)
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
			if (match(qestionResponse, getQuestionResponseListPredicate(qestionResponse))) {
				return false;
			}
			
		}
		
		
		// validate other forms for answers
		
		 List formSetList = questionnaire.getQuestionForms();
		
		

		for (int i = 0; i < formSetList.size(); i++) {

			QuestionForm form = (QuestionForm) formSetList.get(i);
				if (form.getFormId()!=currentForm.getFormId() &&  form.getFormType().getId() == INTERNAL.getFormTypeId()
						|| form.getFormType().getId()  == POST.getFormTypeId()) {				
					
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
	public boolean hasUserPassedTheTest(Questionnaire questionnaire, User user)
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
				int totalForScoreQuestionsAnsweredCorrectly = getTotalForScoreQuestionsAnsweredCorrectly(questionnaire);

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
	 * QuestionForm are answered. The method call flow is based on DELAGATION
	 * pattern. This method does not distinguish between REQUIRED, FOR SCORE or
	 * GENERAL QUESTIONS.
	 * 
	 * @param userResponse
	 * @return boolean
	 */
	private boolean areAllQuestionsAnswered(List<Question> questionList, UserResponse response) {

		// Itarate over the question List
		List questionResponseList = null;
		boolean result=true;
		for (int i = 0; i < questionList.size(); i++) {

			// Get the question
			Question question = (Question) questionList.get(i);
			

		

			if (question.getQuestionTypeId() == QuestionTypeConstant.MATRIX.getQuestionTypeId()) {

				// Create QuestionControl for the Child Question
				List subQuestionList = question.getMatrixQuestions();

				if (subQuestionList != null) {

						for (int k = 0; k < subQuestionList.size(); k++) {
	
							Question subQuestion = (Question) subQuestionList.get(k);
							questionResponseList = response.getQuestionResponseList(question.getQuestionId());							
							if(questionResponseList==null||(questionResponseList!=null && questionResponseList.isEmpty())){
								result=false;
							}
	
							
							
						}
				}

			} 
			else {

				
				questionResponseList = response.getQuestionResponseList(question.getQuestionId());
				if(questionResponseList==null||(questionResponseList!=null && questionResponseList.isEmpty())){
					result=false;
				}

			}

		}
		// The control will reach to this point
		// when the whole questionList is being iterated through
		return result;
	}
	
	/**
	 * Helper method to find out, if all the required questions associated to
	 * this question form are answered. The method call flow is based on
	 * DECORATOR pattern
	 * 
	 * @param userResponse
	 * @return boolean
	 */
	public boolean areAllRequiredQuestionsAnswered(List<Question> questionList, Response userResponse) {
		
		return allRequiredQuestionsAnswered(questionList,	userResponse);
	}
	
	private  <T>  boolean match(T t, Predicate<T> predicate){	
		  return predicate.test(t);
		
	}


	private Predicate<Question> getQuestionTypePredicate(Question quesiton){
		
		return (t)->t.getQuestionTypeId()==QuestionTypeConstant.MATRIX.getQuestionTypeId();
		
	}

	private Predicate<List<QuestionResponse>> getQuestionResponseListPredicate(List<QuestionResponse> qestionResponse){
		
		return (responseList) -> (responseList==null ||(responseList!=null&&responseList.isEmpty()));
		
		
	}
	
	
	
	
private  boolean allRequiredQuestionsAnswered(List<Question> questionList, Response userResponse ) {
		
		boolean result = false;
		// Iterate the questionList to check whether all required question have been answered
		for (int i = 0; i < questionList.size(); i++) {
			// The question below, could be a required question or a non required question
			Question question = (Question) questionList.get(i);
			
			// If Matrix Question
			if(question.getQuestionTypeId() == QuestionTypeConstant.MATRIX.getQuestionTypeId()){				
				List subQuestionList = question.getMatrixQuestions();				
				if(subQuestionList != null) {					
					for (int k = 0; k < subQuestionList.size(); k++){												
						result = isRequiredQuestionsAnswered(userResponse, (Question) subQuestionList.get(k));
					}
				}
				
			// Else if Regular Question
			} else {					
				result = isRequiredQuestionsAnswered(userResponse, question);	
			}			

			if (!result) {
				return result;
			}
		}		
		return true;
	}

	
	/**
	 * This method validates if the required question has been answered.
	 * 
	 * @param userResponse
	 * @param question
	 * @return boolean
	 */
	private  boolean isRequiredQuestionsAnswered(Response userResponse, Question question) { 
		int questionId = question.getQuestionId();

		// The list below could come empty, if the user have not responded to this question
		List questionResponseList = userResponse.getQuestionResponseList(questionId);

		/**
		 * Logic Explained:
		 * 1. If Question is NOT Required 
		 * 2. If Question is Required and questionResponseList is NULL
		 * return false ( i.e. user has to answer this mendatory question )
		 */
		boolean result = false;

		if (!question.isRequired()) {
			// Setting the result to TRUE, as per Case 1 explained above
			result = true;
		}

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
	 * Helper method to find out, this  question is answered or not. Does not check for mandatory or required
	 * 
	 * @param choiceId
	 * @param responseText
	 * @return boolean
	 */
	private boolean isQuestionsAnswered(Question question,int choiceId) {

		if (question.isRequired()) {

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
		} else {

			return true;
		}
		return false;
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
	
	private int getTotalForScoreQuestionsAnsweredCorrectly(Questionnaire questionnaire) {

		int totalForScoreQuestionsAnsweredCorrectly = 0;
		
		List<QuestionForm> formSetList = questionnaire.getQuestionForms();

		for (int i = 0; i < formSetList.size(); i++) {

			QuestionForm questionForm = (QuestionForm) formSetList.get(i);
			
			for(Question question: questionForm.getQuestions()){
				
				if(question.isRequired()){					
					
						
						if(question.answeredCorrectly){
							
							totalForScoreQuestionsAnsweredCorrectly = totalForScoreQuestionsAnsweredCorrectly + 1;
						
						
					}
					
					
				}
				
			}
		}

		return totalForScoreQuestionsAnsweredCorrectly;
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
	 *//*
	private boolean areAllQuestionsAnswered(List<Question> questionList, UserResponse response) {

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
			
	
		
	
		
		// Itarate over the question List
		List questionResponseList = null;
		boolean result=true;
		for (int i = 0; i < questionList.size(); i++) {

			// Get the question
			Question question = (Question) questionList.get(i);

			if (question.getQuestionTypeId() == QuestionTypeConstant.MATRIX.getQuestionTypeId()) {

				// Create QuestionControl for the Child Question
				List subQuestionList = question.getMatrixQuestions();

				if (subQuestionList != null) {

						for (int k = 0; k < subQuestionList.size(); k++) {
	
							Question subQuestion = (Question) subQuestionList.get(k);
							questionResponseList = response.getQuestionResponseList(question.getQuestionId());
	
							int questionId = subQuestion.getQuestionId();
							result=isQuestionsAnswered(response,subQuestion);
							
						}
				}

			} 
			else {

				
				questionResponseList = response.getQuestionResponseList(question.getQuestionId());
				result=isQuestionsAnswered(response,question);
			}

		}
		// The control will reach to this point
		// when the whole questionList is being iterated through
		return result.get();
	}
*/



}
