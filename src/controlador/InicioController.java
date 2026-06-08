package controlador;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InicioController {
	@FXML
	private Button btnNueva;
	
	@FXML
	private Button btnCargar;
	
	@FXML
	private Button btnSalir;
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void nuevaPartida() {
		try {
			Stage stage = (Stage) btnNueva.getScene().getWindow();

			GestorVistas.cambiarVista(
					"/vista/Partida.fxml",
					stage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	private void cargarPartida() {
		try {
			Stage stage = (Stage) btnCargar.getScene().getWindow();

			GestorVistas.cambiarVista(
					"/vista/Partida.fxml",
					stage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void salir() {
		Platform.exit();
	}
}
