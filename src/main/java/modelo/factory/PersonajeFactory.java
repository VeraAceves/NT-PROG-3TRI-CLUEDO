package modelo.factory;

import java.util.List;
import modelo.beans.Personaje;

public class PersonajeFactory {

	public static List<Personaje> crear() {
		return List.of(new Personaje("P_MAGO", "Erion", ""), new Personaje("P_ELFO", "Etharel", ""),
				new Personaje("P_NINFA", "Estigia", ""), new Personaje("P_HADA", "Lucette", ""),
				new Personaje("P_ENANA", "Dis", ""), new Personaje("P_CAMBIAFORMAS", "Alastar", ""));
	}
}