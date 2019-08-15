package com.  .activity.qna.model;

import java.io.Serializable;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import com.  .activity.qna.storedprocedures.UpdateCMEResultStoredProcedure;


/**
 * This class defines the DataModel used to Update the activity info
 * info in activity result table.
 * Since Updated Fields are passed as an ARRAY to the Oracle Stored procedure,
 * the class implements  SQLData and Serializable.
 * It overrides readSQL, writeSQL and getSQLTypeName Methods defined in SQLData interface.
 * 
 * The class defines the required serialVersionUID required by SQLData 
 * Defines Getters and Setters
 * 
 * @author Vikas Gupta
 * @version 1.0
 */
public class DBUpdateActivityResult implements SQLData,
		Serializable {

	// Defining serialVersionUID as required by SQLData
	static final long serialVersionUID = 4070409649129120458L;
	
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * data members
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	protected String fieldName;
	protected String fieldValue;
	
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Public methods
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	
	public String toString(){
		
		StringBuffer sb = new StringBuffer();
		sb.append( "----------------------------------------------------------");
		sb.append( "fieldName = " + fieldName );
		sb.append( "::");
		sb.append( "fieldValue = " + fieldValue );
		sb.append( "----------------------------------------------------------");
		
		
		return sb.toString();
		
	}
	
	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Public abstract methods, defined in interface java.sql.SQLData
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */

	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		
		fieldName = stream.readString();
		fieldValue = stream.readString();
	}

	public void writeSQL(SQLOutput stream) throws SQLException {

		stream.writeString(getFieldName());
		stream.writeString(getFieldValue());
	}

	public String getSQLTypeName() throws SQLException {
		// TODO Auto-generated method stub
		return UpdateCMEResultStoredProcedure.TYPE_FIELD_OBJECT_TYPE;
	}
	
}	
