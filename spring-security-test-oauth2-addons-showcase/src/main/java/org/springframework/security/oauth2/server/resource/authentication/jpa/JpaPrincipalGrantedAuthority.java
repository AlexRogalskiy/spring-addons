/*
 * Copyright 2019 Jérôme Wacongne
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.oauth2.server.resource.authentication.jpa;

@Entity
public final class JpaPrincipalGrantedAuthority {
	private final String principal;
	private final String authority;
	
	public JpaPrincipalGrantedAuthority(String principal, String authority) {
		super();
		this.principal = principal;
		this.authority = authority;
	}

	private JpaPrincipalGrantedAuthority() {
		this.principal = null;
		this.authority = null;
	}

	public String getPrincipal() {
		return principal;
	}

	public String getAuthority() {
		return authority;
	}
}