package com.ritesh.application.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ritesh.application.model.TokenDetails;
import com.ritesh.application.validator.TokenValidator;

public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenValidator tokenValidator;
	
	private RestOperations restTemplate = new RestTemplate();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = parseJwt(request);
		if (jwtToken == null) {
			response.sendError(403, "invalid token");
		}
		
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
		List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
		updatedAuthorities.add(authority);
		//updatedAuthorities.addAll((Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities());

		if (jwtToken != null && this.validateAndGetJwtDetails(jwtToken,response)) {
				final User user = new User("ritesh", "", true, true, true, true,
						updatedAuthorities);
				final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user,
						null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
				filterChain.doFilter(request, response);
			} else {
				response.sendError(403, "invalid token");
			}
			
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("token");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
	
	public boolean validateAndGetJwtDetails(String accessToken, HttpServletResponse response) throws AuthenticationException, IOException {
		HttpHeaders headers = new HttpHeaders();
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
		body.add("token", accessToken);
		headers.set("Authorization", "Basic cml0ZXNoLWNsaWVudDpyaXRlc2gtc2VjcmV0");
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);

		TokenDetails tokenDetails = new TokenDetails();
		ResponseEntity<String> model = null;
		try {
			model = restTemplate.exchange("http://localhost:8080/oauth/check_token",
					HttpMethod.POST, httpEntity, String.class);
			JSONObject jsonObject = new JSONObject(model.getBody());
		} catch (RestClientException e) {
			response.sendError(400, e.getMessage());
			e.printStackTrace();
		}
		
		return model==null ? false : true;
	}
	
	


}
