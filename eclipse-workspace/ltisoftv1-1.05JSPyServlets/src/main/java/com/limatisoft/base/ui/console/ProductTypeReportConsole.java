package com.limatisoft.base.ui.console;

import java.util.List;

import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.service.ProductTypeQueryService;


public class ProductTypeReportConsole {
	private ProductTypeQueryService productTypeQueryService ;
	
	public void setProductTypeQueryService(ProductTypeQueryService productTypeQueryService) {
		this.productTypeQueryService = productTypeQueryService;
	}
	
	public void show() {
		MyConsole console = MyConsole.getInstance();
		List<ProductType> productTypeos = productTypeQueryService.findAll();
		console.printf("Los productTypeos ingresados son:\n");
		for (ProductType productType : productTypeos) {
			String out = String.format("%s - %s (S/ %f)", productType.getCode(), productType.getName());
			console.printf(out + "\n");
		}
	}

}
