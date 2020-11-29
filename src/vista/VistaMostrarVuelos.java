package vista;

import java.util.Collection;
import modelo.CRUDVentas;
import modelo.Vuelo;

public class VistaMostrarVuelos implements Vista {
	
	CRUDVentas crud;
	
	public VistaMostrarVuelos(CRUDVentas crud) {
		this.crud = crud;
	}


	public void mostrar() {
		
		try {
			
			Collection<Vuelo> vuelos = crud.getVuelosDisponibles();
			
			new TablaVuelos(vuelos).mostrar();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
