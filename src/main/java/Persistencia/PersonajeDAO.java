package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import beans.Personaje;

public class PersonajeDAO {

	// Variable para enlazar con la colección en Mongo
	private final MongoCollection<Document> coleccion;

	// Constructor que establece el puente con la base de datos
	public PersonajeDAO() {
		// 1. Pedimos la base de datos a nuestra clase gestora
		MongoDatabase db = Conexion.getDatabase();
		// 2. Apuntamos a la colección "Personaje"
		this.coleccion = db.getCollection("Personaje");
	}

	// Método para encontrar a un sospechoso por su ID (ej. "P_MAGO")
	public Personaje obtenerPersonajePorId(String idPersonaje) {

		// 1. Realizamos la consulta (query) a MongoDB filtrando por el ID
		Document doc = coleccion.find(Filters.eq("idPersonaje", idPersonaje)).first();

		// 2. Verificamos que el documento exista realmente
		if (doc == null) {
			return null;
		}

		// 3. Creamos el personaje usando el constructor vacío del Bean
		Personaje personaje = new Personaje();

		// 4. Pasamos los datos del documento JSON a las propiedades del objeto Java
		personaje.setIdPersonaje(doc.getString("idPersonaje"));
		personaje.setNombre(doc.getString("nombre"));
		personaje.setDescripcion(doc.getString("descripcion"));

		// 5. Devolvemos al personaje con toda su información
		return personaje;
	}

	// Método para obtener el listado completo de personajes
	public List<Personaje> obtenerTodosLosPersonajes() {

		// 1. Inicializamos la lista que contendrá a todos los personajes
		List<Personaje> listaPersonajes = new ArrayList<>();

		// 2. Hacemos la consulta general (find sin filtros) y la recorremos
		for (Document doc : coleccion.find()) {

			// 3. Instanciamos un personaje temporal
			Personaje personaje = new Personaje();
			
			// 4. Le asignamos los valores sacados de la base de datos
			personaje.setIdPersonaje(doc.getString("idPersonaje"));
			personaje.setNombre(doc.getString("nombre"));
			personaje.setDescripcion(doc.getString("descripcion"));

			// 5. Guardamos este personaje temporal en nuestra lista definitiva
			listaPersonajes.add(personaje);
		}

		// 6. Retornamos la lista con todos los sospechosos
		return listaPersonajes;
	}
}
