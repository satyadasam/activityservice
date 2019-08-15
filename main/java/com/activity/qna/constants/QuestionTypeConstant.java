/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/

package com.  .activity.qna.constants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class defines the enum pattern for Question Type
 * 
 * @author Vikas Gupta
 * @version 1.0
 */
public class QuestionTypeConstant {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * data members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private int questionTypeId;
	private String questionTypeName;
	private static final Map questionTypeMap = new HashMap();

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Private Constructor
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private QuestionTypeConstant(int questionTypeId, String questionTypeName) {
		this.questionTypeId = questionTypeId;
		this.questionTypeName = questionTypeName;
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Static Final Constant Definitions
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	public static final QuestionTypeConstant REGULAR = new QuestionTypeConstant(
			1, "REGULAR");
	public static final QuestionTypeConstant MATRIX = new QuestionTypeConstant(
			2, "MATRIX");

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Public methods
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	/**
	 * This methods is used to get the Question Type Id
	 */
	public int getQuestionTypeId() {
		return questionTypeId;
	}

	/**
	 * This methods is used to get the Question Type Name
	 */
	public String getQuestionTypeName() {
		return questionTypeName;
	}

	/**
	 * This method returns the QuestionTypeConstant based on question Type id.
	 * 
	 * @param questionTypeId
	 * @return QuestionTypeConstant
	 */
	public static QuestionTypeConstant getQuestionTypeConstantById(int questionTypeId) {

		if (questionTypeMap.containsKey(Integer.toString(questionTypeId))) {
			return (QuestionTypeConstant) questionTypeMap.get(Integer
					.toString(questionTypeId));
		}

		return null;
	}

	/**
	 * This method returns the QuestionTypeConstant based on question Type Name.
	 * 
	 * @param questionTypeName
	 * @return QuestionTypeConstant
	 */
	public static QuestionTypeConstant getQuestionTypeConstantByName(
			String questionTypeName) {

		// getting the keyset
		Set keyset = questionTypeMap.keySet();

		// getting the iterator
		Iterator iter = keyset.iterator();

		while (iter.hasNext()) {
			
			// Getting the QuestionTypeConstant object
			QuestionTypeConstant qtc = (QuestionTypeConstant) iter.next();
			
			// matching the QuestionTypeConstant names
			if (qtc.getQuestionTypeName().equalsIgnoreCase(questionTypeName)) {
				
				// If a match found, return QuestionTypeConstant
				return qtc;
			}
		}
		
		// No match Found, return null.
		return null;
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Static Initializer
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	static {
		
		// Populating the questionTypeMap
		
		questionTypeMap.put(Integer.toString(QuestionTypeConstant.REGULAR
				.getQuestionTypeId()), QuestionTypeConstant.REGULAR);
		questionTypeMap.put(Integer.toString(QuestionTypeConstant.MATRIX
				.getQuestionTypeId()), QuestionTypeConstant.MATRIX);		
	}

}
