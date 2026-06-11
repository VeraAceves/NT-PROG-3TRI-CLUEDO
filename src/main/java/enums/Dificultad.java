package enums;

public enum Dificultad {
	
		FACIL(100, 12, 3), DIFICIL(70, 8, 1);

		private final int puntosIniciales;
		private final int numeroRondas;
		private final int numeroPistas;

		private Dificultad(int puntosIniciales, int numeroRondas, int numeroPistas) {
			this.puntosIniciales = puntosIniciales;
			this.numeroRondas = numeroRondas;
			this.numeroPistas = numeroPistas;
		}

		public int getPuntosIniciales() {
			return puntosIniciales;
		}

		public int getNumeroRondas() {
			return numeroRondas;
		}

		public int getNumeroPistas() {
			return numeroPistas;
		}

	}

