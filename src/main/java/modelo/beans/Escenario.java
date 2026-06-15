package modelo.beans;

/**
 * Representa un escenario dentro del juego. Contiene información básica como su
 * identificador, nombre y descripción.
 */
public class Escenario {

	// Atributos
	private String idEscenario;
	private String nombre;
	private String descripcion;

	// Constructores

	/**
	 * Constructor vacío.
	 */
	public Escenario() {
	}

	/**
	 * Constructor con todos los atributos del escenario.
	 *
	 * @param idEscenario identificador del escenario
	 * @param nombre      nombre del escenario
	 * @param descripcion descripción del escenario
	 */
	public Escenario(String idEscenario, String nombre, String descripcion) {
		this.idEscenario = idEscenario;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	// Getters y setters

	/**
	 * Devuelve el identificador del escenario.
	 *
	 * @return id del escenario
	 */
	public String getIdEscenario() {
		return idEscenario;
	}

	/**
	 * Establece el identificador del escenario.
	 *
	 * @param idEscenario nuevo id
	 */
	public void setIdEscenario(String idEscenario) {
		this.idEscenario = idEscenario;
	}

	/**
	 * Devuelve el nombre del escenario.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del escenario.
	 *
	 * @param nombre nuevo nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve la descripción del escenario.
	 *
	 * @return descripción
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del escenario.
	 *
	 * @param descripcion nueva descripción
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Devuelve una representación en texto del escenario.
	 *
	 * @return información completa del escenario
	 */
	@Override
	public String toString() {
		return "Escenario \nIdEscenario: " + idEscenario + "\nNombre del escenario: " + nombre
				+ "\nDescripción del escenario: " + descripcion;
	}
}