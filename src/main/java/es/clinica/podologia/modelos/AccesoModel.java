package es.clinica.podologia.modelos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <p>Modelo para la vista {@code acceso}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public class AccesoModel {

	private final StringProperty usuario;
	private final StringProperty contrasena;


	/**
	 * <p>Constructor vac�o.</p>
	 */
	public AccesoModel() {
		this(null, null);
	}

	/**
	 * <p>Constructor con todos los atributos de la clase.</p>
	 *
	 * @param usuario {@link String} el identificador
	 * @param contrasena {@link String} la contrase�a correspondiente al identificador
	 */
	public AccesoModel(String usuario, String contrasena) {
		super();
		this.usuario = new SimpleStringProperty(usuario);
		this.contrasena = new SimpleStringProperty(contrasena);
	}

	public StringProperty getUsuario() {
		return usuario;
	}

	public StringProperty getContrasena() {
		return contrasena;
	}

}
