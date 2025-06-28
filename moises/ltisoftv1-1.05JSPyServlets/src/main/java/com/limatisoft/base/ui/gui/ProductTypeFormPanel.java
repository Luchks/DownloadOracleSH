package com.limatisoft.base.ui.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.limatisoft.base.model.ProductType;
import com.limatisoft.gui.swing.components.FormattedDecimalField;

public class ProductTypeFormPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField idField;
	private JTextField codeField;
	private JTextField nameField;
	private FormattedDecimalField salesPriceField;

	public ProductTypeFormPanel() {
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

	public ProductType getProductTypeData() {
		ProductType productType = new ProductType();
		productType.setId(idField.getText().isEmpty() ? null : Long.parseLong(idField.getText()));
		productType.setCode(codeField.getText());
		productType.setName(nameField.getText());
		return productType;
	}

	public void setProductTypeData(ProductType productType) {
		idField.setText(productType.getId() != null ? productType.getId().toString() : "");
		codeField.setText(productType.getCode());
		nameField.setText(productType.getName());
	}

	public void clearForm() {
		idField.setText("");
		codeField.setText("");
		nameField.setText("");
		salesPriceField.setText("");
	}
}
