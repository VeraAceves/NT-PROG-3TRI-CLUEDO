package modelo.beans;

public class Escenario {

	// Atributos
	private String idEscenario;
	private String nombre;
	private String descripcion;

	// Constructores
	public Escenario() {
	}

	public Escenario(String idEscenario, String nombre, String descripcion) {
		this.idEscenario = idEscenario;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	// Getters y setters
	public String getIdEscenario() {
		return idEscenario;
	}

	public void setIdEscenario(String idEscenario) {
		this.idEscenario = idEscenario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	// toString
	@Override
	public String toString() {
		return "Escenario \nIdEscenario: " + idEscenario + "\nNombre del escenario: " + nombre
				+ "\nDescripción del escenario: " + descripcion;
	}

}