package com.limatisoft.base.ui.console;

import java.util.List;

import com.limatisoft.base.model.Product;
import com.limatisoft.base.service.ProductQueryService;


public class ProductReportConsole {
	private ProductQueryService productQueryService ;
	
	public void setProductQueryService(ProductQueryService productQueryService) {
		this.productQueryService = productQueryService;
	}
	
	public void show() {
		MyConsole console = MyConsole.getInstance();
		List<Product> productos = productQueryService.findAll();
		console.printf("Los productos ingresados son:\n");
		for (Product product : productos) {
			String out = String.format("%s - %s (S/ %f)", product.getCode(), product.getName(), product.getSalesPrice().floatValue());
			console.printf(out + "\n");
		}
	}

}
