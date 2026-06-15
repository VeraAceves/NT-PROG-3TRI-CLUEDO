package Persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

import beans.Nivel;
import beans.Personaje;
import beans.Escenario;
import beans.Arma;
import enums.Dificultad; 

/**
 * Carga la solución del caso, las pistas y la historia introductoria desde la colección "Nivel".
 */
public class NivelDAO {

    private final MongoCollection<Document> coleccion;

    /**
     * Constructor que enlaza con la colección "Nivel" de la base de datos.
     */
    public NivelDAO() {
        MongoDatabase db = Conexion.getDatabase();
        this.coleccion = db.getCollection("Nivel");
    }

    /**
     * Rescata toda la configuración de un nivel (incluyendo los subdocumentos de la solución) 
     * y reconstruye el objeto Java complejo.
     * * @param idNivel El código del nivel a cargar (ej. "NIVEL_01").
     * @return El objeto Nivel preparado para iniciar una partida, o null si no se encuentra.
     */
    public Nivel obtenerNivelPorId(String idNivel) {
        Document doc = coleccion.find(Filters.eq("idNivel", idNivel)).first();
        
        if (doc == null) {
            return null;
        }

        Document docAsesino = (Document) doc.get("asesino");
        Document docEscenario = (Document) doc.get("escenarioCrimen");
        Document docArma = (Document) doc.get("armaCrimen");

        Personaje asesino = null;
        if (docAsesino != null) {
            asesino = new Personaje(
                docAsesino.getString("idPersonaje"),
                docAsesino.getString("nombre"),
                docAsesino.getString("descripcion")
            );
        }

        Escenario escenario = null;
        if (docEscenario != null) {
            escenario = new Escenario(
                docEscenario.getString("idEscenario"),
                docEscenario.getString("nombre"),
                docEscenario.getString("descripcion")
            );
        }

        Arma arma = null;
        if (docArma != null) {
            arma = new Arma(
                docArma.getString("idArma"),
                docArma.getString("nombre"),
                docArma.getString("descripcion")
            );
        }

        Dificultad dificultad = null;
        if (doc.getString("dificultad") != null) {
            dificultad = Dificultad.valueOf(doc.getString("dificultad"));
        }

        List<String> pistas = doc.getList("pistas", String.class);
        int pistaActual = doc.getInteger("pistaActual") != null ? doc.getInteger("pistaActual") : 0;

        return new Nivel(
            doc.getString("idNivel"),
            dificultad,
            asesino,
            escenario,
            arma,
            doc.getString("descripcion"),
            pistas,
            pistaActual
        );
    }

    /**
     * Inserta un nuevo nivel o actualiza uno existente en la base de datos.
     * Convierte los objetos Java de la solución en subdocumentos BSON.
     * * @param nivel El objeto Nivel que se desea persistir en MongoDB.
     */
    public void guardarNivel(Nivel nivel) {
        
        Document docAsesino = new Document();
        if (nivel.getAsesino() != null) {
            docAsesino.append("idPersonaje", nivel.getAsesino().getIdPersonaje())
                      .append("nombre", nivel.getAsesino().getNombre())
                      .append("descripcion", nivel.getAsesino().getDescripcion());
        }

        Document docEscenario = new Document();
        if (nivel.getEscenarioCrimen() != null) {
            docEscenario.append("idEscenario", nivel.getEscenarioCrimen().getIdEscenario())
                        .append("nombre", nivel.getEscenarioCrimen().getNombre())
                        .append("descripcion", nivel.getEscenarioCrimen().getDescripcion());
        }

        Document docArma = new Document();
        if (nivel.getArmaCrimen() != null) {
            docArma.append("idArma", nivel.getArmaCrimen().getIdArma())
                   .append("nombre", nivel.getArmaCrimen().getNombre())
                   .append("descripcion", nivel.getArmaCrimen().getDescripcion());
        }

        Document docNivel = new Document("idNivel", nivel.getIdNivel())
                .append("dificultad", nivel.getDificultad() != null ? nivel.getDificultad().name() : null)
                .append("asesino", docAsesino)
                .append("escenarioCrimen", docEscenario)
                .append("armaCrimen", docArma)
                .append("descripcion", nivel.getDescripcion())
                .append("pistas", nivel.getPistas())
                .append("pistaActual", 0); 

        coleccion.updateOne(
                Filters.eq("idNivel", nivel.getIdNivel()),
                new Document("$set", docNivel),
                new UpdateOptions().upsert(true)
        );
        
        System.out.println("Nivel guardado correctamente.");
    }
        
    /**
     * Obtiene una lista con todos los niveles disponibles en el juego.
     * * @return Una lista (List) de objetos Nivel.
     */
    public List<Nivel> obtenerTodosLosNiveles() {
    	List<Nivel> lista = new ArrayList<>();
    	for (Document doc : coleccion.find()) {
    		lista.add(obtenerNivelPorId(doc.getString("idNivel")));
    	}
    	return lista;
    }

    /**
     * Genera un texto con la descripción introductoria del nivel y todas sus pistas.
     * Útil para mostrar un resumen al jugador.
     * * @param idNivel El código del nivel a consultar.
     * @return Un String formateado con la historia y la lista de pistas, o un mensaje de error si no existe.
     */
    public String obtenerCronicaCompleta(String idNivel) {
    	Document doc = coleccion.find(Filters.eq("idNivel", idNivel)).first();

    	if (doc == null) {
    		return "No hay crónica disponible.";
    	}

    	String descripcion = doc.getString("descripcion");
    	List<String> pistas = doc.getList("pistas", String.class);

    	StringBuilder cronica = new StringBuilder();
    	cronica.append(descripcion != null ? descripcion : "").append("\n\n");

    	if (pistas != null) {
    		for (String pista : pistas) {
    			cronica.append("- ").append(pista).append("\n");
    		}
    	}

    	return cronica.toString();
    }
}