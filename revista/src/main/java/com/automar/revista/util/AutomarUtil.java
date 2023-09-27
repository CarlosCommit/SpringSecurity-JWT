package com.automar.revista.util;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AutomarUtil {
	public static ResponseEntity<Map<String,Object>> getResponseEntity(Map<String,Object> respuesta, HttpStatus httpStatus)
	{
		return new ResponseEntity<Map<String,Object>>(respuesta,httpStatus);
	}
}
