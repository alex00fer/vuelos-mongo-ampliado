package modelo;

import java.util.Collection;

public interface CRUDVentas {
	public Collection<Vuelo> getVuelosDisponibles() throws Exception;
	public Venta obtenerVenta(String codigoVuelo, String codigoVenta) throws Exception;
	public String insertarVenta(String codigo, Venta venta) throws Exception;
	public boolean eliminarVenta(String codigoVuelo, String codigoVenta, String dni) throws Exception;
	public Vuelo getVuelo(String codigo) throws Exception;
	public boolean modificarVenta(String codVuelo, String codVenta, String dni, String mn, String ma, String md, String mp,
			String mt) throws Exception;
}
