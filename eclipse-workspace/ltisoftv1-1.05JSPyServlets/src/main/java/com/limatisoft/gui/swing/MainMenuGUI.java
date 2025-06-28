package com.limatisoft.gui.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;
import com.limatisoft.base.ui.gui.ProductManagementPanel;

public class MainMenuGUI extends JFrame{
	private static final long serialVersionUID = 1L;
	static {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	
	public MainMenuGUI() {
		
		setTitle("Sistema de Gestion LtiSoft v1.0.0");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem productsMenuItem = new JMenuItem("Products");
        menu.add(productsMenuItem);
        JMenuItem productCategoriesMenuItem = new JMenuItem("Product Categories");
        menu.add(productCategoriesMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        productsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProductGUI();
			}
		});
        
        add(new WelcomePanel(), BorderLayout.CENTER);
	}
	protected void addProductGUI() {
		 getContentPane().removeAll();
	        getContentPane().add(new ProductManagementPanel(), BorderLayout.CENTER);
	        revalidate();
	        repaint();
	}
	public void display() {
		setExtendedState(JFrame.MAXIMIZED_BOTH);  
        setVisible(true);
	}
}
