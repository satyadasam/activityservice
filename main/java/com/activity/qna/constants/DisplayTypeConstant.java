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
 * This class defines the enum pattern for Display Type
 * 
 * @author Vikas Gupta
 * @version 1.0
 */
public class DisplayTypeConstant {

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * data members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private int displayTypeId;
	private String displayName;
	private static final Map displayTypeMap = new HashMap();

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Private Constructor
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	private DisplayTypeConstant(int displayTypeId, String displayName) {
		this.displayTypeId = displayTypeId;
		this.displayName = displayName;
	}

	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Static Final Constant Definitions
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	public static final DisplayTypeConstant RADIOBUTTON = new DisplayTypeConstant(
			1, "RADIOBUTTON");
	public static final DisplayTypeConstant CHECKBOX = new DisplayTypeConstant(
			2, "CHECKBOX");
	public static final DisplayTypeConstant DROPDOWN = new DisplayTypeConstant(
			3, "DROPDOWN");
	public static final DisplayTypeConstant TEXTBOX = new DisplayTypeConstant(
			4, "TEXTBOX");
	public static final DisplayTypeConstant TEXTAREA = new DisplayTypeConstant(
			5, "TEXTAREA");
	public static final DisplayTypeConstant LICKERT = new DisplayTypeConstant(
			6, "LICKERT");
	
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
	public int getDisplayTypeId() {
		return displayTypeId;
	}

	/**
	 * This methods is used to get the Display Type Name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * This method returns the DisplayTypeConstant based on display Type id.
	 * 
	 * @param displayTypeId
	 * @return
	 */
	public static DisplayTypeConstant getDisplayTypeConstantById(int displayTypeId) {

		if (displayTypeMap.containsKey(Integer.toString(displayTypeId))) {
			return (DisplayTypeConstant) displayTypeMap.get(Integer
					.toString(displayTypeId));
		}

		return null;
	}

	/**
	 * This method returns the DisplayTypeConstant based on display Type Name.
	 * 
	 * @param displayTypeId
	 * @return
	 */
	public static DisplayTypeConstant getDisplayTypeConstantByName(
			String displayTypeName) {

		// getting the keyset
		Set keyset = displayTypeMap.keySet();

		// getting the iterator
		Iterator iter = keyset.iterator();

		while (iter.hasNext()) {
			
			// Getting the DisplayTypeConstant object
			DisplayTypeConstant dtc = (DisplayTypeConstant) iter.next();
			
			// matching the DisplayTypeConstant names
			if (dtc.getDisplayName().equalsIgnoreCase(displayTypeName)) {
				
				// If a match found, return DisplayTypeConstant
				return dtc;
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
		
		// Populating the displayTypeMap
		
		displayTypeMap.put(Integer.toString(DisplayTypeConstant.RADIOBUTTON
				.getDisplayTypeId()), DisplayTypeConstant.RADIOBUTTON);
		displayTypeMap.put(Integer.toString(DisplayTypeConstant.CHECKBOX
				.getDisplayTypeId()), DisplayTypeConstant.CHECKBOX);
		displayTypeMap.put(Integer.toString(DisplayTypeConstant.DROPDOWN
				.getDisplayTypeId()), DisplayTypeConstant.DROPDOWN);
		displayTypeMap.put(Integer.toString(DisplayTypeConstant.TEXTBOX
				.getDisplayTypeId()), DisplayTypeConstant.TEXTBOX);
		displayTypeMap.put(Integer.toString(DisplayTypeConstant.TEXTAREA
				.getDisplayTypeId()), DisplayTypeConstant.TEXTAREA);
		displayTypeMap.put(Integer.toString(DisplayTypeConstant.LICKERT
				.getDisplayTypeId()), DisplayTypeConstant.LICKERT);
		
	}

}
