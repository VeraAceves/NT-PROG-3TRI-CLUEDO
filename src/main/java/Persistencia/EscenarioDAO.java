package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import beans.Escenario;

/**
 * Gestiona la extracción de los lugares del crimen desde la colección "Escenario" en MongoDB.
 */
public class EscenarioDAO {

	private final MongoCollection<Document> coleccion;

	/**
	 * Constructor que inicializa la conexión apuntando a la colección "Escenario".
	 */
	public EscenarioDAO() {
		MongoDatabase db = Conexion.getDatabase();
		this.coleccion = db.getCollection("Escenario");
	}

	/**
	 * Busca un escenario específico en la base de datos mediante su código.
	 * * @param idEscenario El código identificador del lugar (por ejemplo, "E_FORJA").
	 * @return Un objeto Escenario completo, o null si el identificador no existe.
	 */
	public Escenario obtenerEscenarioPorId(String idEscenario) {
		Document doc = coleccion.find(Filters.eq("idEscenario", idEscenario)).first();

		if (doc == null) {
			return null;
		}

		Escenario escenario = new Escenario();
		escenario.setIdEscenario(doc.getString("idEscenario"));
		escenario.setNombre(doc.getString("nombre"));
		escenario.setDescripcion(doc.getString("descripcion"));

		return escenario;
	}

	/**
	 * Extrae la lista completa de todos los lugares del castillo guardados en la base de datos.
	 * * @return Una lista (List) que contiene todos los escenarios del juego.
	 */
	public List<Escenario> obtenerTodosLosEscenarios() {
		List<Escenario> listaEscenarios = new ArrayList<>();

		for (Document doc : coleccion.find()) {
			Escenario escenario = new Escenario();
			escenario.setIdEscenario(doc.getString("idEscenario"));
			escenario.setNombre(doc.getString("nombre"));
			escenario.setDescripcion(doc.getString("descripcion"));
			listaEscenarios.add(escenario);
		}

		return listaEscenarios;
	}
}