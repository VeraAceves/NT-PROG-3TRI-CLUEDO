package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import beans.Jugador;

/**
 * Controla el registro y la recuperación de jugadores en la colección "Jugador".
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
     * Registra un jugador nuevo en la base de datos o actualiza su información si ya existe.
     * Valida que el nombre no esté vacío antes de interactuar con MongoDB.
     * * @param jugador El objeto Jugador que contiene los datos a guardar.
     */
    public void guardarJugador(Jugador jugador) {
        if (!jugador.nombreValido()) {
            System.out.println("Error: No se puede guardar en BD un jugador con nombre vacío o nulo.");
            return;
        }

        Document docJugador = new Document("idJugador", jugador.getIdJugador())
                .append("nombre", jugador.getNombre());

        coleccion.updateOne(
                Filters.eq("idJugador", jugador.getIdJugador()), 
                new Document("$set", docJugador), 
                new UpdateOptions().upsert(true)
        );
        
        System.out.println("Jugador guardado exitosamente en MongoDB.");
    }

    /**
     * Recupera el perfil de un jugador a partir de su ID para iniciar sesión.
     * * @param idJugador El identificador único del usuario.
     * @return El objeto Jugador con sus datos, o null si no se encuentra registrado.
     */
    public Jugador obtenerJugador(String idJugador) {
        Document doc = coleccion.find(Filters.eq("idJugador", idJugador)).first();
        
        if (doc != null) {
            return new Jugador(
                    doc.getString("idJugador"),
                    doc.getString("nombre")
            );
        }
        
        System.out.println("No se encontró ningún jugador con el ID proporcionado.");
        return null;
    }

    /**
     * Comprueba en la base de datos si un nombre de usuario ya está en uso.
     * * @param nombre El nombre que se desea verificar.
     * @return true si el nombre ya pertenece a otro jugador, false si está libre.
     */
    public boolean existeJugador(String nombre) {
        return coleccion.find(Filters.eq("nombre", nombre)).first() != null;
    }
}