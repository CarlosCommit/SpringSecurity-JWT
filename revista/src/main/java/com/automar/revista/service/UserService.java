package com.automar.revista.service;

import com.automar.revista.dto.AuthDTO;
import com.automar.revista.dto.UserDTO;

public interface UserService {

	 public UserDTO saveUsuario(UserDTO usuario);
	 public UserDTO getUsuarioById(Long id); 
	 public String login(AuthDTO credentials); 
}
