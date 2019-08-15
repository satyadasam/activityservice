package com.  .activity.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author SSangapalli
 *
 */
public class ExceptionUtils {
	
	
	
	public static String getStackTrace(Exception ex){
		
		StringWriter writer= new StringWriter();
		PrintWriter printWriter= new PrintWriter(writer);		
		ex.printStackTrace(printWriter);
		return writer.toString();
	}

}
