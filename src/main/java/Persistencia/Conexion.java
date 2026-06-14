package Persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conexion {
	
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;
    private static final String url = "mongodb+srv://brukcueto_db_user:1234@cluster0.npiuhb0.mongodb.net/?appName=Cluster0";
    private static final String nombreDataBase = "CluedoFx";

    private Conexion() {
    }

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(url);
                database = mongoClient.getDatabase(nombreDataBase);
                
            } catch (Exception e) {
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
        }
    }
}