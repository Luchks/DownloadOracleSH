package com.limatisoft.base.ui.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.limatisoft.gui.swing.components.TableToolbarPanel;

public class ProductTableToolbarPanel extends TableToolbarPanel {
	private static final long serialVersionUID = 1L;

	private JButton checkStockButton;	
	
    public ProductTableToolbarPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        checkStockButton = new JButton("Consultar Stock");
        add(checkStockButton);
    }

    public void setCheckStockButtonAction(ActionListener action) {
    	checkStockButton.addActionListener(action);
    }
    
}
