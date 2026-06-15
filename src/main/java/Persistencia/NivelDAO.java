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

public class NivelDAO {

	private final MongoCollection<Document> coleccion;

	public NivelDAO() {
		MongoDatabase db = Conexion.getDatabase();
		this.coleccion = db.getCollection("Nivel");
	}

	public Nivel obtenerNivelPorId(String idNivel) {

		Document doc = coleccion.find(Filters.eq("idNivel", idNivel)).first();

		if (doc == null) {
			return null;
		}

		// =========================
		// SOLUCIÓN (asesino/arma/escenario)
		// =========================
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

		// =========================
		// ENUM
		// =========================
		Dificultad dificultad = null;
		String dif = doc.getString("dificultad");
		if (dif != null) {
			dificultad = Dificultad.valueOf(dif);
		}

		// =========================
		// LISTAS SEGURAS (NUNCA NULL)
		// =========================
		List<String> pistas = doc.getList("pistas", String.class);
		if (pistas == null)
			pistas = new ArrayList<>();

		// personajes jugables
		List<Personaje> personajes = new ArrayList<>();
		List<Document> docsPersonajes = doc.getList("personajes", Document.class);
		if (docsPersonajes != null) {
			for (Document d : docsPersonajes) {
				personajes.add(
						new Personaje(d.getString("idPersonaje"), d.getString("nombre"), d.getString("descripcion")));
			}
		}

		// armas jugables
		List<Arma> armas = new ArrayList<>();
		List<Document> docsArmas = doc.getList("armas", Document.class);
		if (docsArmas != null) {
			for (Document d : docsArmas) {
				armas.add(new Arma(d.getString("idArma"), d.getString("nombre"), d.getString("descripcion")));
			}
		}

		// escenarios jugables
		List<Escenario> escenarios = new ArrayList<>();
		List<Document> docsEscenarios = doc.getList("escenarios", Document.class);
		if (docsEscenarios != null) {
			for (Document d : docsEscenarios) {
				escenarios.add(
						new Escenario(d.getString("idEscenario"), d.getString("nombre"), d.getString("descripcion")));
			}
		}

		// =========================
		// pista actual segura
		// =========================
		int pistaActual = doc.getInteger("pistaActual", 0);

		// =========================
		// NUNCA devolver listas null
		// =========================
		Nivel nivel = new Nivel(doc.getString("idNivel"), dificultad, asesino, escenario, arma,
				doc.getString("descripcion"), pistas, pistaActual, personajes, armas, escenarios);

		return nivel;
	}

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