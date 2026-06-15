package modelo.factory;

import java.util.List;
import modelo.beans.Arma;

/**
 * Fábrica de armas del juego. Genera la lista fija de armas disponibles en cada
 * nivel.
 */
public class ArmaFactory {

	/**
	 * Crea y devuelve la lista de armas del juego.
	 *
	 * @return lista inmutable de armas
	 */
	public static List<Arma> crear() {

		return List.of(new Arma("A_ATIZADOR", "Atizador de Hierro Frío", ""),
				new Arma("A_CANDELABRO", "Candelabro de oro élfico", ""),
				new Arma("A_ESENCIA", "Esencia de Loto Negro", ""), new Arma("A_BACULO", "Báculo del Alba", ""),
				new Arma("A_CALIZ", "Cáliz de plata alquímica", ""),
				new Arma("A_ABRECARTAS", "Abrecartas de basilisco", ""));
	}
}