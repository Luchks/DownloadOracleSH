package com.ejemplo.dao;

import com.ejemplo.modelo.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private Connection conexion;

    public PersonaDAO() {
        try {
            conexion = DriverManager.getConnection("jdbc:derby:miBD;create=true");
            Statement stmt = conexion.createStatement();
            stmt.executeUpdate("CREATE TABLE personas (id INT PRIMARY KEY, nombre VARCHAR(100))");
        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32")) { // Tabla ya existe
                e.printStackTrace();
            }
        }
    }

    public void crearPersona(Persona persona) {
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO personas VALUES (?, ?)");
            ps.setInt(1, persona.getId());
            ps.setString(2, persona.getNombre());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Persona obtenerPersona(int id) {
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM personas WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Persona(rs.getInt("id"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Persona> obtenerTodasLasPersonas() {
        List<Persona> lista = new ArrayList<>();
        try {
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM personas");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Persona(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

