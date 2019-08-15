/****************************************************************************
 * Copyright (c) 2009    Incorporated, All Rights Reserved 
 * Unpublished copyright. All rights reserved. This material contains
 * proprietary information that shall be used or copied only FOR THE BENEFIT OF
 * OR USE BY   , except with written permission of   .
 ******************************************************************************/

package com.  .activity.qna.dao;


import javax.sql.DataSource;


/**
 * This class is the Base DAO class for all DAOs
 * The class defines the Data Source and Its getters and Setters
 * 
 * @author Vikas Gupta
 * @version 1.0
 */
public abstract class JDBCBaseDAO {

	/*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Private data members
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

	/**
	 * DataSource for accessing Medcache database. This is injected by Spring
	 */
	DataSource dataSource = null;

	   /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Public methods
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
	/**
	 * Getter for Data Source
	 * @return DataSource
	 */
	public DataSource getDataSource() {
		return this.dataSource;
	}

	/**
	 * Setter for Data Source
	 *  
	 * @param dataSource
	 * @return void
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
