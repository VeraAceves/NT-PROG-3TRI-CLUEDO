package modelo.beans;

/**
 * Representa un arma dentro del juego. Contiene información básica como su
 * identificador, nombre y descripción.
 */
public class Arma {

	// Atributos
	private String idArma;
	private String nombre;
	private String descripcion;

	// Constructores

	/**
	 * Constructor vacío.
	 */
	public Arma() {
	}

	/**
	 * Constructor con todos los atributos del arma.
	 *
	 * @param idArma      identificador del arma
	 * @param nombre      nombre del arma
	 * @param descripcion descripción del arma
	 */
	public Arma(String idArma, String nombre, String descripcion) {
		this.idArma = idArma;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	// Getters y setters

	/**
	 * Devuelve el identificador del arma.
	 *
	 * @return id del arma
	 */
	public String getIdArma() {
		return idArma;
	}

	/**
	 * Establece el identificador del arma.
	 *
	 * @param idArma nuevo id
	 */
	public void setIdArma(String idArma) {
		this.idArma = idArma;
	}

	/**
	 * Devuelve el nombre del arma.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del arma.
	 *
	 * @param nombre nuevo nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la descripción del arma.
	 *
	 * @return descripción
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del arma.
	 *
	 * @param descripcion nueva descripción
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve una representación en texto del arma.
	 *
	 * @return información completa del arma
	 */
	@Override
	public String toString() {
		return "Arma \nId del arma: " + idArma + "\nNombre del arma: " + nombre + "\nDescripción del arma: "
				+ descripcion;
	}
}