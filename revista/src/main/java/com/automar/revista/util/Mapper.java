package com.automar.revista.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.automar.revista.dto.UserDTO;
import com.automar.revista.model.User;
@Component
public class Mapper {

	ModelMapper mapper = new ModelMapper(); 
	
	public User getUserFromDto(UserDTO user)
	{
		User usuario = new User(); 
		mapper.map(user, usuario); 
		return usuario; 
	}
}

