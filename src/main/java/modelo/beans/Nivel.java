package modelo.beans;

import java.util.ArrayList;
import java.util.List;

import modelo.enums.Dificultad;

public class Nivel {

	// Atributos
	private String idNivel;
	private Dificultad dificultad;
	private Personaje asesino;
	private Escenario escenarioCrimen;
	private Arma armaCrimen;
	private String descripcion;
	private List<String> pistas;
	private int pistaActual;
	private List<Personaje> personajes;
	private List<Arma> armas;
	private List<Escenario> escenarios;

	// Constructores
	public Nivel() {
	    this.personajes = new ArrayList<>();
	    this.armas = new ArrayList<>();
	    this.escenarios = new ArrayList<>();
	    this.pistas = new ArrayList<>();
	}

	public Nivel(String idNivel, Dificultad dificultad, Personaje asesino, Escenario escenarioCrimen, Arma armaCrimen,
			String descripcion, List<String> pistas, int pistaActual) {

		this.idNivel = idNivel;
		this.dificultad = dificultad;
		this.asesino = asesino;
		this.escenarioCrimen = escenarioCrimen;
		this.armaCrimen = armaCrimen;
		this.descripcion = descripcion;
		this.pistas = pistas;
		this.pistaActual = pistaActual;
	}

	public Nivel(String idNivel, Dificultad dificultad, Personaje asesino, Escenario escenarioCrimen, Arma armaCrimen,
			String descripcion, List<String> pistas, int pistaActual, List<Personaje> personajes, List<Arma> armas,
			List<Escenario> escenarios) {

		this.idNivel = idNivel;
		this.dificultad = dificultad;
		this.asesino = asesino;
		this.escenarioCrimen = escenarioCrimen;
		this.armaCrimen = armaCrimen;
		this.descripcion = descripcion;
		this.pistas = pistas;
		this.pistaActual = pistaActual;

		this.personajes = personajes;
		this.armas = armas;
		this.escenarios = escenarios;
	}

	// Getters y setters

	public String getIdNivel() {
		return idNivel;
	}

	public void setIdNivel(String idNivel) {
		this.idNivel = idNivel;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}

	public Personaje getAsesino() {
		return asesino;
	}

	public void setAsesino(Personaje asesino) {
		this.asesino = asesino;
	}

	public Escenario getEscenarioCrimen() {
		return escenarioCrimen;
	}

	public void setEscenarioCrimen(Escenario escenarioCrimen) {
		this.escenarioCrimen = escenarioCrimen;
	}

	public Arma getArmaCrimen() {
		return armaCrimen;
	}

	public void setArmaCrimen(Arma armaCrimen) {
		this.armaCrimen = armaCrimen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<String> getPistas() {
		return pistas;
	}

	public void setPistas(List<String> pistas) {
		this.pistas = pistas;
	}

	public int getPistaActual() {
		return pistaActual;
	}

	public void setPistaActual(int pistaActual) {
		this.pistaActual = pistaActual;
	}

	public List<Personaje> getPersonajes() {
		return personajes;
	}

	public void setPersonajes(List<Personaje> personajes) {
		this.personajes = personajes;
	}

	public List<Arma> getArmas() {
		return armas;
	}

	public void setArmas(List<Arma> armas) {
		this.armas = armas;
	}

	public List<Escenario> getEscenarios() {
		return escenarios;
	}

	public void setEscenarios(List<Escenario> escenarios) {
		this.escenarios = escenarios;
	}

	// toString
	@Override
	public String toString() {
		return "Nivel \nIdNivel: " + idNivel + "\nDificultad: " + dificultad + "\nAsesino: " + asesino
				+ "\nEscenario del crimen: " + escenarioCrimen + "\nArma del crimen: " + armaCrimen + "\nDescripción: "
				+ descripcion + "\nPistas: " + pistas;
	}

	// Métodos propios
	public boolean personajeCorrecto(Personaje personaje) {
		return asesino.getIdPersonaje().equals(personaje.getIdPersonaje());
	}

	public boolean armaCorrecta(Arma arma) {
		return armaCrimen.getIdArma().equals(arma.getIdArma());
	}

	public boolean escenarioCorrecto(Escenario escenario) {
		return escenarioCrimen.getIdEscenario().equals(escenario.getIdEscenario());
	}

	public String obtenerSiguientePista() {

		if (pistas.isEmpty()) {
			return null;
		}

		if (pistaActual >= pistas.size()) {
			return null;
		}

		return pistas.get(pistaActual++);
	}

	public int getNumeroPistas() {
		return pistas.size();
	}

}