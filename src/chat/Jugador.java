package modelo.beans;

public class Jugador {

	// Atributos
	private String idJugador;
	private String nombre;

	// Constructores
	public Jugador() {
	}

	public Jugador(String idJugador, String nombre) {
		this.idJugador = idJugador;
		this.nombre = nombre;
	}

	// Getters y setters
	public String getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(String idJugador) {
		this.idJugador = idJugador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	// toString
	@Override
	public String toString() {
		return "Jugador \nIdJugador: " + idJugador + "\nNombre: " + nombre;
	}
	
	// Métodos propios
	public boolean nombreValido() {
		return nombre != null && !nombre.trim().isEmpty();
	}



}