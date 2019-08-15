package com.  .activity.adaptor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.  .activity.model.UserActivity;
import com.  .qnaservice.entity.QuestionForm;
import com.  .qnaservice.entity.Questionnaire;
import com.  .qnaservice.entity.Status;
import com.  .qnaservice.entity.ProfileResponse;
import com.  .activity.qna.model.UserFormResponse;
import com.  .activity.qna.model.UserResponse;
import com.  .prof_utilities.config.MedscapeAppConfig;

public class QNAAdaptor extends BaseAdaptor {
	
	protected final Logger logger = LoggerFactory.getLogger(QNAAdaptor.class);
	private String endPoint;
	private String serviceUrl = null;

	public Questionnaire getQuestionnaireById(int questionnaireId, String guid, Integer siteId, Boolean aggregator,
			Class classz) {

		ArrayList pathvaribleLs = Lists.newArrayList();
		pathvaribleLs.add(String.valueOf(questionnaireId));
		Map<String, String> params = Maps.newHashMap();
		if (aggregator != null) {
			params.put("aggregated", String.valueOf(aggregator));
		}

		return (Questionnaire) get(buildServiceUrl(pathvaribleLs, params, "pub/questionnaire"), classz, guid);
	}

	public QuestionForm getQuestionnaireByFormId(int questionnaireId, Integer formId, String guid, Boolean aggregator,
			Class classz) {

		/// questionnaire/{questionnaireId}/form/{formId}
		ArrayList pathvaribleLs = Lists.newArrayList();
		pathvaribleLs.add(String.valueOf(questionnaireId));
		Map<String, String> params = Maps.newHashMap();
		if (formId != null) {
			params.put("formId", String.valueOf(formId));
		}
		if (aggregator != null) {

			params.put("aggregated", String.valueOf(aggregator));
		}

		return (QuestionForm) get(buildServiceUrl(pathvaribleLs, params, "pub/questionnaire"), classz, guid);
		
	}

	public Status saveUserResponse(UserActivity userActivity, String guid, Class classz) {

		/// "/save/userresponse"
		ArrayList pathvaribleLs = Lists.newArrayList();
		Map<String, String> params = Maps.newHashMap();
		return (Status) post(buildServiceUrl(pathvaribleLs, params, "pub/save/userresponse"), userActivity, classz, guid);
	}

	/*
	 * public Status saveUserResponse(UserFormResponse userFormResponse, String
	 * guid,Class classz){
	 * 
	 * 
	 * ///"/save/userresponse" ArrayList pathvaribleLs =Lists.newArrayList();
	 * Map<String, String> params = Maps.newHashMap(); if(guid!=null){
	 * params.put("guid", String.valueOf(guid)); }
	 * 
	 * 
	 * 
	 * String userResponseJson = null; try { userResponseJson =
	 * JsonStringConverter.getJsonString(userFormResponse); } catch
	 * (JsonProcessingException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } HttpHeaders headers = new HttpHeaders() {{
	 * add("X-User-Guid", guid); }};
	 * headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	 * HttpEntity<String> httpEntity = new
	 * HttpEntity<String>(userResponseJson,headers); ResponseEntity<Object>
	 * exchange = restTemplate.exchange(buildServiceUrl(pathvaribleLs,
	 * params,"/save/userresponse"), HttpMethod.POST, httpEntity, classz);
	 * return (Status) exchange.getBody();
	 * 
	 * }
	 */

	@PostConstruct
	public void initialize() {

		if (endPoint == null) {
			
			endPoint = MedscapeAppConfig.getString("global.qnaServiceUrl");
			
		}
	}

	private String buildServiceUrl(List<String> pathVarible, Map<String, String> params, String serviceName) {

		StringBuilder builder = new StringBuilder(endPoint);
		builder.append("/").append(serviceName);
		
		pathVarible.stream().forEach(e -> {
			builder.append("/" + e);
		});
		return buildURI(builder.toString(), params);

	}

}
