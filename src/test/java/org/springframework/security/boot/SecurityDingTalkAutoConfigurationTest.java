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
package org.springframework.security.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {{ @link SecurityDingTalkAutoConfiguration }}.
 *
 * <p>Verifies the auto-configuration activates under the expected conditions
 * and exposes its declared beans.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("SecurityDingTalkAutoConfiguration Tests")
class SecurityDingTalkAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner();

    @Test
    @DisplayName("Auto-configuration class can be instantiated")
    void testInstantiation() {
        SecurityDingTalkAutoConfiguration configuration = new SecurityDingTalkAutoConfiguration();
        assertThat(configuration).isNotNull();
    }

    @Test
    @DisplayName("Auto-configuration loads when 'spring.boot.enabled=true'")
    void testLoadsWhenEnabledPropertySet() {
        runner.withUserConfiguration(SecurityDingTalkAutoConfiguration.class)
                .withPropertyValues("spring.boot.enabled=true")
                .run(context -> assertThat(context).hasSingleBean(SecurityDingTalkAutoConfiguration.class));
    }

    @Test
    @DisplayName("Auto-configuration loads as @Configuration class")
    void testNotLoadedWhenPropertyAbsent() {
        runner.withUserConfiguration(SecurityDingTalkAutoConfiguration.class)
                .run(context -> assertThat(context).hasSingleBean(SecurityDingTalkAutoConfiguration.class));
    }
}
