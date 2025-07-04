package com.limatisoft.base.ui.console;


import com.limatisoft.base.model.ProductType;
import com.limatisoft.base.service.ProductTypeCommandService;
import com.limatisoft.exceptions.CodeUniqueException;

public class AddProductTypeConsole {
	private ProductTypeCommandService productTypeCommandService;
	
	public void setProductTypeCommandService(ProductTypeCommandService productTypeCommandService) {
		this.productTypeCommandService = productTypeCommandService;
	}
	
	public void show() {
		MyConsole console = MyConsole.getInstance();
		console.printf("=========================================\n");
		String respuesta = "";
		int productTypeCount = 1;
		do {
			console.printf("-----------------------------------------\n");
			console.printf("ProductTypeo %d\n" , productTypeCount++ );
			console.printf("-----------------------------------------\n");
			String codigo = console.readLine("Codigo: "); 
			String nombre = console.readLine("Nombre: ");
			ProductType productTypeo = new ProductType();
			productTypeo.setCode(codigo);
			productTypeo.setName(nombre);
			
			try { 
				productTypeCommandService.saveUniqueProductType(productTypeo);
				console.printf("Se guardó satisfactoriamente\n");
			}catch(CodeUniqueException ex) {
				console.printf(ex.getMessage()+"\n");
			}
			respuesta = console.readLine("¿Desea continuar añadiendo más registros?");
		}while(respuesta.equalsIgnoreCase("s"));
		
	}
}
