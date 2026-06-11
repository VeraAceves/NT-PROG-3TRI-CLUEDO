package controlador;

import Persistencia.PartidaDAO;
import aplicacion.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import modelo.Juego;
import modelo.beans.Partida;

public class InicioController {
	@FXML
	private Button btnNueva;

	@FXML
	private Button btnCargar;

	@FXML
	private Button btnSalir;

	private PartidaDAO partidaDAO = new PartidaDAO();

	@FXML
	private void initialize() {

	}

	@FXML
	private void nuevaPartida() {
		Main.mostrarMenuPartida();
	}

	@FXML
	private void cargarPartida() {

		try {

			Partida partida = partidaDAO.obtenerUltimaPartida();

			if (partida == null) {
				System.out.println("No existe ninguna partida guardada");
				return;
			}

			Juego juego = Main.getJuego();
			juego.setPartidaActual(partida);

			Main.mostrarPartida();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void salir() {
		Platform.exit();
	}
}
