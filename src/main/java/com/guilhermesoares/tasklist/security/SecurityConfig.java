package com.guilhermesoares.tasklist.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Value("${jwt.public.key}")
	private RSAPublicKey key;

	@Value("${jwt.private.key}")
	private RSAPrivateKey priv;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/login").permitAll().anyRequest().authenticated())
				.oauth2ResourceServer(conf -> conf.jwt(Customizer.withDefaults()));

		return httpSecurity.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(key).build();
	}

	@Bean
	JwtEncoder jwtEncoder() {
		var jwk = new RSAKey.Builder(key).privateKey(priv).build();
		var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
