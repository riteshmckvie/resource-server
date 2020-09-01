package com.ritesh.application.validator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.ritesh.application.model.TokenDetails;

@Component
public class TokenValidator {

	private RestOperations restTemplate = new RestTemplate();;

//	public TokenDetails validateAndGetJwtDetails(String accessToken) {
//		TokenDetails tokenDetails = loadAuthentication(accessToken);
//		return tokenDetails == null ? null : tokenDetails;
//
//	}

	
}
