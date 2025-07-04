package com.limatisoft.base.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.limatisoft.base.model.Product;
import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.repository.ProductRepository;
import com.limatisoft.db.DatabaseConnectionManager;
import com.limatisoft.exceptions.CodeRequiredException;
import com.limatisoft.exceptions.DataException;

public class ProductJdbcRepository implements ProductRepository {

	public void save(Product product) {
		if (product.getCode() == null || product.getCode().isEmpty()) {
			throw new CodeRequiredException("El código es requerido");
		}
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				INSERT INTO public.tb_product(
					code, name, sales_price, tb_product_type_id)
				VALUES (?, ?, ?, ?)
			""");
			ps.setString(1, product.getCode());
			ps.setString(2, product.getName());
			ps.setBigDecimal(3, product.getSalesPrice());
			if (product.getProductType() != null) {
				ps.setLong(4, product.getProductType().getId());
			} else {
				ps.setObject(4, null);
			}
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new DataException(ex.getMessage());
		}
	}

	public void update(Product product) {
		if (product.getCode() == null || product.getCode().isEmpty()) {
			throw new CodeRequiredException("El código es requerido");
		}
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				UPDATE public.tb_product
				SET code = ?, name = ?, sales_price = ?, tb_product_type_id = ?
				WHERE tb_product_id = ?
			""");
			ps.setString(1, product.getCode());
			ps.setString(2, product.getName());
			ps.setBigDecimal(3, product.getSalesPrice());
			if (product.getProductType() != null) {
				ps.setLong(4, product.getProductType().getId());
			} else {
				ps.setObject(4, null);
			}
			ps.setLong(5, product.getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new DataException(ex.getMessage());
		}
	}

	public void delete(Product product) {
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				DELETE FROM public.tb_product
				WHERE tb_product_id = ?
			""");
			ps.setLong(1, product.getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public Product findByCode(String codeToFind) {
		if (codeToFind == null || codeToFind.isEmpty()) {
			throw new CodeRequiredException("El código es requerido");
		}
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				SELECT p.tb_product_id, p.name, p.sales_price, p.code,
				       pt.tb_product_type_id, pt.code as pt_code, pt.name as pt_name
				FROM public.tb_product p
				LEFT JOIN tb_product_type pt ON p.tb_product_type_id = pt.tb_product_type_id
				WHERE lower(p.code) = ?
			""");
			ps.setString(1, codeToFind.toLowerCase());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Product producto = new Product();
				producto.setId(rs.getLong("tb_product_id"));
				producto.setName(rs.getString("name"));
				producto.setCode(rs.getString("code"));
				producto.setSalesPrice(rs.getBigDecimal("sales_price"));

				ProductType pt = new ProductType();
				pt.setId(rs.getLong("tb_product_type_id"));
				pt.setCode(rs.getString("pt_code"));
				pt.setName(rs.getString("pt_name"));
				producto.setProductType(pt);

				return producto;
			}
			return null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public List<Product> findAll() {
		ArrayList<Product> productos = new ArrayList<>();
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				SELECT p.tb_product_id, p.name, p.sales_price, p.code,
				       pt.tb_product_type_id, pt.code as pt_code, pt.name as pt_name
				FROM public.tb_product p
				LEFT JOIN tb_product_type pt ON p.tb_product_type_id = pt.tb_product_type_id
			""");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product producto = new Product();
				producto.setId(rs.getLong("tb_product_id"));
				producto.setName(rs.getString("name"));
				producto.setCode(rs.getString("code"));
				producto.setSalesPrice(rs.getBigDecimal("sales_price"));

				ProductType pt = new ProductType();
				pt.setId(rs.getLong("tb_product_type_id"));
				pt.setCode(rs.getString("pt_code"));
				pt.setName(rs.getString("pt_name"));
				producto.setProductType(pt);

				productos.add(producto);
			}
			return productos;
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

	public Product findByCodeAndIdNot(String codeToFind, Long id) {
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				SELECT tb_product_id, name, sales_price, code
				FROM public.tb_product
				WHERE lower(code) = ? AND tb_product_id <> ?
			""");
			ps.setString(1, codeToFind.toLowerCase());
			ps.setLong(2, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Product producto = new Product();
				producto.setId(rs.getLong("tb_product_id"));
				producto.setName(rs.getString("name"));
				producto.setCode(rs.getString("code"));
				producto.setSalesPrice(rs.getBigDecimal("sales_price"));
				return producto;
			}
			return null;
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}
}
