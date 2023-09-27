package com.automar.revista.service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.automar.revista.dao.RolDAO;
import com.automar.revista.dao.UserDAO;
import com.automar.revista.dto.AuthDTO;
import com.automar.revista.dto.UserDTO;
import com.automar.revista.exceptions.RegisterNotFoundBd;
import com.automar.revista.exceptions.RoleNotFound;
import com.automar.revista.exceptions.UsernameDuplicated;
import com.automar.revista.jwt.JwtUtils;
import com.automar.revista.model.Role;
import com.automar.revista.model.User;
import com.automar.revista.security.UserDetailServiceCustom;
import com.automar.revista.util.Mapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userRepository;
	@Autowired
	Mapper mapper;
	@Autowired
	RolDAO roleRepository;
	@Autowired
	JwtUtils jwtUtils; 
	@Autowired
	UserDetailServiceCustom userDetailService;
	
	@Autowired
	AuthenticationManager authenticationManager; 
	
	private BCryptPasswordEncoder encrypt = new BCryptPasswordEncoder(4); 
	
	@Override
	public UserDTO saveUsuario(UserDTO usuario) {
		Optional<User> u = userRepository.findByUsername(usuario.getUsername());
		if (u.isPresent())
			throw new UsernameDuplicated("El username ya esta ocupado");
		Role rol = roleRepository.findByNombre(usuario.getRol())
				.orElseThrow(() -> new RoleNotFound("El rol especificado no existe"));
		User usuarioEntity = mapper.getUserFromDto(usuario);
		usuarioEntity.setRol(rol);
		usuarioEntity.setPassword(encrypt.encode(usuario.getPassword()));
		userRepository.save(usuarioEntity);
		return usuario;
	}

	@Override
	public String login(AuthDTO credentials) {
	
		String token = " ";
		Authentication auth = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
		if(auth.isAuthenticated())
	    token=jwtUtils.generateToken(userDetailService.getUserDetail().getUsername(),userDetailService.getUserDetail().getRol().getNombre());
		return token;
	}

	@Override
	public UserDTO getUsuarioById(Long id) {
		User usuario = userRepository.findById(id).orElseThrow(()-> new RegisterNotFoundBd("El recurso no existe en la base de datos"));
	    return mapper.getDtoFromUser(usuario); 
	}
	
	

}
