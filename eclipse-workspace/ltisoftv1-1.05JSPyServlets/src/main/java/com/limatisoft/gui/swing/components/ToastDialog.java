package com.limatisoft.gui.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class ToastDialog extends JWindow {
	private static final long serialVersionUID = 1L;
	private static final int durationInSeconds = 3;
    private ToastDialog(Window parent, String message, int durationInSeconds) {
        super(parent);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(60, 63, 65));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);
        label.setFont(label.getFont().deriveFont(label.getFont().getSize() + 1.0f));  
        panel.add(label);

        getContentPane().add(panel);
        pack();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - getWidth() - 30);
        int y = 30;
        setLocation(x, y);

        setVisible(true);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                dispose();
            }
        }, durationInSeconds * 1000);
    }
    
    public static void showNotification(String message, int durationInSeconds) {
        SwingUtilities.invokeLater(
        		() -> new ToastDialog(null, message, durationInSeconds)
        );
    }
    
    public static void showNotification(String message) {
       showNotification(message, durationInSeconds);
    }
}
