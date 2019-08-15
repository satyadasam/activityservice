package com.  .activity.qna.exception.rule;

import com.  .activity.qna.exception.BaseException;

/**
 * The class extends BaseException. As a result, it is a checked exception class.<br>
 * This exception will be thrown When there is an exception while analyzing
 * an QNA Business Rule. i.e. from QNABusinessRuleService
 *
 * @author Vikas Gupta
 * @version 1.0
 * 
 */
public class QNARuleException extends BaseException{

	public QNARuleException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public QNARuleException(String msg) {
		super(msg);
	}
}
