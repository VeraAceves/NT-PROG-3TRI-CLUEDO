package Persistencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

import modelo.beans.*;
import modelo.enums.*;

/**
 * Carga la solución del caso, las pistas y la historia introductoria desde la
 * colección "Nivel".
 */
public class NivelDAO {

	private final MongoCollection<Document> coleccion;

	/**
	 * Constructor que enlaza con la colección "Nivel" de la base de datos.
	 */
	public NivelDAO() {
		MongoDatabase db = Conexion.getDatabase();
		this.coleccion = db.getCollection("Nivel");
	}

	/**
	 * Rescata toda la configuración de un nivel (incluyendo los subdocumentos de la
	 * solución) y reconstruye el objeto Java complejo. * @param idNivel El código
	 * del nivel a cargar (ej. "NIVEL_01").
	 * 
	 * @return El objeto Nivel preparado para iniciar una partida, o null si no se
	 *         encuentra.
	 */
	public Nivel obtenerNivelPorId(String idNivel) {

		Document doc = coleccion.find(Filters.eq("idNivel", idNivel)).first();

		if (doc == null) {
			return null;
		}

		Document docAsesino = doc.get("asesino", Document.class);
		Document docEscenario = doc.get("escenarioCrimen", Document.class);
		Document docArma = doc.get("armaCrimen", Document.class);

		Personaje asesino = (docAsesino != null)
				? new Personaje(docAsesino.getString("idPersonaje"), docAsesino.getString("nombre"),
						docAsesino.getString("descripcion"))
				: null;

		Escenario escenario = (docEscenario != null)
				? new Escenario(docEscenario.getString("idEscenario"), docEscenario.getString("nombre"),
						docEscenario.getString("descripcion"))
				: null;

		Arma arma = (docArma != null)
				? new Arma(docArma.getString("idArma"), docArma.getString("nombre"), docArma.getString("descripcion"))
				: null;

		Dificultad dificultad = null;
		String dif = doc.getString("dificultad");
		if (dif != null) {
			dificultad = Dificultad.valueOf(dif);
		}

		List<String> pistas = doc.getList("pistas", String.class);
		if (pistas == null)
			pistas = new ArrayList<>();

		List<Personaje> personajes = new ArrayList<>();
		List<Document> docsPersonajes = doc.getList("personajes", Document.class);
		if (docsPersonajes != null) {
			for (Document d : docsPersonajes) {
				personajes.add(
						new Personaje(d.getString("idPersonaje"), d.getString("nombre"), d.getString("descripcion")));
			}
		}

		List<Arma> armas = new ArrayList<>();
		List<Document> docsArmas = doc.getList("armas", Document.class);
		if (docsArmas != null) {
			for (Document d : docsArmas) {
				armas.add(new Arma(d.getString("idArma"), d.getString("nombre"), d.getString("descripcion")));
			}
		}

		List<Escenario> escenarios = new ArrayList<>();
		List<Document> docsEscenarios = doc.getList("escenarios", Document.class);
		if (docsEscenarios != null) {
			for (Document d : docsEscenarios) {
				escenarios.add(
						new Escenario(d.getString("idEscenario"), d.getString("nombre"), d.getString("descripcion")));
			}
		}

		int pistaActual = doc.getInteger("pistaActual", 0);

		Nivel nivel = new Nivel(doc.getString("idNivel"), dificultad, asesino, escenario, arma,
				doc.getString("descripcion"), pistas, pistaActual, personajes, armas, escenarios);

		return nivel;
	}

	/**
	 * Inserta un nuevo nivel o actualiza uno existente en la base de datos.
	 * Convierte los objetos Java de la solución en subdocumentos BSON. * @param
	 * nivel El objeto Nivel que se desea persistir en MongoDB.
	 */
	public void guardarNivel(Nivel nivel) {

		if (nivel.getIdNivel() == null || nivel.getIdNivel().isEmpty()) {
			nivel.setIdNivel("NIVEL_" + java.util.UUID.randomUUID());
		}

		Document docAsesino = new Document();
		if (nivel.getAsesino() != null) {
			docAsesino.append("idPersonaje", nivel.getAsesino().getIdPersonaje())
					.append("nombre", nivel.getAsesino().getNombre())
					.append("descripcion", nivel.getAsesino().getDescripcion());
		}

		Document docEscenario = new Document();
		if (nivel.getEscenarioCrimen() != null) {
			docEscenario.append("idEscenario", nivel.getEscenarioCrimen().getIdEscenario())
					.append("nombre", nivel.getEscenarioCrimen().getNombre())
					.append("descripcion", nivel.getEscenarioCrimen().getDescripcion());
		}

		Document docArma = new Document();
		if (nivel.getArmaCrimen() != null) {
			docArma.append("idArma", nivel.getArmaCrimen().getIdArma())
					.append("nombre", nivel.getArmaCrimen().getNombre())
					.append("descripcion", nivel.getArmaCrimen().getDescripcion());
		}

		Document docNivel = new Document("idNivel", nivel.getIdNivel())
				.append("dificultad", nivel.getDificultad() != null ? nivel.getDificultad().name() : null)
				.append("asesino", docAsesino).append("escenarioCrimen", docEscenario).append("armaCrimen", docArma)
				.append("descripcion", nivel.getDescripcion())
				.append("pistas", nivel.getPistas() != null ? nivel.getPistas() : Collections.emptyList())
				.append("pistaActual", nivel.getPistaActual());

		coleccion.updateOne(Filters.eq("idNivel", nivel.getIdNivel()), new Document("$set", docNivel),
				new UpdateOptions().upsert(true));
	}

	/**
	 * Obtiene una lista con todos los niveles disponibles en el juego. * @return
	 * Una lista (List) de objetos Nivel.
	 */
	public List<Nivel> obtenerTodosLosNiveles() {

		List<Nivel> lista = new ArrayList<>();

		for (Document doc : coleccion.find()) {
			Nivel n = obtenerNivelPorId(doc.getString("idNivel"));
			if (n != null) {
				lista.add(n);
			}
		}

		return lista;
	}

	/**
	 * Genera un texto con la descripción introductoria del nivel y todas sus
	 * pistas. Útil para mostrar un resumen al jugador. * @param idNivel El código
	 * del nivel a consultar.
	 * 
	 * @return Un String formateado con la historia y la lista de pistas, o un
	 *         mensaje de error si no existe.
	 */
	public String obtenerCronicaCompleta(String idNivel) {

		Document doc = coleccion.find(Filters.eq("idNivel", idNivel)).first();

		if (doc == null) {
			throw new IllegalArgumentException("Nivel no encontrado: " + idNivel);
		}

		String descripcion = doc.getString("descripcion");
		List<String> pistas = doc.getList("pistas", String.class);

		StringBuilder cronica = new StringBuilder();

		if (descripcion != null) {
			cronica.append(descripcion).append("\n\n");
		}

		if (pistas != null) {
			for (String pista : pistas) {
				cronica.append("- ").append(pista).append("\n");
			}
		}

		return cronica.toString();
	}
}