package modelo.beans;

/**
 * Representa un jugador del juego. Contiene su identificador y su nombre.
 */
public class Jugador {

	// Atributos
	private String idJugador;
	private String nombre;

	// Constructores

	/**
	 * Constructor vacío.
	 */
	public Jugador() {
	}

	/**
	 * Constructor que inicializa únicamente el nombre.
	 *
	 * @param nombre nombre del jugador
	 */
	public Jugador(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Constructor con todos los atributos del jugador.
	 *
	 * @param idJugador identificador del jugador
	 * @param nombre    nombre del jugador
	 */
	public Jugador(String idJugador, String nombre) {
		this.idJugador = idJugador;
		this.nombre = nombre;
	}

	// Getters y setters

	/**
	 * Devuelve el identificador del jugador.
	 *
	 * @return id del jugador
	 */
	public String getIdJugador() {
		return idJugador;
	}

	/**
	 * Establece el identificador del jugador.
	 *
	 * @param idJugador nuevo id
	 */
	public void setIdJugador(String idJugador) {
		this.idJugador = idJugador;
	}

	/**
	 * Devuelve el nombre del jugador.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del jugador.
	 *
	 * @param nombre nuevo nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Devuelve una representación en texto del jugador.
	 *
	 * @return información del jugador
	 */
	@Override
	public String toString() {
		return "Jugador \nIdJugador: " + idJugador + "\nNombre: " + nombre;
	}

	// Métodos propios

	/**
	 * Valida que el nombre del jugador no sea nulo ni vacío.
	 *
	 * @return true si el nombre es válido
	 */
	public boolean nombreValido() {
		return nombre != null && !nombre.trim().isEmpty();
	}
}