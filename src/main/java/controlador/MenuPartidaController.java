package controlador;

import Persistencia.*;
import aplicacion.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Juego;
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
	private TextField txtNombre;

	private final JugadorDAO jugadorDAO = new JugadorDAO();
	private Jugador jugador;
	private String idNivelSeleccionado;

	@FXML
	private void initialize() {

	}

	@FXML
	private void validarNombre() {

		String nombre = txtNombre.getText();

		Jugador j = new Jugador();
		j.setNombre(nombre);

		if (!j.nombreValido()) {
			System.out.println("Nombre no válido");
			return;
		}

		try {
			if (jugadorDAO.existeJugador(nombre)) {
				System.out.println("Ya existe ese nombre");
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		this.jugador = j;

		System.out.println("Jugador validado correctamente");
	}

	@FXML
	private void seleccionarNivel(ActionEvent event) {

		Button btn = (Button) event.getSource();

		switch (btn.getText()) {
		case "Nivel 1":
			idNivelSeleccionado = "N1";
			break;
		case "Nivel 2":
			idNivelSeleccionado = "N2";
			break;
		case "Nivel 3":
			idNivelSeleccionado = "N3";
			break;
		case "Nivel 4":
			idNivelSeleccionado = "N4";
			break;
		case "Nivel 5":
			idNivelSeleccionado = "N5";
			break;
		case "Nivel 6":
			idNivelSeleccionado = "N6";
			break;
		}

		System.out.println("Nivel seleccionado: " + idNivelSeleccionado);
	}

	@FXML
	private void iniciarPartida() {

		if (jugador == null) {
			System.out.println("Debes validar el jugador");
			return;
		}

		if (idNivelSeleccionado == null) {
			System.out.println("Debes seleccionar un nivel");
			return;
		}

		Juego juego = Main.getJuego();

		juego.iniciarNuevaPartida(jugador, idNivelSeleccionado);

		Main.mostrarPartida();
	}
}