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

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
/** Authentication token for Ding Talk Scan Code authentication.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */

@SuppressWarnings("serial")
public class DingTalkScanCodeAuthenticationToken extends AbstractAuthenticationToken {

	private Object principal;
	private Object credentials;
	/**
	 * third-partyplatformUnionID（third-partyuser uniqueID）
	 */
	protected String unionid;
	/**
	 * third-partyplatformOpenID（third-partyapplicationuser uniqueID）
	 */
	protected String openid;
	/**
	 * userinformation
	 */
	protected OapiSnsGetuserinfoBycodeResponse.UserInfo userInfo ;

	public DingTalkScanCodeAuthenticationToken(Object principal) {
		super(null);
		this.principal = principal;
		setAuthenticated(false);
	}

	public DingTalkScanCodeAuthenticationToken(Object principal, String credentials) {
		super(null);
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(false);
	}

	public DingTalkScanCodeAuthenticationToken(Object principal, Object credentials,
                                               Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true); // must use super, as we override
	}

	// ~ Methods
	// ========================================================================================================

	@Override
	/** Returns the credentials.
	 * @return the result
	 */
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	/** Returns the principal.
	 * @return the result
	 */
	public Object getPrincipal() {
		return this.principal;
	}
	
	/** Sets the principal.
	 * @param principal the principal
	 */
	public void setPrincipal(Object principal) {
		this.principal = principal;
	}

	@Override
	/** Sets the authenticated.
	 * @param isAuthenticated the isAuthenticated
	 */
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	/** Erases the sensitive credentials from this token.
	 */
	public void eraseCredentials() {
		super.eraseCredentials();
		credentials = null;
	}

	/** Returns the unionid.
	 * @return the result
	 */
	public String getUnionid() {
		return unionid;
	}

	/** Sets the unionid.
	 * @param unionid the unionid
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	/** Returns the openid.
	 * @return the result
	 */
	public String getOpenid() {
		return openid;
	}

	/** Sets the openid.
	 * @param openid the openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public OapiSnsGetuserinfoBycodeResponse.UserInfo getUserInfo() {
		return userInfo;
	}

	/** Sets the user info.
	 * @param userInfo the userInfo
	 */
	public void setUserInfo(OapiSnsGetuserinfoBycodeResponse.UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}