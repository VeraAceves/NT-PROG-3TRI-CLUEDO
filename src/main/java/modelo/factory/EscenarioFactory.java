package modelo.factory;

import java.util.List;
import modelo.beans.Escenario;

public class EscenarioFactory {

	public static List<Escenario> crear() {

		return List.of(new Escenario("E_TRONO", "Salón del Trono", ""),
				new Escenario("E_ARCHIVO", "Archivo de Runas", ""),
				new Escenario("E_DORMITORIO", "Dormitorio Principal", ""),
				new Escenario("E_FORJA", "Forja de Honor", ""), new Escenario("E_DESPENSA", "Despensa Real", ""),
				new Escenario("E_INVERNADERO", "Invernadero de Mandrágoras", ""));
	}
}