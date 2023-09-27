package com.automar.revista.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class AuthDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@NotBlank
	private String username; 
	@NotBlank
	private String password;
	

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
