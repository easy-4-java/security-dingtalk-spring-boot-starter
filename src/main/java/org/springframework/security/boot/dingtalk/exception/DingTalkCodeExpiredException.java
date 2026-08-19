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

	/**
	 * Constructs a new ding talk code expired exception instance.
	 *
	 * @param msg the msg
	 */
	public DingTalkCodeExpiredException(String msg) {
		super(AuthResponseCode.SC_AUTHZ_CODE_EXPIRED, msg);
	}
	
	/**
	 * Constructs a new ding talk code expired exception instance.
	 *
	 * @param msg the msg
	 * @param t the t
	 */
	public DingTalkCodeExpiredException(String msg, Throwable t) {
		super(AuthResponseCode.SC_AUTHZ_CODE_EXPIRED, msg, t);
	}

}
