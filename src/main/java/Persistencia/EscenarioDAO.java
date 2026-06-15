package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import beans.Escenario;

public class EscenarioDAO {

	// Variable para manejar la colección de escenarios
	private final MongoCollection<Document> coleccion;

	// Constructor para inicializar la conexión
	public EscenarioDAO() {
		// 1. Obtenemos la conexión a la BD
		MongoDatabase db = Conexion.getDatabase();
		// 2. Nos conectamos a la colección "Escenario"
		this.coleccion = db.getCollection("Escenario");
	}

	// Método para buscar un escenario por su ID (ej. "E_FORJA")
	public Escenario obtenerEscenarioPorId(String idEscenario) {

		// 1. Filtramos en Mongo buscando exactamente el ID solicitado
		Document doc = coleccion.find(Filters.eq("idEscenario", idEscenario)).first();

		// 2. Si no existe, paramos aquí y devolvemos null
		if (doc == null) {
			return null;
		}

		// 3. Instanciamos un escenario vacío
		Escenario escenario = new Escenario();

		// 4. Mapeamos (traducimos) los datos de BSON/Mongo a nuestro Bean de Java
		escenario.setIdEscenario(doc.getString("idEscenario"));
		escenario.setNombre(doc.getString("nombre"));
		escenario.setDescripcion(doc.getString("descripcion"));

		// 5. Retornamos el objeto final
		return escenario;
	}

	// Método para rescatar todos los escenarios de golpe
	public List<Escenario> obtenerTodosLosEscenarios() {

		// 1. Preparamos el ArrayList vacío
		List<Escenario> listaEscenarios = new ArrayList<>();

		// 2. Recorremos la colección entera
		for (Document doc : coleccion.find()) {

			// 3. Creamos y rellenamos el objeto en cada vuelta del bucle
			Escenario escenario = new Escenario();
			escenario.setIdEscenario(doc.getString("idEscenario"));
			escenario.setNombre(doc.getString("nombre"));
			escenario.setDescripcion(doc.getString("descripcion"));

			// 4. Lo sumamos a la lista
			listaEscenarios.add(escenario);
		}

		// 5. Devolvemos la lista cargada
		return listaEscenarios;
	}
}