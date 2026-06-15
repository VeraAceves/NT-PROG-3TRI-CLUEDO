package modelo.beans;

/**
 * Representa un personaje dentro del juego. Contiene información básica como su
 * identificador, nombre y descripción.
 */
public class Personaje {

	// Atributos
	private String idPersonaje;
	private String nombre;
	private String descripcion;

	// Constructores

	/**
	 * Constructor vacío.
	 */
	public Personaje() {
	}

	/**
	 * Constructor con todos los atributos del personaje.
	 *
	 * @param idPersonaje identificador del personaje
	 * @param nombre      nombre del personaje
	 * @param descripcion descripción del personaje
	 */
	public Personaje(String idPersonaje, String nombre, String descripcion) {
		this.idPersonaje = idPersonaje;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	// Getters y setters

	/**
	 * Devuelve el identificador del personaje.
	 *
	 * @return id del personaje
	 */
	public String getIdPersonaje() {
		return idPersonaje;
	}

	/**
	 * Establece el identificador del personaje.
	 *
	 * @param idPersonaje nuevo id
	 */
	public void setIdPersonaje(String idPersonaje) {
		this.idPersonaje = idPersonaje;
	}

	/**
	 * Devuelve el nombre del personaje.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del personaje.
	 *
	 * @param nombre nuevo nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la descripción del personaje.
	 *
	 * @return descripción
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del personaje.
	 *
	 * @param descripcion nueva descripción
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve una representación en texto del personaje.
	 *
	 * @return información completa del personaje
	 */
	@Override
	public String toString() {
		return "Personaje \nIdPersonaje: " + idPersonaje + "\nNombre del personaje: " + nombre
				+ "\nDescripción del personaje: " + descripcion;
	}
}