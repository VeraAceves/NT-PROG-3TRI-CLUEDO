package Persistencia;



import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conexion {
	
	private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;
    
    
    private static final String url = "brukcueto_db_user:1234@cluster0.npiuhb0.mongodb.net/?appName=Cluster0";
    private static final String nombreDataBase = "CluedoFx";

    // Constructor 
    private Conexion() {}

    public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(url);
                database = mongoClient.getDatabase(nombreDataBase);
                System.out.println("Conexión exitosa a la base de datos: " + nombreDataBase);
            } catch (Exception e) {
                System.err.println("Error al conectar a MongoDB: " + e.getMessage());
            }
        }
        return database;
    }

    public static void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
}
	
	
	
	
	
	
	