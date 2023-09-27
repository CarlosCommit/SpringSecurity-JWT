package com.automar.revista.service;


import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automar.revista.dao.RolDAO;
import com.automar.revista.dao.UserDAO;
import com.automar.revista.dto.UserDTO;
import com.automar.revista.exceptions.RoleNotFound;
import com.automar.revista.exceptions.UsernameDuplicated;
import com.automar.revista.model.Role;
import com.automar.revista.model.User;
import com.automar.revista.util.Mapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDAO userRepository;
	@Autowired
	Mapper mapper;
   
	@Autowired
	RolDAO roleRepository;

	@Override
	public UserDTO saveUsuario(UserDTO usuario) {
		Optional<User> u = userRepository.findByUsername(usuario.getUsername());
		if (u.isPresent())
			throw new UsernameDuplicated("El username ya esta ocupado");
		Role rol = roleRepository.findByNombre(usuario.getRol())
				.orElseThrow(() -> new RoleNotFound("El rol especificado no existe"));
		User usuarioEntity = mapper.getUserFromDto(usuario);
		usuarioEntity.setRol(rol);
		userRepository.save(usuarioEntity);
		return usuario;
	}

}
