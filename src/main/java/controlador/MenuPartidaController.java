package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import modelo.beans.Jugador;

public class MenuPartidaController {
	@FXML
	private Button btnValidarJugador;

	@FXML
	private Button btnNivel1;

	@FXML
	private Button btnNivel2;

	@FXML
	private Button btnNivel3;

	@FXML
	private Button btnNivel4;

	@FXML
	private Button btnNivel5;

	@FXML
	private Button btnNivel6;

	@FXML
	Text txtNombre;

	@FXML
	private void initialize() {

	}

	@FXML
	private void validarNombre() {
		String nombre = txtNombre.getText();

		Jugador jugador = new Jugador();
		jugador.setNombre(nombre);

		if (!jugador.nombreValido()) {
			System.out.println("Nombre no válido");
			return;
		}

		if (jugadorDAO.existeJugador(nombre)) {
			mostrarError("Ese nombre ya existe");
			return;
		}

		this.jugador = jugador;
	}

	@FXML
	private void seleccionarNivel() {

	}

	@FXML
	private void iniciarPartida() {

	}

}
