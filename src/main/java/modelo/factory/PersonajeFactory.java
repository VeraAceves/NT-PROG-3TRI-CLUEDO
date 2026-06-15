package modelo.factory;

import java.util.List;
import modelo.beans.Personaje;

/**
 * Fábrica de personajes del juego. Genera la lista fija de personajes
 * disponibles en cada nivel.
 */
public class PersonajeFactory {

	/**
	 * Crea y devuelve la lista de personajes del juego.
	 *
	 * @return lista inmutable de personajes
	 */
	public static List<Personaje> crear() {
		return List.of(new Personaje("P_MAGO", "Erion", ""), new Personaje("P_ELFO", "Etharel", ""),
				new Personaje("P_NINFA", "Estigia", ""), new Personaje("P_HADA", "Lucette", ""),
				new Personaje("P_ENANA", "Dis", ""), new Personaje("P_CAMBIAFORMAS", "Alastar", ""));
	}
}