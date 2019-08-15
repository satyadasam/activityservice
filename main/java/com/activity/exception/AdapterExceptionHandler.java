package com.  .activity.exception;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

public class AdapterExceptionHandler implements ResponseErrorHandler {

	public boolean hasError(ClientHttpResponse response) throws IOException {
		return !response.getStatusCode().is2xxSuccessful();
	}

	public void handleError(ClientHttpResponse response) throws IOException {
		String body = getBodyAsString(response);
		throw new AdapterException(response.getStatusCode(), body, extractMessage(body));
	}

	private String getBodyAsString(ClientHttpResponse response) throws IOException {
		InputStream stream = response.getBody();
		if (stream == null) {
			return "Error getting response from service call.";
		}
		return StreamUtils.copyToString(stream, Charset.defaultCharset());
	}

	private String extractMessage(String body) {
		return body;
	}
}
