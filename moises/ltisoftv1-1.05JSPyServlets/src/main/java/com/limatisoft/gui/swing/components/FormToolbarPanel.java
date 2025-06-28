package com.limatisoft.gui.swing.components;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FormToolbarPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton saveButton;
    private JButton closeButton;
    
    public FormToolbarPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        saveButton = new JButton("Guardar");
        closeButton = new JButton("Cerrar");
        
        add(saveButton);
        add(closeButton);
    }
    
    public void setSaveButtonAction(ActionListener action) {
        saveButton.addActionListener(action);
    }
    
    public void setCloseButtonAction(ActionListener action) {
        closeButton.addActionListener(action);
    }

    public void showCloseButton(boolean show) {
        closeButton.setVisible(show);
    }
}
