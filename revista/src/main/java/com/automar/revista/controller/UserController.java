package com.automar.revista.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	
	@PostMapping("/guardar")
	public ResponseEntity<?> guardarUsuario(@Valid @RequestBody UserDTO user)
	{
		Map<String, Object> response = new HashMap<String, Object>(); 
		response.put("Message:", "Guardado"); 
	    response.put("Usuario", userService.saveUsuario(user));
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	@GetMapping("/obtener")
	public ResponseEntity<?> guardarUsuario()
	{
		System.out.println("entro ");
		Map<String, Object> response = new HashMap<>(); 
		response.put("Message:", "Usuario"); 
	//	response.put("Usuario", user);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
}
