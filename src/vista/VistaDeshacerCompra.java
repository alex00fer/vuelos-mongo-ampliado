package vista;

import controlador.InputController;
import modelo.CRUDVentas;

public class VistaDeshacerCompra implements Vista {

	CRUDVentas crud;

	public VistaDeshacerCompra(CRUDVentas crud) {
		this.crud = crud;
	}

	@Override
	public void mostrar() {

		try {
			System.out.println("INTRODUCE LOS DATOS PARA DESHACER SU COMPRA:");
			String codVuelo = InputController.readString("  Código de vuelo", 5, 5);
			String codVenta = InputController.readString("  Código de venta", 1, 10);
			String dni = InputController.readString("  DNI del viajero", 1, 20);
			if (crud.eliminarVenta(codVuelo, codVenta, dni))
			System.out.println(
					String.format("Compra deshecha para el vuelo %s con código de venta: %s", codVuelo, codVenta));
			else
				System.out.println("No se encontro ninguna compra con esos datos");

		} catch (Exception e) {
			System.out.println("Error. No se encontro ninguna compra con esos datos");
			System.err.println(e.getMessage());
		}

	}
}
