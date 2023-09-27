package com.automar.revista.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.automar.revista.jwt.EntryPointJwt;
import com.automar.revista.jwt.JwtFilter;

/*Configuration nos permite registrar beans en spring IOC
  EnableWebSecurity nos permite dar la configuracion especifica de spring security**/

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class ConfigureSecurity {
	
	@Autowired
	private JwtFilter jwtFilter; 
	@Autowired
	private EntryPointJwt entryPointJwt; 
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder(4);
	}
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/login", "/user/signup", "/user/forgotPassword", "/error").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling.authenticationEntryPoint(entryPointJwt));
		
		httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); 
		
		return httpSecurity.build(); 
		
		
	}
	
	@Bean
	AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception
	{
	return authenticationConfiguration.getAuthenticationManager();	
	}
	

}
