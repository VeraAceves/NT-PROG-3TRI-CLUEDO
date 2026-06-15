package modelo.lore;

/**
 * Contiene la narrativa general del juego.
 */
public class LoreHistoria {

	private static final String TEXTO = "El Castillo de Valenwood, un santuario neutral conocido en todas las tierras como \"La Mesa del Bosque\", "
			+ "es célebre por ser el único lugar donde representantes de numerosas razas fantásticas se reúnen para parlamentar, "
			+ "comerciar y firmar tratados de paz.\n\n"
			+ "Sin embargo, al despuntar el alba comenzó la tragedia. Una serie de asesinatos empezaron a suceder dentro del castillo...\n\n"
			+ "La Dama Beatrice deberá resolver el misterio antes de que ocurra una nueva desgracia.";

	/**
	 * Devuelve la historia principal del juego.
	 */
	public static String get() {
		return TEXTO;
	}
}