package com.limatisoft.base.ui.console;

import com.limatisoft.base.model.Product;
import com.limatisoft.base.service.ProductCommandService;
import com.limatisoft.base.service.ProductQueryService;

public class DeleteProductConsole {
	private ProductCommandService productCommandService;
	private ProductQueryService productQueryService;
	
	public void setProductCommandService(ProductCommandService productCommandService) {
		this.productCommandService = productCommandService;
	}

	public void setProductQueryService(ProductQueryService productQueryService) {
		this.productQueryService = productQueryService;
	}

	public void show() {
		MyConsole console = MyConsole.getInstance();
		console.printf("=========================================\n");
		String respuesta = "";
		do {
			console.printf("-----------------------------------------\n");
			console.printf("Ingrese el código del producto a eliminar:\n");
			String codigoABuscar = console.readLine("Codigo: ");
			Product productoEncontrado = productQueryService.findByCode(codigoABuscar);
			if(productoEncontrado == null) {
				System.out.println("Producto NO Encontrado");
				respuesta = console.readLine("¿Desea continuar eliminando más registros?");
				if(respuesta.equalsIgnoreCase("S")) {
					continue;
				}
				break;
			}
			productCommandService.delete(productoEncontrado);
			console.printf("Producto Eliminado Satisfactoriamente\n");
			respuesta = console.readLine("¿Desea continuar eliminando más registros?");
		}while(respuesta.equalsIgnoreCase("s"));
	}
	
}
