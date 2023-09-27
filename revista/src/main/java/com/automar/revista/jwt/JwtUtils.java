package com.automar.revista.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//Metodos necesarios para generar, validar token, extraer informacion del token
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtils {

	private String secret = "HRlELXqpSB"; 
	
	public String extractUsername(String token)
	{
		return extractClaims(token,Claims::getSubject);
	}
	
	public Date extractExpiration(String token)
	{
		return extractClaims(token, Claims::getExpiration);
	}
	
	public Boolean isTokenExpiration(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(String username, String role)
	{
		Map<String,Object> claims = new HashMap<>();
		claims.put("role", role); 
		return createToken(claims,username); 
	}
	
	 String createToken(Map<String,Object> claims, String subject)
	{
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+100*60*60*10))
				.signWith(SignatureAlgorithm.HS384, secret).compact();	
		}
	
	
	//en el payload los claims
	public <T> T extractClaims(String token, Function<Claims,T> claimsResolver)
	{
	final Claims claims = extractAllClaims(token); 
	return claimsResolver.apply(claims);
	}
	
	public Claims extractAllClaims(String token)
	{
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
	}
	
 public Boolean validateToken(UserDetails userDetails, String token) {
	   
	   final String username = extractUsername(token); 
	   return (username.equals( userDetails.getUsername() ) && !isTokenExpiration(token)); 
	   
 }
 
}