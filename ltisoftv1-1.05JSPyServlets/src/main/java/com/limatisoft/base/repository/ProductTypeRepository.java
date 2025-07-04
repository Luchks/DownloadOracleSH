package com.limatisoft.base.repository;

import java.util.List;

import com.limatisoft.base.model.ProductType;

public interface ProductTypeRepository {

    // Obtener todos los tipos
    public List<ProductType> findAll();

    // Buscar por código
    public ProductType findByCode(String code);

    // Buscar por código excluyendo un ID específico (útil para validaciones al editar)
    public ProductType findByCodeAndIdNot(String code, Long id);

    public void save(ProductType tipo);

    public void update(ProductType tipo);

    public void delete(ProductType tipo);
    
   
}
