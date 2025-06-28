package com.limatisoft.gui.swing;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WelcomePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public WelcomePanel() {
		setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Bienvenido al Sistema de Gestion LtiSoft", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);
	}
}
