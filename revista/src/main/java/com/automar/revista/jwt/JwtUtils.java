package com.automar.revista.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.automar.revista.exceptions.BaseException;
import com.automar.revista.security.UserDetailServiceCustom;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
//Metodos necesarios para generar, validar token, extraer informacion del token

@Service
public class JwtUtils {

	private String secret = "HRlELXqpSB"; 
	@Autowired
	private UserDetailServiceCustom userDetailsService; 
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
	 
	 public Claims extractClaims(String token) {
	        return Jwts
	                .parser()
	                .setSigningKey(secret)
	                .parseClaimsJws(token)
	                .getBody();
	    }

	 
	 private <T> T extractClaims(String token, Function<Claims,T> claimsResolver)
	{
	final Claims claims = extractAllClaims(token); 
	return claimsResolver.apply(claims);
	}
	/*
	public Claims extractAllClaims(String token)
	{
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
	}*/
	
	
	 private Claims extractAllClaims(String token){
	        Claims claims = null;

	        try {
	            claims = Jwts.parser()
	                    .setSigningKey(secret)
	                    .parseClaimsJws(token)
	                    .getBody();
	        }catch (ExpiredJwtException e){
	            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token expiration");
	        }catch (UnsupportedJwtException e){
	            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token's not supported");
	        }catch (MalformedJwtException e){
	            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Invalid format 3 part of token");
	        }catch (SignatureException e){
	            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Signature Alteraded");
	        }catch (Exception e){
	            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), e.getLocalizedMessage());
	        }

	        return claims;
	    }

	
	
 public Boolean validateToken(UserDetails userDetails, String token) {
	   
	   final String username = extractUsername(token); 
	   return (username.equals( userDetails.getUsername() ) && !isTokenExpiration(token)); 
	   
 }
 
 public boolean isValidToken(String token) {
     final String username = extractUsername(token);

     UserDetails userDetails = userDetailsService.loadUserByUsername(username);

     return !ObjectUtils.isEmpty(userDetails);
 }
 
}
