package com.coffeeandit.example.usecase.exception;

public class NotAllowedException extends RuntimeException {
	public NotAllowedException(final String message) {
		super(message);
	}
}
