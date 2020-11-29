package vista;

import java.util.Collection;
import modelo.Vuelo;

public class TablaVuelos implements Vista {
	
	private Collection<Vuelo> vuelos;

	public TablaVuelos(Collection<Vuelo> vuelos) 
	{
		this.vuelos = vuelos;
	}

	@Override
	public void mostrar() 
	{
		imprimirCabecera();
		imprimirContenido();
	}
	
	private void imprimirCabecera() 
	{
		System.out.println();
		System.out.format("| CÓDIGO | ORIGEN       | DESTINO      | FECHA      | HORA  | PLAZAS  |%n");
		System.out.format("-----------------------------------------------------------------------%n");
	}
	
	private void imprimirContenido()
	{

		String rowFormat = "| %-6s | %-12s | %-12s | %-8s | %-5s | %-7s |%n";
		
		for (Vuelo vuelo : vuelos) {
		    System.out.format(rowFormat, 
		    		vuelo.getCodigo(), vuelo.getSalida(), 
		    		vuelo.getDestino(), vuelo.getFecha(), vuelo.getHora(), 
		    		vuelo.getPlazasLibres() + "/" + vuelo.getPlazas());
		}
	}

}
