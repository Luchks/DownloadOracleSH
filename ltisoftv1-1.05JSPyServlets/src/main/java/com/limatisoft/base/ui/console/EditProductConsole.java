package com.limatisoft.base.ui.console;

import java.math.BigDecimal;

import com.limatisoft.base.model.Product;
import com.limatisoft.base.service.ProductCommandService;
import com.limatisoft.base.service.ProductQueryService;
import com.limatisoft.exceptions.CodeUniqueException;

public class EditProductConsole {
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
			console.printf("Si desea modificar el valor en algún atributo, ingréselo, \r\n"
					+ "caso contrario presione ENTER para mantener su valor\n"  );
			console.printf("Ingrese el código del producto a modificar:\n");
			String codigoABuscar = console.readLine("Codigo: ");
			
			Product productoEncontrado = productQueryService.findByCode(codigoABuscar);
			if(productoEncontrado == null) {
				System.out.println("Producto NO Encontrado");
				respuesta = console.readLine("¿Desea continuar añadiendo más registros?");
				if(respuesta.equalsIgnoreCase("S")) {
					continue;
				}
				break;
			}
			
			console.printf("Producto Encontrado:\n");
			
			console.printf("Codigo Actual: %s\n", productoEncontrado.getCode());
			String codigoNuevo = console.readLine("Codigo Nuevo: ");
			if(!codigoNuevo.isEmpty()) {
				productoEncontrado.setCode(codigoNuevo);
			}
			
			console.printf("Nombre Actual: %s\n", productoEncontrado.getName());
			String nombreNuevo = console.readLine("Nombre Nuevo: ");
			if(!nombreNuevo.isEmpty()) {
				productoEncontrado.setName(nombreNuevo);
			}
			console.printf("Precio Actual: %f\n", productoEncontrado.getSalesPrice().floatValue());
			this.solicitarPrecioNuevo(productoEncontrado);
			try { 
				productCommandService.updateUniqueProduct(productoEncontrado);
				console.printf("Se actualizó satisfactoriamente\n");
			}catch(CodeUniqueException ex) {
				console.printf(ex.getMessage() + "\n");
			}
			
			respuesta = console.readLine("¿Desea continuar modificando más registros?");
		}while(respuesta.equalsIgnoreCase("s"));
	}
	
	private void solicitarPrecioNuevo(Product producto) {
		MyConsole console = MyConsole.getInstance();
		String precioNuevo = console.readLine("Precio Nuevo: ");
		try {
			if(!precioNuevo.isEmpty()) {
				producto.setSalesPrice(new BigDecimal(precioNuevo));
			}
		}catch(NumberFormatException ex) {
			solicitarPrecioNuevo(producto);
		}
		
	}
}
