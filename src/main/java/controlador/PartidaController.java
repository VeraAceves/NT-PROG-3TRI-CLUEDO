package controlador;

import java.util.List;

import aplicacion.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import modelo.Juego;
import modelo.beans.*;
import modelo.enums.*;
import modelo.lore.*;

public class PartidaController {

	// Botones (todos con prefijo b_)
	@FXML
	private Button b_erion;
	@FXML
	private Button b_etharel;
	@FXML
	private Button b_estigia;
	@FXML
	private Button b_lucette;
	@FXML
	private Button b_dis;
	@FXML
	private Button b_alastar;
	@FXML
	private Button b_trono;
	@FXML
	private Button b_archivo;
	@FXML
	private Button b_dormitorio;
	@FXML
	private Button b_forja;
	@FXML
	private Button b_despensa;

	@FXML
	private Button b_invernadero;
	@FXML
	private Button b_atizador;
	@FXML
	private Button b_candelabro;
	@FXML
	private Button b_esencia;
	@FXML
	private Button b_baculo;
	@FXML
	private Button b_caliz;
	@FXML
	private Button b_abrecartas;
	@FXML
	private Button b_guardar;
	@FXML
	private Button b_guardarSalir;
	@FXML
	private Button b_salir;
	@FXML
	private Button b_deducir;
	@FXML
	private Button b_acusar;
	@FXML
	private Button b_asesinato;
	@FXML
	private Button b_pista1;
	@FXML
	private Button b_pista2;
	@FXML
	private Button b_pista3;

	// Labels
	@FXML
	private Label l_rondaTxt;
	@FXML
	private Label l_puntosTxt;
	@FXML
	private Label l_sospechoso;
	@FXML
	private Label l_arma;
	@FXML
	private Label l_lugar;

	// Panes
	@FXML
	private Pane pnlPista;
	@FXML
	private HBox hbox_datosPartida;
	@FXML
	private BorderPane raizPartida;

	@FXML
	private Label lblTextoPista;

	private Juego juego;
	private Nivel nivel;
	private Personaje sospechosoSeleccionado;
	private Arma armaSeleccionada;
	private Escenario escenarioSeleccionado;
	private Button btnPersonajeSeleccionado;
	private Button btnArmaSeleccionada;
	private Button btnEscenarioSeleccionado;

	@FXML
	private void initialize() {

	}

	// Partida
	public void setJuego(Juego juego) {
		this.juego = juego;
		this.nivel = juego.getNivelActual();
		juego.inicializarNivel(nivel);
		cargarPartida();
	}

	private void cargarPartida() {
		Partida partida = juego.getPartidaActual();

		if (partida == null) {
			return;
		}

		l_rondaTxt.setText(String.valueOf(partida.getRondaActual()));
		l_puntosTxt.setText(String.valueOf(partida.getPuntosActuales()));

		l_sospechoso.setText("—");
		l_arma.setText("—");
		l_lugar.setText("—");
	}

	@FXML
	private void guardarPartida() {
		try {
			juego.guardarPartidaActual();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void salirMenu() {
		try {
			Main.mostrarInicio();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	private void guardarYSalir() {
		try {
			guardarPartida();
			salirMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Pistas
	@FXML
	private void mostrarAsesinato() {

		try {
			String descripcion = juego.obtenerDescripcionNivel();

			lblTextoPista.setText(descripcion);
			pnlPista.setVisible(true);
			pnlPista.setManaged(true);

		} catch (Exception e) {
			lblTextoPista.setText("No hay información del caso disponible");
			pnlPista.setVisible(true);
			pnlPista.setManaged(true);
		}
	}

	@FXML
	private void pedirPista(ActionEvent event) {

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

	// Flujo Principal

	@FXML
	private void interrogar() {

		if (!hipotesisCompleta()) {
			return;
		}

		juego.realizarInterrogatorio(sospechosoSeleccionado, armaSeleccionada, escenarioSeleccionado);

		Partida p = juego.getPartidaActual();

		marcarBoton(btnPersonajeSeleccionado, p.isUltimoAcertoPersonaje());
		marcarBoton(btnArmaSeleccionada, p.isUltimoAcertoArma());
		marcarBoton(btnEscenarioSeleccionado, p.isUltimoAcertoEscenario());

		if (juego.getPartidaActual().getEstado() == EstadoPartida.FINALIZADA) {
			juego.guardarPartidaActual();
			Main.mostrarFinalPartida();
			return;
		}
		actualizarVista();
		limpiarSeleccion();
	}

	@FXML
	private void acusar() {

		if (!hipotesisCompleta())
			return;

		juego.realizarAcusacion(sospechosoSeleccionado, armaSeleccionada, escenarioSeleccionado);
		juego.guardarPartidaActual();
		Main.mostrarFinalPartida();
	}

	@FXML
	private void seleccionarPersonaje(ActionEvent e) {

		Button btn = (Button) e.getSource();
		String nombre = btn.getText();

		for (Personaje p : nivel.getPersonajes()) {
			if (p.getNombre().equals(nombre)) {
				if (btnPersonajeSeleccionado != null) {
					btnPersonajeSeleccionado.getStyleClass().remove("btn-seleccionado-actual");
				}
				sospechosoSeleccionado = p;
				l_sospechoso.setText(p.getNombre());
				btnPersonajeSeleccionado = btn;

				btnPersonajeSeleccionado.getStyleClass().add("btn-seleccionado-actual");
				actualizarBotones();
				return;
			}
		}
	}

	@FXML
	private void seleccionarArma(ActionEvent e) {

		Button btn = (Button) e.getSource();
		String nombre = btn.getText();

		for (Arma a : nivel.getArmas()) {
			if (a.getNombre().equals(nombre)) {

				if (btnArmaSeleccionada != null) {
					btnArmaSeleccionada.getStyleClass().remove("btn-seleccionado-actual");
				}
				armaSeleccionada = a;
				l_arma.setText(a.getNombre());
				btnArmaSeleccionada = btn;

				btnArmaSeleccionada.getStyleClass().add("btn-seleccionado-actual");
				actualizarBotones();
				return;
			}
		}
	}

	@FXML
	private void seleccionarEscenario(ActionEvent e) {

		Button btn = (Button) e.getSource();
		String nombre = btn.getText();

		for (Escenario s : nivel.getEscenarios()) {
			if (s.getNombre().equals(nombre)) {

				if (btnEscenarioSeleccionado != null) {
					btnEscenarioSeleccionado.getStyleClass().remove("btn-seleccionado-actual");
				}
				escenarioSeleccionado = s;
				l_lugar.setText(s.getNombre());
				btnEscenarioSeleccionado = btn;

				btnEscenarioSeleccionado.getStyleClass().add("btn-seleccionado-actual");
				actualizarBotones();
				return;
			}
		}
	}

	// Hover

	private void mostrarDescripcion(String descripcion) {
		if (descripcion == null)
			return;

		lblTextoPista.setText(descripcion);
		pnlPista.setVisible(true);
		pnlPista.setManaged(true);
	}

	@FXML
	private void ocultarDescripcion() {
		pnlPista.setVisible(false);
		pnlPista.setManaged(false);
	}

	@FXML
	private void hoverPersonaje(MouseEvent e) {
		Button btn = (Button) e.getSource();

		String nombre = btn.getText();

		LorePersonaje lore = LorePersonaje.get(nombre);

		if (lore != null) {
			mostrarDescripcion(lore.getDescripcion());
		}
	}

	@FXML
	private void hoverArma(MouseEvent e) {
		Button btn = (Button) e.getSource();

		String nombre = btn.getText();

		LoreArma lore = LoreArma.get(nombre);

		if (lore != null) {
			mostrarDescripcion(lore.getDescripcion());
		}
	}

	@FXML
	private void hoverEscenario(MouseEvent e) {
		Object node = e.getSource();

		String nombre;

		if (node instanceof Button) {
			nombre = ((Button) node).getText();
		} else {
			nombre = ((Text) node).getText();
		}

		LoreEscenario lore = LoreEscenario.get(nombre);

		if (lore != null) {
			mostrarDescripcion(lore.getDescripcion());
		}
	}

	@FXML
	private void hoverAmbientacion(MouseEvent e) {
		mostrarDescripcion(LoreHistoria.get());
	}

	// Utilidades
	private void actualizarVista() {
		Partida partida = juego.getPartidaActual();
		if (partida == null) {
			return;
		}

		l_rondaTxt.setText(String.valueOf(partida.getRondaActual()));
		l_puntosTxt.setText(String.valueOf(partida.getPuntosActuales()));

	}

	private void marcarBoton(Button b, boolean acierto) {
		if (b == null)
			return;

		b.getStyleClass().removeAll("btn-normal", "btn-usado", "btn-acierto", "btn-error", "btn-seleccionado-actual");

		if (acierto) {
			b.getStyleClass().add("btn-acierto");

		} else {
			b.getStyleClass().add("btn-error");

		}
	}

	private boolean hipotesisCompleta() {
		return sospechosoSeleccionado != null && armaSeleccionada != null && escenarioSeleccionado != null;
	}

	private void actualizarBotones() {
		boolean habilitar = hipotesisCompleta();
		b_deducir.setDisable(!habilitar);
		b_acusar.setDisable(!habilitar);
	}

	private void limpiarSeleccion() {

		if (btnPersonajeSeleccionado != null)
			btnPersonajeSeleccionado.getStyleClass().remove("btn-seleccionado-actual");
		if (btnArmaSeleccionada != null)
			btnArmaSeleccionada.getStyleClass().remove("btn-seleccionado-actual");
		if (btnEscenarioSeleccionado != null)
			btnEscenarioSeleccionado.getStyleClass().remove("btn-seleccionado-actual");

		btnPersonajeSeleccionado = null;
		btnArmaSeleccionada = null;
		btnEscenarioSeleccionado = null;

		sospechosoSeleccionado = null;
		armaSeleccionada = null;
		escenarioSeleccionado = null;

		l_sospechoso.setText("—");
		l_arma.setText("—");
		l_lugar.setText("—");

		actualizarBotones();

		if (raizPartida != null) {
			raizPartida.requestFocus();
		}
	}
}
