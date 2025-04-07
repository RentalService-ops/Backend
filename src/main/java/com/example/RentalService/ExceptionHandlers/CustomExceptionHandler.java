package com.example.RentalService.ExceptionHandlers;

import java.io.IOException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.example.RentalService.Exceptions.ImageUnsupportedException;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
	
	@ExceptionHandler(value= {MaxUploadSizeExceededException.class})
	public ResponseEntity<?> handleMaxUploadSizeExceededException(HttpServletResponse response, MaxUploadSizeExceededException exception) throws IOException{
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(exception.getMessage());
	}
	
	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<?> handleJsonProcessingException(HttpServletResponse response,JsonProcessingException exception) throws IOException{
		log.error("hello "+exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(exception.getMessage());
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(HttpServletResponse response,DataIntegrityViolationException exception) throws IOException{
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body("The category you are trying to delete is in use. Please remove the equipment associated with this category first.");
	}
	
	@ExceptionHandler(ImageUnsupportedException.class)
	public ResponseEntity<?> handleImageUnsupportedException(ImageUnsupportedException exception){
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("Failed to save Image.");
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleEntityNotFoundException(IllegalArgumentException exception){
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}
}
