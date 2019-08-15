package com.  .activity.matcher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

import com.  .prof_utilities.util.CookieUtil;

public class UserResponseRequestMatcher implements RequestMatcher {

	@Override
	public boolean matches(HttpServletRequest request) {
		boolean matchFound = false;
		Cookie mednet = CookieUtil.getCookie(request, CookieUtil.MEDNET);
		
		if(mednet != null) {
			matchFound = true;
		}
		
		return matchFound;
	}

}
