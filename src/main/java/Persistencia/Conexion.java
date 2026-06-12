package Persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conexion {
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;

    // ✅ CORREGIDA: Añadir el prefijo que falta
    private static final String url = "mongodb+srv://brukcueto_db_user:1234@cluster0.npiuhb0.mongodb.net/?appName=Cluster0";
    private static final String nombreDataBase = "CluedoFx";

    private Conexion() {
    }

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            try {
                System.out.println("Intentando conectar a MongoDB Atlas...");
                mongoClient = MongoClients.create(url);
                database = mongoClient.getDatabase(nombreDataBase);
                
                // Probar la conexión
                database.runCommand(new org.bson.Document("ping", 1));
                System.out.println("Conexion exitosa a MongoDB Atlas");
                System.out.println(" Base de datos: " + nombreDataBase);
                
            } catch (Exception e) {
                System.err.println("❌ Error al conectar a MongoDB: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return database;
    }

    public static void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("🔌 Conexión a MongoDB cerrada.");
        }
    }
}