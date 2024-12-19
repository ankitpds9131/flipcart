package com.sahu.response;

import java.util.List;

import com.sahu.dto.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceResponse {

	
	private int pageSize;
	private int pageNumber;
	private long totalElements;
	private List<ProductDto> dtos;
}
