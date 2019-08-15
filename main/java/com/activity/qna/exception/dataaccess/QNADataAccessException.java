package com.  .activity.qna.exception.dataaccess;

import com.  .activity.exception.ActivityException;
import com.  .activity.exception.ActivityExceptionsEnum;
import com.  .activity.qna.exception.BaseException;

/**
 * The class extends BaseException. As a result, it is a checked exception class.<br>
 * This exception will be thrown, if the stored procedure when executed will
 * throw an Data Access exeption.
 *
 * @author Vikas Gupta
 * @version 1.0
 * 
 */
public class QNADataAccessException  extends ActivityException  {

	public QNADataAccessException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public QNADataAccessException(String msg) {
		super(msg);
	}

	@Override
	public ActivityExceptionsEnum getType() {
		// TODO Auto-generated method stub
		return ActivityExceptionsEnum.QNADATAACESSEXCEPTION;
	}
}
