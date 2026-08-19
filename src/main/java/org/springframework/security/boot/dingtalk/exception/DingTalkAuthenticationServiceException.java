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
package org.springframework.security.boot.dingtalk.exception;

import org.springframework.security.boot.biz.exception.AuthResponseCode;
import org.springframework.security.boot.biz.exception.AuthenticationServiceExceptionAdapter;

/**
 * Exception thrown when a DingTalk server-side authentication error occurs
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class DingTalkAuthenticationServiceException extends AuthenticationServiceExceptionAdapter {

	/**
	 * Constructs a new ding talk authentication service exception instance.
	 *
	 * @param msg the msg
	 */
	public DingTalkAuthenticationServiceException( String msg) {
		super(AuthResponseCode.SC_AUTHZ_THIRD_PARTY_SERVICE, msg);
	}
	
	/**
	 * Constructs a new ding talk authentication service exception instance.
	 *
	 * @param msg the msg
	 * @param t the t
	 */
	public DingTalkAuthenticationServiceException( String msg, Throwable t) {
		super(AuthResponseCode.SC_AUTHZ_THIRD_PARTY_SERVICE,msg, t);
	}
	
}
