package com.limatisoft.base.ui.console;


public class ProductTypeMenuConsole {
	private ProductTypeReportConsole productTypeReportConsole;
	private EditProductTypeConsole editProductTypeConsole;
	private DeleteProductTypeConsole deleteProductTypeConsole;
	private AddProductTypeConsole addProductTypeConsole;
		
	public ProductTypeMenuConsole(
			AddProductTypeConsole addProductTypeConsole,
			EditProductTypeConsole editProductTypeConsole,
			DeleteProductTypeConsole deleteProductTypeConsole,
			ProductTypeReportConsole productTypeReportConsole 
			) {
		super();
		this.productTypeReportConsole = productTypeReportConsole;
		this.editProductTypeConsole = editProductTypeConsole;
		this.deleteProductTypeConsole = deleteProductTypeConsole;
		this.addProductTypeConsole = addProductTypeConsole;
	}



	public void show() {
		MyConsole console = MyConsole.getInstance();
		String opcionMenuPrincipal = "";
		
		do{
			System.out.println(" ");
			System.out.println("====================");
			System.out.println("Menú de opciones");
			System.out.println("1. Ingreso de ProductTypeos");
			System.out.println("2. Edicion de ProductTypeos");
			System.out.println("3. Eliminacion de ProductTypeos");
			System.out.println("4. Reporte de ProductTypeos");
			System.out.println("5. Salir del Sistema");
			boolean contieneError;
			do {
				contieneError = false;
				opcionMenuPrincipal = console.readLine("Elija una opcion: ");
				try {
					int opcionElegida = Integer.parseInt(opcionMenuPrincipal);
					if(!(opcionElegida >= 1 && opcionElegida <= 4)) {
						contieneError = true;
					}
				}catch(NumberFormatException ex) {
					contieneError = true;
				}
			}while(contieneError);
			
			switch(opcionMenuPrincipal) {
				case "1" :
					addProductTypeConsole.show();
					break;
				
				case "2" :
					editProductTypeConsole.show();
					break;
				case "3" :
					deleteProductTypeConsole.show();
					break;
				case "4" :
					productTypeReportConsole.show();
					break;
				case "5" :
					System.exit(0);
					break;
				
			}
			
		}while(opcionMenuPrincipal.equals("5") == false);
	}
}
