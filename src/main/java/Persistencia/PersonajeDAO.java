package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import beans.Personaje;

/**
 * Data Access Object (DAO) encargado de recuperar la información de los sospechosos
 * desde la colección "Personaje" en MongoDB.
 */
public class PersonajeDAO {

	private final MongoCollection<Document> coleccion;

	/**
	 * Constructor que establece la conexión directa con la colección "Personaje".
	 */
	public PersonajeDAO() {
		MongoDatabase db = Conexion.getDatabase();
		this.coleccion = db.getCollection("Personaje");
	}

	/**
	 * Busca los datos y la historia de un sospechoso concreto utilizando su ID.
	 * * @param idPersonaje El código identificador (ej. "P_MAGO").
	 * @return Un objeto Personaje relleno con la información de la base de datos, o null si no existe.
	 */
	public Personaje obtenerPersonajePorId(String idPersonaje) {
		Document doc = coleccion.find(Filters.eq("idPersonaje", idPersonaje)).first();

		if (doc == null) {
			return null;
		}

		Personaje personaje = new Personaje();
		personaje.setIdPersonaje(doc.getString("idPersonaje"));
		personaje.setNombre(doc.getString("nombre"));
		personaje.setDescripcion(doc.getString("descripcion"));

		return personaje;
	}

	/**
	 * Obtiene el listado de todos los sospechosos que participan en el juego.
	 * * @return Una lista (List) con todos los personajes instanciados.
	 */
	public List<Personaje> obtenerTodosLosPersonajes() {
		List<Personaje> listaPersonajes = new ArrayList<>();

		for (Document doc : coleccion.find()) {
			Personaje personaje = new Personaje();
			personaje.setIdPersonaje(doc.getString("idPersonaje"));
			personaje.setNombre(doc.getString("nombre"));
			personaje.setDescripcion(doc.getString("descripcion"));
			listaPersonajes.add(personaje);
		}

		return listaPersonajes;
	}
}