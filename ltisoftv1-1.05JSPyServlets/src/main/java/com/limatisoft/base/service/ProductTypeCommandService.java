package com.limatisoft.base.service;

import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.repository.ProductTypeRepository;
import com.limatisoft.exceptions.CodeUniqueException;

public class ProductTypeCommandService {
	private ProductTypeRepository productTypeRepository;

	public void setProductTypeRepository(ProductTypeRepository productTypeRepository) {
		this.productTypeRepository = productTypeRepository;
	}

	public void save(ProductType productType){
		productTypeRepository.save(productType);
	}
	
	public void saveUniqueProductType(ProductType productType){
		assertUniqueCode(productType);
		productTypeRepository.save(productType);
	}
	
	public void update(ProductType productType){
		productTypeRepository.update(productType);
	}
	
	public void updateUniqueProductType(ProductType productType){
		assertUniqueCode(productType);
		productTypeRepository.update(productType);
	}
	
	public void delete(ProductType productType){
		productTypeRepository.delete(productType);
	}
	
	public void deleteAnSave(ProductType productType){
		productTypeRepository.delete(productType);
		productType.setId(null);
		productTypeRepository.save(productType);
	}
	
	private void assertUniqueCode(ProductType productType) {
		ProductType found = null;
		if(productType.getId() != null) {
			found = productTypeRepository.findByCodeAndIdNot(productType.getCode(), productType.getId());
		} else {
			found = productTypeRepository.findByCode(productType.getCode());
		}
		if(found != null) {
			throw new CodeUniqueException("Ya existe el código");
		}
	}

	public void deleteByCode(String code) {
	    ProductType productType = productTypeRepository.findByCode(code);
	    if (productType != null) {
	        productTypeRepository.delete(productType);
	    } else {
	        throw new IllegalArgumentException("Tipo de producto con código " + code + " no encontrado.");
	    }
	}

}
