package modelo;

public class Venta {

	private int asiento;
	private String nombre, apellido, dni, dniPagador, tarjeta, codigoVenta;

	public Venta(int asiento, String nombre, String apellido, String dni, String dniPagador, String tarjeta,
			String codigoVenta) {
		this.asiento = asiento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.dniPagador = dniPagador;
		this.tarjeta = tarjeta;
		this.codigoVenta = codigoVenta;
	}
	
	

	public Venta(String nombre, String apellido, String dni, String dniPagador, String tarjeta) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.dniPagador = dniPagador;
		this.tarjeta = tarjeta;
	}



	public int getAsiento() {
		return asiento;
	}

	public void setAsiento(int asiento) {
		this.asiento = asiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getDniPagador() {
		return dniPagador;
	}

	public void setDniPagador(String dniPagador) {
		this.dniPagador = dniPagador;
	}

	public String getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(String tarjeta) {
		this.tarjeta = tarjeta;
	}

	public String getCodigoVenta() {
		return codigoVenta;
	}

	public void setCodigoVenta(String codigoVenta) {
		this.codigoVenta = codigoVenta;
	}

}
