package org.springframework.security.boot.dingtalk.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Enterprise internal application free-login、Third-party enterprise application free-login、Application management backend free-login
 * @author [@Loong Wan](https://github.com/loong10k)
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DingTalkTmpCodeLoginRequest {

	/**
	 * 	enterprise corpid
	 */
	protected String corpId;
	/**
	 * 	application uniqueidentifierkey
	 */
	protected String key;
	/**
	 * temporarylogincredentialcode
	 */
	protected String code;
	/**
	 * 	request token，used forbindinguser
	 */
	protected String token;
	/**
	 * Access Token
	 */
	protected String accessToken;
    
	@JsonIgnoreProperties(ignoreUnknown = true)
    @JsonCreator
    public DingTalkTmpCodeLoginRequest(@JsonProperty("corpId") String corpId,
									   @JsonProperty("key") String key,
									   @JsonProperty("token") String token,
									   @JsonProperty("code") String code) {
        this.corpId = corpId;
		this.key = key;
		this.token = token;
		this.code = code;
    }

}
