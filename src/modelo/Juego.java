package modelo;

import java.util.ArrayList;
import java.util.List;
import modelo.beans.Partida;
import modelo.beans.Jugador;
import modelo.beans.Nivel;
import modelo.beans.Personaje;
import modelo.beans.Arma;
import modelo.beans.Escenario;
import modelo.enums.Dificultad;
import modelo.enums.EstadoPartida;
import modelo.enums.ResultadoPartida;

public class Juego {

	// Atributos
	private Partida partidaActual;

	// Constructores
	public Juego() {
	}

	// Métodos
	public void iniciarNuevaPartida(Jugador jugador, Nivel nivel) {
		this.partidaActual = new Partida("PART-" + System.currentTimeMillis(), jugador, nivel);
		this.partidaActual.iniciarPartida();
	}

	public boolean proponerHipotesis(Personaje p, Arma a, Escenario e) {
		if (partidaActual == null || partidaActual.getEstado() == EstadoPartida.FINALIZADA) {
			throw new IllegalStateException();
		}
		return partidaActual.realizarHipotesis(p, a, e);
	}

	public boolean lanzarAcusacionDefinitiva(Personaje p, Arma a, Escenario e) {
		if (partidaActual == null || partidaActual.getEstado() == EstadoPartida.FINALIZADA) {
			throw new IllegalStateException();
		}
		return partidaActual.realizarAcusacion(p, a, e);
	}

	public String pedirSiguientePista() {
		if (partidaActual == null || partidaActual.getEstado() == EstadoPartida.FINALIZADA) {
			throw new IllegalStateException();
		}
		try {
			return partidaActual.solicitarPista();
		} catch (IllegalStateException | IndexOutOfBoundsException ex) {
			return ex.getMessage();
		}
	}

	public Partida getPartidaActual() {
		return partidaActual;
	}

	public void setPartidaActual(Partida partidaActual) {
		this.partidaActual = partidaActual;
	}
}