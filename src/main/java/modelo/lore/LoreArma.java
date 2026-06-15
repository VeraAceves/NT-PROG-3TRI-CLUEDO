package modelo.lore;

import java.util.Map;

/**
 * Contiene la información narrativa (lore) asociada a las armas del juego.
 */
public class LoreArma {

	private final String nombre;
	private final String descripcion;

	/**
	 * Constructor del lore de un arma.
	 */
	public LoreArma(String nombre, String descripcion) {
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
	 * Mapa estático que asocia el nombre del arma con su lore.
	 */
	private static final Map<String, LoreArma> LORE = Map.of(

			"Candelabro de oro élfico",
			new LoreArma("Candelabro de oro élfico",
					"Candelabro de tres brazos forjado con el oro de los bosques y decorado con relieves de hojas de parra. "
							+ "Su estructura de metal macizo posee un peso sorprendentemente denso y contundente al sostenerse por la base, "
							+ "y aún se pueden encontrar restos de cera mágica de color verde esmeralda incrustados en su base dorada."),

			"Abrecartas de basilisco",
			new LoreArma("Abrecartas de basilisco",
					"Tallado a partir de las escamas afiladas de un basilisco abisal. Su hoja presenta un oscuro reflejo tornasolado "
							+ "y su filo es tan fino que puede cortar cualquier material sin hacer apenas ruido, convirtiendo esta herramienta "
							+ "de escritorio en un objeto punzante de una precisión quirúrgica."),

			"Atizador de Hierro Frío",
			new LoreArma("Atizador de Hierro Frío",
					"Vara larga de metal oscuro que se encuentra junto a las grandes chimeneas del castillo. A lo largo de su cuerpo "
							+ "se pueden ver diversas runas grabadas para avivar el fuego mágico; una pieza de hierro macizo que termina en un extremo "
							+ "inusualmente aguzado y firme, ideal tanto para remover los troncos como para asestar una estocada limpia y certera."),

			"Esencia de Loto Negro",
			new LoreArma("Esencia de Loto Negro",
					"Vial alargado de porcelana que contiene el extracto de la flor prohibida de los pantanos. En las manos equivocadas, "
							+ "actúa como un compuesto de esencia letal capaz de sumergir a la víctima en un sueño definitivo del que es imposible despertar."),

			"Báculo del Alba",
			new LoreArma("Báculo del Alba",
					"Bastón largo de madera de fresno blanco, coronado con fragmentas de zafiro azulado y anillos planetarios dorados que ayudan a canalizar poder. "
							+ "Su gran longitud y el refuerzo metálico de su pomo inferior le confieren el equilibrio y la fuerza de impacto de una maza ceremonial."),

			"Cáliz de plata alquímica",
			new LoreArma("Cáliz de plata alquímica",
					"Copa real de plata con un interior tratado con metal alquímico. Este revestimiento místico tiene la propiedad de alterar cualquier sustancia vertida en ella "
							+ "según los deseos de su portador, transformando un brindis común en una trampa indetectable y de efectos fulminantes."));

	/**
	 * Devuelve el lore asociado a un arma por su nombre.
	 */
	public static LoreArma get(String nombre) {
		return LORE.get(nombre);
	}
}