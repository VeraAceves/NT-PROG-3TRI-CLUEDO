package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

import beans.Partida;
import beans.Jugador;
import beans.Nivel;
import enums.EstadoPartida;
import enums.ResultadoPartida;

public class PartidaDAO {

    // Colección de Mongo donde guardaremos y leeremos los datos
    private final MongoCollection<Document> coleccion;

    public PartidaDAO() {
        // Obtenemos la conexión a la base de datos
        MongoDatabase db = Conexion.getDatabase();
        // Nos conectamos a la colección "Partida"
        this.coleccion = db.getCollection("Partida");
    }

    // =============================================================
    // GUARDAR O ACTUALIZAR PARTIDA EN MONGODB
    // =============================================================
    public void guardarPartida(Partida partida) {
        
        // 1. Guardamos los datos esenciales del Jugador en un subdocumento
        Document docJugador = new Document("idJugador", partida.getJugador().getIdJugador())
                .append("nombre", partida.getJugador().getNombre());

        // 2. Guardamos la referencia al Nivel en otro subdocumento
        Document docNivel = new Document("idNivel", partida.getNivel().getIdNivel())
                .append("nombre", partida.getNivel().getIdNivel());

        // 3. Creamos el documento principal de la partida juntando todo
        Document docPartida = new Document("idPartida", partida.getIdPartida())
                .append("jugador", docJugador)
                .append("nivel", docNivel)
                .append("rondaActual", partida.getRondaActual())
                .append("puntosActuales", partida.getPuntosActuales())
                .append("pistasRestantes", partida.getPistasRestantes())
                
                // Convertimos los Enums a texto (String) asegurándonos de que no sean nulos
                .append("estado", partida.getEstado() != null ? partida.getEstado().name() : null)
                .append("resultado", partida.getResultado() != null ? partida.getResultado().name() : null)
                
                // Guardamos los resultados del último interrogatorio (booleanos)
                .append("ultimoAcertoPersonaje", partida.isUltimoAcertoPersonaje())
                .append("ultimoAcertoArma", partida.isUltimoAcertoArma())
                .append("ultimoAcertoEscenario", partida.isUltimoAcertoEscenario());

        // 4. Upsert: Si el idPartida ya existe, lo actualiza. Si no existe, lo inserta nuevo.
        coleccion.updateOne(
                Filters.eq("idPartida", partida.getIdPartida()),
                new Document("$set", docPartida),
                new UpdateOptions().upsert(true)
        );
        System.out.println("Partida guardada correctamente en MongoDB.");
    }

    // =============================================================
    // CARGAR UNA PARTIDA ESPECÍFICA POR SU ID
    // =============================================================
    public Partida cargarPartida(String idPartida) {
        // Buscamos el primer documento que tenga este ID
        Document doc = coleccion.find(Filters.eq("idPartida", idPartida)).first();
        
        if (doc == null) {
            System.out.println("No se ha encontrado ninguna partida con ese ID.");
            return null;
        }

        // Si lo encuentra, usamos el método de abajo para convertirlo a objeto Java
        return mapearDocumentoAPartida(doc);
    }

    // =============================================================
    // OBTENER LAS ÚLTIMAS 10 PARTIDAS TERMINADAS DE UN JUGADOR
    // =============================================================
    public List<Partida> obtenerHistorial(String idJugador) {
        List<Partida> historial = new ArrayList<>();
        
        // Filtramos para buscar solo las de ese jugador que estén en estado FINALIZADA
        try (MongoCursor<Document> cursor = coleccion.find(
                Filters.and(
                        Filters.eq("jugador.idJugador", idJugador),
                        Filters.eq("estado", EstadoPartida.FINALIZADA.name())
                ))
                .sort(new Document("_id", -1)) // Las ordenamos de más nueva a más vieja
                .limit(10)                     // Cogemos solo las últimas 10
                .iterator()) {
            
            // Recorremos los resultados y los vamos metiendo en la lista
            while (cursor.hasNext()) {
                historial.add(mapearDocumentoAPartida(cursor.next()));
            }
        }
        return historial;
    }

    // =============================================================
    // OBTENER LA ÚLTIMA PARTIDA GLOBAL (Para continuar jugando)
    // =============================================================
    public Partida obtenerUltimaPartida() {
        // Buscamos el último documento insertado en la colección general
        Document doc = coleccion.find().sort(new Document("_id", -1)).first();

        if (doc == null) {
            return null;
        }

        return mapearDocumentoAPartida(doc);
    }

    // =============================================================
    // MÉTODO PRIVADO: CONVERTIR DE MONGODB (BSON) A JAVA (OBJETO)
    // =============================================================
    private Partida mapearDocumentoAPartida(Document doc) {
        
        // 1. Extraemos los bloques de datos (subdocumentos) del Jugador y del Nivel
        Document docJugador = (Document) doc.get("jugador");
        Document docNivel = (Document) doc.get("nivel");

        // 2. Reconstruimos el objeto Jugador
        Jugador jugador = new Jugador(
                docJugador.getString("idJugador"), 
                docJugador.getString("nombre")
        );

        // 3. Reconstruimos el objeto Nivel (con el ID es suficiente para esta pantalla)
        Nivel nivel = new Nivel();
        nivel.setIdNivel(docNivel.getString("idNivel"));
        
        // 4. Creamos la Partida base
        Partida partida = new Partida(doc.getString("idPartida"), jugador, nivel);
        
        // 5. Recuperamos los números. Si por algún motivo están vacíos, ponemos un valor por defecto
        partida.setRondaActual(doc.getInteger("rondaActual", 1));
        partida.setPuntosActuales(doc.getInteger("puntosActuales", 0));
        partida.setPistasRestantes(doc.getInteger("pistasRestantes", 0));

        // 6. Volvemos a transformar el texto a tipos Enum
        if (doc.getString("estado") != null) {
            partida.setEstado(EstadoPartida.valueOf(doc.getString("estado")));
        }
        if (doc.getString("resultado") != null) {
            partida.setResultado(ResultadoPartida.valueOf(doc.getString("resultado")));
        }

        // 7. Recuperamos los booleanos de la última hipótesis. (false si el campo no existe)
        partida.setUltimoAcertoPersonaje(doc.getBoolean("ultimoAcertoPersonaje", false));
        partida.setUltimoAcertoArma(doc.getBoolean("ultimoAcertoArma", false));
        partida.setUltimoAcertoEscenario(doc.getBoolean("ultimoAcertoEscenario", false));

        return partida;
    }
}