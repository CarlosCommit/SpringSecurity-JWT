package com.automar.revista.controller;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import com.automar.revista.dto.ErrorDTO;
import com.automar.revista.exceptions.RegisterNotFoundBd;
import com.automar.revista.exceptions.RoleNotFound;
import com.automar.revista.exceptions.UsernameDuplicated;





@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = UsernameDuplicated.class)
	public ResponseEntity<ErrorDTO> handleUsernameDuplicatedException(UsernameDuplicated ex)
	{
		ErrorDTO error = new ErrorDTO(); 
		error.setMensaje(ex.getMessage());
		return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<ErrorDTO> runtimeExceptionHandler(RuntimeException exception)
	{
		ErrorDTO error = new ErrorDTO(); 
		error.setMensaje(exception.getMessage());
	
		return new ResponseEntity<ErrorDTO>(error,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = RoleNotFound.class)
	public ResponseEntity<ErrorDTO> handleRoleNotFoundException(RoleNotFound ex)
	{
		ErrorDTO error = new ErrorDTO(); 
		error.setMensaje(ex.getMessage());
		return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND); 
	}
	
	
	@ExceptionHandler(value = RegisterNotFoundBd.class)
	public ResponseEntity<ErrorDTO> handleRegisterNotFoundBd(RegisterNotFoundBd ex)
	{
		ErrorDTO error = new ErrorDTO(); 
		error.setMensaje(ex.getMessage());
		return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND); 
	}
	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub
		return new ResponseEntity<Object>("no mandaste nada al post  bro", HttpStatus.BAD_GATEWAY); 
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	Map<String,Object> response = new HashMap<String,Object>();
	
	ex.getBindingResult().getFieldErrors().forEach(error -> response.put(error.getField(), error.getDefaultMessage()));
	
	return new ResponseEntity<Object>(response,HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(value = AccessDeniedException.class)
	public ResponseEntity<ErrorDTO> handleRegisterDenied(AccessDeniedException ex)
	{
		ErrorDTO error = new ErrorDTO(); 
		error.setMensaje(ex.getMessage());
		return new ResponseEntity<ErrorDTO>(error,HttpStatus.NOT_FOUND); 
	}


   
	
}
