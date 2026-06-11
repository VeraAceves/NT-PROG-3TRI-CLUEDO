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
		// Obtenemos la conexión desde nuestra clase gestora
		MongoDatabase db = Conexion.getDatabase();
		this.coleccion = db.getCollection("Partida");
	}

	// =============================================================
	// F05: GUARDAR / ACTUALIZAR PARTIDA
	// =============================================================
	public void guardarPartida(Partida partida) {
		// 1. Embeber datos del Jugador (solo ID y Nombre)
		Document docJugador = new Document("idJugador", partida.getJugador().getIdJugador()).append("nombre",
				partida.getJugador().getNombre());

		// 2. Embeber datos del Nivel
		Document docNivel = new Document("idNivel", partida.getNivel().getIdNivel()).append("nombre",
				partida.getNivel());

		// 3. Construir el documento principal de la Partida
		Document docPartida = new Document("idPartida", partida.getIdPartida()).append("jugador", docJugador)
				.append("nivel", docNivel).append("rondaActual", partida.getRondaActual())
				.append("puntosActuales", partida.getPuntosActuales())
				.append("pistasRestantes", partida.getPistasRestantes())
				.append("estado", partida.getEstado() != null ? partida.getEstado().name() : null)
				.append("resultado", partida.getResultado() != null ? partida.getResultado().name() : null);

		// Operación Upsert: Si el ID ya existe lo actualiza, si no, inserta una nueva
		// partida
		coleccion.updateOne(Filters.eq("idPartida", partida.getIdPartida()), new Document("$set", docPartida),
				new UpdateOptions().upsert(true));
		System.out.println("Partida guardada correctamente en MongoDB.");
	}

	// =============================================================
	// F02: CARGAR PARTIDA
	// =============================================================
	public Partida cargarPartida(String idPartida) {
		Document doc = coleccion.find(Filters.eq("idPartida", idPartida)).first();
		if (doc == null) {
			System.out.println("No se ha encontrado ninguna partida con ese ID.");
			return null;
		}

		return mapearDocumentoAPartida(doc);
	}

	// =============================================================
	// F10: HISTORIAL DE PARTIDAS (Últimas 10 finalizadas)
	// =============================================================
	public List<Partida> obtenerHistorial(String idJugador) {
		List<Partida> historial = new ArrayList<>();

		// Filtramos por el ID del jugador y aseguramos que la partida esté terminada
		try (MongoCursor<Document> cursor = coleccion
				.find(Filters.and(Filters.eq("jugador.idJugador", idJugador),
						Filters.eq("estado", EstadoPartida.FINALIZADA.name())))
				.sort(new Document("_id", -1)) // Las más recientes primero
				.limit(10) // Límite de 10 partidas exigido por la funcionalidad
				.iterator()) {

			while (cursor.hasNext()) {
				historial.add(mapearDocumentoAPartida(cursor.next()));
			}
		}
		return historial;
	}

	// =============================================================
	// MÉTODO AUXILIAR: RECONSTRUCCIÓN COMPLETA DE ENTIDADES
	// =============================================================
	private Partida mapearDocumentoAPartida(Document doc) {
		// Extraer los subdocumentos BSON
		Document docJugador = (Document) doc.get("jugador");
		Document docNivel = (Document) doc.get("nivel");

		// Reconstruir Jugador
		Jugador jugador = new Jugador(docJugador.getString("idJugador"), docJugador.getString("nombre"));

		// Reconstruir Nivel (Solo necesitamos el ID y Nombre para cargar el estado del
		// juego)
		Nivel nivel = new Nivel();
		nivel.setIdNivel(docNivel.getString("idNivel"));

		// Instanciar Partida usando el constructor correspondiente
		Partida partida = new Partida(doc.getString("idPartida"), jugador, nivel);

		// Cargar los atributos dinámicos del juego
		partida.setRondaActual(doc.getInteger("rondaActual"));
		partida.setPuntosActuales(doc.getInteger("puntosActuales"));
		partida.setPistasRestantes(doc.getInteger("pistasRestantes"));

		// Restaurar Enums de manera segura desde su representación en texto
		if (doc.getString("estado") != null) {
			partida.setEstado(EstadoPartida.valueOf(doc.getString("estado")));
		}
		if (doc.getString("resultado") != null) {
			partida.setResultado(ResultadoPartida.valueOf(doc.getString("resultado")));
		}

		return partida;
	}

//MEtodo nuevooo!!!!
	public Partida obtenerUltimaPartida() {

		Document doc = coleccion.find().sort(new Document("_id", -1)).first();

		if (doc == null)
			return null;

		return mapearDocumentoAPartida(doc);
	}
}