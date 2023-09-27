package com.automar.revista.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.automar.revista.dao.UserDAO;
import com.automar.revista.model.User;

/*Como es una implementacion de una interfaz donde se llame al userDetailService usara la unica implementacion que existe*/


@Service
public class UserDetailServiceCustom implements UserDetailsService{

	private User userUsed; 
	
	@Autowired
	UserDAO userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {       	
		 userUsed= userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Usuario no existente"));
		/*for each si un user tuviera varios roles*/
		List<GrantedAuthority> roles = new ArrayList<>();
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userUsed.getRol().getNombre()); 
	    roles.add(grantedAuthority);
	      return  (UserDetails) new org.springframework.security.core.userdetails.User(username, userUsed.getPassword() ,roles);
	   
	  
	}
	
	public User getUserDetail()
	{
		return userUsed; 
	}

}
