package com.sahu.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sahu.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity>{
	
	
	List<ProductEntity> findByBrandIn(List<String> brands);
	
	default Specification<ProductEntity> idSpecification(List<Integer> ids){
		return (root,query,cb)->root.get("id").in(ids);
	}
	
	
	

}
