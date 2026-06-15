package modelo;

import Persistencia.*;
import modelo.beans.*;
import modelo.enums.*;
import modelo.factory.*;

public class Juego {

	private Partida partidaActual;
	private NivelDAO nivelDAO;
	private PartidaDAO partidaDAO = new PartidaDAO();

	public Juego() {
		this.nivelDAO = new NivelDAO();
	}

	public Juego(NivelDAO nivelDAO) {
		this.nivelDAO = nivelDAO;
	}

	public Partida getPartidaActual() {
		return partidaActual;
	}

	public void setPartidaActual(Partida partidaActual) {
		this.partidaActual = partidaActual;
	}

	public NivelDAO getNivelDAO() {
		return nivelDAO;
	}

	public void setNivelDAO(NivelDAO nivelDAO) {
		this.nivelDAO = nivelDAO;
	}

	public void inicializarNivel(Nivel nivel) {

		nivel.setPersonajes(PersonajeFactory.crear());
		nivel.setArmas(ArmaFactory.crear());
		nivel.setEscenarios(EscenarioFactory.crear());
	}

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

	public void guardarPartidaActual() {
		if (partidaActual == null) {
			throw new IllegalStateException("No hay partida para guardar");
		}

		partidaDAO.guardarPartida(partidaActual);
	}

	public boolean realizarInterrogatorio(Personaje p, Arma a, Escenario e) {
		validarParametros(p, a, e);
		return getPartidaActiva().realizarInterrogatorio(p, a, e);
	}

	public void realizarAcusacion(Personaje p, Arma a, Escenario e) {
		validarParametros(p, a, e);
		getPartidaActiva().realizarAcusacion(p, a, e);
	}

	public String obtenerDescripcionNivel() {
		if (partidaActual == null || partidaActual.getNivel() == null) {
			throw new IllegalStateException("No hay partida o nivel activo");
		}
		return partidaActual.getNivel().getDescripcion();
	}

	public String pedirSiguientePista() {
		if (!hayPartidaActiva()) {
			throw new IllegalStateException("No hay partida activa");
		}
		return partidaActual.solicitarPista();
	}

	public boolean hayPartidaActiva() {
		return partidaActual != null && partidaActual.getEstado() == EstadoPartida.EN_CURSO;
	}

	public int getPuntuacionActual() {
		if (partidaActual == null) {
			return 0;
		}
		return partidaActual.getPuntosActuales();
	}

	public int getRondaActual() {
		if (partidaActual == null) {
			return 0;
		}
		return partidaActual.getRondaActual();
	}

	public int getPistasRestantes() {
		if (partidaActual == null) {
			return 0;
		}
		return partidaActual.getPistasRestantes();
	}

	public ResultadoPartida getResultadoPartida() {
		if (partidaActual == null) {
			return null;
		}
		return partidaActual.getResultado();
	}

	public Nivel getNivelActual() {
		if (partidaActual == null) {
			return null;
		}
		return partidaActual.getNivel();
	}

	public Jugador getJugadorActual() {
		if (partidaActual == null) {
			return null;
		}
		return partidaActual.getJugador();
	}

	private Partida getPartidaActiva() {
		if (partidaActual == null || partidaActual.getEstado() == EstadoPartida.FINALIZADA) {
			throw new IllegalStateException("No hay una partida activa");
		}
		return partidaActual;
	}

	private void validarParametros(Personaje p, Arma a, Escenario e) {
		if (p == null || a == null || e == null) {
			throw new IllegalArgumentException("Personaje, arma y escenario no pueden ser null");
		}
	}
}