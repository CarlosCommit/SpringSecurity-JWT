package com.automar.revista.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

	Claims claims = null; 
	
	private String username = null; 
	
	@Autowired
	private JwtUtils jwtUtil; 
	@Autowired 
    private UserDetailsService customerDetailsService; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (request.getRequestURI().equals("/user/signup") || request.getRequestURI().equals("/auth/login")) {
			filterChain.doFilter(request, response);
        }else
        {
			String authorizationHeader = request.getHeader("Authorization"); 
			String token = null; 
	
			if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer "))
			{
				token = authorizationHeader.substring(7); 
				username = jwtUtil.extractUsername(token); 
				claims = jwtUtil.extractAllClaims(token); 
			}
			
		
			if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null)
			{
				UserDetails userDetails = customerDetailsService.loadUserByUsername(username);
				
				if(jwtUtil.validateToken(userDetails, token))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					new WebAuthenticationDetailsSource().buildDetails(request); 
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); 
				}
			}
			filterChain.doFilter(request, response);
        }
	
	
	}

	public Boolean isAdmin() {
		return "admin".equalsIgnoreCase((String) claims.get("role")); 
	}
	public Boolean isUser() {
		return "user".equalsIgnoreCase((String) claims.get("role")); 
	}
	
	public String getCurrentUser()
	{
		return username; 
	}
}