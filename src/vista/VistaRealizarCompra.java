package vista;

import controlador.InputController;
import modelo.CRUDVentas;
import modelo.Venta;

public class VistaRealizarCompra implements Vista {

	CRUDVentas crud;

	public VistaRealizarCompra(CRUDVentas crud) {
		super();
		this.crud = crud;
	}

	@Override
	public void mostrar() {

		try {
			System.out.println("INTRODUCE LOS DATOS DE COMPRA:");
			String cod = InputController.readString("  Código de vuelo", 5, 5);

			if (crud.getVuelo(cod).getPlazasLibres() <= 0) {
				System.out.println("El vuelo seleccionado no tiene plazas disponibles");
			} else {
				String codVenta = crud.insertarVenta(cod,
						new Venta(InputController.readString("  Nombre"), InputController.readString("  Apellido"),
								InputController.readString("  DNI"), InputController.readString("  DNI pagador"),
								InputController.readString("  Numero de tarjeta")));
				System.out.println(String.format("Compra realizada para el vuelo %s. Guarde su código de venta: %s",
						cod, codVenta));
			}
		} catch (Exception e) {
			System.out.println("La compra no se pudo completar, posiblemente el código es inválido");
			System.err.println(e.getMessage());
		}

	}

}
