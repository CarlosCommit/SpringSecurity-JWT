package com.automar.revista.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.aspectj.weaver.patterns.HasMemberTypePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automar.revista.dto.AuthDTO;
import com.automar.revista.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

	@Autowired
	UserService userService; 
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@Valid @RequestBody AuthDTO credentials)
	{
		Map<String, Object> response = new HashMap<String, Object>(); 
		response.put("token",userService.login(credentials)); 
		return new ResponseEntity<Object>(response, HttpStatus.OK); 

	}
	
	
}
