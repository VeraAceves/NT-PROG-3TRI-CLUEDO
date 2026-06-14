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

public class NivelDAO {

    private final MongoCollection<Document> coleccion;

    public NivelDAO() {
        MongoDatabase db = Conexion.getDatabase();
        this.coleccion = db.getCollection("Nivel");
    }

    // =============================================================
    // OBTENER NIVEL (Cargar la configuración de un caso)
    // =============================================================
    public Nivel obtenerNivelPorId(String idNivel) {
        Document doc = coleccion.find(Filters.eq("idNivel", idNivel)).first();
        
        if (doc == null) {
            return null;
        }

        // 1. Extraer los subdocumentos embebidos de la solución
        Document docAsesino = (Document) doc.get("asesino");
        Document docEscenario = (Document) doc.get("escenarioCrimen");
        Document docArma = (Document) doc.get("armaCrimen");

        // 2. Instanciar el Personaje Asesino
        Personaje asesino = null;
        if (docAsesino != null) {
            asesino = new Personaje(
                docAsesino.getString("idPersonaje"),
                docAsesino.getString("nombre"),
                docAsesino.getString("descripcion")
            );
        }

        // 3. Instanciar el Escenario del Crimen
        Escenario escenario = null;
        if (docEscenario != null) {
            escenario = new Escenario(
                docEscenario.getString("idEscenario"),
                docEscenario.getString("nombre"),
                docEscenario.getString("descripcion")
            );
        }

        // 4. Instanciar el Arma del Crimen
        Arma arma = null;
        if (docArma != null) {
            arma = new Arma(
                docArma.getString("idArma"),
                docArma.getString("nombre"),
                docArma.getString("descripcion")
            );
        }

        // 5. Manejar el Enum de Dificultad
        Dificultad dificultad = null;
        if (doc.getString("dificultad") != null) {
            dificultad = Dificultad.valueOf(doc.getString("dificultad"));
        }

        // 6. Obtener la lista de pistas
        List<String> pistas = doc.getList("pistas", String.class);
        
        // 7. Extraer la pista actual (inicia en 0 por defecto si no existe en la BD)
        int pistaActual = doc.getInteger("pistaActual") != null ? doc.getInteger("pistaActual") : 0;

        // 8. Reconstruir y retornar el objeto Nivel con su constructor completo
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

    // =============================================================
    // GUARDAR NIVEL (Para poblar tu base de datos desde código)
    // =============================================================
    public void guardarNivel(Nivel nivel) {
        
        // Mapeo del Asesino
        Document docAsesino = new Document();
        if (nivel.getAsesino() != null) {
            docAsesino.append("idPersonaje", nivel.getAsesino().getIdPersonaje())
                      .append("nombre", nivel.getAsesino().getNombre())
                      .append("descripcion", nivel.getAsesino().getDescripcion());
        }

        // Mapeo del Escenario
        Document docEscenario = new Document();
        if (nivel.getEscenarioCrimen() != null) {
            docEscenario.append("idEscenario", nivel.getEscenarioCrimen().getIdEscenario())
                        .append("nombre", nivel.getEscenarioCrimen().getNombre())
                        .append("descripcion", nivel.getEscenarioCrimen().getDescripcion());
        }

        // Mapeo del Arma
        Document docArma = new Document();
        if (nivel.getArmaCrimen() != null) {
            docArma.append("idArma", nivel.getArmaCrimen().getIdArma())
                   .append("nombre", nivel.getArmaCrimen().getNombre())
                   .append("descripcion", nivel.getArmaCrimen().getDescripcion());
        }

        // Construcción del documento Nivel
        Document docNivel = new Document("idNivel", nivel.getIdNivel())
                .append("dificultad", nivel.getDificultad() != null ? nivel.getDificultad().name() : null)
                .append("asesino", docAsesino)
                .append("escenarioCrimen", docEscenario)
                .append("armaCrimen", docArma)
                .append("descripcion", nivel.getDescripcion())
                .append("pistas", nivel.getPistas())
                .append("pistaActual", 0); // Siempre reiniciamos la lectura de pistas al guardar un nivel base

        coleccion.updateOne(
                Filters.eq("idNivel", nivel.getIdNivel()),
                new Document("$set", docNivel),
                new UpdateOptions().upsert(true)
        );
        
        System.out.println("Nivel guardado correctamente.");
    }
        
     // MEtodos nuevo!!!
    	public List<Nivel> obtenerTodosLosNiveles() {

    		List<Nivel> lista = new ArrayList<>();

    		for (Document doc : coleccion.find()) {

    			lista.add(obtenerNivelPorId(doc.getString("idNivel")));
    		}

    		return lista;
    	}

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