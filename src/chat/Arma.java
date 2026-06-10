package modelo.beans;

public class Arma {
	// Atributos
	private String idArma;
	private String nombre;
	private String descripcion;

	// Constructores
	public Arma() {
	}

	public Arma(String idArma, String nombre, String descripcion) {
		this.idArma = idArma;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	// Getters y setters
	public String getIdArma() {
		return idArma;
	}

	public void setIdArma(String idArma) {
		this.idArma = idArma;
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
		return "Arma \nId del arma: " + idArma + "\nNombre del arma: " + nombre + "\nDescripción del arma: "
				+ descripcion;
	}

}
