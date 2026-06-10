package controlador;

import aplicacion.Main;
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
		Main.mostrarMenuPartida();
	}

	@FXML
	private void cargarPartida() {
		Main.mostrarPartida();
	}

	@FXML
	private void salir() {
		Platform.exit();
	}
}
