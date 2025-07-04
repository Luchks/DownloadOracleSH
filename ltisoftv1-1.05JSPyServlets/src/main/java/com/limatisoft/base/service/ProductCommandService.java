package com.limatisoft.base.service;

import com.limatisoft.base.model.Product;
import com.limatisoft.base.repository.ProductRepository;
import com.limatisoft.exceptions.CodeUniqueException;

public class ProductCommandService {
	private ProductRepository productRepository;
	 
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public void save(Product product){
		productRepository.save(product);
	}
	
	public void saveUniqueProduct(Product product){
		assertUniqueCode(product);
		productRepository.save(product);
	}
	
	public void update(Product product){
		productRepository.update(product);
	}
	
	public void updateUniqueProduct(Product product){
		assertUniqueCode(product);
		productRepository.update(product);
	}
	
	public void delete(Product product){
		productRepository.delete(product);
	}
	
	public void deleteAnSave(Product product){
		
		///
		productRepository.delete(product);
		product.setId(null);
		productRepository.save(product);
		//
	}
	
	private void assertUniqueCode(Product product) {
		Product productFinded = null;
		if(product.getId() != null) {
			productFinded = productRepository.findByCodeAndIdNot(product.getCode(), product.getId());
		}else {
			productFinded = productRepository.findByCode(product.getCode());
		}
		if(productFinded != null) {
			throw new CodeUniqueException("Ya existe el codigo");
		}
	}

	public void deleteUniqueProduct(String code) {
	    Product product = productRepository.findByCode(code);
	    if (product != null) {
	        productRepository.delete(product);
	    } else {
	        throw new IllegalArgumentException("Producto con código " + code + " no encontrado.");
	    }
	}

		
	}
	

