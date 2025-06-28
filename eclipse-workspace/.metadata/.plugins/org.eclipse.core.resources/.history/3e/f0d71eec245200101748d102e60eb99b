package com.limatisoft.base.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.repository.ProductTypeRepository;
import com.limatisoft.db.DatabaseConnectionManager;
import com.limatisoft.exceptions.CodeRequiredException;
import com.limatisoft.exceptions.DataException;


public class ProductTypeJdbcRepository implements ProductTypeRepository{
	public void save(ProductType productType) {
		if(productType.getCode() == null || productType.getCode().isEmpty()) {
			throw new CodeRequiredException("El codigo es requerido");
		}
		try (
			Connection connection =  DatabaseConnectionManager.getConnection(); 
			) {
			PreparedStatement ps = connection.prepareStatement(""" 
					INSERT INTO public.tb_product_type(
							code, name)
							VALUES (?, ?)
					""");
			ps.setString(1, productType.getCode());
			ps.setString(2, productType.getName());
			ps.executeUpdate();
		}catch(SQLException ex) {
			throw new DataException(ex.getMessage());
		}
		
	}
	
	public void update(ProductType productType) {
		if(productType.getCode() == null || productType.getCode().isEmpty()) {
			throw new CodeRequiredException("El codigo es requerido");
		}
		try (
				Connection connection = DatabaseConnectionManager.getConnection();
			) {
			PreparedStatement ps = connection.prepareStatement(""" 
					UPDATE public.tb_product_type
					SET  code = ?, name=?
					WHERE tb_product_type_id = ?
					""");
			ps.setString(1, productType.getCode());
			ps.setString(2, productType.getName());
			ps.setLong(4, productType.getId());
			ps.executeUpdate();
		}catch(SQLException ex) {
			throw new DataException(ex.getMessage());
		}
	}
	
	public void delete(ProductType productType) {
		try (
			Connection connection = DatabaseConnectionManager.getConnection();
			) {
			PreparedStatement ps = connection.prepareStatement(""" 
					DELETE FROM public.tb_product_type
					WHERE tb_product_type_id = ?
					""");
			ps.setLong(1, productType.getId());
			ps.executeUpdate();
		}catch(SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	public ProductType findByCode(String codeToFind) {
		if(codeToFind == null || codeToFind.isEmpty()) {
			throw new CodeRequiredException("El codigo es requerido");
		}
		try (
			Connection connection = 
				DatabaseConnectionManager.getConnection(); 
			) {
			 
			PreparedStatement ps = connection.prepareStatement(""" 
					SELECT tb_product_type_id, name, code
					FROM public.tb_product_type 
					WHERE lower(code) = ? ;
					""");
			ps.setString(1, codeToFind.toLowerCase());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ProductType productTypeo = new ProductType();
				productTypeo.setId(rs.getLong("tb_product_type_id"));
				productTypeo.setName(rs.getString("name"));
				productTypeo.setCode(rs.getString("code"));
				return productTypeo;
			}
			return null;
		}catch(SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	public List<ProductType> findAll() {
		ArrayList<ProductType> productTypeos = new ArrayList<ProductType>();
		try (Connection connection = 
				DatabaseConnectionManager.getConnection(); 
			) {
			 
			PreparedStatement ps = connection.prepareStatement(""" 
					SELECT tb_product_type_id, name, code
					FROM public.tb_product_type 
					""");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ProductType productTypeo = new ProductType();
				productTypeo.setId(rs.getLong("tb_product_type_id"));
				productTypeo.setName(rs.getString("name"));
				productTypeo.setCode(rs.getString("code"));
				productTypeos.add(productTypeo);
			}
			return productTypeos;
		}catch(SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
	
	public ProductType findByCodeAndIdNot(String codeToFind ,Long id){
		try (
			Connection connection = 
				DatabaseConnectionManager.getConnection(); 
			) {
			 
			PreparedStatement ps = connection.prepareStatement(""" 
					SELECT tb_product_type_id, name, code
					FROM public.tb_product_type 
					WHERE lower(code) = ? AND tb_product_type_id <> ? ;
					""");
			ps.setString(1, codeToFind.toLowerCase());
			ps.setLong(2, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ProductType productTypeo = new ProductType();
				productTypeo.setId(rs.getLong("tb_product_type_id"));
				productTypeo.setName(rs.getString("name"));
				productTypeo.setCode(rs.getString("code"));
				return productTypeo;
			}
			return null;
		}catch(SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
}
