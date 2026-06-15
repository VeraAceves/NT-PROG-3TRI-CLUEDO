package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

import modelo.beans.*;
import modelo.enums.*;

public class PartidaDAO {

	private final MongoCollection<Document> coleccion;

	public PartidaDAO() {

		MongoDatabase db = Conexion.getDatabase();
		this.coleccion = db.getCollection("Partida");
	}

	public void guardarPartida(Partida partida) {

		Document docJugador = new Document("nombre", partida.getJugador().getNombre());

		Document docNivel = new Document("idNivel", partida.getNivel().getIdNivel());

		Document docPartida = new Document("idPartida", partida.getIdPartida()).append("jugador", docJugador)
				.append("nivel", docNivel).append("rondaActual", partida.getRondaActual())
				.append("puntosActuales", partida.getPuntosActuales())
				.append("pistasRestantes", partida.getPistasRestantes())
				.append("estado", partida.getEstado() != null ? partida.getEstado().name() : null)
				.append("resultado", partida.getResultado() != null ? partida.getResultado().name() : null);

		coleccion.updateOne(Filters.eq("idPartida", partida.getIdPartida()), new Document("$set", docPartida),
				new UpdateOptions().upsert(true));
	}

	public Partida cargarPartida(String idPartida) {
		Document doc = coleccion.find(Filters.eq("idPartida", idPartida)).first();

		if (doc == null) {
			return null;
		}

		return mapearDocumentoAPartida(doc);
	}

	public List<Partida> obtenerHistorial(String idJugador) {

		List<Partida> historial = new ArrayList<>();

		try (MongoCursor<Document> cursor = coleccion.find(Filters.eq("jugador.nombre", idJugador))
				.sort(new Document("_id", -1)).limit(3).iterator()) {

			while (cursor.hasNext()) {
				historial.add(mapearDocumentoAPartida(cursor.next()));
			}
		}

		return historial;
	}

	private Partida mapearDocumentoAPartida(Document doc) {

		Document docJugador = (Document) doc.get("jugador");
		Document docNivel = (Document) doc.get("nivel");

		Jugador jugador = new Jugador(null, docJugador.getString("nombre"));

		Nivel nivel = new Nivel();
		nivel.setIdNivel(docNivel.getString("idNivel"));

		Partida partida = new Partida();
		partida.setIdPartida(doc.getString("idPartida"));
		partida.setJugador(jugador);
		partida.setNivel(nivel);

		Integer ronda = doc.getInteger("rondaActual");
		Integer puntos = doc.getInteger("puntosActuales");
		Integer pistas = doc.getInteger("pistasRestantes");

		partida.setRondaActual(ronda != null ? ronda : 0);
		partida.setPuntosActuales(puntos != null ? puntos : 0);
		partida.setPistasRestantes(pistas != null ? pistas : 0);

		String estado = doc.getString("estado");
		if (estado != null) {
			partida.setEstado(EstadoPartida.valueOf(estado));
		}

		String resultado = doc.getString("resultado");
		if (resultado != null) {
			partida.setResultado(ResultadoPartida.valueOf(resultado));
		}

		return partida;
	}

	public Partida obtenerUltimaPartida() {

		Document doc = coleccion.find().sort(new Document("_id", -1)).first();

		if (doc == null) {
			return null;
		}

		return mapearDocumentoAPartida(doc);
	}
}