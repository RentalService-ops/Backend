package com.example.RentalService.ExceptionHandlers;

import java.io.IOException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.example.RentalService.Exceptions.ImageUnsupportedException;
import com.example.RentalService.Exceptions.UserNotFoundException;
import com.example.RentalService.Exceptions.WrongUserEmailException;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * Handles exceptions when the uploaded file exceeds the maximum allowed size.
	 * @param response HttpServletResponse
	 * @param exception MaxUploadSizeExceededException
	 * @return ResponseEntity with INTERNAL_SERVER_ERROR status and error message
	 */
	@ExceptionHandler(value = { MaxUploadSizeExceededException.class })
	public ResponseEntity<?> handleMaxUploadSizeExceededException(HttpServletResponse response,
			MaxUploadSizeExceededException exception) throws IOException {
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(exception.getMessage());
	}

	/**
	 * Handles exceptions related to JSON processing issues (serialization/deserialization).
	 * @param response HttpServletResponse
	 * @param exception JsonProcessingException
	 * @return ResponseEntity with BAD_REQUEST status and error message
	 */
	@ExceptionHandler(JsonProcessingException.class)
	public ResponseEntity<?> handleJsonProcessingException(HttpServletResponse response,
			JsonProcessingException exception) throws IOException {
		log.error("hello " + exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(exception.getMessage());
	}

	/**
	 * Handles database integrity violations such as constraint failures (e.g., duplicate key).
	 * @param response HttpServletResponse
	 * @param exception DataIntegrityViolationException
	 * @return ResponseEntity with BAD_REQUEST status and error message
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(HttpServletResponse response,
			DataIntegrityViolationException exception) throws IOException {
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(exception.getMessage());
	}

	/**
	 * Handles custom exception when an unsupported image format is uploaded.
	 * @param exception ImageUnsupportedException
	 * @return ResponseEntity with INTERNAL_SERVER_ERROR status and static message
	 */
	@ExceptionHandler(ImageUnsupportedException.class)
	public ResponseEntity<?> handleImageUnsupportedException(ImageUnsupportedException exception) {
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body("Failed to save Image.");
	}

	/**
	 * Handles illegal arguments passed to a method, typically indicating invalid input.
	 * @param exception IllegalArgumentException
	 * @return ResponseEntity with BAD_REQUEST status and error message
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<?> handleEntityNotFoundException(IllegalArgumentException exception) {
		log.error(exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
	}

	/**
	 * Handles custom exception when a user is not found in the system.
	 * @param exception UserNotFoundException
	 * @return ResponseEntity with UNAUTHORIZED status and error message
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception) {
		log.error(exception.getMessage());
		return ResponseEntity.status(401).body(exception.getMessage());
	}

	/**
	 * Handles custom exception when a user email is invalid or does not match records.
	 * @param exception WrongUserEmailException
	 * @return ResponseEntity with UNAUTHORIZED status and error message
	 */
	@ExceptionHandler(WrongUserEmailException.class)
	public ResponseEntity<?> handleWrongUserEmailException(WrongUserEmailException exception) {
		log.error(exception.getMessage());
		return ResponseEntity.status(401).body(exception.getMessage());
	}
}
