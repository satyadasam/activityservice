package com.  .activity.qna.exception.manager;

import com.  .activity.qna.exception.BaseException;

/**
 * The class extends BaseException. As a result, it is a checked exception class.<br>
 * This exception will be thrown from the QNA Manager Classes.
 * Since QNAManager could be catching multiple types of exceptions, Its better to
 * combine all those exceptions and throw a more general exception like 
 * <strong>QNAManagerException</strong>
 *
 * @author Vikas Gupta
 * @version 1.0
 * 
 */
public class QNAManagerException extends BaseException {

	public QNAManagerException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public QNAManagerException(String msg) {
		super(msg);
	}
}
