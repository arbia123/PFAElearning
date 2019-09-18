package com.pfa.elearning.message.response;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;

public class JwtResponse {
	
	
	private Long id;

	private String token;
    private String type = "Bearer";
    private String username;
private Collection <?extends GrantedAuthority> authorities;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}
  
    public String getToken() {
	return token;
}

public void setToken(String token) {
	this.token = token;
}

public String getUsername() {
	return username;
}

public void setUsername(String username) {
	this.username = username;
}

public Collection<? extends GrantedAuthority> getAuthorities() {
	return authorities;
}

public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
	this.authorities = authorities;
}



	public JwtResponse(Long id, String token, String username,Collection<? extends GrantedAuthority> authorities) {
	super();
	this.id = id;
	this.token = token;
	this.username = username;
	this.authorities = authorities;
}

	public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

   
	public void setTokenType(String tokenType) {
        this.type = tokenType;
    }
}