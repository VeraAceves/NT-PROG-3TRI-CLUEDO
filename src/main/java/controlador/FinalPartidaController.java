package controlador;

import java.util.List;

import Persistencia.NivelDAO;
import Persistencia.PartidaDAO;
import aplicacion.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import modelo.Juego;
import modelo.beans.Partida;

/**
 * Controlador de la pantalla final de partida. Muestra el resultado de la
 * partida actual, el historial de partidas del jugador y la historia asociada
 * al nivel.
 */
public class FinalPartidaController {

	@FXML
	private VBox pnlHistoria;

	@FXML
	private Label lblTituloHistoria;

	@FXML
	private TextArea txtHistoria;

	@FXML
	private TableView<Partida> tablaHistorial;

	@FXML
	private TableColumn<Partida, String> colUsuario;

	@FXML
	private TableColumn<Partida, String> colResultado;

	@FXML
	private TableColumn<Partida, Integer> colPuntos;

	@FXML
	private TableColumn<Partida, String> colNivel;

	@FXML
	private Button b_volver;

	@FXML
	private Button b_historia;

	@FXML
	private Label lblResultado;

	private PartidaDAO partidaDAO;

	private NivelDAO nivelDAO;

	private ObservableList<Partida> datosTabla = FXCollections.observableArrayList();

	/**
	 * Inicializa los componentes de la vista. Configura las columnas de la tabla y
	 * oculta el panel de historia.
	 */
	@FXML
	private void initialize() {

		partidaDAO = new PartidaDAO();
		nivelDAO = new NivelDAO();

		pnlHistoria.setVisible(false);
		pnlHistoria.setManaged(false);

		configurarColumnas();
	}

	/**
	 * Carga los datos de la partida actual y el historial del jugador.
	 */
	public void cargarDatos() {

		Juego juego = Main.getJuego();

		if (juego == null || juego.getPartidaActual() == null)
			return;

		Partida partidaActual = juego.getPartidaActual();

		if (partidaActual.getJugador() == null)
			return;

		String idJugador = partidaActual.getJugador().getNombre();

		List<Partida> historial = partidaDAO.obtenerHistorial(idJugador);

		datosTabla.setAll(historial);
		tablaHistorial.setItems(datosTabla);

		cargarResultado();
	}

	/**
	 * Muestra el resultado de la partida actual.
	 */
	private void cargarResultado() {

		Juego juego = Main.getJuego();

		if (juego == null || juego.getPartidaActual() == null) {
			lblResultado.setText("No hay partida actual");
			return;
		}

		Partida p = juego.getPartidaActual();

		lblResultado.setText(p.getResultado() != null ? p.getResultado().toString() : "EN_CURSO");
	}

	/**
	 * Configura las columnas de la tabla de historial.
	 */
	private void configurarColumnas() {

		colUsuario.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getJugador() != null ? data.getValue().getJugador().getNombre() : "-"));

		colResultado.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getResultado() != null ? data.getValue().getResultado().toString() : "EN_CURSO"));

		colPuntos.setCellValueFactory(
				data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPuntosActuales()));

		colNivel.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
				data.getValue().getNivel() != null ? data.getValue().getNivel().getIdNivel() : "-"));
	}

	/**
	 * Muestra la historia correspondiente al nivel jugado.
	 */
	@FXML
	private void mostrarHistoria() {

		try {

			Partida partida = Main.getJuego().getPartidaActual();

			if (partida == null || partida.getNivel() == null) {
				txtHistoria.setText("No hay historia disponible");
			} else {

				String cronica = nivelDAO.obtenerCronicaCompleta(partida.getNivel().getIdNivel());

				lblTituloHistoria.setText("Nivel " + partida.getNivel().getIdNivel());
				txtHistoria.setText(cronica);
			}

			pnlHistoria.setVisible(true);
			pnlHistoria.setManaged(true);

			pnlHistoria.toFront();

		} catch (Exception e) {

			txtHistoria.setText("Error al cargar la historia");

			pnlHistoria.setVisible(true);
			pnlHistoria.setManaged(true);

			pnlHistoria.toFront();
		}
	}

	/**
	 * Oculta el panel de historia.
	 */
	@FXML
	private void cerrarHistoria() {
		pnlHistoria.setVisible(false);
		pnlHistoria.setManaged(false);
	}

	/**
	 * Regresa a la pantalla inicial de la aplicación.
	 */
	@FXML
	private void volverMenu() {
		Main.mostrarInicio();
	}
}