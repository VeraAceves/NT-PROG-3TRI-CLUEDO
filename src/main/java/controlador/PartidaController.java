package controlador;

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

	@FXML
	private void initialize() {
		juego = Main.getJuego();
		nivel = juego.getNivelActual();
		cargarPartida();
	}

	private void cargarPartida() {
		Partida partida = juego.getPartidaActual();

		if (partida == null) {
			return;
		}

		l_rondaTxt.setText(String.valueOf(partida.getRondaActual()));
		l_puntosTxt.setText(String.valueOf(partida.getPuntosActuales()));

		l_sospechoso.setText("?");
		l_arma.setText("?");
		l_lugar.setText("?");
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

	@FXML
	private void interrogar() {
		if (sospechosoSeleccionado == null || armaSeleccionada == null || escenarioSeleccionado == null) {
			return;
		}

		try {
			juego.realizarInterrogatorio(sospechosoSeleccionado, armaSeleccionada, escenarioSeleccionado);

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
		if (sospechosoSeleccionado == null || armaSeleccionada == null || escenarioSeleccionado == null) {
			return;
		}

		try {
			juego.realizarAcusacion(sospechosoSeleccionado, armaSeleccionada, escenarioSeleccionado);

			Main.mostrarFinalPartida();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void actualizarVista() {
		Partida partida = juego.getPartidaActual();
		if (partida == null) {
			return;
		}

		l_rondaTxt.setText(String.valueOf(partida.getRondaActual()));
		l_puntosTxt.setText(String.valueOf(partida.getPuntosActuales()));

		aplicarEstilo(l_sospechoso, partida.isUltimoAcertoPersonaje());
		aplicarEstilo(l_arma, partida.isUltimoAcertoArma());
		aplicarEstilo(l_lugar, partida.isUltimoAcertoEscenario());
	}

	private void aplicarEstilo(Label label, boolean esAcertado) {
		label.getStyleClass().removeAll("label-acertado", "label-normal");

		if (esAcertado) {
			label.getStyleClass().add("label-acertado");
		} else {
			label.getStyleClass().add("label-normal");
		}
	}

	@FXML
	private void ocultarDescripcion() {
		pnlPista.setVisible(false);
		pnlPista.setManaged(false);
	}

	private void mostrarDescripcion(String descripcion) {
		if (descripcion == null)
			return;

		lblTextoPista.setText(descripcion);
		pnlPista.setVisible(true);
		pnlPista.setManaged(true);
	}

	@FXML
	private void hoverPersonaje(MouseEvent e) {
		Button btn = (Button) e.getSource();

		String nombre = btn.getText();

		for (Personaje p : nivel.getPersonajes()) {
			if (p.getNombre().equals(nombre)) {
				mostrarDescripcion(p.getDescripcion());
				return;
			}
		}
	}

	@FXML
	private void hoverArma(MouseEvent e) {
		Button btn = (Button) e.getSource();

		String nombre = btn.getText();

		for (Arma a : nivel.getArmas()) {
			if (a.getNombre().equals(nombre)) {
				mostrarDescripcion(a.getDescripcion());
				return;
			}
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

		for (Escenario s : nivel.getEscenarios()) {
			if (s.getNombre().equals(nombre)) {
				mostrarDescripcion(s.getDescripcion());
				return;
			}
		}
	}
	@FXML
	private void seleccionarPersonaje(MouseEvent e) {
	    Button btn = (Button) e.getSource();
	    String nombre = btn.getText();

	    for (Personaje p : nivel.getPersonajes()) {
	        if (p.getNombre().equals(nombre)) {
	            sospechosoSeleccionado = p;
	            l_sospechoso.setText(p.getNombre());
	            return;
	        }
	    }
	}
	@FXML
	private void seleccionarArma(MouseEvent e) {

	    Button btn = (Button) e.getSource();
	    String nombre = btn.getText();

	    for (Arma a : nivel.getArmas()) {
	        if (a.getNombre().equals(nombre)) {
	            armaSeleccionada = a;
	            l_arma.setText(a.getNombre());
	            return;
	        }
	    }
	}
	@FXML
	private void seleccionarEscenario(MouseEvent e) {

	    Button btn = (Button) e.getSource();
	    String nombre = btn.getText();

	    for (Escenario s : nivel.getEscenarios()) {
	        if (s.getNombre().equals(nombre)) {
	            escenarioSeleccionado = s;
	            l_lugar.setText(s.getNombre());
	            return;
	        }
	    }
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

}