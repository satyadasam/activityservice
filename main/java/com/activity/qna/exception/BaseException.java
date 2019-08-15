package com.  .activity.qna.exception;

/**
 * The class extends Exception. As a result, it is a checked exception class.<br>
 * (Of course, it is also a Throwable class, because Exception extends
 * Throwable.)<br>
 * 
 * @author Vikas Gupta
 * @version "%I%, %G%"
 * 
 */
public class BaseException extends Exception {

	public static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public BaseException() {
		super();
	}// end constructor

	/**
	 * Constructor, which takes a String parameter
	 * 
	 * @param message
	 */
	public BaseException(String message) {
		super(message);
	}// end constructor

	/**
	 * Constructor, which takes a Throwable parameter
	 * 
	 * @param throwable
	 */
	public BaseException(Throwable throwable) {
		super(throwable);
	}// end constructor

	/**
	 * Constructor, which takes a String parameter and a Throwable parameter
	 * 
	 * @param message
	 * @param throwable
	 */
	public BaseException(String message, Throwable throwable) {
		super(message, throwable);
	}// end constructor

	/**
	 * This method returns the String error message associated with an exception
	 * 
	 * @return String
	 */
	public String getMessage() {
		return super.getMessage();
	}

	/**
	 * The method returns the Throwable object associated with the exception
	 * message.
	 * 
	 * @return Throwable
	 */
	public Throwable getCause() {
		return super.getCause();
	}
}

