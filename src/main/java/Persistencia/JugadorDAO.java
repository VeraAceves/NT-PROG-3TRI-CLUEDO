package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import modelo.beans.*;

public class JugadorDAO {

    private final MongoCollection<Document> coleccion;

    public JugadorDAO() {
        MongoDatabase db = Conexion.getDatabase();
        this.coleccion = db.getCollection("Jugador");
    }

    // =============================================================
    // GUARDAR / ACTUALIZAR JUGADOR (Registro)
    // =============================================================
    public void guardarJugador(Jugador jugador) {
        
        // Aprovechamos tu método de validación antes de tocar la base de datos
        if (!jugador.nombreValido()) {
            System.out.println("Error: No se puede guardar en BD un jugador con nombre vacío o nulo.");
            return;
        }

        Document docJugador = new Document("idJugador", jugador.getIdJugador())
                .append("nombre", jugador.getNombre());

        // Operación Upsert: Si el idJugador ya existe, lo actualiza. Si no, lo inserta.
        coleccion.updateOne(
                Filters.eq("idJugador", jugador.getIdJugador()), 
                new Document("$set", docJugador), 
                new UpdateOptions().upsert(true)
        );
        
        System.out.println("Jugador guardado exitosamente en MongoDB.");
    }

    // =============================================================
    // OBTENER JUGADOR (Para iniciar sesión o recuperar perfil)
    // =============================================================
    public Jugador obtenerJugador(String idJugador) {
        
        Document doc = coleccion.find(Filters.eq("idJugador", idJugador)).first();
        
        if (doc != null) {
            // Reconstruimos el objeto Jugador con los datos de Atlas
            return new Jugador(
                    doc.getString("idJugador"),
                    doc.getString("nombre")
            );
        }
        
        System.out.println("No se encontró ningún jugador con el ID proporcionado.");
        return null;
    }
    
    
    //Metodo nuevo!!!
    
    public boolean existeJugador(String nombre) {
        return coleccion.find(Filters.eq("nombre", nombre)).first() != null;
    }
}