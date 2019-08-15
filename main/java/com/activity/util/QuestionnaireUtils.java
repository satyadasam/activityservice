package com.  .activity.util;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.  .qnaservice.entity.Eligibility;
import com.  .qnaservice.entity.QuestionForm;
import com.  .qnaservice.entity.Questionnaire;



public class QuestionnaireUtils {
	
	
	public static QuestionForm  getQuestionFormForId(Questionnaire questionnaire, int formId){	
		Optional<QuestionForm> optional=questionnaire.getQuestionForms().stream().filter(questionForm->(formId==questionForm.getFormId())).findFirst();
		if(optional.isPresent()){			
			return optional.get();
		}
		return null;
	}
	
	
	public static List<QuestionForm>  getQuestionFormByFormTypeId(Questionnaire questionnarire, int formTypeId){	
		List<QuestionForm> questionFormList=questionnarire.getQuestionForms().stream().filter(questionForm->(formTypeId==questionForm.getFormTypeId())).collect(toList());	
		return questionFormList;
	}
	
	
	/**
	 * This method is to determine if the creditType is associated with the questionnaire
	 * 
	 * @param creditTypeId
	 * @param questionnaire
	 * 
	 * @return boolean
	 */
	public static boolean isCreditTypeAssociated(int creditTypeId, Questionnaire questionnaire){
		
		boolean isCreditTypeAssociated = false;	
		Map<Integer, Eligibility> eligibilityMap=questionnaire.getActivityInfo().getEligibilityMap();
		Eligibility eligibility =eligibilityMap.get(new Integer(creditTypeId) );		
		if(eligibility != null){
			isCreditTypeAssociated = true;
		}		
		return isCreditTypeAssociated;

	}
	
	public static boolean isValidString(String aVariable) {

		if ((aVariable != null) && (aVariable.trim().length() > 0)) {
			return true;
		} else {
			return false;
		}
	}
	

}
