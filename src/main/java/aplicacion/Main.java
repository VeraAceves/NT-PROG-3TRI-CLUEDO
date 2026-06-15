package aplicacion;

import java.io.IOException;

import Persistencia.PartidaDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Juego;
import controlador.PartidaController;

public class Main extends Application {

	private static Stage stage;

	private static Scene escenaInicio;
	private static Scene escenaMenuPartida;
	private static Scene escenaFinalPartida;

	private static Juego juego = new Juego();
	private static PartidaDAO partidaDAO = new PartidaDAO();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;

			escenaInicio = new Scene(FXMLLoader.load(getClass().getResource("/vista/MenuInicial.fxml")));

			escenaMenuPartida = new Scene(FXMLLoader.load(getClass().getResource("/vista/MenuPartida.fxml")));

			escenaFinalPartida = new Scene(FXMLLoader.load(getClass().getResource("/vista/FinalPartida.fxml")));

			stage.setScene(escenaInicio);
			stage.setMaximized(true);
			stage.setTitle("Cluedo Fantasy");
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void mostrarInicio() {
		stage.setScene(escenaInicio);
	}

	public static void mostrarMenuPartida() {
		stage.setScene(escenaMenuPartida);
	}

	public static void mostrarFinalPartida() {
		stage.setScene(escenaFinalPartida);
	}

	public static void mostrarPartida() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/vista/Partida.fxml"));
			Scene scene = new Scene(loader.load());

			PartidaController controller = loader.getController();
			controller.setJuego(juego);

			stage.setScene(scene);
			stage.setMaximized(true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void reiniciarJuego() {
		juego = new Juego();
	}

	public static Juego getJuego() {
		return juego;
	}

	public static PartidaDAO getPartidaDAO() {
		return partidaDAO;
	}
}