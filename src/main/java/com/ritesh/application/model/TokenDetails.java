package com.ritesh.application.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public class TokenDetails {
	
	private long exp;
	private String username;
	private Collection<? extends GrantedAuthority> authorities;
	private String jti;
	private String clientId;
	private Collection<String> secret;
	public long getExp() {
		return exp;
	}
	public void setExp(long exp) {
		this.exp = exp;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@JsonDeserialize(using = CustomAuthorityDeserializer.class)
    //@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public String getJti() {
		return jti;
	}
	public void setJti(String jti) {
		this.jti = jti;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Collection<String> getSecret() {
		return secret;
	}
	public void setSecret(Collection<String> secret) {
		this.secret = secret;
	}
	


}
