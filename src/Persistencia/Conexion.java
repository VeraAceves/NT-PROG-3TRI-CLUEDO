package Persistencia;


import com.mongodb.client.MongoCollection;
import org.bson.Document;
import javafx.concurrent.Task;
import javafx.application.Platform;


    public void cargarDatosDesdeMongo() {
        // 1. Crear una tarea en segundo plano
        Task<String> dbTask = new Task<String>() {
            @Override
            protected String call() throws Exception {
                // Aquí estamos en un hilo secundario. ¡Seguro para consultar la DB!
                MongoDatabase db = MongoManager.getInstance().getDatabase();
                MongoCollection<Document> collection = db.getCollection("usuarios");
                
                // Ejemplo: Buscar el primer usuario
                Document primerUsuario = collection.find().first();
                
                if (primerUsuario != null) {
                    return primerUsuario.getString("nombre"); // Devuelve el nombre
                }
                return "Usuario no encontrado";
            }
        };

        // 2. ¿Qué hacer cuando la tarea termine con éxito?
        dbTask.setOnSucceeded(event -> {
            // Aquí VOLVEMOS automáticamente al hilo de JavaFX
            String nombreObtenido = dbTask.getValue();
            // labelNombre.setText(nombreObtenido); // Actualizas tu UI aquí
            System.out.println("El nombre es: " + nombreObtenido);
        });

        // 3. ¿Qué hacer si hay un error de conexión?
        dbTask.setOnFailed(event -> {
            Throwable error = dbTask.getException();
            System.err.println("Error conectando a Mongo: " + error.getMessage());
        });

        // 4. Iniciar el hilo
        new Thread(dbTask).start();
    }
}

