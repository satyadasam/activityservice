/**
 * @author SSangapalli
 *
 */
package com.  .activity.exception;

public class ValidationError {
	
private String field;

private String error;	
	

public ValidationError(String fieldName, String error){
	
	this.field=fieldName;
	this.error=error;
}
public String getField() {
return field;
}

public void setField(String field) {
this.field = field;
}


	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	
	public String toString(){
		
		return String.format("Field  %s -> Error %s", field,error);
	}
	

}
