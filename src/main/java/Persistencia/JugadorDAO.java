package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import modelo.beans.Jugador;

public class JugadorDAO {

	private final MongoCollection<Document> coleccion;

	public JugadorDAO() {
		MongoDatabase db = Conexion.getDatabase();
		this.coleccion = db.getCollection("Jugador");
	}

	public void guardarJugador(Jugador jugador) {

		if (!jugador.nombreValido()) {
			throw new IllegalArgumentException("Nombre de jugador no válido");
		}

		Document doc = new Document("nombre", jugador.getNombre());

		if (jugador.getIdJugador() != null) {

			coleccion.updateOne(Filters.eq("_id", jugador.getIdJugador()), new Document("$set", doc),
					new UpdateOptions().upsert(true));

		} else {

			doc.append("_id", java.util.UUID.randomUUID().toString());
			coleccion.insertOne(doc);

			jugador.setIdJugador(doc.getString("_id"));
		}
	}

	public Jugador obtenerJugador(String idJugador) {

		Document doc = coleccion.find(Filters.eq("_id", idJugador)).first();

		if (doc == null) {
			return null;
		}

		Jugador j = new Jugador();
		j.setIdJugador(doc.getString("_id"));
		j.setNombre(doc.getString("nombre"));

		return j;
	}

	public boolean existeJugador(String nombre) {
		return coleccion.find(Filters.eq("nombre", nombre)).first() != null;
	}
}