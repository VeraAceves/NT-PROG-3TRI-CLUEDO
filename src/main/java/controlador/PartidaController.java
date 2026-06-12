package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import modelo.Juego;
import modelo.*;
import modelo.beans.*;
import modelo.enums.*;

public class PartidaController {

	@FXML
	private Label lblRonda;

	@FXML
	private Label lblPuntos;

	@FXML
	private Label lblSospechoso;

	@FXML
	private Label lblArma;

	@FXML
	private Label lblLugar;
	@FXML
	private Pane pnlPista;

	@FXML
	private Label lblTextoPista;

	private Juego juego;
	private Personaje sospechosoSeleccionado;
	private Arma armaSeleccionada;
	private Escenario escenarioSeleccionado;

	@FXML
	private void initialize() {

		juego = aplicacion.Main.getJuego();

		cargarPartida();
	}

	private void cargarPartida() {

		Partida partida = juego.getPartidaActual();

		if (partida == null) {
			System.out.println("No hay partida activa");
			return;
		}

		lblRonda.setText(String.valueOf(partida.getRondaActual()));
		lblPuntos.setText(String.valueOf(partida.getPuntosActuales()));

		lblSospechoso.setText("?");
		lblArma.setText("?");
		lblLugar.setText("?");
	}

	@FXML
	private void pedirPista() {

		try {

			String pista = juego.pedirSiguientePista();

			lblTextoPista.setText(pista);

			pnlPista.setVisible(true);
			pnlPista.setManaged(true);

		} catch (Exception e) {

			lblTextoPista.setText("No hay pistas disponibles");

			pnlPista.setVisible(true);
			pnlPista.setManaged(true);
		}
	}

	@FXML
	private void cerrarPista() {

		pnlPista.setVisible(false);
		pnlPista.setManaged(false);
	}

	@FXML
	private void interrogar() {
		try {

			if (sospechosoSeleccionado == null || armaSeleccionada == null || escenarioSeleccionado == null) {
				return;
			}

			juego.realizarHipotesis(sospechosoSeleccionado, armaSeleccionada, escenarioSeleccionado);

			sospechosoSeleccionado = null;
			armaSeleccionada = null;
			escenarioSeleccionado = null;

			actualizarVista();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void acusar() {

		try {

			boolean resultado = juego.lanzarAcusacionDefinitiva(sospechosoSeleccionado, armaSeleccionada,
					escenarioSeleccionado);

			juego.getPartidaActual().setResultado(resultado ? ResultadoPartida.VICTORIA : ResultadoPartida.DERROTA);

			juego.getPartidaActual().finalizarPartida();

			aplicacion.Main.mostrarFinalPartida();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void actualizarVista() {

		Partida partida = juego.getPartidaActual();
		if (partida == null)
			return;

		lblRonda.setText(String.valueOf(partida.getRondaActual()));
		lblPuntos.setText(String.valueOf(partida.getPuntosActuales()));

		aplicarEstilo(lblSospechoso, partida.isUltimoAcertoPersonaje());
		aplicarEstilo(lblArma, partida.isUltimoAcertoArma());
		aplicarEstilo(lblLugar, partida.isUltimoAcertoEscenario());
	}

	private void aplicarEstilo(Label label, boolean esAcertado) {
		// Solo cambia las clases CSS, no los estilos directamente
		label.getStyleClass().removeAll("label-acertado", "label-normal");

		if (esAcertado) {
			label.getStyleClass().add("label-acertado");
		} else {
			label.getStyleClass().add("label-normal");
		}
	}
}