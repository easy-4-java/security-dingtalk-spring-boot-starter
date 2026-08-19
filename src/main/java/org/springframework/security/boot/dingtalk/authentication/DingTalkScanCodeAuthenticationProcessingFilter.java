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
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.boot.biz.SpringSecurityBizMessageSource;
import org.springframework.security.boot.biz.exception.AuthResponseCode;
import org.springframework.security.boot.biz.exception.AuthenticationMethodNotSupportedException;
import org.springframework.security.boot.dingtalk.exception.DingTalkCodeNotFoundException;
import org.springframework.security.boot.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Scan QR code to log on to third-party websites: https://open.dingtalk.com/document/orgapp-server/scan-qr-code-to-log-on-to-third-party-websites
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
@Slf4j
/**
 * <p>DingTalkScanCodeAuthenticationProcessingFilter implementation.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class DingTalkScanCodeAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

	protected MessageSourceAccessor messages = SpringSecurityBizMessageSource.getAccessor();
	public static final String SPRING_SECURITY_FORM_CROPID_KEY = "cropId";
    public static final String SPRING_SECURITY_FORM_APP_KEY = "key";
	public static final String SPRING_SECURITY_FORM_TOKEN_KEY = "token";
    public static final String SPRING_SECURITY_FORM_TMPCODE_KEY = "loginTmpCode";

	private String cropIdParameter = SPRING_SECURITY_FORM_CROPID_KEY;
    private String keyParameter = SPRING_SECURITY_FORM_APP_KEY;
	private String tokenParameter = SPRING_SECURITY_FORM_TOKEN_KEY;
    private String codeParameter = SPRING_SECURITY_FORM_TMPCODE_KEY;
    private boolean postOnly = false;
    private ObjectMapper objectMapper = new ObjectMapper();

    public DingTalkScanCodeAuthenticationProcessingFilter(ObjectMapper objectMapper) {
    	super(PathPatternRequestMatcher.pathPattern("/login/dingtalk/scancode"));
		this.objectMapper = objectMapper;
	}

	public DingTalkScanCodeAuthenticationProcessingFilter(ObjectMapper objectMapper, RequestMatcher requestMatcher) {
		super(requestMatcher);
		this.objectMapper = objectMapper;
	}

    @Override
    /** Attempts to authenticate the incoming request.
     * @param request the request
     * @param response the response
     * @return the result
     */
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (isPostOnly() && !WebUtils.isPostRequest(request) ) {
			if (log.isDebugEnabled()) {
				log.debug("Authentication method not supported. Request method: " + request.getMethod());
			}
			throw new AuthenticationMethodNotSupportedException(messages.getMessage(AuthResponseCode.SC_AUTHC_METHOD_NOT_ALLOWED.getMsgKey(), new Object[] { request.getMethod() },
					"Authentication method not supported. Request method:" + request.getMethod()));
		}

        AbstractAuthenticationToken authRequest;

        // Post && JSON
		if(WebUtils.isObjectRequest(request)) {

			if (log.isDebugEnabled()) {
				log.debug("Post && JSON");
			}

			DingTalkScanCodeLoginRequest loginRequest = objectMapper.readValue(request.getReader(), DingTalkScanCodeLoginRequest.class);

			if ( !StringUtils.hasText(loginRequest.getKey())) {
				log.debug("No key (appId or appKey) found in request.");
				throw new DingTalkCodeNotFoundException("No key (appId or appKey) found in request.");
			}
			if (!StringUtils.hasText(loginRequest.getLoginTmpCode())) {
				log.debug("No loginTmpCode or Code found in request.");
				throw new DingTalkCodeNotFoundException("No loginTmpCode or Code found in request.");
			}

			authRequest = this.authenticationToken( loginRequest );

		} else {


			String corpId = obtainCropId(request);
			String appId = obtainKey(request);;
			String token = obtainToken(request);
			String loginTmpCode = obtainTmpCode(request);

			if ( !StringUtils.hasText(appId)) {
				log.debug("No appId found in request.");
				throw new DingTalkCodeNotFoundException("No appId found in request.");
			}
			if ( !StringUtils.hasText(loginTmpCode)) {
				log.debug("No loginTmpCode or Code found in request.");
				throw new DingTalkCodeNotFoundException("No loginTmpCode or Code found in request.");
			}

			DingTalkScanCodeLoginRequest loginRequest = new DingTalkScanCodeLoginRequest(corpId, appId, token, loginTmpCode);

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

    /** Extracts the tmp code parameter from the HTTP request.
     * @param request the request
     * @return the result
     */
    protected String obtainTmpCode(HttpServletRequest request) {
        return request.getParameter(codeParameter);
    }


    /**
	 * Provided so that subclasses may configure what is put into the authentication
	 * request's details property.
	 *
	 * @param request that an authentication request is being created for
	 * @param authRequest the authentication request object that should have its details
	 * set
	 */
	protected void setDetails(HttpServletRequest request,
			AbstractAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/** Creates an authentication token from the login request.
	 * @param loginRequest the loginRequest
	 * @return the result
	 */
	protected AbstractAuthenticationToken authenticationToken(DingTalkScanCodeLoginRequest loginRequest) {
		return new DingTalkScanCodeAuthenticationToken(loginRequest);
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

	/** Returns the code parameter.
	 * @return the result
	 */
	public String getCodeParameter() {
		return codeParameter;
	}

	/** Sets the code parameter.
	 * @param codeParameter the codeParameter
	 */
	public void setCodeParameter(String codeParameter) {
		this.codeParameter = codeParameter;
	}
	/** Returns whether the post only is enabled.
	 * @return the result
	 */
	public boolean isPostOnly() {
		return postOnly;
	}

	/** Sets the post only.
	 * @param postOnly the postOnly
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

}
