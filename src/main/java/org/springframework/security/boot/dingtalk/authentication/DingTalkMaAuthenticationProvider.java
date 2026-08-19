package org.springframework.security.boot.dingtalk.authentication;

import com.dingtalk.spring.boot.DingTalkTemplate;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.biz.userdetails.UserDetailsServiceAdapter;
import org.springframework.security.boot.dingtalk.exception.DingTalkAuthenticationServiceException;
import org.springframework.security.boot.dingtalk.exception.DingTalkCodeNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
/** Authentication provider for Ding Talk Ma authentication.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */

@Slf4j
public class DingTalkMaAuthenticationProvider implements AuthenticationProvider, InitializingBean {

	private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
    private final UserDetailsServiceAdapter userDetailsService;
    private final DingTalkTemplate dingTalkTemplate;

    public DingTalkMaAuthenticationProvider(final UserDetailsServiceAdapter userDetailsService,
    		final DingTalkTemplate dingTalkTemplate) {
        this.userDetailsService = userDetailsService;
        this.dingTalkTemplate = dingTalkTemplate;
    }

	@Override
	/** Called after properties are set. Validates that required dependencies are available.
	 */
	public void afterPropertiesSet() throws Exception {

	}

    /**
     *
     * <p>Completes authentication matching the token，The returned object will be placed in the context via：SecurityContextHolder.getContext().setAuthentication(authResult); </p>
     * @author <a href="https://github.com/loong10k">Loong Wan</a>
     * @param authentication {@link DingTalkMaAuthenticationToken IdentityCodeAuthenticationToken} object
     * @return the authentication result{@link Authentication}object
     * @throws AuthenticationException if authentication fails
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    	Assert.notNull(authentication, "No authentication data provided");

    	if (log.isDebugEnabled()) {
			log.debug("Processing authentication request : " + authentication);
		}

    	DingTalkMaLoginRequest loginRequest = (DingTalkMaLoginRequest) authentication.getPrincipal();

    	if (!StringUtils.hasText(loginRequest.getAuthCode())) {
			log.debug("No authCode found in request.");
			throw new DingTalkCodeNotFoundException("No authCode found in request.");
		}

		if(!dingTalkTemplate.hasAppKey(loginRequest.getKey())) {
			log.debug("Invalid App Key {} .", loginRequest.getKey());
			throw new DingTalkCodeNotFoundException("Invalid App Key.");
		}

		DingTalkMaAuthenticationToken dingTalkToken = (DingTalkMaAuthenticationToken) authentication;
		try {
			if (StringUtils.hasText(loginRequest.getAuthCode())) {

				String appKey = loginRequest.getKey();
				String appSecret = dingTalkTemplate.getAppSecret(loginRequest.getCorpId(), loginRequest.getKey());
				// 获取access_token
				String accessToken = dingTalkTemplate.getAccessToken(appKey, appSecret);
				loginRequest.setAccessToken(accessToken);
			}
		} catch (ApiException e) {
			throw new DingTalkAuthenticationServiceException(e.getErrMsg(), e);
		}

		UserDetails ud = getUserDetailsService().loadUserDetails(dingTalkToken);

		// User Status Check
		getUserDetailsChecker().check(ud);

		DingTalkMaAuthenticationToken authenticationToken = null;
		if(SecurityPrincipal.class.isAssignableFrom(ud.getClass())) {
			authenticationToken = new DingTalkMaAuthenticationToken(ud, ud.getPassword(), ud.getAuthorities());
		} else {
			authenticationToken = new DingTalkMaAuthenticationToken(ud.getUsername(), ud.getPassword(), ud.getAuthorities());
		}
		authenticationToken.setDetails(authentication.getDetails());

		return authenticationToken;
    }

    @Override
    /** Indicates whether this provider supports the given authentication class.
     * @param authentication the authentication
     * @return the result
     */
    public boolean supports(Class<?> authentication) {
        return (DingTalkMaAuthenticationToken.class.isAssignableFrom(authentication));
    }

	/** Sets the user details checker.
	 * @param userDetailsChecker the userDetailsChecker
	 */
	public void setUserDetailsChecker(UserDetailsChecker userDetailsChecker) {
		this.userDetailsChecker = userDetailsChecker;
	}

	/** Returns the user details checker.
	 * @return the result
	 */
	public UserDetailsChecker getUserDetailsChecker() {
		return userDetailsChecker;
	}

	/** Returns the user details service.
	 * @return the result
	 */
	public UserDetailsServiceAdapter getUserDetailsService() {
		return userDetailsService;
	}

}
