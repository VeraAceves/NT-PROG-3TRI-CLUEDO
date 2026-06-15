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

/**
 * Controlador de la pantalla de inicio. Gestiona las acciones para crear una
 * nueva partida, cargar una partida guardada o salir de la aplicación.
 */
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

	/**
	 * Inicializa los componentes de la vista.
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * Muestra la pantalla de configuración de una nueva partida.
	 */
	@FXML
	private void nuevaPartida() {
		Main.mostrarMenuPartida();
	}

	/**
	 * Carga la última partida guardada y muestra la pantalla de juego. Recupera
	 * también la información completa del nivel asociado.
	 */
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

	/**
	 * Cierra la aplicación.
	 */
	@FXML
	private void salir() {
		Platform.exit();
	}
}