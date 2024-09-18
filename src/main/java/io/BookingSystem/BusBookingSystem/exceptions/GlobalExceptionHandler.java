package io.BookingSystem.BusBookingSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	// this will handle the input validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		// Return a response entity with the errors and a BAD_REQUEST status
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AuthorizationDeniedException.class)
	public ResponseEntity<ErrorResponse> handleUnknownExceptions(AuthorizationDeniedException e) {
		ErrorResponse errorResponse = new ErrorResponse("Access Denied");
		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(value = { BookingException.class })
	public ResponseEntity<ErrorResponse> handleCustomExceptions(BookingException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
		return new ResponseEntity<>(errorResponse, e.getHttpStatus());
	}
}
