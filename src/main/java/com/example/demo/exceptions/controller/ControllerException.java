package com.example.demo.exceptions.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.exceptions.CPFException;
import com.example.demo.exceptions.IdNotFoundException;
import com.example.demo.exceptions.NullPointerExceptionClient;
import com.example.demo.exceptions.NullPointerExceptionPedido;
import com.example.demo.exceptions.QuantindadeInsuficienteException;


@ControllerAdvice
public class ControllerException {
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<StandardError> IdNotFoundException (IdNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<StandardError> HttpClientErrorException (HttpClientErrorException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), "CEP Inválido", System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrity (DataIntegrityViolationException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<StandardError> dataIntegrity (ConstraintViolationException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	@ExceptionHandler(CPFException.class)
	public ResponseEntity<StandardError> dataIntegrity (CPFException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(QuantindadeInsuficienteException.class)
	public ResponseEntity<StandardError> dataIntegrity (QuantindadeInsuficienteException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(NullPointerExceptionClient.class)
	public ResponseEntity<StandardError> dataIntegrity (NullPointerExceptionClient e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	
	@ExceptionHandler(NullPointerExceptionPedido.class)
	public ResponseEntity<StandardError> dataIntegrity (NullPointerExceptionPedido e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
