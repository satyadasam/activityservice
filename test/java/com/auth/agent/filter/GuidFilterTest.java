package com.  .  .auth.agent.filter;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.  .auth.core.AuthPassport;
import com.  .auth.core.  AuthToken;
import com.  .prof_utilities.common.utils.CookieUtils;
import com.  .prof_utilities.util.CookieUtil;


@RunWith(SpringJUnit4ClassRunner.class)
public class GuidFilterTest extends BaseFilterTest {
	
	
	@Before
	public void setUp() throws Exception {
		reset();
		
		AuthPassport authPassport = authServiceAdapter.authenticateGuid(626199, null);
		
		  AuthToken authToken = authPassport.getAuthToken();
		
		Cookie mednetCookie = new Cookie(CookieUtil.MEDNET, CookieUtils.encryptString(authToken.getTokenValue()));
		
		request.setCookies(mednetCookie);
		
		addCookieHeader(mednetCookie);
		
		//guidFilter.init(filterConfig);
	}
	
	@Test
	public void setGuidTest() throws Exception {
		//guidFilter.doFilter(request, response, filterChain);
		
		String guid = (String) request.getAttribute("guid");
		
		assertEquals("Guid found and set.", "626199", guid);
	}

}
