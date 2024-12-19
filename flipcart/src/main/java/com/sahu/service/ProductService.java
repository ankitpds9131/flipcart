package com.sahu.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sahu.dto.ProductDto;
import com.sahu.entity.ProductEntity;
import com.sahu.exception.DataNotFoundException;
import com.sahu.exception.ProductException;
import com.sahu.mapper.ProductMapper;
import com.sahu.repository.ProductRepository;
import com.sahu.response.ServiceResponse;

import jakarta.persistence.PersistenceException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	@Autowired
	private ProductMapper mapper;

	@Transactional
	public String saveProductDetails(List<ProductDto> dtos) {
		try {
			// 1st condition && 2nd condition if both true then insert into loop
			// 1st condition -> if true will not go to second condition if false will check
			// 2nd condition -> sort circute
			// true -> false false -> true

			List<String> brands = dtos.stream().map(ProductDto::getBrand).collect(Collectors.toList());

			List<ProductEntity> availableBrands = repository.findByBrandIn(brands);

			if (!availableBrands.isEmpty()) {
				throw new ProductException("Brand details Already Exist");
			}

			List<ProductEntity> entities = mapper.convertToEntity(dtos);
			repository.saveAll(entities);
			return "Details Details successfully";
		} catch (PersistenceException pe) {
			throw new ProductException("Unable to save details");
		}

	}

	@Transactional
	public ServiceResponse fetchAll(int pageNumber, int pageSize, String sortBy, Direction direction, List<Integer> ids, String brand)throws DataNotFoundException, ProductException{
		try {
			

			Specification<ProductEntity> baseSpecification = (root,query,cb)->cb.conjunction();
			
			if(ids != null) {
				baseSpecification = baseSpecification.and((root,query,cb)->root.get("id").in(ids));
			}
			
			if(brand != null) {
				baseSpecification = baseSpecification.and((root,query,cb)-> cb.equal(root.get("brand"), brand));
			}

			
			Sort sort = Sort.by(direction, sortBy);
			
			PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize, sort);
			
			Page<ProductEntity> page = repository.findAll(baseSpecification,pageRequest);
			
			List<ProductEntity> entities = page.getContent();
			
			if(entities.isEmpty()) {
				throw new DataNotFoundException("Data is not present for the given criteria");
			}
			
			List<ProductDto> dtos = mapper.convertToDto(entities);
			return new ServiceResponse(pageSize, pageNumber,page.getTotalElements(), dtos);
		} catch (PersistenceException pe) {
			throw new ProductException("Unable to fetch details");
		}

	}
    
	@Transactional
	public String updateProduct(Long productId, Double price, String color, String description) {
		Optional<ProductEntity> optional = repository.findById(productId);
		
		if(optional.isEmpty()) {
			throw new DataNotFoundException("Data is not present for the givend id "+productId);
		}
		
		ProductEntity entity = optional.get();
		
		if(price != null) {
			entity.setPrice(price);
		}

		if(color != null) {
			entity.setColor(color);

		}
		if (description != null) {
			entity.setDescription(description);
		}
		repository.save(entity);
		return "Price Updated Successfully";
	}

	public Map<String, List<ProductDto>> fetchAllProducts() {
		List<ProductEntity> entities = repository.findAll();
		List<ProductDto> dtos = mapper.convertToDto(entities);
		Map<String, List<ProductDto>> productsMap = dtos.stream().collect(Collectors.groupingBy(ProductDto::getColor));
		return productsMap;
		
	}
	

}
