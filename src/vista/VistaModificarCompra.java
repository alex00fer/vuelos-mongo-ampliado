package vista;

import controlador.InputController;
import modelo.CRUDVentas;

public class VistaModificarCompra  implements Vista {

	CRUDVentas crud;

	public VistaModificarCompra(CRUDVentas crud) {
		this.crud = crud;
	}

	@Override
	public void mostrar() {

		try {
			System.out.println("INTRODUCE LOS DATOS DE LA COMPRA A MODIFICAR:");
			String codVuelo = InputController.readString("  Código de vuelo", 5, 5);
			String codVenta = InputController.readString("  Código de venta", 1, 10);
			String dni = InputController.readString("  DNI del viajero", 1, 20);
			
			System.out.println("INTRODUCE LOS NUEVOS DATOS:");
			String mn = InputController.readString("  Nombre");
			String ma = InputController.readString("  Apellido");
			String md = InputController.readString("  DNI");
			String mp = InputController.readString("  DNI Pagador");
			String mt = InputController.readString("  Tarjeta");
			
			if (crud.modificarVenta(codVuelo, codVenta, dni, mn, ma, md, mp, mt))
				System.out.println(String.format("Datos de compra modificados para el vuelo %s con código de venta %s", codVuelo, codVenta));
			else
				System.out.println("No se encontro ninguna compra con esos datos");
		} catch (Exception e) {
			System.out.println("Error. No se encontro ninguna compra con esos datos");
			System.err.println(e.getMessage());
		}

	}
}
