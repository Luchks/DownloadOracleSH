package com.limatisoft.base.ui.console;


import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.service.ProductTypeCommandService;
import com.limatisoft.base.service.ProductTypeQueryService;
import com.limatisoft.exceptions.CodeUniqueException;

public class EditProductTypeConsole {
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
			console.printf("Si desea modificar el valor en algún atributo, ingréselo, \r\n"
					+ "caso contrario presione ENTER para mantener su valor\n"  );
			console.printf("Ingrese el código del productTypeo a modificar:\n");
			String codigoABuscar = console.readLine("Codigo: ");
			
			ProductType productTypeoEncontrado = productTypeQueryService.findByCode(codigoABuscar);
			if(productTypeoEncontrado == null) {
				System.out.println("ProductTypeo NO Encontrado");
				respuesta = console.readLine("¿Desea continuar añadiendo más registros?");
				if(respuesta.equalsIgnoreCase("S")) {
					continue;
				}
				break;
			}
			
			console.printf("ProductTypeo Encontrado:\n");
			
			console.printf("Codigo Actual: %s\n", productTypeoEncontrado.getCode());
			String codigoNuevo = console.readLine("Codigo Nuevo: ");
			if(!codigoNuevo.isEmpty()) {
				productTypeoEncontrado.setCode(codigoNuevo);
			}
			
			console.printf("Nombre Actual: %s\n", productTypeoEncontrado.getName());
			String nombreNuevo = console.readLine("Nombre Nuevo: ");
			if(!nombreNuevo.isEmpty()) {
				productTypeoEncontrado.setName(nombreNuevo);
			}
			try { 
				productTypeCommandService.updateUniqueProductType(productTypeoEncontrado);
				console.printf("Se actualizó satisfactoriamente\n");
			}catch(CodeUniqueException ex) {
				console.printf(ex.getMessage() + "\n");
			}
			
			respuesta = console.readLine("¿Desea continuar modificando más registros?");
		}while(respuesta.equalsIgnoreCase("s"));
	}
	
}
