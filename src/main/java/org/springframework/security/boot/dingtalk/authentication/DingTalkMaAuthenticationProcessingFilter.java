/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.security.boot.dingtalk.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.boot.biz.authentication.PostOnlyAuthenticationProcessingFilter;
import org.springframework.security.boot.dingtalk.exception.DingTalkCodeNotFoundException;
import org.springframework.security.boot.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * https://open.dingtalk.com/document/orgapp-client/mini-program-free-login
 */
/**
 * <p>DingTalkMaAuthenticationProcessingFilter implementation.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class DingTalkMaAuthenticationProcessingFilter extends PostOnlyAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_CROPID_KEY = "cropId";
    public static final String SPRING_SECURITY_FORM_APP_KEY = "key";
	public static final String SPRING_SECURITY_FORM_TOKEN_KEY = "token";
    public static final String SPRING_SECURITY_FORM_CODE_KEY = "authCode";

	private String cropIdParameter = SPRING_SECURITY_FORM_CROPID_KEY;
    private String keyParameter = SPRING_SECURITY_FORM_APP_KEY;
	private String tokenParameter = SPRING_SECURITY_FORM_TOKEN_KEY;
    private String authCodeParameter = SPRING_SECURITY_FORM_CODE_KEY;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Constructs a new ding talk ma authentication processing filter instance.
     *
     * @param objectMapper the object mapper
     */
    public DingTalkMaAuthenticationProcessingFilter(ObjectMapper objectMapper) {
    	super(PathPatternRequestMatcher.pathPattern("/login/dingtalk/ma"));
		this.objectMapper = objectMapper;
	}

	/**
	 * Constructs a new ding talk ma authentication processing filter instance.
	 *
	 * @param objectMapper the object mapper
	 * @param requestMatcher the request matcher
	 */
	public DingTalkMaAuthenticationProcessingFilter(ObjectMapper objectMapper, RequestMatcher requestMatcher) {
		super(requestMatcher);
		this.objectMapper = objectMapper;
	}
	
    @Override
    /** Attempts to authenticate the incoming request.
     * @param request the request
     * @param response the response
     * @return the result
     */
    public Authentication doAttemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        
        AbstractAuthenticationToken authRequest;
        
        // Post && JSON
		if(WebUtils.isObjectRequest(request)) {
			
			if (logger.isDebugEnabled()) {
				logger.debug("Post && JSON");
			}
			
			DingTalkMaLoginRequest loginRequest = objectMapper.readValue(request.getReader(), DingTalkMaLoginRequest.class);
			
			if ( !StringUtils.hasText(loginRequest.getKey())) {
				logger.debug("No key (appId or appKey) found in request.");
				throw new DingTalkCodeNotFoundException("No key (appId or appKey) found in request.");
			}
			if ( !StringUtils.hasText(loginRequest.getAuthCode())) {
				logger.debug("No AuthCode found in request.");
				throw new DingTalkCodeNotFoundException("No AuthCode found in request.");
			}
			
			authRequest = this.authenticationToken( loginRequest );
			
		} else {
			
			String corpId = obtainCropId(request);
			String appId = obtainKey(request);
			String token = obtainToken(request);
			String authCode = obtainAuthCode(request);
	        
			if ( !StringUtils.hasText(appId)) {
				logger.debug("No appId found in request.");
				throw new DingTalkCodeNotFoundException("No appId found in request.");
			}
			if ( !StringUtils.hasText(authCode)) {
				logger.debug("No authCode found in request.");
				throw new DingTalkCodeNotFoundException("No authCode found in request.");
			}
	        
			DingTalkMaLoginRequest loginRequest = new DingTalkMaLoginRequest(corpId, appId, token, authCode);
	        
	        authRequest = this.authenticationToken( loginRequest);
	        
		}
        

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);

    }

	/** Extracts the crop id parameter from the HTTP request.
	 * @param request the request
	 * @return the result
	 */
	protected String obtainCropId(HttpServletRequest request) {
		return request.getParameter(cropIdParameter);
	}

    /** Extracts the key parameter from the HTTP request.
     * @param request the request
     * @return the result
     */
    protected String obtainKey(HttpServletRequest request) {
        return request.getParameter(keyParameter);
    }

	/** Extracts the token parameter from the HTTP request.
	 * @param request the request
	 * @return the result
	 */
	protected String obtainToken(HttpServletRequest request) {
		return request.getParameter(tokenParameter);
	}

    /** Extracts the auth code parameter from the HTTP request.
     * @param request the request
     * @return the result
     */
    protected String obtainAuthCode(HttpServletRequest request) {
        return request.getParameter(authCodeParameter);
    }

    /**
	 * Provided so that subclasses may configure what is put into the authentication
	 * request's details property.
	 *
	 * @param request that an authentication request is being created for
	 * @param authRequest the authentication request object that should have its details
	 * set
	 */
	@Override
	protected void setDetails(HttpServletRequest request,
							  AbstractAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}
	
	/** Creates an authentication token from the login request.
	 * @param loginRequest the loginRequest
	 * @return the result
	 */
	protected AbstractAuthenticationToken authenticationToken(DingTalkMaLoginRequest loginRequest) {
		return new DingTalkMaAuthenticationToken(loginRequest);
	}

	/** Returns the crop id parameter.
	 * @return the result
	 */
	public String getCropIdParameter() {
		return cropIdParameter;
	}

	/** Sets the crop id parameter.
	 * @param cropIdParameter the cropIdParameter
	 */
	public void setCropIdParameter(String cropIdParameter) {
		this.cropIdParameter = cropIdParameter;
	}

	/** Returns the key parameter.
	 * @return the result
	 */
	public String getKeyParameter() {
		return keyParameter;
	}

	/** Sets the key parameter.
	 * @param keyParameter the keyParameter
	 */
	public void setKeyParameter(String keyParameter) {
		this.keyParameter = keyParameter;
	}

	/** Sets the token parameter.
	 * @param tokenParameter the tokenParameter
	 */
	public void setTokenParameter(String tokenParameter) {
		this.tokenParameter = tokenParameter;
	}

	/** Returns the token parameter.
	 * @return the result
	 */
	public String getTokenParameter() {
		return tokenParameter;
	}

	/** Returns the auth code parameter.
	 * @return the result
	 */
	public String getAuthCodeParameter() {
		return authCodeParameter;
	}

	/** Sets the auth code parameter.
	 * @param authCodeParameter the authCodeParameter
	 */
	public void setAuthCodeParameter(String authCodeParameter) {
		this.authCodeParameter = authCodeParameter;
	}

}