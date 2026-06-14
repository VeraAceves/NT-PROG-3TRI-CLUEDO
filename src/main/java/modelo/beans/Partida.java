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
	private static final int PENALIZACION_ERROR = 10;
	private static final int PENALIZACION_PISTA = 10;

	// Constructores
	public Partida() {
	}

	public Partida(String idPartida, Jugador jugador, Nivel nivel) {
		this.idPartida = idPartida;
		this.jugador = jugador;
		this.nivel = nivel;
		this.rondaActual = 1;
		this.puntosActuales = nivel.getDificultad().getPuntosIniciales();
		this.pistasRestantes = nivel.getNumeroPistas();
		this.estado = EstadoPartida.EN_CURSO;
		this.resultado = null;
	}

	public Partida(Jugador jugador, Nivel nivel) {
		this.jugador = jugador;
		this.nivel = nivel;
		this.rondaActual = 1;
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

	public static int getPenalizacionError() {
		return PENALIZACION_ERROR;
	}

	public static int getPenalizacionPista() {
		return PENALIZACION_PISTA;
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
		resetearAciertos();
	}

	public boolean realizarInterrogatorio(Personaje personaje, Arma arma, Escenario escenario) {

		if (estado == EstadoPartida.FINALIZADA) {
			return false;
		}

		boolean acierto = comprobarSolucion(personaje, arma, escenario);

		rondaActual++;

		if (!acierto) {
			restarPuntos(PENALIZACION_ERROR);
		}

		if (puntosActuales <= 0 || rondaActual >= nivel.getDificultad().getNumeroRondas()) {

			resultado = ResultadoPartida.DERROTA;
			finalizarPartida();
		}

		return acierto;
	}

	public boolean realizarAcusacion(Personaje personaje, Arma arma, Escenario escenario) {

		if (estado == EstadoPartida.FINALIZADA) {
			return false;
		}

		boolean acierto = comprobarSolucion(personaje, arma, escenario);

		resultado = acierto ? ResultadoPartida.VICTORIA : ResultadoPartida.DERROTA;

		finalizarPartida();

		return acierto;
	}

	public boolean comprobarSolucion(Personaje p, Arma a, Escenario e) {
		return nivel.personajeCorrecto(p) && nivel.armaCorrecta(a) && nivel.escenarioCorrecto(e);
	}

	public String solicitarPista() {

		if (pistasRestantes <= 0 || estado == EstadoPartida.FINALIZADA) {
			return null;
		}

		String pista = nivel.obtenerSiguientePista();

		pistasRestantes--;
		restarPuntos(PENALIZACION_PISTA);

		return pista;
	}

	public void restarPuntos(int puntos) {
		this.puntosActuales = Math.max(0, this.puntosActuales - puntos);
	}

	private void resetearAciertos() {
		this.ultimoAcertoPersonaje = false;
		this.ultimoAcertoArma = false;
		this.ultimoAcertoEscenario = false;
	}

	public void finalizarPartida() {
		this.estado = EstadoPartida.FINALIZADA;
	}
}