package com.sahu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sahu.dto.ProductDto;
import com.sahu.response.ServiceResponse;
import com.sahu.service.ProductService;

@RestController
@RequestMapping("/v2")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/saveDetails")
	public String saveDetails(@RequestBody List<ProductDto> dtos) {
		return productService.saveProductDetails(dtos);
	}
	
	@GetMapping
	public ServiceResponse fetchAll(int pageNumber, int pageSize, String sortBy, Direction direction, @RequestParam(required = false) List<Integer> ids, String brand){
		return productService.fetchAll(pageNumber,pageSize,sortBy, direction,ids, brand);
	}
	
	@PutMapping("/update")
	public String updatePrice(Double price, Long productId, String color,String description) {
		return productService.updateProduct(productId,price, color,description);
	}
	
	@GetMapping("fetchAll")
	public Map<String, List<ProductDto>> fetchAllProduct() {
		return productService.fetchAllProducts();
	}
	
}
