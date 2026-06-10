package controlador;

import aplicacion.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modelo.beans.Partida;

public class FinalPartidaController {
	@FXML
	private Label lblResultado;
	
	@FXML
	private Button btnVerHistorial;
	
	@FXML
	private Button btnLeerHistoria;
	
	@FXML
	private Button btnVolverMenu;

	private Partida partida;
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void mostrarResultado() {
		lblResultado.setText(partida.getResultado().toString());
	}
	
	@FXML
	private void mostrarHistorial() {
		
	}
	
	@FXML
	private void mostrarHistoria() {
		
	}
	
	@FXML
	private void volverMenu() {
		Main.mostrarInicio();
	}
}
