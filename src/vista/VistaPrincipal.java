package vista;

import modelo.CRUDVentas;
import modelo.MongoCRUD;

public class VistaPrincipal implements Vista {
	
	Vista vistaMostrar, vistaComprar, vistaModificar, vistaBorrar;
	Menu menuPrincipal;
	
	public VistaPrincipal() 
	{
		CRUDVentas crud = new MongoCRUD();
		vistaMostrar = new VistaMostrarVuelos(crud);
		vistaComprar = new VistaRealizarCompra(crud);
		vistaModificar = new VistaModificarCompra(crud);
		vistaBorrar = new VistaDeshacerCompra(crud);
		menuPrincipal = new Menu("Ver vuelos disponibles", "Realizar compra", "Modificar compra", "Deshacer compra", "Salir");
	}
	
	@Override
	public void mostrar() 
	{
		
		boolean exit = false;
		while (!exit) {
			System.out.println();
			menuPrincipal.mostrar();
			int opcion = menuPrincipal.seleccionado();

			switch (opcion) {
			
			case 1:
				vistaMostrar.mostrar();
				break;
				
			case 2:
				vistaComprar.mostrar();
				break;
				
			case 3:
				vistaModificar.mostrar();
				break;
			case 4:
				vistaBorrar.mostrar();
				break;
	
			default:
				exit = true;
				break;
			}
		}
		System.exit(0);
	}
}
