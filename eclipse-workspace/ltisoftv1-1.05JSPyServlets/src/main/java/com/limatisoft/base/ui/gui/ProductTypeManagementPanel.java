package com.limatisoft.base.ui.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.limatisoft.ApplicationContext;
import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.service.ProductTypeCommandService;
import com.limatisoft.base.service.ProductTypeQueryService;
import com.limatisoft.gui.swing.components.ToastDialog;

public class ProductTypeManagementPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private ProductTypeTablePanel productTypeTablePanel;
	private ProductTypeTableToolbarPanel toolbarPanel;
	private ProductTypeQueryService productTypeQueryService;
	private ProductTypeCommandService productTypeCommandService;
    
    public ProductTypeManagementPanel() {
    	setLayout(new BorderLayout());
        productTypeTablePanel = new ProductTypeTablePanel();
        toolbarPanel = new ProductTypeTableToolbarPanel();
        productTypeQueryService = ApplicationContext.getInstance().getProductTypeQueryService();
        productTypeCommandService = ApplicationContext.getInstance().getProductTypeCommandService();
        
        add(toolbarPanel, BorderLayout.NORTH);
        add(productTypeTablePanel, BorderLayout.CENTER);
        
        loadProductTypeData();
        
        toolbarPanel.setAddButtonAction(this::openAddProductTypeDialog);
        
        toolbarPanel.setEditButtonAction(this::openEditProductTypeDialog);
        
        toolbarPanel.setDeleteButtonAction(this::deleteSelectedProductType);
    }
   
	private void loadProductTypeData() {
        List<ProductType> productTypes = productTypeQueryService.findAll();
        productTypeTablePanel.updateTableData(productTypes);
    }

    private void openAddProductTypeDialog(ActionEvent e) {
        ProductTypeFormDialog productTypeFormDialog = new ProductTypeFormDialog();
        productTypeFormDialog.setProductTypeSaveListener( productType -> loadProductTypeData());
        productTypeFormDialog.setVisible(true);
    }
    
    private void openEditProductTypeDialog(ActionEvent e) {
        ProductType selectedProductType = productTypeTablePanel.getSelectedProductType();
        if (selectedProductType == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un registro", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ProductTypeFormDialog productTypeFormDialog = new ProductTypeFormDialog();
        productTypeFormDialog.setProductTypeData(selectedProductType);
        productTypeFormDialog.setProductTypeSaveListener(productType -> loadProductTypeData());
        productTypeFormDialog.setVisible(true);
    }
    
    
    protected void deleteSelectedProductType(ActionEvent event) {
    	 ProductType selectedProductType = productTypeTablePanel.getSelectedProductType();
         if (selectedProductType == null) {
             JOptionPane.showMessageDialog(this, "Debe seleccionar un registro", "Error", JOptionPane.ERROR_MESSAGE);
             return;
         }
         int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el productTypeo?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
         if (confirm == JOptionPane.YES_OPTION) {
             productTypeCommandService.delete(selectedProductType);
             ToastDialog.showNotification("Se eliminó satisfactoriamente");
             loadProductTypeData();
         }
	}

}




