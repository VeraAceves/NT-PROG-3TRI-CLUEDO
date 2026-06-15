package modelo.factory;

import java.util.List;
import modelo.beans.Arma;

public class ArmaFactory {

	public static List<Arma> crear() {

		return List.of(new Arma("A_ATIZADOR", "Atizador de Hierro Frío", ""),
				new Arma("A_CANDELABRO", "Candelabro de oro élfico", ""),
				new Arma("A_ESENCIA", "Esencia de Loto Negro", ""), new Arma("A_BACULO", "Báculo del Alba", ""),
				new Arma("A_CALIZ", "Cáliz de plata alquímica", ""),
				new Arma("A_ABRECARTAS", "Abrecartas de basilisco", ""));
	}
}