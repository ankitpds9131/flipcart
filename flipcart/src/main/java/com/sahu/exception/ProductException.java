package com.sahu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductException extends RuntimeException{

	private String message;
}
