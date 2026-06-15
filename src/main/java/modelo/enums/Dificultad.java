package modelo.enums;

/**
 * Define la dificultad del nivel y sus parámetros asociados: puntos iniciales,
 * número máximo de rondas y número de pistas disponibles.
 */
public enum Dificultad {

	FACIL(100, 12, 3), DIFICIL(70, 8, 1);

	private final int puntosIniciales;
	private final int numeroRondas;
	private final int numeroPistas;

	/**
	 * Constructor del enum con sus parámetros asociados.
	 */
	private Dificultad(int puntosIniciales, int numeroRondas, int numeroPistas) {
		this.puntosIniciales = puntosIniciales;
		this.numeroRondas = numeroRondas;
		this.numeroPistas = numeroPistas;
	}

	/**
	 * Devuelve los puntos iniciales del nivel.
	 */
	public int getPuntosIniciales() {
		return puntosIniciales;
	}

	/**
	 * Devuelve el número máximo de rondas.
	 */
	public int getNumeroRondas() {
		return numeroRondas;
	}

	/**
	 * Devuelve el número de pistas disponibles.
	 */
	public int getNumeroPistas() {
		return numeroPistas;
	}
}