package com.limatisoft.base.ui.console;

import java.math.BigDecimal;

import com.limatisoft.base.model.Product;
import com.limatisoft.base.service.ProductCommandService;
import com.limatisoft.exceptions.CodeUniqueException;

public class AddProductConsole {
	private ProductCommandService productCommandService;
	
	public void setProductCommandService(ProductCommandService productCommandService) {
		this.productCommandService = productCommandService;
	}
	
	public void show() {
		MyConsole console = MyConsole.getInstance();
		console.printf("=========================================\n");
		String respuesta = "";
		int productCount = 1;
		do {
			console.printf("-----------------------------------------\n");
			console.printf("Producto %d\n" , productCount++ );
			console.printf("-----------------------------------------\n");
			String codigo = console.readLine("Codigo: "); 
			String nombre = console.readLine("Nombre: ");
			Product producto = new Product();
			producto.setCode(codigo);
			producto.setName(nombre);
			
			boolean contieneError;
			do {
				contieneError = false;
				String precio = console.readLine("Precio: ");
				try {
					producto.setSalesPrice(new BigDecimal(precio));
				}catch(NumberFormatException ex) {
					contieneError = true;
				}
			}while(contieneError);
			try { 
				productCommandService.saveUniqueProduct(producto);
				console.printf("Se guardó satisfactoriamente\n");
			}catch(CodeUniqueException ex) {
				console.printf(ex.getMessage()+"\n");
			}
			respuesta = console.readLine("¿Desea continuar añadiendo más registros?");
		}while(respuesta.equalsIgnoreCase("s"));
		
	}
}
