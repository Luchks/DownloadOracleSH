package com.limatisoft.base.ui.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.limatisoft.ApplicationContext;
import com.limatisoft.base.model.Product;
import com.limatisoft.base.service.ProductCommandService;
import com.limatisoft.base.service.ProductQueryService;
import com.limatisoft.gui.swing.components.ToastDialog;

public class ProductManagementPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private ProductTablePanel productTablePanel;
	private ProductTableToolbarPanel toolbarPanel;
	private ProductQueryService productQueryService;
	private ProductCommandService productCommandService;
    
    public ProductManagementPanel() {
    	setLayout(new BorderLayout());
        productTablePanel = new ProductTablePanel();
        toolbarPanel = new ProductTableToolbarPanel();
        productQueryService = ApplicationContext.getInstance().getProductQueryService();
        productCommandService = ApplicationContext.getInstance().getProductCommandService();
        
        add(toolbarPanel, BorderLayout.NORTH);
        add(productTablePanel, BorderLayout.CENTER);
        
        loadProductData();
        
        toolbarPanel.setAddButtonAction(this::openAddProductDialog);
        
        toolbarPanel.setEditButtonAction(this::openEditProductDialog);
        
        toolbarPanel.setDeleteButtonAction(this::deleteSelectedProduct);
    }
   
	private void loadProductData() {
        List<Product> products = productQueryService.findAll();
        productTablePanel.updateTableData(products);
    }

    private void openAddProductDialog(ActionEvent e) {
        ProductFormDialog productFormDialog = new ProductFormDialog();
        productFormDialog.setProductSaveListener( product -> loadProductData());
        productFormDialog.setVisible(true);
    }
    
    private void openEditProductDialog(ActionEvent e) {
        Product selectedProduct = productTablePanel.getSelectedProduct();
        if (selectedProduct == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ProductFormDialog productFormDialog = new ProductFormDialog();
        productFormDialog.setProductData(selectedProduct);
        productFormDialog.setProductSaveListener(product -> loadProductData());
        productFormDialog.setVisible(true);
    }
    
    
    protected void deleteSelectedProduct(ActionEvent event) {
    	 Product selectedProduct = productTablePanel.getSelectedProduct();
         if (selectedProduct == null) {
             JOptionPane.showMessageDialog(this, "Debe seleccionar un registro", "Error", JOptionPane.ERROR_MESSAGE);
             return;
         }
         int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el producto?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
         if (confirm == JOptionPane.YES_OPTION) {
             productCommandService.delete(selectedProduct);
             ToastDialog.showNotification("Se eliminó satisfactoriamente");
             loadProductData();
         }
	}

}




