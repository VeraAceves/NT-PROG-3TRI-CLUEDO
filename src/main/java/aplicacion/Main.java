package aplicacion;

import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import Persistencia.PartidaDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modelo.Juego;
import controlador.FinalPartidaController;
import controlador.PartidaController;

/**
 * Clase principal de la aplicación Cluedo Fantasy. Se encarga de iniciar la
 * aplicación, cargar las escenas principales y gestionar la navegación entre
 * las distintas pantallas.
 */
public class Main extends Application {

	private static Stage stage;

	private static Scene escenaInicio;
	private static Scene escenaMenuPartida;

	private static Juego juego = new Juego();
	private static PartidaDAO partidaDAO = new PartidaDAO();
	private MediaPlayer mediaPlayer;

	/**
	 * Punto de entrada de la aplicación.
	 *
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Inicializa la aplicación JavaFX. Carga las escenas principales, configura la
	 * ventana y reproduce la música de fondo.
	 *
	 * @param primaryStage ventana principal de la aplicación
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;

			escenaInicio = new Scene(FXMLLoader.load(getClass().getResource("/vista/MenuInicial.fxml")));

			escenaMenuPartida = new Scene(FXMLLoader.load(getClass().getResource("/vista/MenuPartida.fxml")));

			stage.setScene(escenaInicio);
			stage.setMaximized(true);
			stage.setTitle("Cluedo Fantasy");
			stage.show();

			Media media = new Media(getClass().getResource("/vista/recursos/musica.mp3").toString());
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayer.play();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Muestra la pantalla inicial de la aplicación.
	 */
	public static void mostrarInicio() {
		stage.setScene(escenaInicio);
	}

	/**
	 * Muestra el menú de gestión de partidas.
	 */
	public static void mostrarMenuPartida() {
		stage.setScene(escenaMenuPartida);
	}

	/**
	 * Carga y muestra la pantalla principal de juego. Asigna al controlador la
	 * instancia actual del juego.
	 */
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

	/**
	 * Carga y muestra la pantalla final de partida. Recupera y muestra los datos de
	 * la partida finalizada.
	 */
	public static void mostrarFinalPartida() {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/vista/FinalPartida.fxml"));
			Scene scene = new Scene(loader.load());

			FinalPartidaController controller = loader.getController();
			controller.cargarDatos();

			stage.setScene(scene);
			stage.setMaximized(true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crea una nueva instancia del juego para comenzar una nueva partida.
	 */
	public static void reiniciarJuego() {
		juego = new Juego();
	}

	/**
	 * Devuelve la instancia actual del juego.
	 *
	 * @return juego en ejecución
	 */
	public static Juego getJuego() {
		return juego;
	}

	/**
	 * Devuelve el objeto de acceso a datos de partidas.
	 *
	 * @return instancia de PartidaDAO
	 */
	public static PartidaDAO getPartidaDAO() {
		return partidaDAO;
	}
}