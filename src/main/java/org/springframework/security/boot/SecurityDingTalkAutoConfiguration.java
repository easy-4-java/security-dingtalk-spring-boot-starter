package org.springframework.security.boot;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.boot.dingtalk.authentication.DingTalkMatchedAuthenticationEntryPoint;
import org.springframework.security.boot.dingtalk.authentication.DingTalkMatchedAuthenticationFailureHandler;
/** Auto-configuration for Security Ding Talk.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */

@Configuration
@AutoConfigureBefore({ SecurityFilterAutoConfiguration.class })
@EnableConfigurationProperties({ SecurityBizProperties.class, SecurityDingTalkProperties.class})
public class SecurityDingTalkAutoConfiguration {
	
	@Bean
	/** Creates a dingtalk matched authentication entry point bean.
	 * @return the result
	 */
	public DingTalkMatchedAuthenticationEntryPoint dingtalkMatchedAuthenticationEntryPoint() {
		return new DingTalkMatchedAuthenticationEntryPoint();
	}
	
	@Bean
	public DingTalkMatchedAuthenticationFailureHandler dingtalkMatchedAuthenticationFailureHandler() {
		return new DingTalkMatchedAuthenticationFailureHandler();
	}

}



