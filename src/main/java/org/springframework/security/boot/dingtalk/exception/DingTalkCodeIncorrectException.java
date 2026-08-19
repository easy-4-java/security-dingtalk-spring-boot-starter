package org.springframework.security.boot.dingtalk.exception;

import org.springframework.security.boot.biz.exception.AuthResponseCode;
import org.springframework.security.boot.biz.exception.AuthenticationExceptionAdapter;
/** Exception thrown when a Ding Talk Code Incorrect error occurs.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */

@SuppressWarnings("serial")
public class DingTalkCodeIncorrectException extends AuthenticationExceptionAdapter {

	/**
	 * Constructs a new ding talk code incorrect exception instance.
	 *
	 * @param msg the msg
	 */
	public DingTalkCodeIncorrectException(String msg) {
		super(AuthResponseCode.SC_AUTHZ_CODE_INCORRECT, msg);
	}
	
	/**
	 * Constructs a new ding talk code incorrect exception instance.
	 *
	 * @param msg the msg
	 * @param t the t
	 */
	public DingTalkCodeIncorrectException(String msg, Throwable t) {
		super(AuthResponseCode.SC_AUTHZ_CODE_INCORRECT, msg, t);
	}
	
}
