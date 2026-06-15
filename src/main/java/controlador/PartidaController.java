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

/**
 * Controlador de la pantalla principal de juego. Gestiona la interacción del
 * jugador durante la partida, incluyendo la selección de hipótesis,
 * interrogatorios, acusaciones, pistas y navegación.
 */
public class PartidaController {

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
	private Button[] botonesPista;

	/**
	 * Inicializa los componentes de la vista.
	 */
	@FXML
	private void initialize() {

	}

	// Partida

	/**
	 * Asigna la instancia del juego al controlador y carga la partida.
	 *
	 * @param juego instancia del juego en ejecución
	 */
	public void setJuego(Juego juego) {
		this.juego = juego;
		this.nivel = juego.getNivelActual();
		juego.inicializarNivel(nivel);
		cargarPartida();
		inicializarBotonesPista();
	}

	/**
	 * Carga los datos de la partida actual en la interfaz.
	 */
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

	/**
	 * Guarda la partida actual.
	 */
	@FXML
	private void guardarPartida() {
		try {
			juego.guardarPartidaActual();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Regresa al menú principal.
	 */
	@FXML
	private void salirMenu() {
		try {
			Main.mostrarInicio();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Guarda la partida actual y vuelve al menú principal.
	 */
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

	/**
	 * Inicializa el estado de los botones de pistas.
	 */
	private void inicializarBotonesPista() {

		botonesPista = new Button[] { b_pista1, b_pista2, b_pista3 };

		Partida partida = juego.getPartidaActual();

		if (partida != null) {
			for (int i = 0; i < botonesPista.length && i < partida.getNivel().getNumeroPistas(); i++) {
				if (partida.isPistaSolicitada(i)) {
					botonesPista[i].setDisable(true);
				}
			}
		}
	}

	/**
	 * Solicita una pista concreta del nivel.
	 *
	 * @param indice posición de la pista solicitada
	 */
	private void pedirPistaPorIndice(int indice) {

		try {

			String pista = juego.pedirPista(indice);

			if (pista == null) {
				lblTextoPista.setText("Pista no disponible (ya solicitada o inválida).");
			} else {

				lblTextoPista.setText(pista);

				if (botonesPista != null && indice < botonesPista.length) {
					botonesPista[indice].setDisable(true);
				}

				actualizarVista();
			}

			pnlPista.setVisible(true);
			pnlPista.setManaged(true);

		} catch (Exception e) {

			lblTextoPista.setText("Error al obtener la pista.");
			pnlPista.setVisible(true);
			pnlPista.setManaged(true);
		}
	}

	/**
	 * Muestra la descripción inicial del caso.
	 */
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

	/**
	 * Solicita la primera pista.
	 */
	@FXML
	private void pedirPista1() {
		pedirPistaPorIndice(0);
	}

	/**
	 * Solicita la segunda pista.
	 */
	@FXML
	private void pedirPista2() {
		pedirPistaPorIndice(1);
	}

	/**
	 * Solicita la tercera pista.
	 */
	@FXML
	private void pedirPista3() {
		pedirPistaPorIndice(2);
	}

	/**
	 * Oculta el panel de pistas.
	 */
	@FXML
	private void cerrarPista() {
		pnlPista.setVisible(false);
		pnlPista.setManaged(false);
	}

	// Flujo Principal

	/**
	 * Realiza un interrogatorio con la hipótesis seleccionada. Actualiza la partida
	 * y marca los aciertos obtenidos.
	 */
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

	/**
	 * Realiza una acusación con la hipótesis seleccionada y finaliza la partida.
	 */
	@FXML
	private void acusar() {

		if (!hipotesisCompleta())
			return;

		juego.realizarAcusacion(sospechosoSeleccionado, armaSeleccionada, escenarioSeleccionado);
		juego.guardarPartidaActual();
		Main.mostrarFinalPartida();
	}

	/**
	 * Gestiona la selección de un personaje sospechoso.
	 *
	 * @param e evento generado al pulsar un personaje
	 */
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

	/**
	 * Gestiona la selección de un arma.
	 *
	 * @param e evento generado al pulsar un arma
	 */
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

	/**
	 * Gestiona la selección de un escenario.
	 *
	 * @param e evento generado al pulsar un escenario
	 */
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

	/**
	 * Muestra una descripción en el panel de información.
	 *
	 * @param descripcion texto a mostrar
	 */
	private void mostrarDescripcion(String descripcion) {

		if (descripcion == null)
			return;

		lblTextoPista.setText(descripcion);
		pnlPista.setVisible(true);
		pnlPista.setManaged(true);
	}

	/**
	 * Oculta el panel de descripción.
	 */
	@FXML
	private void ocultarDescripcion() {
		pnlPista.setVisible(false);
		pnlPista.setManaged(false);
	}

	/**
	 * Muestra la descripción del personaje bajo el cursor.
	 *
	 * @param e evento de ratón
	 */
	@FXML
	private void hoverPersonaje(MouseEvent e) {

		Button btn = (Button) e.getSource();

		String nombre = btn.getText();

		LorePersonaje lore = LorePersonaje.get(nombre);

		if (lore != null) {
			mostrarDescripcion(lore.getDescripcion());
		}
	}

	/**
	 * Muestra la descripción del arma bajo el cursor.
	 *
	 * @param e evento de ratón
	 */
	@FXML
	private void hoverArma(MouseEvent e) {

		Button btn = (Button) e.getSource();

		String nombre = btn.getText();

		LoreArma lore = LoreArma.get(nombre);

		if (lore != null) {
			mostrarDescripcion(lore.getDescripcion());
		}
	}

	/**
	 * Muestra la descripción del escenario bajo el cursor.
	 *
	 * @param e evento de ratón
	 */
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

	/**
	 * Muestra la ambientación general del juego.
	 *
	 * @param e evento de ratón
	 */
	@FXML
	private void hoverAmbientacion(MouseEvent e) {
		mostrarDescripcion(LoreHistoria.get());
	}

	// Utilidades

	/**
	 * Actualiza los valores visibles de la partida en la interfaz.
	 */
	private void actualizarVista() {

		Partida partida = juego.getPartidaActual();

		if (partida == null) {
			return;
		}

		l_rondaTxt.setText(String.valueOf(partida.getRondaActual()));
		l_puntosTxt.setText(String.valueOf(partida.getPuntosActuales()));
	}

	/**
	 * Aplica el estilo visual correspondiente al resultado de una acción.
	 *
	 * @param b       botón a modificar
	 * @param acierto true si el resultado es correcto
	 */
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

	/**
	 * Comprueba si la hipótesis actual está completa.
	 *
	 * @return true si hay personaje, arma y escenario seleccionados
	 */
	private boolean hipotesisCompleta() {
		return sospechosoSeleccionado != null && armaSeleccionada != null && escenarioSeleccionado != null;
	}

	/**
	 * Habilita o deshabilita los botones de deducir y acusar según si la hipótesis
	 * está completa.
	 */
	private void actualizarBotones() {

		boolean habilitar = hipotesisCompleta();

		b_deducir.setDisable(!habilitar);
		b_acusar.setDisable(!habilitar);
	}

	/**
	 * Limpia todas las selecciones actuales del jugador.
	 */
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
