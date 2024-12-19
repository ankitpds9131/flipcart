package com.sahu.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataNotFoundException extends RuntimeException{

	public final String message;
	
}
