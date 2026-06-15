package modelo.lore;

import java.util.Map;

/**
 * Contiene la información narrativa (lore) asociada a cada personaje.
 */
public class LorePersonaje {

	private final String nombre;
	private final String descripcion;

	/**
	 * Constructor del lore de un personaje.
	 */
	public LorePersonaje(String nombre, String descripcion) {
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
	 * Mapa estático que asocia el nombre del personaje con su lore.
	 */
	private static final Map<String, LorePersonaje> LORE = Map.of("Estigia", new LorePersonaje("Estigia, la Ninfa",
			"Representante de la raza feérica. Diplomática que viste con ropas que se asemejan a cascadas de agua dulce. "
					+ "Su castaño cabello ondulado, del que se asoman dos orejas con forma de aleta, "
					+ "se presenta siempre húmeda. Se la considera calmada y solitaria, aunque algunos dirían que puede llegar a tener un aire manipulador."),

			"Etharel",
			new LorePersonaje("Etharel, el elfo de los bosques",
					"Noble aristócrata de la corte. Presenta facciones perfectas, orejas puntiagudas y pelo rubio lacio. "
							+ "Sus vestiduras de satén son verdes con costuras doradas que simulan hojas de vid. "
							+ "Es un político frío y un experto en inhabilitar competidores."),

			"Dis",
			new LorePersonaje("Dis, la Enana",
					"Maestra de la forja e ingeniera del castillo. Viste un delantal de cuero lleno de herramientas. "
							+ "Su pelo dorado está trenzado al estilo enano. "
							+ "Es ruda, directa y propensa a estallidos de furia."),

			"Erion",
			new LorePersonaje("Erion, el Mago", "Consejero del castillo. Hechicero con túnica gris con runas. "
					+ "Intelectual, calculador y orgulloso. " + "Se rumorea que planea un experimento prohibido."),

			"Lucette",
			new LorePersonaje("Lucette, el Hada madrina",
					"Ama de llaves del castillo. Pelo rojizo y alas traslúcidas. "
							+ "Carácter burlón e impredecible, con impulsividad agresiva ocasional."),

			"Alastar",
			new LorePersonaje("Alastar, el Cambiaformas",
					"Maestre de ceremonias. Cabello pelirrojo y túnicas púrpuras. "
							+ "Carismático, embaucador y manipulador social."));

	/**
	 * Devuelve el lore asociado a un personaje por su nombre.
	 */
	public static LorePersonaje get(String nombre) {
		return LORE.get(nombre);
	}
}