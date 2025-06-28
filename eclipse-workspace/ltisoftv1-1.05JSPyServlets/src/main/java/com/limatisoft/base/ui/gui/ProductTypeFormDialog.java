package com.limatisoft.base.ui.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import com.limatisoft.ApplicationContext;
import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.service.ProductTypeCommandService;
import com.limatisoft.gui.swing.components.ToastDialog;

public class ProductTypeFormDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ProductTypeFormPanel productTypeFormPanel;
    private ProductTypeFormToolbarPanel formToolbarPanel;
    private ProductTypeCommandService productTypeCommandService;
    private ProductTypeSaveListener productTypeSaveListener;
    
    public ProductTypeFormDialog() {
    	setModal(true);
    	setResizable(false);
        setTitle("Add ProductType");
        productTypeFormPanel = new ProductTypeFormPanel();
        formToolbarPanel = new ProductTypeFormToolbarPanel();
        productTypeCommandService = ApplicationContext.getInstance().getProductTypeCommandService();
        
        setLayout(new BorderLayout());
        add(formToolbarPanel, BorderLayout.NORTH);
        add(productTypeFormPanel, BorderLayout.CENTER);
        
        formToolbarPanel.setSaveButtonAction(this::saveProductType);
        formToolbarPanel.setCloseButtonAction(this::closeDialog);
        pack();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                setLocationRelativeTo(SwingUtilities.getWindowAncestor(ProductTypeFormDialog.this));
            }
        });
    }
    
    public void setProductTypeSaveListener(ProductTypeSaveListener productTypeSaveListener) {
		this.productTypeSaveListener = productTypeSaveListener;
	}
    
    private void saveProductType(ActionEvent event) {
        ProductType productType = productTypeFormPanel.getProductTypeData();
        if(productType.getId() == null) {
        	productTypeCommandService.save(productType);
        	ToastDialog.showNotification("Se guardó satisfactoriamente");
        }else {
        	productTypeCommandService.update(productType);
        	ToastDialog.showNotification("Se actualizó satisfactoriamente");
        }
        if(productTypeSaveListener != null) {
        	productTypeSaveListener.onProductTypeSaved(productType);
        }
        dispose();
    }
    
    private void closeDialog(ActionEvent e) {
        dispose();  
    }

	public void setProductTypeData(ProductType productType) {
		productTypeFormPanel.setProductTypeData(productType);
	}

	
}
