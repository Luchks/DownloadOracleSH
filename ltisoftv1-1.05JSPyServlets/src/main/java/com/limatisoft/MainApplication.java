package com.limatisoft;

import com.limatisoft.base.ui.console.MainMenuConsole;
import com.limatisoft.gui.swing.MainMenuGUI;

public class MainApplication {
	private MainMenuConsole mainMenuConsole;
	private MainMenuGUI mainMenuGUI;
	
	public static void main(String[] args) {
		MainApplication mainApplication = new MainApplication();
		/*
		mainApplication.mainMenuConsole = ApplicationContext.getInstance().getMainMenuConsole();
		mainApplication.mainMenuConsole.show();
		*/
		mainApplication.mainMenuGUI = new MainMenuGUI();
		mainApplication.mainMenuGUI.display();
	}
}
