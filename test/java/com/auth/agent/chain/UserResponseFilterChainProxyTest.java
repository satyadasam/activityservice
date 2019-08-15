package com.  .  .auth.agent.chain;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.  .auth.core.AuthPassport;
import com.  .auth.core.  AuthToken;
import com.  .prof_utilities.common.utils.CookieUtils;
import com.  .prof_utilities.util.CookieUtil;
import com.  .  .auth.agent.filter.BaseFilterTest;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserResponseFilterChainProxyTest extends BaseFilterTest {
	
	@Autowired
	private FilterChainProxy userResponseFilterChainProxy;
	
	@Before
	public void setUp() throws Exception {
		//Only way to reset headers
		request = new MockHttpServletRequest();
				
		//If response is redirected or written to then it cannot be reused
		response = new MockHttpServletResponse();
				
		reset(true, true);
		
		request.setRequestURI("/guid");
		
		userResponseFilterChainProxy.init(filterConfig);
	}
	
	@Test
	public void anonymousTest() throws Exception {
		AuthPassport authPassport = authServiceAdapter.authenticateAnon();
		
		  AuthToken authToken = authPassport.getAuthToken();
		
		Cookie mednetCookie = new Cookie(CookieUtil.MEDNET, CookieUtils.encryptString(authToken.getTokenValue()));
		
		request.setCookies(mednetCookie);
		
		addCookieHeader(mednetCookie);
		
		userResponseFilterChainProxy.doFilter(request, response, filterChain);
		
		String guid = (String) request.getAttribute("guid");
		
		assertEquals("Guid found and set", authToken.getGuid(), guid);
		
		assertTrue("Did not get error response", response.getStatus() != 404);
	}
	
	@Test
	public void notAnonymousTest() throws Exception {
		AuthPassport authPassport = authServiceAdapter.authenticateGuid(626199, null);
		
		  AuthToken authToken = authPassport.getAuthToken();
		
		Cookie mednetCookie = new Cookie(CookieUtil.MEDNET, CookieUtils.encryptString(authToken.getTokenValue()));
		
		request.setCookies(mednetCookie);
		
		addCookieHeader(mednetCookie);
		
		userResponseFilterChainProxy.doFilter(request, response, filterChain);
		
		String guid = (String) request.getAttribute("guid");
		
		assertEquals("Guid found and set", authToken.getGuid(), guid);
		
		assertTrue("Did not get error response", response.getStatus() != 404);
	}
	
	@Test
	public void NoMednetCookieTest() throws Exception {
		userResponseFilterChainProxy.doFilter(request, response, filterChain);
		
		String guid = (String) request.getAttribute("guid");
		
		assertNull("Guid not set", guid);
		
		assertTrue("Did not get error response", response.getStatus() != 404);
	}

}
