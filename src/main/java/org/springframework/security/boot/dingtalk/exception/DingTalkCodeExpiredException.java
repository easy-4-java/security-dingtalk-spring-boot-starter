package org.springframework.security.boot.dingtalk.exception;

import org.springframework.security.boot.biz.exception.AuthResponseCode;
import org.springframework.security.boot.biz.exception.AuthenticationExceptionAdapter;
/** Exception thrown when a Ding Talk Code Expired error occurs.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */

@SuppressWarnings("serial")
public class DingTalkCodeExpiredException extends AuthenticationExceptionAdapter {

	public DingTalkCodeExpiredException(String msg) {
		super(AuthResponseCode.SC_AUTHZ_CODE_EXPIRED, msg);
	}
	
	public DingTalkCodeExpiredException(String msg, Throwable t) {
		super(AuthResponseCode.SC_AUTHZ_CODE_EXPIRED, msg, t);
	}

}
