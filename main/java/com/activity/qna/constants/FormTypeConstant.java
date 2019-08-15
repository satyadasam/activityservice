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
 * This class defines the enum pattern for Form Type
 * 
 * @author Vikas Gupta
 * @version 1.0
 */
public class FormTypeConstant {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * data members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private int formTypeId;
	private String formName;
	private static final Map formTypeMap = new HashMap();

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Private Constructor
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private FormTypeConstant(int formTypeId, String formName) {
		this.formTypeId = formTypeId;
		this.formName = formName;
	}
	
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Static Final Constant Definitions
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	public static final FormTypeConstant DEFAULT = new FormTypeConstant(1, "DEFAULT");
	public static final FormTypeConstant PRE = new FormTypeConstant(2, "PRE");
	public static final FormTypeConstant INTERNAL = new FormTypeConstant(3, "INTERNAL");
	public static final FormTypeConstant POST = new FormTypeConstant(4, "POST");
	public static final FormTypeConstant EVAL = new FormTypeConstant(5, "EVAL");
	public static final FormTypeConstant MISCELLANEOUS = new FormTypeConstant(6, "MISCELLANEOUS");	
	public static final FormTypeConstant SELFASSESSMENT = new FormTypeConstant(11, "SELFASSESSMENT");
	public static final FormTypeConstant CHARTREVIEW = new FormTypeConstant(12, "CHARTREVIEW");

	
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Public methods
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	/**
	 * This methods is used to get the Display Type Id
	 */
	public int getFormTypeId() {
		return formTypeId;
	}

	/**
	 * This methods is used to get the Display Type Name
	 */
	public String getFormName() {
		return formName;
	}

	/**
	 * This method returns the FormTypeConstant based on display Type id.
	 * 
	 * @param formTypeId
	 * @return FormTypeConstant
	 */
	public static FormTypeConstant getDisplayTypeConstantById(int formTypeId) {

		if (formTypeMap.containsKey(Integer.toString(formTypeId))) {
			return (FormTypeConstant) formTypeMap.get(Integer
					.toString(formTypeId));
		}

		return null;
	}

	/**
	 * This method returns the FormTypeConstant based on display Type Name.
	 * 
	 * @param formName
	 * @return FormTypeConstant
	 */
	public static FormTypeConstant getDisplayTypeConstantByName(
			String formName) {

		// getting the keyset
		Set keyset = formTypeMap.keySet();

		// getting the iterator
		Iterator iter = keyset.iterator();

		while (iter.hasNext()) {
			
			// Getting the FormTypeConstant object
			FormTypeConstant ftc = (FormTypeConstant) iter.next();
			
			// matching the FormTypeConstant names
			if (ftc.getFormName().equalsIgnoreCase(formName)) {
				
				// If a match found, return FormTypeConstant
				return ftc;
			}
		}
		
		// No match Found, return null.
		return null;
	}
	
	/**
	 * 
	 */
	public static String getFormTypeString( int formTypeId ){
		
		FormTypeConstant ftc = getDisplayTypeConstantById( formTypeId );
		
		if( null != ftc ){
			return ftc.getFormName();
		}
		
		return "";
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Static Initializer
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	static {
		
		// Populating the formTypeMap
		
		formTypeMap.put(Integer.toString(FormTypeConstant.DEFAULT
				.getFormTypeId()), FormTypeConstant.DEFAULT);
		formTypeMap.put(Integer.toString(FormTypeConstant.PRE
				.getFormTypeId()), FormTypeConstant.PRE);
		formTypeMap.put(Integer.toString(FormTypeConstant.INTERNAL
				.getFormTypeId()), FormTypeConstant.INTERNAL);
		formTypeMap.put(Integer.toString(FormTypeConstant.POST				
				.getFormTypeId()), FormTypeConstant.POST);
		formTypeMap.put(Integer.toString(FormTypeConstant.EVAL
				.getFormTypeId()), FormTypeConstant.EVAL);
		formTypeMap.put(Integer.toString(FormTypeConstant.MISCELLANEOUS
				.getFormTypeId()), FormTypeConstant.MISCELLANEOUS);
	}
	

}
