package modelo.lore;

import java.util.Map;

/**
 * Contiene la información narrativa (lore) asociada a los escenarios del juego.
 */
public class LoreEscenario {

	private final String nombre;
	private final String descripcion;

	/**
	 * Constructor del lore de un escenario.
	 */
	public LoreEscenario(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Mapa estático que asocia el nombre del escenario con su lore.
	 */
	private static final Map<String, LoreEscenario> LORE = Map.of(

			"Dormitorio Principal",
			new LoreEscenario("Dormitorio Principal",
					"Estancia de techos altos y paredes cubiertas de tapices heráldicos desgastados. "
							+ "Los aposentos están presididos por una cama con dosel púrpura, sobre una alfombra de musgo mágico. "
							+ "Junto a la chimenea de piedra apagada se encuentra un escritorio de roble con tinteros de cristal."),

			"Despensa Real",
			new LoreEscenario("Despensa Real",
					"Habitación de techos bajos con estantes de madera de roble que sostienen manjares exóticos de múltiples razas. "
							+ "Barriles de hidromiel ocupan una pared entera de la estancia."),

			"Forja de Honor",
			new LoreEscenario("Forja de Honor",
					"Habitación más caliente del castillo por el calor de los hornos. "
							+ "En el centro hay un gran yunque rodeado de brasas. "
							+ "Las paredes exhiben armas y armaduras forjadas por enanos."),

			"Archivo de Runas",
			new LoreEscenario("Archivo de Runas",
					"Despacho circular con estanterías que custodian pergaminos antiguos, mapas y tablillas. "
							+ "Se rumorea que hay pasajes ocultos tras librerías falsas con tomos prohibidos."),

			"Salón del Trono",
			new LoreEscenario("Salón del Trono",
					"Estancia monumental presidida por un trono de roca esmeralda. "
							+ "Grandes columnas de mármol sostienen el techo iluminado por vidrieras de amatista."),

			"Invernadero de Mandrágoras",
			new LoreEscenario("Invernadero de Mandrágoras",
					"Invernadero cubierto de niebla húmeda ideal para botánica mágica. "
							+ "El suelo está lleno de macetas exóticas y jaulas con raíces vivas."));

	/**
	 * Devuelve el lore asociado a un escenario por su nombre.
	 */
	public static LoreEscenario get(String nombre) {
		return LORE.get(nombre);
	}
}