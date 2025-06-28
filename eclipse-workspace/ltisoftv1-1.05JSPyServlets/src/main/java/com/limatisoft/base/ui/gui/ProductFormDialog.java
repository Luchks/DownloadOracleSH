package com.limatisoft.base.ui.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import com.limatisoft.ApplicationContext;
import com.limatisoft.base.model.Product;
import com.limatisoft.base.service.ProductCommandService;
import com.limatisoft.gui.swing.components.ToastDialog;

public class ProductFormDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ProductFormPanel productFormPanel;
    private ProductFormToolbarPanel formToolbarPanel;
    private ProductCommandService productCommandService;
    private ProductSaveListener productSaveListener;
    
    public ProductFormDialog() {
    	setModal(true);
    	setResizable(false);
        setTitle("Add Product");
        productFormPanel = new ProductFormPanel();
        formToolbarPanel = new ProductFormToolbarPanel();
        productCommandService = ApplicationContext.getInstance().getProductCommandService();
        
        setLayout(new BorderLayout());
        add(formToolbarPanel, BorderLayout.NORTH);
        add(productFormPanel, BorderLayout.CENTER);
        
        formToolbarPanel.setSaveButtonAction(this::saveProduct);
        formToolbarPanel.setCloseButtonAction(this::closeDialog);
        pack();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                setLocationRelativeTo(SwingUtilities.getWindowAncestor(ProductFormDialog.this));
            }
        });
    }
    
    public void setProductSaveListener(ProductSaveListener productSaveListener) {
		this.productSaveListener = productSaveListener;
	}
    
    private void saveProduct(ActionEvent event) {
        Product product = productFormPanel.getProductData();
        if(product.getId() == null) {
        	productCommandService.save(product);
        	ToastDialog.showNotification("Se guardó satisfactoriamente");
        }else {
        	productCommandService.update(product);
        	ToastDialog.showNotification("Se actualizó satisfactoriamente");
        }
        if(productSaveListener != null) {
        	productSaveListener.onProductSaved(product);
        }
        dispose();
    }
    
    private void closeDialog(ActionEvent e) {
        dispose();  
    }

	public void setProductData(Product product) {
		productFormPanel.setProductData(product);
	}

	
}
