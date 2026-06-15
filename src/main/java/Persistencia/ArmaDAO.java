package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import beans.Arma;

/**
 * Se encarga de realizar todas las consultas a la colección "Arma" en MongoDB.
 */
public class ArmaDAO {

	private final MongoCollection<Document> coleccion;

	/**
	 * Constructor que inicializa la conexión apuntando a la colección "Arma".
	 */
	public ArmaDAO() {
		MongoDatabase db = Conexion.getDatabase();
		this.coleccion = db.getCollection("Arma");
	}

	/**
	 * Busca un arma en la base de datos utilizando su id.
	 * * @param idArma El código identificador del arma (por ejemplo, "A_CANDELABRO").
	 * @return Un objeto Arma con todos sus datos, o null si no se encuentra en la base de datos.
	 */
	public Arma obtenerArmaPorId(String idArma) {
		Document doc = coleccion.find(Filters.eq("idArma", idArma)).first();

		if (doc == null) {
			return null;
		}

		Arma arma = new Arma();
		arma.setIdArma(doc.getString("idArma"));
		arma.setNombre(doc.getString("nombre"));
		arma.setDescripcion(doc.getString("descripcion"));

		return arma;
	}

	/**
	 * Recupera todas las armas disponibles en el catálogo del juego.
	 * * @return Una lista (List) con todos los objetos Arma instanciados.
	 */
	public List<Arma> obtenerTodasLasArmas() {
		List<Arma> listaArmas = new ArrayList<>();

		for (Document doc : coleccion.find()) {
			Arma arma = new Arma();
			arma.setIdArma(doc.getString("idArma"));
			arma.setNombre(doc.getString("nombre"));
			arma.setDescripcion(doc.getString("descripcion"));
			listaArmas.add(arma);
		}

		return listaArmas;
	}
}