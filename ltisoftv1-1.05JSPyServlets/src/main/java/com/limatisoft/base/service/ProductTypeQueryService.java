package com.limatisoft.base.service;

import java.util.List;

import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.repository.ProductTypeRepository;

public class ProductTypeQueryService {
	private ProductTypeRepository productTypeRepository;

	public void setProductTypeRepository(ProductTypeRepository productTypeRepository) {
		this.productTypeRepository = productTypeRepository;
	}

	public List<ProductType> findAll() {
		return productTypeRepository.findAll();
	}
	
	public List<ProductType> getAll() {
	    return productTypeRepository.findAll();
	}

	public ProductType findByCode(String code) {
		return productTypeRepository.findByCode(code);
	}
}
