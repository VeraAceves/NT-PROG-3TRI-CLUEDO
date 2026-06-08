package controlador;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestorVistas {

	public static void cambiarVista(String rutaFXML, Stage stage) throws IOException {
		Scene scene = new Scene(FXMLLoader.load(GestorVistas.class.getResource(rutaFXML)));
		stage.setScene(scene);
		stage.show();
	}
}
