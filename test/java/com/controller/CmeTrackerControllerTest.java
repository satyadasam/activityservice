package com.  .controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.  .BaseActivityServiceApplicationTests;
import com.  .activity.model.ActivityResultUpdate;
import com.  .activity.model.AppResponse;
import com.  .activity.model.UserActivity;
import com.  .activity.qna.model.QuestionResponse;
import com.  .activitytracker.util.ActivityTrackerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
public class CmeTrackerControllerTest extends BaseActivityServiceApplicationTests {



	@Test
	public void getCMETracker() throws Exception {
		ActivityTrackerRequest request = new ActivityTrackerRequest();
		request.setFmonth("1");
		request.setFyear("2016");
		request.setTmonth("12");
		request.setTyear("2016");

		ObjectMapper mapper = new ObjectMapper();
		String input = mapper.writeValueAsString(request);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/activitytracker/v2/json")
				.servletPath("/activitytracker/v2/json")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON)
				.content(input)
				.accept(MediaType.APPLICATION_JSON))
				//.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				;
		String response = result.andReturn().getResponse().getContentAsString();
		//AppResponse appResponse= mapper.readValue(response, AppResponse.class);
 		assertNotNull(response);


	}

	@Test
	public void test() throws Exception {
		ActivityTrackerRequest request = new ActivityTrackerRequest();
		request.setFmonth("1");
		request.setFyear("2016");
		request.setTmonth("12");
		request.setTyear("2016");

		ObjectMapper mapper = new ObjectMapper();
		String input = mapper.writeValueAsString(request);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/tracker/test")
				.servletPath("/tracker/test")
				.headers(headers)
				.contentType(MediaType.APPLICATION_JSON)
				.content(input)
				.accept(MediaType.APPLICATION_JSON))
				//.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				;
		String response = result.andReturn().getResponse().getContentAsString();
		//AppResponse appResponse= mapper.readValue(response, AppResponse.class);
		assertNotNull(response);


	}
	
}
