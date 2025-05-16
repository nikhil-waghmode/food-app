package com.capgemini.food_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OwnerAlreadyHasRestaurantException extends RuntimeException {
    public OwnerAlreadyHasRestaurantException(String message) {
        super(message);
    }
}