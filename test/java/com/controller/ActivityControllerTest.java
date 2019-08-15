package com.  .controller;

import static org.junit.Assert.assertEquals;


import javax.servlet.http.Cookie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.  .BaseActivityServiceApplicationTests;
import com.  .activity.model.AppResponse;
import com.  .activity.model.UserActivity;
import com.  .activity.qna.model.QuestionResponse;
import com.  .auth.core.AuthPassport;
import com.  .auth.core.  AuthToken;
import com.  .prof_utilities.common.utils.CookieUtils;
import com.  .prof_utilities.util.CookieUtil;



@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityControllerTest extends BaseActivityServiceApplicationTests {
	
	/*@Test
	public void testConnection() throws ClassNotFoundException, SQLException{
		
		try{
			
			Class.forName("oracle.jdbc.OracleDriver");
			
			   Connection con=	DriverManager.getConnection("jdbc:oracle:thin:@racsn01d-shr-08.portal.  .com:1521:PDCMTRTI", "qa_app", "qa_app");
			   System.out.println(con);
		}catch(Exception ex){
			
			ex.printStackTrace();
		}
	
		
	}*/
	
	/*
	 * 
	 * 
	 * 
	 */
	@Test	
	public void saveUserActivityInternalFormAnsweredTest() throws Exception {		
  
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("questionnaireId", "3061");
	    params.set("formId", "3061");
	    params.set("guid", "626199");
	    
		
		UserActivity userFormResponse = new UserActivity();
		userFormResponse.setQuestionnaireId(2288);
		userFormResponse.setFormId(9);
	    userFormResponse.setSiteId(2001);
		
		/*	QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setQuestionId(9078);
		questionResponse.setChoiceId(35269);
		
	
		QuestionResponse questionResponse1 = new QuestionResponse();
		questionResponse1.setQuestionId(9079);
		questionResponse1.setChoiceId(35272);
		
		QuestionResponse questionResponse2 = new QuestionResponse();
		questionResponse2.setQuestionId(9080);
		questionResponse2.setChoiceId(35277);
		
		QuestionResponse questionResponse3 = new QuestionResponse();
		questionResponse3.setQuestionId(9081);
		questionResponse3.setChoiceId(35280);
		
		QuestionResponse questionResponse4 = new QuestionResponse();
		questionResponse4.setQuestionId(9082);
		questionResponse4.setChoiceId(35285);
		
		QuestionResponse questionResponse5 = new QuestionResponse();
		questionResponse5.setQuestionId(9083);
		questionResponse5.setChoiceId(35290);*/
		
		QuestionResponse questionResponse6 = new QuestionResponse();
		questionResponse6.setQuestionId(9084);
		questionResponse6.setChoiceId(35295);
		
	/*	QuestionResponse questionResponse7 = new QuestionResponse();
		questionResponse7.setQuestionId(9085);
		questionResponse7.setChoiceId(35298);
		*/
		
		userFormResponse.setQuestionResponses(Lists.newArrayList(questionResponse6));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);		
		String userResponseJson = mapper.writeValueAsString(userFormResponse);		
	//	AuthPassport authPassport = authServiceAdapter.authenticateGuid(626199, null);		
		//  AuthToken authToken = authPassport.getAuthToken();		
		//Cookie mednetCookie = new Cookie(CookieUtil.MEDNET, CookieUtils.encryptString(authToken.getTokenValue()));  
	
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/save/useractivity")
				.servletPath("/save/useractivity")
				.headers(headers)
				//.header("Cookie", getCookieHeader(mednetCookie))
				//.cookie(mednetCookie)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userResponseJson)
				.accept(MediaType.APPLICATION_JSON))
			    .andExpect(MockMvcResultMatchers.status().isCreated());	
		String response = result.andReturn().getResponse().getContentAsString();
		AppResponse appResponse= mapper.readValue(response, AppResponse.class);
		assertEquals("User Activity was Sucess ", "SUCCESS", appResponse.getResponseMsg().getMessage());
	}
	
		@Test
	public void saveUserActivityInternalFormUnAnsweredTest() throws Exception {		
		

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("questionnaireId", "3061");
	    params.set("formId", "3061");
	    params.set("guid", "626199");
	    
		
		UserActivity userFormResponse = new UserActivity();
		userFormResponse.setQuestionnaireId(2288);
		userFormResponse.setFormId(9);
		userFormResponse.setSiteId(2001);
		
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setQuestionId(9085);
		questionResponse.setChoiceId(3);
		
		userFormResponse.setQuestionResponses(Lists.newArrayList(questionResponse));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);		
		String userResponseJson = mapper.writeValueAsString(userFormResponse);		
		/*AuthPassport authPassport = authServiceAdapter.authenticateGuid(626199, null);		
		  AuthToken authToken = authPassport.getAuthToken();		
		Cookie mednetCookie = new Cookie(CookieUtil.MEDNET, CookieUtils.encryptString(authToken.getTokenValue()));  */
	
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/save/useractivity")
				.servletPath("/save/useractivity")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userResponseJson)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());		
		String response = result.andReturn().getResponse().getContentAsString();		
		AppResponse appResponse= mapper.readValue(response, AppResponse.class);
		assertEquals("User Not Answered Questions was Sucess ", "UNANSWERED_QUESTIONS", appResponse.getResponseMsg().getMessage());
	}
	
	
	@Test
	//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public void saveUserActivityEvalFormActivityUnavilableTest() throws Exception {		
		

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("questionnaireId", "22");
	    params.set("formId", "3");
	    params.set("guid", "626199");
	    
		
		UserActivity userFormResponse = new UserActivity();
		userFormResponse.setQuestionnaireId(22);
		userFormResponse.setFormId(3);
		userFormResponse.setSiteId(2001);
		
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setQuestionId(499);
		questionResponse.setChoiceId(1100);
		

		QuestionResponse questionResponse1 = new QuestionResponse();
		questionResponse1.setQuestionId(507);
		questionResponse1.setChoiceId(1260);
		
		userFormResponse.setQuestionResponses(Lists.newArrayList(questionResponse));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);		
		String userResponseJson = mapper.writeValueAsString(userFormResponse);		
	/*	AuthPassport authPassport = authServiceAdapter.authenticateGuid(626199, null);		
		  AuthToken authToken = authPassport.getAuthToken();		
		Cookie mednetCookie = new Cookie(CookieUtil.MEDNET, CookieUtils.encryptString(authToken.getTokenValue()));  */
	
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/save/useractivity")
				.servletPath("/save/useractivity")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userResponseJson)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());		
		String response = result.andReturn().getResponse().getContentAsString();		
		AppResponse appResponse= mapper.readValue(response, AppResponse.class);
		assertEquals("User Activity was Sucess ", "SUCCESS", appResponse.getResponseMsg().getMessage());
	}
	
	@Test
	//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public void saveUserActivityEvalFormTest() throws Exception {		
		

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("questionnaireId", "2288");
	    params.set("formId", "2");
	    params.set("guid", "626199");
	    
		
		UserActivity userFormResponse = new UserActivity();
		userFormResponse.setQuestionnaireId(2288);
		userFormResponse.setFormId(2);
		userFormResponse.setSiteId(2001);
		
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setQuestionId(499);
		questionResponse.setChoiceId(1103);
		
		
		QuestionResponse questionResponse1 = new QuestionResponse();
		questionResponse1.setQuestionId(500);
		questionResponse1.setChoiceId(1104);
		
		QuestionResponse questionResponse2 = new QuestionResponse();
		questionResponse2.setQuestionId(501);
		questionResponse2.setChoiceId(1100);
		
		QuestionResponse questionResponse3 = new QuestionResponse();
		questionResponse3.setQuestionId(502);
		questionResponse3.setChoiceId(1101);
		
		QuestionResponse questionResponse4 = new QuestionResponse();
		questionResponse4.setQuestionId(503);
		questionResponse4.setChoiceId(1103);
		
		QuestionResponse questionResponse5 = new QuestionResponse();
		questionResponse5.setQuestionId(504);
		questionResponse5.setChoiceId(35290);
		
		QuestionResponse questionResponse6 = new QuestionResponse();
		questionResponse6.setQuestionId(505);
		questionResponse6.setChoiceId(35295);
		
		userFormResponse.setQuestionResponses(Lists.newArrayList(questionResponse,questionResponse1,questionResponse2,questionResponse3,questionResponse4,questionResponse5,questionResponse6));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);		
		String userResponseJson = mapper.writeValueAsString(userFormResponse);		
		/*AuthPassport authPassport = authServiceAdapter.authenticateGuid(626199, null);		
		  AuthToken authToken = authPassport.getAuthToken();		
		Cookie mednetCookie = new Cookie(CookieUtil.MEDNET, CookieUtils.encryptString(authToken.getTokenValue()));  
	*/
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/save/useractivity")
				.servletPath("/save/useractivity")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userResponseJson)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());		
		String response = result.andReturn().getResponse().getContentAsString();		
		AppResponse appResponse= mapper.readValue(response, AppResponse.class);
		assertEquals("User Not Answered Questions was Sucess ", "UNANSWERED_QUESTIONS", appResponse.getResponseMsg().getMessage());
	}
	
	@Test
	//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public void saveUserActivityPreFormTest() throws Exception {		
		

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("questionnaireId", "2888");
	    params.set("formId", "4");
	    params.set("guid", "626199");
	    
		
		UserActivity userFormResponse = new UserActivity();
		userFormResponse.setQuestionnaireId(2888);
		userFormResponse.setFormId(4);
		userFormResponse.setSiteId(2001);
		
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setQuestionId(11912);
		questionResponse.setChoiceId(46813);
		
		
		QuestionResponse questionResponse1 = new QuestionResponse();
		questionResponse1.setQuestionId(11913);
		questionResponse1.setChoiceId(46823);
		
	
		
		userFormResponse.setQuestionResponses(Lists.newArrayList(questionResponse,questionResponse1));
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);		
		String userResponseJson = mapper.writeValueAsString(userFormResponse);		
			
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/save/useractivity")
				.servletPath("/save/useractivity")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userResponseJson)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());		
		String response = result.andReturn().getResponse().getContentAsString();		
		AppResponse appResponse= mapper.readValue(response, AppResponse.class);
		assertEquals("User Activity was Sucess ", "SUCCESS", appResponse.getResponseMsg().getMessage());
	}
	@Test
	//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	public void saveUserActivityPreFormUnansweredTest() throws Exception {		
		


		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("questionnaireId", "2888");
	    params.set("formId", "4");
	    params.set("guid", "626199");
	    
		
		UserActivity userFormResponse = new UserActivity();
		userFormResponse.setQuestionnaireId(2888);
		userFormResponse.setFormId(4);
		userFormResponse.setSiteId(2001);
		
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setQuestionId(11912);
		questionResponse.setChoiceId(46813);
		
		
		QuestionResponse questionResponse1 = new QuestionResponse();
		questionResponse1.setQuestionId(11900);
		questionResponse1.setChoiceId(46823);
		
	
		
		userFormResponse.setQuestionResponses(Lists.newArrayList());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);		
		String userResponseJson = mapper.writeValueAsString(userFormResponse);		
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/save/useractivity")
				.servletPath("/save/useractivity")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userResponseJson)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());		
		String response = result.andReturn().getResponse().getContentAsString();		
		AppResponse appResponse= mapper.readValue(response, AppResponse.class);
		assertEquals("User Not Answered Questions was Sucess ", "UNANSWERED_QUESTIONS", appResponse.getResponseMsg().getMessage());
	}
	
	@Test
	public void saveUserActivityPostFormTest() throws Exception {		
		

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("questionnaireId", "2888");
	    params.set("formId", "3");
	    params.set("guid", "626199");
	    
		
		UserActivity userFormResponse = new UserActivity();
		userFormResponse.setQuestionnaireId(2888);
		userFormResponse.setFormId(3);
		userFormResponse.setSiteId(2001);
		
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setQuestionId(11875);
		questionResponse.setChoiceId(46667);
		
		
		QuestionResponse questionResponse1 = new QuestionResponse();
		questionResponse1.setQuestionId(11876);
		questionResponse1.setChoiceId(46669);
		
		QuestionResponse questionResponse2 = new QuestionResponse();
		questionResponse2.setQuestionId(11877);
		questionResponse2.setChoiceId(46675);
		
		QuestionResponse questionResponse3 = new QuestionResponse();
		questionResponse3.setQuestionId(11878);
		questionResponse3.setChoiceId(46678);
		
		QuestionResponse questionResponse4 = new QuestionResponse();
		questionResponse4.setQuestionId(11879);
		questionResponse4.setChoiceId(46678);
		
	    
		userFormResponse.setQuestionResponses(Lists.newArrayList(questionResponse,questionResponse1,questionResponse2,questionResponse3,questionResponse4));
		
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);		
		String userResponseJson = mapper.writeValueAsString(userFormResponse);		
			ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/save/postUseractivity")
				.servletPath("/save/postUseractivity")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userResponseJson)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());		
		String response = result.andReturn().getResponse().getContentAsString();		
		AppResponse appResponse= mapper.readValue(response, AppResponse.class);
		assertEquals("User Activity was Sucess ", "SUCCESS", appResponse.getResponseMsg().getMessage());
	}
	
	@Test
	public void saveUserActivityPostCreditAlreadyEarnedTest() throws Exception {		
		

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("questionnaireId", "2888");
	    params.set("formId", "3");
	    params.set("guid", "626199");
	    
		
		UserActivity userFormResponse = new UserActivity();
		userFormResponse.setQuestionnaireId(2888);
		userFormResponse.setFormId(3);
		userFormResponse.setSiteId(2001);
		
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setQuestionId(11800);
		questionResponse.setChoiceId(46667);
		
		
		QuestionResponse questionResponse1 = new QuestionResponse();
		questionResponse1.setQuestionId(11876);
		questionResponse1.setChoiceId(46669);
		
		QuestionResponse questionResponse2 = new QuestionResponse();
		questionResponse2.setQuestionId(11877);
		questionResponse2.setChoiceId(46675);
		
		QuestionResponse questionResponse3 = new QuestionResponse();
		questionResponse3.setQuestionId(11878);
		questionResponse3.setChoiceId(46678);
		
		QuestionResponse questionResponse4 = new QuestionResponse();
		questionResponse4.setQuestionId(11879);
		questionResponse4.setChoiceId(46678);
		
	
		userFormResponse.setQuestionResponses(Lists.newArrayList(questionResponse,questionResponse1,questionResponse2,questionResponse3,questionResponse4));
		
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);		
		String userResponseJson = mapper.writeValueAsString(userFormResponse);		
	
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/save/postUseractivity")
				.servletPath("/save/postUseractivity")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userResponseJson)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());		
		String response = result.andReturn().getResponse().getContentAsString();		
		AppResponse appResponse= mapper.readValue(response, AppResponse.class);
		assertEquals("Credits Already Earned ", "CREDIT_ALREADY_EARNED", appResponse.getResponseMsg().getMessage());
	}
	
	
	@Test
	public void saveUserActivityPostMaxAttemptsFormTest() throws Exception {		
		

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
	    params.set("questionnaireId", "21166");
	    params.set("formId", "5");
	    params.set("guid", "12686985");
	    
		
		UserActivity userFormResponse = new UserActivity();
		userFormResponse.setQuestionnaireId(14498);
		userFormResponse.setFormId(5);
		userFormResponse.setSiteId(2003);
		
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setQuestionId(124620);
		questionResponse.setChoiceId(422703);
		
		
		QuestionResponse questionResponse1 = new QuestionResponse();
		questionResponse1.setQuestionId(124621);
		questionResponse1.setChoiceId(422707);
		
		QuestionResponse questionResponse2 = new QuestionResponse();
		questionResponse2.setQuestionId(124622);
		questionResponse2.setChoiceId(422711);
		
	
		userFormResponse.setQuestionResponses(Lists.newArrayList(questionResponse,questionResponse1,questionResponse2));
		
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);		
		String userResponseJson = mapper.writeValueAsString(userFormResponse);		
		
	
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/save/postUseractivity")
				.servletPath("/save/postUseractivity")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userResponseJson)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());		
		String response = result.andReturn().getResponse().getContentAsString();		
		AppResponse appResponse= mapper.readValue(response, AppResponse.class);
		assertEquals("Max Attempts ", "MAX_ATTEMPTS_MET", appResponse.getResponseMsg().getMessage());
	}
	
}
