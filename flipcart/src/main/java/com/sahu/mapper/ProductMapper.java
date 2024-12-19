package com.sahu.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.sahu.dto.ProductDto;
import com.sahu.entity.ProductEntity;

@Component
public class ProductMapper {
	
	public ProductEntity convertToEntity(ProductDto dto) {
		ProductEntity entity = new ProductEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}
	
	public ProductDto convertToDto(ProductEntity entity) {
		ProductDto dto = new ProductDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
	
	public List<ProductEntity> convertToEntity (List<ProductDto> dtos){
	return dtos.stream().map(this::convertToEntity).collect(Collectors.toList());
	}
	
	public List<ProductDto> convertToDto (List<ProductEntity> entities){
		return entities.stream().map(this::convertToDto).collect(Collectors.toList());
	}

}
