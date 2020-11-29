package modelo;

public class Vuelo {
	
	private String codigo, salida, destino;
	private String fecha;
	private String hora;
	private int plazas, plazasLibres;
	
	public Vuelo(String codigo, String salida, String destino, String fecha, String hora, int plazas, int plazasLibres) {
		this.codigo = codigo;
		this.salida = salida;
		this.destino = destino;
		this.fecha = fecha;
		this.hora = hora;
		this.plazas = plazas;
		this.plazasLibres = plazasLibres;
	}
	
	public Vuelo(String codigo, String salida, String destino, String fecha, String hora, String plazas, String plazasLibres) {
		this.codigo = codigo;
		this.salida = salida;
		this.destino = destino;
		this.fecha = fecha;
		this.hora = hora;
		this.plazas = Integer.parseInt(plazas);
		this.plazasLibres = Integer.parseInt(plazasLibres);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getSalida() {
		return salida;
	}

	public void setSalida(String salida) {
		this.salida = salida;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	public int getPlazasLibres() {
		return plazasLibres;
	}

	public void setPlazasLibres(int plazasLibres) {
		this.plazasLibres = plazasLibres;
	}

	@Override
	public String toString() {
		return "Vuelo [codigo=" + codigo + ", salida=" + salida + ", destino=" + destino + ", fecha=" + fecha
				+ ", hora=" + hora + ", plazas=" + plazas + ", plazasLibres=" + plazasLibres + "]";
	}
	
	

}
