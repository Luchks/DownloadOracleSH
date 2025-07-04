package com.limatisoft.base.ui.console;


public class ProductMenuConsole {
	private ProductReportConsole productReportConsole;
	private EditProductConsole editProductConsole;
	private DeleteProductConsole deleteProductConsole;
	private AddProductConsole addProductConsole;
		
	public ProductMenuConsole(
			AddProductConsole addProductConsole,
			EditProductConsole editProductConsole,
			DeleteProductConsole deleteProductConsole,
			ProductReportConsole productReportConsole 
			) {
		super();
		this.productReportConsole = productReportConsole;
		this.editProductConsole = editProductConsole;
		this.deleteProductConsole = deleteProductConsole;
		this.addProductConsole = addProductConsole;
	}



	public void show() {
		MyConsole console = MyConsole.getInstance();
		String opcionMenuPrincipal = "";
		
		do{
			System.out.println(" ");
			System.out.println("====================");
			System.out.println("Menú de opciones");
			System.out.println("1. Ingreso de Productos");
			System.out.println("2. Edicion de Productos");
			System.out.println("3. Eliminacion de Productos");
			System.out.println("4. Reporte de Productos");
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
					addProductConsole.show();
					break;
				
				case "2" :
					editProductConsole.show();
					break;
				case "3" :
					deleteProductConsole.show();
					break;
				case "4" :
					productReportConsole.show();
					break;
				case "5" :
					System.exit(0);
					break;
				
			}
			
		}while(opcionMenuPrincipal.equals("5") == false);
	}
}
