package controlador;

import Persistencia.JugadorDAO;
import Persistencia.NivelDAO;
import aplicacion.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import modelo.Juego;
import modelo.beans.Jugador;
import modelo.beans.Nivel;

public class MenuPartidaController {

	@FXML
	private TextField tf_nomUser;

	@FXML
	private Button b_nivel1;
	@FXML
	private Button b_nivel2;
	@FXML
	private Button b_nivel3;
	@FXML
	private Button b_nivel4;
	@FXML
	private Button b_nivel5;

	@FXML
	private Button b_volver;

	@FXML
	private Label lblEstado;

	private final JugadorDAO jugadorDAO = new JugadorDAO();

	private Jugador jugadorValidado;
	private String idNivelSeleccionado;

	@FXML
	private void initialize() {

		deshabilitarNiveles(true);
	}

	@FXML
	private void validarNombre() {

		String nombre = tf_nomUser.getText();

		if (nombre == null || nombre.trim().isEmpty()) {
			lblEstado.setText("Debes introducir un nombre");
			return;
		}

		Jugador j = new Jugador();
		j.setNombre(nombre.trim());

		if (!j.nombreValido()) {
			lblEstado.setText("Nombre no válido");
			return;
		}

		try {
			if (jugadorDAO.existeJugador(nombre)) {
				lblEstado.setText("Ese nombre ya existe");
				return;
			}
		} catch (Exception e) {
			lblEstado.setText("Error al validar jugador");
			return;
		}

		jugadorValidado = j;

		lblEstado.setText("Jugador validado");

		deshabilitarNiveles(false);
	}

	@FXML
	private void guardarJugador() {

		try {

			if (jugadorValidado == null) {
				lblEstado.setText("Primero valida el jugador");
				return;
			}

			jugadorDAO.guardarJugador(jugadorValidado);

			lblEstado.setText("Jugador guardado correctamente");

		} catch (Exception e) {
			lblEstado.setText("Error inesperado al guardar jugador");
		}
	}

	@FXML
	private void seleccionarNivel(ActionEvent event) {

		if (jugadorValidado == null) {
			lblEstado.setText("Primero valida el jugador");
			return;
		}

		Button btn = (Button) event.getSource();
		String texto = btn.getText();

		switch (texto) {
		case "Nivel 1":
			idNivelSeleccionado = "NIVEL_01";
			break;
		case "Nivel 2":
			idNivelSeleccionado = "NIVEL_02";
			break;
		case "Nivel 3":
			idNivelSeleccionado = "NIVEL_03";
			break;
		case "Nivel 4":
			idNivelSeleccionado = "NIVEL_04";
			break;
		case "Nivel 5":
			idNivelSeleccionado = "NIVEL_05";
			break;
		}

		lblEstado.setText("Nivel seleccionado: " + texto + " → iniciando partida...");

		iniciarPartida();
	}

	private void iniciarPartida() {
		if (jugadorValidado == null || idNivelSeleccionado == null) {
			lblEstado.setText("Faltan datos para iniciar la partida");
			return;
		}

		try {
			jugadorDAO.guardarJugador(jugadorValidado);
		} catch (Exception e) {
			lblEstado.setText("Error al guardar el jugador");
			return;
		}

		Nivel nivel = new NivelDAO().obtenerNivelPorId(idNivelSeleccionado);

		if (nivel == null) {
			lblEstado.setText("ERROR: No se pudo cargar el nivel. Revisa la base de datos.");
			System.err.println("Nivel no encontrado: " + idNivelSeleccionado);
			return;
		}

		if (nivel.getAsesino() == null || nivel.getArmaCrimen() == null || nivel.getEscenarioCrimen() == null) {
			lblEstado.setText("ERROR: El nivel no tiene definida la solución (asesino, arma o escenario).");
			return;
		}
		System.out.println("Nivel cargado: " + nivel.getIdNivel());
		System.out.println("Descripción: " + nivel.getDescripcion());
		System.out.println("Pistas: " + (nivel.getPistas() != null ? nivel.getPistas().size() : 0));
		System.out.println("Asesino: " + (nivel.getAsesino() != null ? nivel.getAsesino().getNombre() : "null"));
		Juego juego = Main.getJuego();
		juego.iniciarNuevaPartida(jugadorValidado, nivel);
		Main.mostrarPartida();
	}

	@FXML
	private void volverMenu() {
		Main.mostrarInicio();
	}

	private void deshabilitarNiveles(boolean estado) {
		b_nivel1.setDisable(estado);
		b_nivel2.setDisable(estado);
		b_nivel3.setDisable(estado);
		b_nivel4.setDisable(estado);
		b_nivel5.setDisable(estado);
	}
}