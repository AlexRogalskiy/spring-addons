/*
 * Copyright 2019 Jérôme Wacongne
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.c4_soft.springaddons.security.oauth2.test.mockmvc.keycloak;

import java.util.Optional;

import org.keycloak.adapters.springboot.KeycloakAutoConfiguration;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import com.c4_soft.springaddons.security.oauth2.test.mockmvc.MockMvcSupport;
import com.c4_soft.springaddons.security.oauth2.test.mockmvc.ServletUnitTestingSupport;

/**
 * @author Jérôme Wacongne &lt;ch4mp&#64;c4-soft.com&gt;
 */
@Import(ServletKeycloakAuthUnitTestingSupport.UnitTestConfig.class)
public class ServletKeycloakAuthUnitTestingSupport extends ServletUnitTestingSupport {

	public KeycloakAuthRequestPostProcessor authentication() {
		return beanFactory.getBean(KeycloakAuthRequestPostProcessor.class);
	}

	@TestConfiguration(proxyBeanMethods = false)
	@Import({ MockMvcSupport.class, KeycloakAutoConfiguration.class })
	public static class UnitTestConfig {

		@Bean
		@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
		public KeycloakAuthRequestPostProcessor keycloakAuthRequestPostProcessor(Optional<GrantedAuthoritiesMapper> authoritiesMapper) {
			return new KeycloakAuthRequestPostProcessor(authoritiesMapper);
		}
	}

}
