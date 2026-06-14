package Persistencia;

import java.util.ArrayList;
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

		// 1. Extraer los subdocumentos embebidos de la solución
		Document docAsesino = (Document) doc.get("asesino");
		Document docEscenario = (Document) doc.get("escenarioCrimen");
		Document docArma = (Document) doc.get("armaCrimen");

		// 2. Instanciar el Personaje Asesino
		Personaje asesino = null;
		if (docAsesino != null) {
			asesino = new Personaje(docAsesino.getString("idPersonaje"), docAsesino.getString("nombre"),
					docAsesino.getString("descripcion"));
		}

		// 3. Instanciar el Escenario del Crimen
		Escenario escenario = null;
		if (docEscenario != null) {
			escenario = new Escenario(docEscenario.getString("idEscenario"), docEscenario.getString("nombre"),
					docEscenario.getString("descripcion"));
		}

		// 4. Instanciar el Arma del Crimen
		Arma arma = null;
		if (docArma != null) {
			arma = new Arma(docArma.getString("idArma"), docArma.getString("nombre"), docArma.getString("descripcion"));
		}

		// 5. Manejar el Enum de Dificultad
		Dificultad dificultad = null;
		if (doc.getString("dificultad") != null) {
			dificultad = Dificultad.valueOf(doc.getString("dificultad"));
		}

		// 6. Obtener la lista de pistas
		List<String> pistas = doc.getList("pistas", String.class);
		// 6.5 PERSONAJES
		List<Personaje> personajes = new ArrayList<>();
		List<Document> docsPersonajes = doc.getList("personajes", Document.class);

		if (docsPersonajes != null) {
			for (Document d : docsPersonajes) {
				personajes.add(
						new Personaje(d.getString("idPersonaje"), d.getString("nombre"), d.getString("descripcion")));
			}
		}

		// 6.6 ARMAS
		List<Arma> armas = new ArrayList<>();
		List<Document> docsArmas = doc.getList("armas", Document.class);

		if (docsArmas != null) {
			for (Document d : docsArmas) {
				armas.add(new Arma(d.getString("idArma"), d.getString("nombre"), d.getString("descripcion")));
			}
		}

		// 6.7 ESCENARIOS
		List<Escenario> escenarios = new ArrayList<>();
		List<Document> docsEscenarios = doc.getList("escenarios", Document.class);

		if (docsEscenarios != null) {
			for (Document d : docsEscenarios) {
				escenarios.add(
						new Escenario(d.getString("idEscenario"), d.getString("nombre"), d.getString("descripcion")));
			}
		}

		// 7. Extraer la pista actual (inicia en 0 por defecto si no existe en la BD)
		int pistaActual = doc.getInteger("pistaActual") != null ? doc.getInteger("pistaActual") : 0;

		// 8. Reconstruir y retornar el objeto Nivel con su constructor completo
		Nivel nivel = new Nivel(doc.getString("idNivel"), dificultad, asesino, escenario, arma,
				doc.getString("descripcion"), pistas, pistaActual);

		nivel.setPersonajes(personajes);
		nivel.setArmas(armas);
		nivel.setEscenarios(escenarios);

		return nivel;
	}

	public void guardarNivel(Nivel nivel) {

		if (nivel.getIdNivel() == null || nivel.getIdNivel().isEmpty()) {
			nivel.setIdNivel("NIVEL_" + java.util.UUID.randomUUID().toString());
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
				.append("descripcion", nivel.getDescripcion()).append("pistas", nivel.getPistas())
				.append("pistaActual", nivel.getPistaActual());

		coleccion.updateOne(Filters.eq("idNivel", nivel.getIdNivel()), new Document("$set", docNivel),
				new UpdateOptions().upsert(true));
	}

	public List<Nivel> obtenerTodosLosNiveles() {

		List<Nivel> lista = new ArrayList<>();

		for (Document doc : coleccion.find()) {
			lista.add(obtenerNivelPorId(doc.getString("idNivel")));
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