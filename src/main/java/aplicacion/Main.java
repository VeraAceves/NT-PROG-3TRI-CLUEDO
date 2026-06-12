package aplicacion;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import modelo.Juego;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	private static Stage stage;
	private static Scene escenaInicio;
	private static Scene escenaMenuPartida;
	private static Scene escenaPartida;
	private static Scene escenaFinalPartida;
	private static Juego juego = new Juego();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;

			escenaInicio = new Scene(FXMLLoader.load(getClass().getResource("/vista/MenuInicial.fxml")));
			escenaMenuPartida = new Scene(FXMLLoader.load(getClass().getResource("/vista/MenuPartida.fxml")));
			//escenaPartida = new Scene(FXMLLoader.load(getClass().getResource("/vista/Partida.fxml")));
			//escenaFinalPartida = new Scene(FXMLLoader.load(getClass().getResource("/vista/FinalPartida.fxml")));

			primaryStage.setScene(escenaInicio);
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Cluedo Fantasy");
			primaryStage.show();

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

	public static void mostrarPartida() {
		stage.setScene(escenaPartida);
	}

	public static void mostrarFinalPartida() {
		stage.setScene(escenaFinalPartida);
	}

	public static Juego getJuego() {
		return juego;
	}
}