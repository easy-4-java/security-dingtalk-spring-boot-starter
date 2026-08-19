package org.springframework.security.boot.dingtalk.authentication;

import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse.UserInfo;
import com.dingtalk.spring.boot.DingTalkTemplate;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.boot.biz.userdetails.SecurityPrincipal;
import org.springframework.security.boot.biz.userdetails.UserDetailsServiceAdapter;
import org.springframework.security.boot.dingtalk.exception.DingTalkAuthenticationServiceException;
import org.springframework.security.boot.dingtalk.exception.DingTalkCodeNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * https://open.dingtalk.com/document/orgapp-server/scan-qr-code-to-log-on-to-third-party-websites
 */
/**
 * <p>DingTalkScanCodeAuthenticationProvider implementation.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class DingTalkScanCodeAuthenticationProvider implements AuthenticationProvider, InitializingBean {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();
	private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserDetailsServiceAdapter userDetailsService;
    private final DingTalkTemplate dingTalkTemplate;

    /**
     * Constructs a new ding talk scan code authentication provider instance.
     *
     * @param userDetailsService the user details service
     * @param dingTalkTemplate the ding talk template
     */
    public DingTalkScanCodeAuthenticationProvider(final UserDetailsServiceAdapter userDetailsService,
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
     * @param authentication {@link DingTalkScanCodeAuthenticationToken IdentityCodeAuthenticationToken} object
     * @return the authentication result{@link Authentication}object
     * @throws AuthenticationException if authentication fails
    /**
     * authenticate.
     *
     * @param authentication the authentication
     * @return the result
     * @throws AuthenticationException if an error occurs
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
    	Assert.notNull(authentication, "No authentication data provided");
    	
    	if (logger.isDebugEnabled()) {
			logger.debug("Processing authentication request : " + authentication);
		}
 
    	DingTalkScanCodeLoginRequest loginRequest = (DingTalkScanCodeLoginRequest) authentication.getPrincipal();

		if ( !StringUtils.hasText(loginRequest.getLoginTmpCode())) {
			logger.debug("No loginTmpCode found in request.");
			throw new DingTalkCodeNotFoundException("No loginTmpCode found in request.");
		}
		
		try {

			if(!dingTalkTemplate.hasAppKey(loginRequest.getKey())) {
				logger.debug("Invalid App Key {} .", loginRequest.getKey());
				throw new DingTalkCodeNotFoundException("Invalid App Key.");
			}
			
			String appKey = loginRequest.getKey();
			String appSecret = dingTalkTemplate.getAppSecret(loginRequest.getCorpId(), loginRequest.getKey());
			
			DingTalkScanCodeAuthenticationToken dingTalkToken = (DingTalkScanCodeAuthenticationToken) authentication;
			
			if (StringUtils.hasText(loginRequest.getLoginTmpCode())) {
				
				// 第三方应用钉钉扫码登录：通过临时授权码Code获取用户信息，临时授权码只能使用一次
				OapiSnsGetuserinfoBycodeResponse response = dingTalkTemplate.opsForSns().getUserinfoByTmpCode(loginRequest.getLoginTmpCode(), appKey, appSecret);
				/*{ 
				    "errcode": 0,
				    "errmsg": "ok",
				    "user_info": {
				        "nick": "张三",
				        "openid": "liSii8KCxxxxx",
				        "unionid": "7Huu46kk"
				    }
				}*/
				if(!response.isSuccess()) {
					logger.error(response.getBody());
					throw new DingTalkAuthenticationServiceException(response.getErrmsg());
				}

				UserInfo userInfo = response.getUserInfo();
				
				dingTalkToken.setUnionid(userInfo.getUnionid());
				dingTalkToken.setOpenid(userInfo.getOpenid());
				dingTalkToken.setUserInfo(userInfo);

			}

			UserDetails ud = getUserDetailsService().loadUserDetails(dingTalkToken);
	        
	        // User Status Check
	        getUserDetailsChecker().check(ud);
	        
	        DingTalkScanCodeAuthenticationToken authenticationToken = null;
	        if(SecurityPrincipal.class.isAssignableFrom(ud.getClass())) {
	        	authenticationToken = new DingTalkScanCodeAuthenticationToken(ud, ud.getPassword(), ud.getAuthorities());        	
	        } else {
	        	authenticationToken = new DingTalkScanCodeAuthenticationToken(ud.getUsername(), ud.getPassword(), ud.getAuthorities());
			}
	        authenticationToken.setDetails(authentication.getDetails());
	        
	        return authenticationToken;
		} catch (ApiException e) {
			throw new DingTalkAuthenticationServiceException(e.getErrMsg(), e);
		}
    }
    
    @Override
    /** Indicates whether this provider supports the given authentication class.
     * @param authentication the authentication
     * @return the result
     */
    public boolean supports(Class<?> authentication) {
        return (DingTalkScanCodeAuthenticationToken.class.isAssignableFrom(authentication));
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
