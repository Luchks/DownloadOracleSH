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
		
		///
		productTypeRepository.delete(productType);
		productType.setId(null);
		productTypeRepository.save(productType);
		//
	}
	
	private void assertUniqueCode(ProductType productType) {
		ProductType productTypeFinded = null;
		if(productType.getId() != null) {
			productTypeFinded = productTypeRepository.findByCodeAndIdNot(productType.getCode(), productType.getId());
		}else {
			productTypeFinded = productTypeRepository.findByCode(productType.getCode());
		}
		if(productTypeFinded != null) {
			throw new CodeUniqueException("Ya existe el codigo");
		}
	}
	
}
