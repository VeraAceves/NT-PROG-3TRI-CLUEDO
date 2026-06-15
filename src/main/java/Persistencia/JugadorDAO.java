package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import modelo.beans.Jugador;

/**
 * Controla el registro y la recuperación de jugadores en la colección
 * "Jugador".
 */
public class JugadorDAO {

	private final MongoCollection<Document> coleccion;

	/**
	 * Constructor que enlaza con la colección "Jugador" de MongoDB.
	 */
	public JugadorDAO() {
		MongoDatabase db = Conexion.getDatabase();
		this.coleccion = db.getCollection("Jugador");
	}

	/**
	 * Registra un jugador nuevo en la base de datos o actualiza su información si
	 * ya existe. Valida que el nombre no esté vacío antes de interactuar con
	 * MongoDB. * @param jugador El objeto Jugador que contiene los datos a guardar.
	 */
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

	/**
	 * Recupera el perfil de un jugador a partir de su ID para iniciar sesión.
	 * * @param idJugador El identificador único del usuario.
	 * 
	 * @return El objeto Jugador con sus datos, o null si no se encuentra
	 *         registrado.
	 */
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

	/**
	 * Comprueba en la base de datos si un nombre de usuario ya está en uso.
	 * * @param nombre El nombre que se desea verificar.
	 * 
	 * @return true si el nombre ya pertenece a otro jugador, false si está libre.
	 */
	public boolean existeJugador(String nombre) {
		return coleccion.find(Filters.eq("nombre", nombre)).first() != null;
	}
}