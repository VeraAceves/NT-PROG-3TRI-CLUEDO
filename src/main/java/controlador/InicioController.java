package controlador;

import Persistencia.NivelDAO;
import Persistencia.PartidaDAO;
import aplicacion.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import modelo.Juego;
import modelo.beans.Nivel;
import modelo.beans.Partida;

public class InicioController {
	@FXML
	private Button btnNueva;

	@FXML
	private Button btnCargar;

	@FXML
	private Button btnSalir;

	@FXML
	private ImageView imagenFondo;

	@FXML
	private StackPane raizMenu;

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
			Partida partida = Main.getPartidaDAO().obtenerUltimaPartida();
			if (partida == null) {
				btnCargar.setText("No hay partidas guardadas");
				btnCargar.setDisable(true);
				return;
			}

			String idNivel = partida.getNivel().getIdNivel();
			Nivel nivelCompleto = new NivelDAO().obtenerNivelPorId(idNivel);
			if (nivelCompleto == null) {
				System.err.println("No se encontró el nivel con id: " + idNivel);
				btnCargar.setText("Error: nivel no encontrado");
				return;
			}
			partida.setNivel(nivelCompleto);

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
