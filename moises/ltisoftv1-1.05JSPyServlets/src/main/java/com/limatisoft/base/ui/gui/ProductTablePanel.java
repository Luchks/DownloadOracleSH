package com.limatisoft.base.ui.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.limatisoft.base.model.Product;

public class ProductTablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable productTable;
    private ProductTableModel productTableModel;

    public ProductTablePanel() {
        setLayout(new BorderLayout());
        productTableModel = new ProductTableModel();
        productTable = new JTable(productTableModel); 
        add(new JScrollPane(productTable), BorderLayout.CENTER);
    }

    public void updateTableData(List<Product> products) {
        productTableModel.setProducts(products);
    }

    private static class ProductTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final String[] columnNames = { "Code", "Name", "Sales Price"};
        private List<Product> products;

        @Override
        public int getRowCount() {
            return products == null ? 0 : products.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (products == null || products.size() == 0) {
                return null;
            }

            Product product = products.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return product.getCode();
                case 1:
                	return product.getName();
                case 2:
                	return product.getSalesPrice();
                default:
                    return null;
            }
        }

        public void setProducts(List<Product> products) {
            this.products = products;
            fireTableDataChanged();  
        }
        
        public Product getProductAt(int index) {
        	return products.get(index);
        }
    }
    
    public Product getSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            return productTableModel.getProductAt(selectedRow);
        }
        return null;
    }
}
