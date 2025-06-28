package com.limatisoft.base.ui.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.limatisoft.base.model.ProductType;

public class ProductTypeTablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable productTypeTable;
    private ProductTypeTableModel productTypeTableModel;

    public ProductTypeTablePanel() {
        setLayout(new BorderLayout());
        productTypeTableModel = new ProductTypeTableModel();
        productTypeTable = new JTable(productTypeTableModel); 
        add(new JScrollPane(productTypeTable), BorderLayout.CENTER);
    }

    public void updateTableData(List<ProductType> productTypes) {
        productTypeTableModel.setProductTypes(productTypes);
    }

    private static class ProductTypeTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private final String[] columnNames = { "Code", "Name"};
        private List<ProductType> productTypes;

        @Override
        public int getRowCount() {
            return productTypes == null ? 0 : productTypes.size();
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
            if (productTypes == null || productTypes.size() == 0) {
                return null;
            }

            ProductType productType = productTypes.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return productType.getCode();
                case 1:
                	return productType.getName();
                default:
                    return null;
            }
        }

        public void setProductTypes(List<ProductType> productTypes) {
            this.productTypes = productTypes;
            fireTableDataChanged();  
        }
        
        public ProductType getProductTypeAt(int index) {
        	return productTypes.get(index);
        }
    }
    
    public ProductType getSelectedProductType() {
        int selectedRow = productTypeTable.getSelectedRow();
        if (selectedRow >= 0) {
            return productTypeTableModel.getProductTypeAt(selectedRow);
        }
        return null;
    }
}
