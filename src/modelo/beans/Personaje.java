package modelo.beans;

public class Personaje {

	// Atributos
	private String idPersonaje;
	private String nombre;
	private String descripcion;

	// Constructores
	public Personaje() {
	}

	public Personaje(String idPersonaje, String nombre, String descripcion) {
		this.idPersonaje = idPersonaje;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	// Getters y setters
	public String getIdPersonaje() {
		return idPersonaje;
	}

	public void setIdPersonaje(String idPersonaje) {
		this.idPersonaje = idPersonaje;
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
		return "Personaje \nIdPersonaje: " + idPersonaje + "\nNombre del personaje: " + nombre
				+ "\nDescripción del personaje: " + descripcion;
	}

}