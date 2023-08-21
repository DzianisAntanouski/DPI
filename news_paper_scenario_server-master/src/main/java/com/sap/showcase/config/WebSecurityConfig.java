package com.sap.showcase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.XsuaaServiceConfigurationDefault;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String API_CUST_PATH = "/customers";
	public static final String API_MGZN_PATH = "/magazines";
	public static final String API_SUBT_PATH = "/subscriptionTypes";
	public static final String API_ADVT_PATH = "/advertisementTypes";
	public static final String API_BUPA_PATH = "/businesspartners";

	private static final String DISPLAY_SCOPE_LOCAL = "Display";
	private static final String UPDATE_SCOPE_LOCAL = "Update";
	public static final String REGEX_TENANT_INDEX = "(!t\\d+)?.";
	private static final String XSAPPNAME = "media-app";
	public static final String DISPLAY_SCOPE = XSAPPNAME + "." + DISPLAY_SCOPE_LOCAL;
	public static final String UPDATE_SCOPE = XSAPPNAME + "." + UPDATE_SCOPE_LOCAL;

	@Autowired
	XsuaaServiceConfiguration xsuaaServiceConfiguration;

	// configure Spring Security, demand authentication and specific scopes
	@Override
	public void configure(HttpSecurity http) throws Exception {

		// @formatter:off
		http.sessionManagement()
				// session is created by approuter
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// demand specific scopes depending on intended request
//				.authorizeRequests().antMatchers(GET, "/api/**").permitAll()
//				.antMatchers(POST, "/api/**").permitAll()
//				.antMatchers(PUT, "/api/**").permitAll()
//				.antMatchers(DELETE, "/api/**").permitAll()
//				.antMatchers(GET, "/health**").permitAll()
//				.anyRequest().denyAll().and().oauth2ResourceServer().jwt(); // deny
				.authorizeRequests()
				.anyRequest().permitAll()
				.and()
				.oauth2ResourceServer()
				.jwt()
				.jwtAuthenticationConverter(getJwtAuthenticationConverter());
																														// anything
																														// not
																														// configured
																														// above


	}


	/**
	 * Customizes how GrantedAuthority are derived from a Jwt
	 */
	Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
		TokenAuthenticationConverter converter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);
		converter.setLocalScopeAsAuthorities(true);
		return converter;
	}

//	@Bean
//	JwtDecoder jwtDecoder() {
//		return new XsuaaJwtDecoderBuilder(xsuaaServiceConfiguration).build();
//	}

	@Bean
	@ConditionalOnMissingBean(XsuaaServiceConfiguration.class)
	public XsuaaServiceConfiguration xsuaaServiceConfiguration() {
		return new XsuaaServiceConfigurationDefault();
	}



}
