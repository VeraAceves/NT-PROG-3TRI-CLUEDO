package controlador;

import aplicacion.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import modelo.beans.Partida;
import Persistencia.PartidaDAO;

import java.util.List;

public class FinalPartidaController {
	@FXML
	private VBox pnlHistoria;

	@FXML
	private Label lblTituloHistoria;

	@FXML
	private TextArea txtHistoria;

	@FXML
	private Button b_volver;

	@FXML
	private Button b_historia;

	@FXML
	private Label id_dUser_user1;

	@FXML
	private Label id_dUser_user2;

	@FXML
	private Label id_dUser_user3;

	@FXML
	private Label id_dEstado_user1;

	@FXML
	private Label id_dEstado_user2;

	@FXML
	private Label id_dEstado_user3;

	@FXML
	private Label id_dPunts_user1;

	@FXML
	private Label id_dPunts_user2;

	@FXML
	private Label id_dPunts_user3;

	@FXML
	private Label id_dRonda_user1;

	@FXML
	private Label id_dRonda_user2;

	@FXML
	private Label id_dRonda_user3;

	private PartidaDAO partidaDAO;
	private List<Partida> historial;

	@FXML
	private void initialize() {

		partidaDAO = new PartidaDAO();

		String idJugador = Main.getJuego().getJugadorActual().getIdJugador();

		historial = partidaDAO.obtenerHistorial(idJugador);

		cargarTabla();
	}

	private void cargarTabla() {

		if (historial == null || historial.isEmpty()) {
			return;
		}

		// Fila 1
		if (historial.size() > 0) {
			cargarFila(historial.get(0), id_dUser_user1, id_dEstado_user1, id_dPunts_user1, id_dRonda_user1);
		}

		// Fila 2
		if (historial.size() > 1) {
			cargarFila(historial.get(1), id_dUser_user2, id_dEstado_user2, id_dPunts_user2, id_dRonda_user2);
		}

		// Fila 3
		if (historial.size() > 2) {
			cargarFila(historial.get(2), id_dUser_user3, id_dEstado_user3, id_dPunts_user3, id_dRonda_user3);
		}
	}

	private void cargarFila(Partida p, Label user, Label estado, Label puntos, Label ronda) {

		user.setText(p.getJugador() != null ? p.getJugador().getNombre() : "-");

		estado.setText(p.getEstado() != null ? p.getEstado().toString() : "-");

		puntos.setText(String.valueOf(p.getPuntosActuales()));
		ronda.setText(String.valueOf(p.getRondaActual()));
	}

	@FXML
	private void mostrarHistoria() {

		try {

			Partida partida = Main.getJuego().getPartidaActual();

			if (partida == null || partida.getNivel() == null) {
				txtHistoria.setText("No hay historia disponible");
			}

			NivelDAO nivelDAO = new NivelDAO();

			String cronica = nivelDAO.obtenerCronicaCompleta(partida.getNivel().getIdNivel());

			lblTituloHistoria.setText("Nivel " + partida.getNivel().getIdNivel());
			txtHistoria.setText(cronica);

			pnlHistoria.setVisible(true);
			pnlHistoria.setManaged(true);

		} catch (Exception e) {

			txtHistoria.setText("Error al cargar la historia");

			pnlHistoria.setVisible(true);
			pnlHistoria.setManaged(true);
		}
	}

	@FXML
	private void cerrarHistoria() {
		pnlHistoria.setVisible(false);
		pnlHistoria.setManaged(false);
	}

	@FXML
	private void volverMenu() {
		Main.mostrarInicio();
	}
}