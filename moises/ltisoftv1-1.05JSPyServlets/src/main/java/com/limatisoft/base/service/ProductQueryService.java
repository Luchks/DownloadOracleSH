package com.limatisoft.base.service;

import java.util.List;

import com.limatisoft.base.model.Product;
import com.limatisoft.base.repository.ProductRepository;

public class ProductQueryService {
	private ProductRepository productRepository;
	 
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	public Product findByCode(String code){
		return productRepository.findByCode(code);
	}
}
