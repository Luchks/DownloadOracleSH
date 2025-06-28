package com.limatisoft.base.ui.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.limatisoft.base.model.Product;
import com.limatisoft.gui.swing.components.FormattedDecimalField;

public class ProductFormPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField idField;
	private JTextField codeField;
	private JTextField nameField;
	private FormattedDecimalField salesPriceField;

	public ProductFormPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		idField = new JTextField();
		idField.setVisible(false);  

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Code:"), gbc);

		gbc.gridx = 1;
		codeField = new JTextField(20);
		add(codeField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Name:"), gbc);

		gbc.gridx = 1;
		nameField = new JTextField(20);
		add(nameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Sales Price:"), gbc);

		gbc.gridx = 1;
		salesPriceField = new FormattedDecimalField();
		add(salesPriceField, gbc);
	}

	public Product getProductData() {
		Product product = new Product();
		product.setId(idField.getText().isEmpty() ? null : Long.parseLong(idField.getText()));
		product.setCode(codeField.getText());
		product.setName(nameField.getText());
		product.setSalesPrice(salesPriceField.getValueAsBigDecimal());
		return product;
	}

	public void setProductData(Product product) {
		idField.setText(product.getId() != null ? product.getId().toString() : "");
		codeField.setText(product.getCode());
		nameField.setText(product.getName());
		salesPriceField.setValue(product.getSalesPrice());
	}

	public void clearForm() {
		idField.setText("");
		codeField.setText("");
		nameField.setText("");
		salesPriceField.setText("");
	}
}
