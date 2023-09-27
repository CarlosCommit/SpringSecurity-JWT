package com.automar.revista.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.automar.revista.dto.UserDTO;
import com.automar.revista.service.UserService;

import jakarta.validation.Valid;



@RestController
@RequestMapping(path="/user")
public class UserController {

	
	@Autowired
	private UserService userService; 

	
	@PostMapping("/signup")
	public ResponseEntity<?> guardarUsuario(@Valid @RequestBody UserDTO user)
	{
		Map<String, Object> response = new HashMap<String, Object>(); 
		response.put("Message:", "Guardado"); 
	    response.put("Usuario", userService.saveUsuario(user));
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	
	@PreAuthorize("hasAuthority('user')")
	@GetMapping("get/{id}")
	public ResponseEntity<?> obtener(@PathVariable(name = "id") Long id)
	{
		
		Map<String, Object> response = new HashMap<String, Object>(); 
		response.put("Message:", "Encontrado"); 
	    response.put("Usuario", userService.getUsuarioById(id));
	    //SecurityContextHolder.clearContext();
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		
	  	
	}
	
}
