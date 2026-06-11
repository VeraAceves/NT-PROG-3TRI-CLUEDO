package modelo;

import Persistencia.*;
import modelo.beans.*;
import modelo.enums.*;

public class Juego {

	// Atributos
	private Partida partidaActual;
	private NivelDAO nivelDAO = new NivelDAO();

	// Constructores
	public Juego() {
	}

	// Getters y setters
	public Partida getPartidaActual() {
		return partidaActual;
	}

	public void setPartidaActual(Partida partidaActual) {
		this.partidaActual = partidaActual;
	}

	// Métodos

	public void iniciarNuevaPartida(Jugador jugador, Nivel nivel) {

		Partida nuevaPartida = new Partida(jugador, nivel);
		nuevaPartida.iniciarPartida();
		partidaActual = nuevaPartida;

	}

	public void iniciarNuevaPartida(Jugador jugador, String idNivel) {

		Nivel nivel = nivelDAO.obtenerNivelPorId(idNivel);

		iniciarNuevaPartida(jugador, nivel);
	}

	public boolean realizarHipotesis(Personaje p, Arma a, Escenario e) {

	    if (partidaActual == null || partidaActual.getEstado() == EstadoPartida.FINALIZADA) {
	        throw new IllegalStateException("No hay una partida activa");
	    }

	    return partidaActual.realizarHipotesis(p, a, e);
	}

	/**
	 * Lanza la acusación definitiva
	 * 
	 * @return true si la acusación es correcta (victoria)
	 */
	public boolean lanzarAcusacionDefinitiva(Personaje p, Arma a, Escenario e) {
		if (partidaActual == null || partidaActual.getEstado() == EstadoPartida.FINALIZADA) {
			throw new IllegalStateException("No hay una partida activa");
		}
		if (p == null || a == null || e == null) {
			throw new IllegalArgumentException("Personaje, arma y escenario no pueden ser null");
		}
		return partidaActual.realizarAcusacion(p, a, e);
	}

	/**
	 * Solicita la siguiente pista
	 * 
	 * @return La pista solicitada
	 */
	public String pedirSiguientePista() {
		if (partidaActual == null || partidaActual.getEstado() == EstadoPartida.FINALIZADA) {
			throw new IllegalStateException("No hay una partida activa");
		}
		try {
			return partidaActual.solicitarPista();
		} catch (IllegalStateException | IndexOutOfBoundsException ex) {
			return ex.getMessage();
		}
	}

	/**
	 * Verifica si hay una partida activa
	 * 
	 * @return true si hay partida en curso
	 */
	public boolean hayPartidaActiva() {
		return partidaActual != null && partidaActual.getEstado() == EstadoPartida.EN_CURSO;
	}

	/**
	 * Obtiene la puntuación actual
	 * 
	 * @return puntos actuales
	 */
	public int getPuntuacionActual() {
		if (partidaActual == null) {
			return 0;
		}
		return partidaActual.getPuntosActuales();
	}

	/**
	 * Obtiene la ronda actual
	 * 
	 * @return ronda actual
	 */
	public int getRondaActual() {
		if (partidaActual == null) {
			return 0;
		}
		return partidaActual.getRondaActual();
	}

	/**
	 * Obtiene las pistas restantes
	 * 
	 * @return número de pistas restantes
	 */
	public int getPistasRestantes() {
		if (partidaActual == null) {
			return 0;
		}
		return partidaActual.getPistasRestantes();
	}

	/**
	 * Obtiene el resultado de la partida (si ha finalizado)
	 * 
	 * @return resultado o null si está en curso
	 */
	public ResultadoPartida getResultadoPartida() {
		if (partidaActual == null) {
			return null;
		}
		return partidaActual.getResultado();
	}

	/**
	 * Obtiene el nivel de la partida actual
	 * 
	 * @return nivel actual
	 */
	public Nivel getNivelActual() {
		if (partidaActual == null) {
			return null;
		}
		return partidaActual.getNivel();
	}

	/**
	 * Obtiene el jugador de la partida actual
	 * 
	 * @return jugador actual
	 */
	public Jugador getJugadorActual() {
		if (partidaActual == null) {
			return null;
		}
		return partidaActual.getJugador();
	}
}