package com.  .activity.adaptor;



import java.net.URI;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.  .activity.exception.AdapterException;
import com.  .activity.model.UserActivity;
import com.  .activity.qna.model.UserFormResponse;
import com.  .prof_utilities.config.MedscapeAppConfig;


public class BaseAdaptor implements InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(BaseAdaptor.class);

//	@Resource
	protected RestTemplate restTemplate;




    protected Object get(String endPointUrl, Class clazz, String header) {
        return perform(endPointUrl, HttpMethod.GET, new HttpEntity<>(buildRequestHeader(header)), clazz);
	}
    protected Object get(String endPointUrl, Class clazz) {
        return perform(endPointUrl, HttpMethod.GET, new HttpEntity<>(buildRequestHeader(null)), clazz);
	}
	
	protected Object post(String endPointUrl, Object data, Class clazz,String header) {
        return perform(endPointUrl, HttpMethod.POST, getHttpEntity(data,header), clazz);
	}
	protected Object post(String endPointUrl, UserActivity data, Class clazz,String header,String type) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		String userResponseJson = null;
		try {
			 userResponseJson = mapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return perform(endPointUrl, HttpMethod.POST, getHttpEntity(data,header,MediaType.APPLICATION_JSON_UTF8), clazz);
	}
	
	
	protected Object put(String endPointUrl, Object data, Class clazz,String header) {
        return perform(endPointUrl, HttpMethod.PUT, getHttpEntity(data,header), clazz);
	}
	
	protected Object patch(String endPointUrl, Object data, Class clazz,String header) {
        return perform(endPointUrl, HttpMethod.PATCH, getHttpEntity(data,header), clazz);
	}
	
	private HttpEntity<Object> getHttpEntity(Object data,String header) {
        return new HttpEntity<>(data, buildRequestHeader(header));
	}
	private HttpEntity<Object> getHttpEntity(Object data,String header,MediaType type) {
        return new HttpEntity<>(data, buildRequestHeader(header,type));
	}
	
	protected HttpHeaders buildRequestHeader(String guid) {
		
		
		HttpHeaders headers= new HttpHeaders() {
			 

		{	
			if(guid!=null){
				
				add("X-User-Guid", guid);
				
			}
			           
        }};
        
       
        return headers;
	}
	
	protected HttpHeaders buildRequestHeader(String guid,MediaType type) {
		
		
		HttpHeaders headers= new HttpHeaders() {
			 

		{	
			if(guid!=null){
				
				add("X-User-Guid", guid);
				
			}
			           
        }};
        
        headers.setContentType(type);
        return headers;
	}
	
	protected String buildURI(String endPointUrl, Map<String, String> uriVariables) {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(endPointUrl);
		if (!CollectionUtils.isEmpty(uriVariables)) {
			uriVariables.forEach((k, v) -> {
				builder.queryParam(k, v);
			});
		}
		return builder.toUriString();
	}
	
	protected Object perform(String endPointUrl, HttpMethod httpMethod, HttpEntity httpEntity, Class clazz) {
		 
		@SuppressWarnings("unchecked")
		ResponseEntity<Object> exchange = restTemplate.exchange(endPointUrl, httpMethod, httpEntity, clazz);
	    return exchange.getBody();
	}
	
	protected Object perform(String endPointUrl, HttpMethod httpMethod, HttpEntity httpEntity, ParameterizedTypeReference parameterizedTypeReference) {
		 
		@SuppressWarnings("unchecked")
		ResponseEntity<Object> exchange = restTemplate.exchange(endPointUrl, httpMethod, httpEntity, parameterizedTypeReference);
	    return exchange.getBody();
	}
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		 restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		 if(restTemplate.getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
			throw new AdapterException("RestTemplate cannot be configure using its default SimpleClientHttpRequestFactory");
		}
		
	}
}
