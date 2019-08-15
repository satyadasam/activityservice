package com.  .  .auth.agent.filter;

import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.After;
import org.junit.AfterClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.  .BaseActivityServiceApplicationTests;
import com.  .auth.service.adapter.AuthServiceAdapter;
import com.  .prof_utilities.config.MedscapeAppConfig;


@ContextConfiguration(locations={
        "classpath*:com/  /prof_utilities/cp-httpclient.xml"
})
public abstract class BaseFilterTest extends BaseActivityServiceApplicationTests {
	
	
	//Mock Objects
	@Autowired
    protected MockHttpServletRequest request;
	
	@Autowired
	protected MockHttpServletResponse response;
	
	@Autowired
	protected MockHttpSession session;
	
	@Autowired
	protected MockServletContext servletContext;
	
	@Autowired
	protected MockFilterChain filterChain;
	
	@Autowired
	protected MockFilterConfig filterConfig;
	
	@Autowired
	protected AuthServiceAdapter authServiceAdapter;
		
	
	@AfterClass
	public static void classTearDown() {
		try {
			MedscapeAppConfig.getInstance().destroy();
		} catch(IllegalStateException e) {
			// never initialized
		}
	}
	
	@After
	public void tearDown() {
		reset();
	}
	
	protected void reset() {
		reset(false, false);
	}
	
	protected void reset(boolean newRequest) {
		reset(newRequest, false);
	}
	
	protected void reset(boolean newRequest, boolean newResponse) {
		if(!newRequest) {
			request.clearAttributes();
			request.removeAllParameters();
			// ugly, ugly way to destroy all headers
			((Map<?, ?>) ReflectionTestUtils.getField(request, "headers")).clear();
		}
		
		if(!newResponse) {
			response.setCommitted(false);
			response.reset();
		}
		
		session.clearAttributes();
		
		request.setSession(session);
		
		filterChain.reset();
		
		request.setAttribute("notFirstInChain", true);
	}
	
	protected void addCookieHeader(Cookie... cookies) {
		StringBuilder cookieHeader = new StringBuilder();
		
		for(Cookie cookie : cookies) {
			cookieHeader.append(cookie.getName())
				.append("=")
				.append(cookie.getValue())
				.append("; ");
		}
		
		request.addHeader("Cookie", cookieHeader.toString());
	}
	
	protected void setParameters(Map<String, String> paramMap) {
		StringBuilder queryString = new StringBuilder();
		
		for(Map.Entry<String, String> param: paramMap.entrySet()) {
			String paramName = param.getKey();
			String paramValue = param.getValue();
			
			request.addParameter(paramName, paramValue);
			
			if(queryString.length() > 0) {
				queryString.append("&");
			}
			
			queryString.append(paramName).append("=").append(paramValue);
		}
		
		request.setQueryString(queryString.toString());
	}

}
