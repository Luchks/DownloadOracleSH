package com.limatisoft.base.repository;

import java.util.List;

import com.limatisoft.base.model.Product;

public interface ProductRepository   {
	public void save(Product product);
	
	public void update(Product product) ;
	
	public void delete(Product product);
	
	public Product findByCode(String code);
	
	public List<Product> findAll();
	
	public Product findByCodeAndIdNot(String code, Long id);
	
	
}
