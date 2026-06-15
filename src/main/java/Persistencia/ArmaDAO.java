package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import beans.Arma;

public class ArmaDAO {

	// Variable que guardará la referencia a nuestra colección en Mongo
	private final MongoCollection<Document> coleccion;

	// El constructor se ejecuta nada más crear un "new ArmaDAO()"
	public ArmaDAO() {
		// 1. Llamamos a nuestra clase de conexión para obtener la base de datos
		MongoDatabase db = Conexion.getDatabase();
		// 2. Nos enganchamos específicamente a la colección llamada "Arma"
		this.coleccion = db.getCollection("Arma");
	}

	// Método para buscar un arma concreta por su ID (ej. "A_CANDELABRO")
	public Arma obtenerArmaPorId(String idArma) {

		// 1. Le decimos a Mongo: "Busca un documento donde el campo 'idArma' sea igual al que me pasan por parámetro y dame el primero que encuentres"
		Document doc = coleccion.find(Filters.eq("idArma", idArma)).first();

		// 2. Control de errores: Si la base de datos no encuentra el arma, devolvemos null
		if (doc == null) {
			return null;
		}

		// 3. Si la hemos encontrado, creamos un objeto Arma vacío
		Arma arma = new Arma();

		// 4. Vamos sacando la información del documento (JSON) y la metemos en nuestro objeto Java
		arma.setIdArma(doc.getString("idArma"));
		arma.setNombre(doc.getString("nombre"));
		arma.setDescripcion(doc.getString("descripcion"));

		// 5. Devolvemos el arma ya montada con todos sus datos
		return arma;
	}

	// Método para obtener una lista con todas las armas (Ideal para llenar ComboBox en JavaFX)
	public List<Arma> obtenerTodasLasArmas() {

		// 1. Creamos una lista vacía de tipo Arma
		List<Arma> listaArmas = new ArrayList<>();

		// 2. Bucle for-each que recorre TODOS los documentos de la colección "Arma"
		for (Document doc : coleccion.find()) {

			// 3. Por cada documento que encuentre, creamos un arma nueva
			Arma arma = new Arma();
			arma.setIdArma(doc.getString("idArma"));
			arma.setNombre(doc.getString("nombre"));
			arma.setDescripcion(doc.getString("descripcion"));

			// 4. Añadimos el arma a nuestra lista
			listaArmas.add(arma);
		}

		// 5. Devolvemos la lista completa
		return listaArmas;
	}
}