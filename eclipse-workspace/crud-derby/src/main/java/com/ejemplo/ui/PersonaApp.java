package com.ejemplo.ui;

import com.ejemplo.dao.PersonaDAO;
import com.ejemplo.modelo.Persona;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PersonaApp extends JFrame {
    private PersonaDAO dao;
    private JTextField idField, nombreField;
    private DefaultTableModel tableModel;

    public PersonaApp() {
        dao = new PersonaDAO();

        setTitle("CRUD Personas");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Formulario arriba
        JPanel form = new JPanel(new GridLayout(3, 2));
        form.add(new JLabel("ID:"));
        idField = new JTextField();
        form.add(idField);

        form.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        form.add(nombreField);

        JButton addBtn = new JButton("Agregar");
        form.add(addBtn);

        add(form, BorderLayout.NORTH);

        // Tabla abajo
        tableModel = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Evento de botón
        addBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String nombre = nombreField.getText();
                Persona persona = new Persona(id, nombre);
                dao.crearPersona(persona);
                cargarPersonas();
                idField.setText("");
                nombreField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        cargarPersonas();
        setVisible(true);
    }

    private void cargarPersonas() {
        tableModel.setRowCount(0); // limpiar
        List<Persona> lista = dao.obtenerTodasLasPersonas();
        for (Persona p : lista) {
            tableModel.addRow(new Object[]{p.getId(), p.getNombre()});
        }
    }
}
