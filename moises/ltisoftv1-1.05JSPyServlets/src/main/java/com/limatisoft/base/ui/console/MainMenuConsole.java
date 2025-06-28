package com.limatisoft.base.ui.console;


public class MainMenuConsole {
	private ProductMenuConsole productMenuConsole;
	private ProductTypeMenuConsole productTypeMenuConsole;
	
	public void setProductMenuConsole(ProductMenuConsole productMenuConsole) {
		this.productMenuConsole = productMenuConsole;
	}
	public void setProductTypeMenuConsole(ProductTypeMenuConsole productTypeMenuConsole) {
		this.productTypeMenuConsole = productTypeMenuConsole;
	}
		
	public void show() {
		MyConsole console = MyConsole.getInstance();
		System.out.println("=========================================");
		System.out.println("Sistema de Ventas - 'Comercial Don Marco'");
		System.out.println("=========================================");
		String opcionMenuPrincipal = "";
		
		do{
			System.out.println(" ");
			System.out.println("====================");
			System.out.println("Menú de opciones");
			System.out.println("1. Productos");
			System.out.println("2. Familias de Productos");
			System.out.println("3. Salir del Sistema");
			
			
			boolean contieneError;
			do {
				contieneError = false;
				opcionMenuPrincipal = console.readLine("Elija una opcion: ");
				try {
					int opcionElegida = Integer.parseInt(opcionMenuPrincipal);
					if(!(opcionElegida >= 1 && opcionElegida <= 3)) {
						contieneError = true;
					}
				}catch(NumberFormatException ex) {
					contieneError = true;
				}
			}while(contieneError);
			
			switch(opcionMenuPrincipal) {
				case "1" :
					productMenuConsole.show();
					break;
				case "2" :
					productTypeMenuConsole.show();
					break;
				case "3" :
					System.exit(0);
					break;
				
			}
			
		}while(opcionMenuPrincipal.equals("3") == false);
	}
}
