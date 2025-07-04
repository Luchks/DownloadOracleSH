package com.limatisoft.gui.swing.components;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TableToolbarPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    public TableToolbarPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Agregar");
        editButton = new JButton("Editar");
        deleteButton = new JButton("Eliminar");
        
        add(addButton);
        add(editButton);
        add(deleteButton);
    }

    public void setAddButtonAction(ActionListener action) {
        addButton.addActionListener(action);
    }

    public void setEditButtonAction(ActionListener action) {
    	editButton.addActionListener(action);
    }
    

    public void setDeleteButtonAction(ActionListener action) {
    	deleteButton.addActionListener(action);
    }
}
