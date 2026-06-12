package modelo.beans;

import modelo.enums.EstadoPartida;
import modelo.enums.ResultadoPartida;

public class Partida {

	// Atributos
	private String idPartida;
	private Jugador jugador;
	private Nivel nivel;
	private int rondaActual;
	private int puntosActuales;
	private int pistasRestantes;
	private EstadoPartida estado;
	private ResultadoPartida resultado;
	private boolean ultimoAcertoPersonaje;
	private boolean ultimoAcertoArma;
	private boolean ultimoAcertoEscenario;

	// Constructores
	public Partida() {
	}

	public Partida(String idPartida, Jugador jugador, Nivel nivel) {
		this.idPartida = idPartida;
		this.jugador = jugador;
		this.nivel = nivel;
		this.rondaActual = 0;
		this.puntosActuales = nivel.getDificultad().getPuntosIniciales();
		this.pistasRestantes = nivel.getNumeroPistas();
		this.estado = EstadoPartida.EN_CURSO;
		this.resultado = null;
	}

	public Partida(Jugador jugador, Nivel nivel) {
		this.jugador = jugador;
		this.nivel = nivel;
		this.rondaActual = 0;
		this.puntosActuales = nivel.getDificultad().getPuntosIniciales();
		this.pistasRestantes = nivel.getNumeroPistas();
		this.estado = EstadoPartida.EN_CURSO;
		this.resultado = null;
	}

	// Getters y setters
	public String getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public int getRondaActual() {
		return rondaActual;
	}

	public void setRondaActual(int rondaActual) {
		this.rondaActual = rondaActual;
	}

	public int getPuntosActuales() {
		return puntosActuales;
	}

	public void setPuntosActuales(int puntosActuales) {
		this.puntosActuales = puntosActuales;
	}

	public int getPistasRestantes() {
		return pistasRestantes;
	}

	public void setPistasRestantes(int pistasRestantes) {
		this.pistasRestantes = pistasRestantes;
	}

	public EstadoPartida getEstado() {
		return estado;
	}

	public void setEstado(EstadoPartida estado) {
		this.estado = estado;
	}

	public ResultadoPartida getResultado() {
		return resultado;
	}

	public void setResultado(ResultadoPartida resultado) {
		this.resultado = resultado;
	}

	public boolean isUltimoAcertoPersonaje() {
		return ultimoAcertoPersonaje;
	}

	public void setUltimoAcertoPersonaje(boolean ultimoAcertoPersonaje) {
		this.ultimoAcertoPersonaje = ultimoAcertoPersonaje;
	}

	public boolean isUltimoAcertoArma() {
		return ultimoAcertoArma;
	}

	public void setUltimoAcertoArma(boolean ultimoAcertoArma) {
		this.ultimoAcertoArma = ultimoAcertoArma;
	}

	public boolean isUltimoAcertoEscenario() {
		return ultimoAcertoEscenario;
	}

	public void setUltimoAcertoEscenario(boolean ultimoAcertoEscenario) {
		this.ultimoAcertoEscenario = ultimoAcertoEscenario;
	}

	// toString
	@Override
	public String toString() {
		return "Partida \nId: " + idPartida + "\nJugador: " + jugador + "\nNivel: " + nivel + "\nRonda actual: "
				+ rondaActual + "\nPuntos actuales: " + puntosActuales + "\nPistas restantes: " + pistasRestantes
				+ "\nEstado: " + estado + "\nResultado: " + resultado;
	}

	// Métodos propios
	public void iniciarPartida() {
		this.rondaActual = 1;
		this.estado = EstadoPartida.EN_CURSO;
		this.puntosActuales = nivel.getDificultad().getPuntosIniciales();
		this.pistasRestantes = nivel.getNumeroPistas();
		this.resultado = null;
	}

	public boolean realizarHipotesis(Personaje personaje, Arma arma, Escenario escenario) {

		ultimoAcertoPersonaje = nivel.personajeCorrecto(personaje);
		ultimoAcertoArma = nivel.armaCorrecta(arma);
		ultimoAcertoEscenario = nivel.escenarioCorrecto(escenario);

		boolean acierto = ultimoAcertoPersonaje && ultimoAcertoArma && ultimoAcertoEscenario;

		if (!acierto) {
			restarPuntos(10);
			rondaActual++;

			if (puntosActuales <= 0) {
				resultado = ResultadoPartida.DERROTA;
				finalizarPartida();
			}
		}

		return acierto;
	}

	public boolean realizarAcusacion(Personaje personaje, Arma arma, Escenario escenario) {

		boolean acierto = nivel.personajeCorrecto(personaje) && nivel.armaCorrecta(arma)
				&& nivel.escenarioCorrecto(escenario);

		if (acierto) {
			resultado = ResultadoPartida.VICTORIA;
		} else {
			resultado = ResultadoPartida.DERROTA;
		}

		finalizarPartida();
		return acierto;
	}

	public String solicitarPista() {

		String pista = nivel.obtenerSiguientePista();

		pistasRestantes--;
		restarPuntos(10);

		return pista;
	}

	public void restarPuntos(int puntos) {
		this.puntosActuales -= puntos;
	}

	public void finalizarPartida() {
		this.estado = EstadoPartida.FINALIZADA;
	}
}