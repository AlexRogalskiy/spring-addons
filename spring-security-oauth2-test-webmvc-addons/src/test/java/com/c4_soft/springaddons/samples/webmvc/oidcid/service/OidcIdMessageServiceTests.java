/*
 * Copyright 2019 Jérôme Wacongne.
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
package com.c4_soft.springaddons.samples.webmvc.oidcid.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import com.c4_soft.springaddons.security.oauth2.oidc.OidcAuthentication;
import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockOidcAuth;
import com.c4_soft.springaddons.security.oauth2.test.mockmvc.JwtTestConf;

/**
 * @author Jérôme Wacongne &lt;ch4mp&#64;c4-soft.com&gt;
 */
@RunWith(SpringRunner.class)
@Import(OidcIdMessageServiceTests.TestConfig.class)
public class OidcIdMessageServiceTests {

	@Autowired
	private MessageService messageService;

	@Test()
	public void greetWitoutAuthentication() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		assertThat(auth).isNull();
	}

	@Test
	@WithMockOidcAuth(authorities = "ROLE_USER", claims = @OpenIdClaims(preferredUsername = "ch4mpy"))
	public void greetWithMockAuthentication() {
		final OidcAuthentication<?> auth = (OidcAuthentication<?>) SecurityContextHolder.getContext().getAuthentication();
		assertThat(messageService.greet(auth)).isEqualTo("Hello ch4mpy! You are granted with [ROLE_USER].");
	}

	@Test(expected = AccessDeniedException.class)
	@WithMockOidcAuth()
	public void secretWithoutAuthorizedPersonnelGrant() {
		assertThat(messageService.getSecret()).isEqualTo("Secret message");
	}

	@Test
	@WithMockOidcAuth(authorities = "ROLE_AUTHORIZED_PERSONNEL")
	public void secretWithScopeAuthorizedPersonnelAuthority() {
		assertThat(messageService.getSecret()).isEqualTo("Secret message");
	}

	@TestConfiguration(proxyBeanMethods = false)
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@Import({ JwtTestConf.class, OidcIdMessageService.class })
	public static class TestConfig {
	}
}
