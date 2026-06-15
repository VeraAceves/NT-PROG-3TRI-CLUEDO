package modelo;

import Persistencia.*;
import modelo.beans.*;
import modelo.enums.*;
import modelo.factory.*;

/**
 * Clase principal de lógica del juego. Gestiona la partida actual, la
 * interacción con el nivel, las acciones del jugador (interrogatorios,
 * acusaciones y pistas) y la persistencia de partidas.
 */
public class Juego {

	private Partida partidaActual;
	private NivelDAO nivelDAO;
	private PartidaDAO partidaDAO = new PartidaDAO();

	/**
	 * Constructor por defecto. Inicializa el acceso a datos de niveles.
	 */
	public Juego() {
		this.nivelDAO = new NivelDAO();
	}

	/**
	 * Constructor con inyección de dependencia para NivelDAO.
	 *
	 * @param nivelDAO DAO de niveles
	 */
	public Juego(NivelDAO nivelDAO) {
		this.nivelDAO = nivelDAO;
	}

	/**
	 * Devuelve la partida actualmente en curso.
	 *
	 * @return partida actual
	 */
	public Partida getPartidaActual() {
		return partidaActual;
	}

	/**
	 * Establece la partida actual.
	 *
	 * @param partidaActual partida a asignar
	 */
	public void setPartidaActual(Partida partidaActual) {
		this.partidaActual = partidaActual;
	}

	/**
	 * Devuelve el DAO de niveles.
	 *
	 * @return nivelDAO
	 */
	public NivelDAO getNivelDAO() {
		return nivelDAO;
	}

	/**
	 * Establece el DAO de niveles.
	 *
	 * @param nivelDAO nuevo DAO
	 */
	public void setNivelDAO(NivelDAO nivelDAO) {
		this.nivelDAO = nivelDAO;
	}

	/**
	 * Inicializa un nivel generando sus personajes, armas y escenarios.
	 *
	 * @param nivel nivel a inicializar
	 */
	public void inicializarNivel(Nivel nivel) {
		nivel.setPersonajes(PersonajeFactory.crear());
		nivel.setArmas(ArmaFactory.crear());
		nivel.setEscenarios(EscenarioFactory.crear());
	}

	/**
	 * Inicia una nueva partida con un jugador y un nivel.
	 *
	 * @param jugador jugador que inicia la partida
	 * @param nivel   nivel seleccionado
	 */
	public void iniciarNuevaPartida(Jugador jugador, Nivel nivel) {

		if (nivel == null) {
			throw new IllegalArgumentException("El nivel no puede ser null");
		}

		if (jugador == null) {
			throw new IllegalArgumentException("El jugador no puede ser null");
		}

		Partida nuevaPartida = new Partida(jugador, nivel);
		nuevaPartida.iniciarPartida();
		partidaActual = nuevaPartida;
	}

	/**
	 * Guarda la partida actual en la base de datos.
	 */
	public void guardarPartidaActual() {

		if (partidaActual == null) {
			throw new IllegalStateException("No hay partida para guardar");
		}

		partidaDAO.guardarPartida(partidaActual);
	}

	/**
	 * Solicita una pista de la partida actual.
	 *
	 * @param indice índice de la pista
	 * @return texto de la pista
	 */
	public String pedirPista(int indice) {

		if (!hayPartidaActiva()) {
			throw new IllegalStateException("No hay partida activa");
		}

		return partidaActual.solicitarPista(indice);
	}

	/**
	 * Realiza un interrogatorio con una hipótesis.
	 *
	 * @param p personaje sospechoso
	 * @param a arma seleccionada
	 * @param e escenario seleccionado
	 * @return true si la hipótesis es correcta
	 */
	public boolean realizarInterrogatorio(Personaje p, Arma a, Escenario e) {
		validarParametros(p, a, e);
		return getPartidaActiva().realizarInterrogatorio(p, a, e);
	}

	/**
	 * Realiza una acusación definitiva.
	 *
	 * @param p personaje acusado
	 * @param a arma acusada
	 * @param e escenario acusado
	 */
	public void realizarAcusacion(Personaje p, Arma a, Escenario e) {
		validarParametros(p, a, e);
		getPartidaActiva().realizarAcusacion(p, a, e);
	}

	/**
	 * Devuelve la descripción del nivel actual.
	 *
	 * @return descripción del nivel
	 */
	public String obtenerDescripcionNivel() {

		if (partidaActual == null || partidaActual.getNivel() == null) {
			throw new IllegalStateException("No hay partida o nivel activo");
		}

		return partidaActual.getNivel().getDescripcion();
	}

	/**
	 * Indica si existe una partida activa en curso.
	 *
	 * @return true si hay partida activa
	 */
	public boolean hayPartidaActiva() {
		return partidaActual != null && partidaActual.getEstado() == EstadoPartida.EN_CURSO;
	}

	/**
	 * Devuelve la puntuación actual de la partida.
	 *
	 * @return puntos actuales o 0 si no hay partida
	 */
	public int getPuntuacionActual() {
		if (partidaActual == null) {
			return 0;
		}
		return partidaActual.getPuntosActuales();
	}

	/**
	 * Devuelve la ronda actual de la partida.
	 *
	 * @return ronda actual o 0 si no hay partida
	 */
	public int getRondaActual() {
		if (partidaActual == null) {
			return 0;
		}
		return partidaActual.getRondaActual();
	}

	/**
	 * Devuelve las pistas restantes en la partida.
	 *
	 * @return pistas restantes o 0 si no hay partida
	 */
	public int getPistasRestantes() {
		if (partidaActual == null) {
			return 0;
		}
		return partidaActual.getPistasRestantes();
	}

	/**
	 * Devuelve el resultado de la partida.
	 *
	 * @return resultado o null si no hay partida
	 */
	public ResultadoPartida getResultadoPartida() {
		if (partidaActual == null) {
			return null;
		}
		return partidaActual.getResultado();
	}

	/**
	 * Devuelve el nivel actual de la partida.
	 *
	 * @return nivel actual o null si no hay partida
	 */
	public Nivel getNivelActual() {
		if (partidaActual == null) {
			return null;
		}
		return partidaActual.getNivel();
	}

	/**
	 * Devuelve el jugador de la partida actual.
	 *
	 * @return jugador o null si no hay partida
	 */
	public Jugador getJugadorActual() {
		if (partidaActual == null) {
			return null;
		}
		return partidaActual.getJugador();
	}

	/**
	 * Obtiene la partida activa validando que no esté finalizada.
	 *
	 * @return partida activa
	 */
	private Partida getPartidaActiva() {

		if (partidaActual == null || partidaActual.getEstado() == EstadoPartida.FINALIZADA) {
			throw new IllegalStateException("No hay una partida activa");
		}

		return partidaActual;
	}

	/**
	 * Valida que los parámetros de una acción no sean nulos.
	 *
	 * @param p personaje
	 * @param a arma
	 * @param e escenario
	 */
	private void validarParametros(Personaje p, Arma a, Escenario e) {

		if (p == null || a == null || e == null) {
			throw new IllegalArgumentException("Personaje, arma y escenario no pueden ser null");
		}
	}
}