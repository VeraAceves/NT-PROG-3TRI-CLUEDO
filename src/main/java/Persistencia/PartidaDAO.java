package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import modelo.beans.*;
import modelo.enums.*;

/**
 * Gestiona el guardado, recuperación y el historial de las partidas jugadas en
 * la colección "Partida".
 */
public class PartidaDAO {

	private final MongoCollection<Document> coleccion;

	/**
	 * Constructor que enlaza con la colección "Partida" de la base de datos.
	 */
	public PartidaDAO() {

		MongoDatabase db = Conexion.getDatabase();
		this.coleccion = db.getCollection("Partida");
	}

	/**
	 * Guarda el estado actual de una partida en la base de datos. Si la partida ya
	 * estaba guardada, actualiza sus puntos, rondas y resultados de las hipótesis.
	 * * @param partida El objeto Partida que contiene el estado actual a guardar.
	 */
	public void guardarPartida(Partida partida) {

		try {
			if (partida.getIdPartida() == null) {
				partida.setIdPartida(UUID.randomUUID().toString());

			}

			Document docJugador = new Document("idJugador", partida.getJugador().getIdJugador()).append("nombre",
					partida.getJugador().getNombre());

			Document docNivel = new Document("idNivel", partida.getNivel().getIdNivel());

			Document docPartida = new Document("idPartida", partida.getIdPartida()).append("jugador", docJugador)
					.append("nivel", docNivel).append("rondaActual", partida.getRondaActual())
					.append("puntosActuales", partida.getPuntosActuales())
					.append("pistasRestantes", partida.getPistasRestantes())
					.append("estado", partida.getEstado().name())
					.append("resultado", partida.getResultado() != null ? partida.getResultado().name() : null);

			coleccion.insertOne(docPartida);

		} catch (Exception e) {
			System.err.println("ERROR al guardar partida: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Carga una partida guardada previamente utilizando su identificador. * @param
	 * idPartida El código de la partida a retomar.
	 * 
	 * @return El objeto Partida reconstruido, o null si no se encuentra.
	 */
	public Partida cargarPartida(String idPartida) {
		Document doc = coleccion.find(Filters.eq("idPartida", idPartida)).first();

		if (doc == null) {
			return null;
		}

		return mapearDocumentoAPartida(doc);
	}

	/**
	 * Busca las últimas 10 partidas finalizadas de un jugador en concreto para
	 * mostrar sus estadísticas. * @param idJugador El código del usuario del que
	 * queremos ver el historial.
	 * 
	 * @return Una lista (List) con las partidas ya terminadas, ordenadas de más
	 *         reciente a más antigua.
	 */
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

	/**
	 * Método auxiliar privado que traduce un documento BSON sacado de MongoDB a un
	 * objeto Partida de Java. * @param doc El documento JSON/BSON recuperado de la
	 * base de datos.
	 * 
	 * @return El objeto Partida con todas sus propiedades y enums correctamente
	 *         asignados.
	 */
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

	/**
	 * Recupera la partida guardada más recientemente de forma global. * @return El
	 * objeto Partida de la última sesión jugada, o null si la base de datos está
	 * vacía.
	 */
	public Partida obtenerUltimaPartida() {

		Document doc = coleccion.find().sort(new Document("_id", -1)).first();

		if (doc == null) {
			return null;
		}

		return mapearDocumentoAPartida(doc);
	}
}