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

public class ProductTypeJdbcRepository implements ProductTypeRepository {

	@Override
	public List<ProductType> findAll() {
		List<ProductType> tipos = new ArrayList<>();

		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				SELECT tb_product_type_id, code, name
				FROM public.tb_product_type
			""");

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductType tipo = new ProductType();
				tipo.setId(rs.getLong("tb_product_type_id"));
				tipo.setCode(rs.getString("code"));
				tipo.setName(rs.getString("name"));
				tipos.add(tipo);
			}

			return tipos;
		} catch (SQLException ex) {
			throw new RuntimeException("Error al obtener los tipos de producto: " + ex.getMessage(), ex);
		}
	}

	@Override
	public ProductType findByCode(String code) {
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				SELECT tb_product_type_id, code, name
				FROM public.tb_product_type
				WHERE lower(code) = ?
			""");
			ps.setString(1, code.toLowerCase());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ProductType tipo = new ProductType();
				tipo.setId(rs.getLong("tb_product_type_id"));
				tipo.setCode(rs.getString("code"));
				tipo.setName(rs.getString("name"));
				return tipo;
			}
			return null;
		} catch (SQLException ex) {
			throw new RuntimeException("Error al buscar tipo de producto por código: " + ex.getMessage(), ex);
		}
	}

	@Override
	public ProductType findByCodeAndIdNot(String code, Long id) {
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				SELECT tb_product_type_id, code, name
				FROM public.tb_product_type
				WHERE lower(code) = ? AND tb_product_type_id != ?
			""");
			ps.setString(1, code.toLowerCase());
			ps.setLong(2, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				ProductType tipo = new ProductType();
				tipo.setId(rs.getLong("tb_product_type_id"));
				tipo.setCode(rs.getString("code"));
				tipo.setName(rs.getString("name"));
				return tipo;
			}
			return null;
		} catch (SQLException ex) {
			throw new RuntimeException("Error al buscar tipo por código excluyendo ID: " + ex.getMessage(), ex);
		}
	}

	@Override
	public void save(ProductType tipo) {
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				INSERT INTO public.tb_product_type (code, name)
				VALUES (?, ?)
			""");
			ps.setString(1, tipo.getCode());
			ps.setString(2, tipo.getName());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Error al guardar tipo de producto: " + ex.getMessage(), ex);
		}
	}

	@Override
	public void update(ProductType tipo) {
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				UPDATE public.tb_product_type
				SET code = ?, name = ?
				WHERE tb_product_type_id = ?
			""");
			ps.setString(1, tipo.getCode());
			ps.setString(2, tipo.getName());
			ps.setLong(3, tipo.getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Error al actualizar tipo de producto: " + ex.getMessage(), ex);
		}
	}

	@Override
	public void delete(ProductType tipo) {
		try (Connection connection = DatabaseConnectionManager.getConnection()) {
			PreparedStatement ps = connection.prepareStatement("""
				DELETE FROM public.tb_product_type
				WHERE tb_product_type_id = ?
			""");
			ps.setLong(1, tipo.getId());
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException("Error al eliminar tipo de producto: " + ex.getMessage(), ex);
		}
	}
}
