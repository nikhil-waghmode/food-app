package com.capgemini.food_app.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String TIMESTAMP = "timestamp";
	private static final String MESSAGE = "message";
	private static final String STATUS = "status";
	private static final String ERRORS = "errors";
	private static final String DETAILS = "details";

	private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put(TIMESTAMP, LocalDateTime.now());
		errorDetails.put(MESSAGE, message);
		errorDetails.put(STATUS, status.value());
		return new ResponseEntity<>(errorDetails, status);
	}

	@ExceptionHandler(OwnerAlreadyHasRestaurantException.class)
	public ResponseEntity<Object> handleOwnerAlreadyHasRestaurant(OwnerAlreadyHasRestaurantException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<Object> handleOrderNotFound(OrderNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(OrderItemNotFoundException.class)
	public ResponseEntity<Object> handleOrderItemNotFound(OrderItemNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(FoodItemNotFoundException.class)
	public ResponseEntity<Object> handleFoodItemNotFound(FoodItemNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(ReviewNotFoundException.class)
	public ResponseEntity<Object> handleReviewNotFound(ReviewNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(RestaurantNotFoundException.class)
	public ResponseEntity<Object> handleRestaurantNotFound(RestaurantNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put(TIMESTAMP, LocalDateTime.now());
		errorDetails.put(STATUS, status.value());

		Map<String, String> fieldErrors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

		errorDetails.put(ERRORS, fieldErrors);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllExceptions(Exception ex) {
		Map<String, Object> errorDetails = new HashMap<>();
		errorDetails.put(TIMESTAMP, LocalDateTime.now());
		errorDetails.put(MESSAGE, "Unexpected error occurred");
		errorDetails.put(DETAILS, ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
