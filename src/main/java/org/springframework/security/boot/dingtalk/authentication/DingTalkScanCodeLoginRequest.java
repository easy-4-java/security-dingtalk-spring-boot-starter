package org.springframework.security.boot.dingtalk.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * DingTalk scan-code login authorization for third-party systems
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DingTalkScanCodeLoginRequest {

	/**
	 * 	enterprise corpid
	 */
	protected String corpId;
	/**
	 * 	application uniqueidentifierkey
	 */
	protected String key;
	/**
	 * 	request token，used forbindinguser
	 */
	protected String token;
	/**
	 * temporarylogincredentialcode
	 */
	protected String loginTmpCode;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonCreator
	public DingTalkScanCodeLoginRequest(@JsonProperty("corpId") String corpId,
										@JsonProperty("key") String key,
										@JsonProperty("token") String token,
									    @JsonProperty("loginTmpCode") String loginTmpCode) {
		this.corpId = corpId;
		this.key = key;
		this.token = token;
		this.loginTmpCode = loginTmpCode;
	}

}
