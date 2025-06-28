package com.limatisoft.base.ui.console;

import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.service.ProductTypeCommandService;
import com.limatisoft.base.service.ProductTypeQueryService;

public class DeleteProductTypeConsole {
	private ProductTypeCommandService productTypeCommandService;
	private ProductTypeQueryService productTypeQueryService;
	
	public void setProductTypeCommandService(ProductTypeCommandService productTypeCommandService) {
		this.productTypeCommandService = productTypeCommandService;
	}

	public void setProductTypeQueryService(ProductTypeQueryService productTypeQueryService) {
		this.productTypeQueryService = productTypeQueryService;
	}

	public void show() {
		MyConsole console = MyConsole.getInstance();
		console.printf("=========================================\n");
		String respuesta = "";
		do {
			console.printf("-----------------------------------------\n");
			console.printf("Ingrese el código del productTypeo a eliminar:\n");
			String codigoABuscar = console.readLine("Codigo: ");
			ProductType productTypeoEncontrado = productTypeQueryService.findByCode(codigoABuscar);
			if(productTypeoEncontrado == null) {
				System.out.println("ProductTypeo NO Encontrado");
				respuesta = console.readLine("¿Desea continuar eliminando más registros?");
				if(respuesta.equalsIgnoreCase("S")) {
					continue;
				}
				break;
			}
			productTypeCommandService.delete(productTypeoEncontrado);
			console.printf("ProductTypeo Eliminado Satisfactoriamente\n");
			respuesta = console.readLine("¿Desea continuar eliminando más registros?");
		}while(respuesta.equalsIgnoreCase("s"));
	}
	
}
