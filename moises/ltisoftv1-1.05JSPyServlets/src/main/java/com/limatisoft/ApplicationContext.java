package com.limatisoft;

import com.limatisoft.base.repository.ProductRepository;
import com.limatisoft.base.repository.ProductTypeRepository;
import com.limatisoft.base.repository.jdbc.ProductJdbcRepository;
import com.limatisoft.base.repository.jdbc.ProductTypeJdbcRepository;
import com.limatisoft.base.service.ProductCommandService;
import com.limatisoft.base.service.ProductQueryService;
import com.limatisoft.base.service.ProductTypeCommandService;
import com.limatisoft.base.service.ProductTypeQueryService;
import com.limatisoft.base.ui.console.AddProductConsole;
import com.limatisoft.base.ui.console.AddProductTypeConsole;
import com.limatisoft.base.ui.console.DeleteProductConsole;
import com.limatisoft.base.ui.console.DeleteProductTypeConsole;
import com.limatisoft.base.ui.console.EditProductConsole;
import com.limatisoft.base.ui.console.EditProductTypeConsole;
import com.limatisoft.base.ui.console.MainMenuConsole;
import com.limatisoft.base.ui.console.ProductMenuConsole;
import com.limatisoft.base.ui.console.ProductReportConsole;
import com.limatisoft.base.ui.console.ProductTypeMenuConsole;
import com.limatisoft.base.ui.console.ProductTypeReportConsole;

public class ApplicationContext {
    private static ApplicationContext instance;

    private MainMenuConsole mainMenuConsole;
    private ProductMenuConsole productMenuConsole;
    private AddProductConsole addProductConsole;
    private EditProductConsole editProductConsole;
    private DeleteProductConsole deleteProductConsole;
    private ProductReportConsole productReportConsole;
    private ProductCommandService productCommandService;
    private ProductQueryService productQueryService;
    private ProductRepository productRepository;
    

 	private ProductTypeMenuConsole productTypeMenuConsole;
    private AddProductTypeConsole addProductTypeConsole;
    private EditProductTypeConsole editProductTypeConsole;
    private DeleteProductTypeConsole deleteProductTypeConsole;
    private ProductTypeReportConsole productTypeReportConsole;
    private ProductTypeCommandService productTypeCommandService;
    private ProductTypeQueryService productTypeQueryService;
    private ProductTypeRepository productTypeRepository;   

    private ApplicationContext() {
        productRepository = new ProductJdbcRepository();
        productCommandService = new ProductCommandService();
        productQueryService = new ProductQueryService();
        addProductConsole = new AddProductConsole();
        editProductConsole = new EditProductConsole();
        deleteProductConsole = new DeleteProductConsole();
        productReportConsole = new ProductReportConsole();
        mainMenuConsole = new MainMenuConsole();
        productMenuConsole = new ProductMenuConsole(addProductConsole, editProductConsole, deleteProductConsole, productReportConsole);
        
        productCommandService.setProductRepository(productRepository);
        productQueryService.setProductRepository(productRepository);
        addProductConsole.setProductCommandService(productCommandService);
        editProductConsole.setProductCommandService(productCommandService);
        deleteProductConsole.setProductCommandService(productCommandService);
        deleteProductConsole.setProductQueryService(productQueryService);
        productReportConsole.setProductQueryService(productQueryService);
        mainMenuConsole.setProductMenuConsole(productMenuConsole);
       ///*********************************************************
        productTypeRepository = new ProductTypeJdbcRepository();
        productTypeCommandService = new ProductTypeCommandService();
        productTypeQueryService = new ProductTypeQueryService();
        addProductTypeConsole = new AddProductTypeConsole();
        editProductTypeConsole = new EditProductTypeConsole();
        deleteProductTypeConsole = new DeleteProductTypeConsole();
        productTypeReportConsole = new ProductTypeReportConsole();
        mainMenuConsole = new MainMenuConsole();
        productTypeMenuConsole = new ProductTypeMenuConsole(addProductTypeConsole, editProductTypeConsole, deleteProductTypeConsole, productTypeReportConsole);
        
        productTypeCommandService.setProductTypeRepository(productTypeRepository);
        productTypeQueryService.setProductTypeRepository(productTypeRepository);
        addProductTypeConsole.setProductTypeCommandService(productTypeCommandService);
        editProductTypeConsole.setProductTypeCommandService(productTypeCommandService);
        deleteProductTypeConsole.setProductTypeCommandService(productTypeCommandService);
        deleteProductTypeConsole.setProductTypeQueryService(productTypeQueryService);
        productTypeReportConsole.setProductTypeQueryService(productTypeQueryService);
        mainMenuConsole.setProductTypeMenuConsole(productTypeMenuConsole);
 
    }

    public static synchronized ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public MainMenuConsole getMainMenuConsole() {
        return mainMenuConsole;
    }
    
    public ProductCommandService getProductCommandService() {
		return productCommandService;
	}
    
    public ProductQueryService getProductQueryService() {
		return productQueryService;
	}

    public ProductTypeCommandService getProductTypeCommandService() {
		return productTypeCommandService;
	}
    public ProductTypeQueryService getProductTypeQueryService() {
		return productTypeQueryService;
	}
    
}
