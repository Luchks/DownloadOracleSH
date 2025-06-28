package com.limatisoft.gui.swing.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

import com.limatisoft.util.StringUtils;

public class FormattedDecimalField extends JFormattedTextField {
	private static final long serialVersionUID = 1L;
	private static final DecimalFormat decimalFormat;
    private boolean selectAllOnFocus = false;  
    private static final DecimalFormatSymbols symbols = new DecimalFormatSymbols();

    static {
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        decimalFormat = new DecimalFormat("#,##0.00", symbols);
        decimalFormat.setParseBigDecimal(true);
    }
    
    public FormattedDecimalField() {
        super(new NumberFormatter(decimalFormat));
        decimalFormat.setParseBigDecimal(true);
        setColumns(10);  // Configura el ancho del campo

        // para filtrar las entradas
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                // Permitir números, punto decimal, coma y retroceso
                if (!Character.isDigit(c) && c != '.' && c != ',' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume(); // Ignorar la entrada no válida
                }

                // Solo permitir un punto decimal
                if (c == '.' && getText().contains(".")) {
                    e.consume(); // Ignorar si ya hay un punto decimal
                }

                // No permitir coma al principio
                if (c == ',' && getText().length() == 0) {
                    e.consume(); // Ignorar si la coma es el primer carácter
                }
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
            	showUnformattedValue();
                if (selectAllOnFocus) {
                    selectAll();
                } 
            }

            @Override
            public void focusLost(FocusEvent e) {
                formatValue();
            }
        });
    }

    private void showUnformattedValue() {
        try {
            BigDecimal value = getValueAsBigDecimal();
            setText(value != null ? value.toPlainString() : "");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            setText("");
        }
    }

    private void formatValue() {
        try {
            BigDecimal value = getValueAsBigDecimal();
            if(value == null) {
            	return;
            }
            setText(decimalFormat.format(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            setText("");
        }
    }

    public BigDecimal getValueAsBigDecimal() {
        try {
        	String text = getText();
        	if(StringUtils.isBlank(text)) {
        		return null;
        	}
            return (BigDecimal) decimalFormat.parse(text.replace(",", ""));
        } catch (ParseException e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    public void setValueFormattedString(String formattedValue) {
        setText(formattedValue);
    }

    public void setValueUnformattedString(String unformattedValue) {
        try {
            BigDecimal value = new BigDecimal(unformattedValue);
            setValue(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            setText("");
        }
    }

    public void setValue(BigDecimal value) {
        if (value != null) {
            setText(decimalFormat.format(value));
        } else {
            setText("");
        }
    }

    public boolean isSelectAllOnFocus() {
        return selectAllOnFocus;
    }

    public void setSelectAllOnFocus(boolean selectAllOnFocus) {
        this.selectAllOnFocus = selectAllOnFocus;
    }
}
