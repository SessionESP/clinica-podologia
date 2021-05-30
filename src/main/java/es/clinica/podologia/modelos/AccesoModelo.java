package es.clinica.podologia.modelos;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <p>Modelo para la vista {@code acceso}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public class AccesoModelo {

    private final StringProperty usuario;
    private final StringProperty contrasena;

    /**
     * <p>Constructor vacío.</p>
     */
    public AccesoModelo() {
	this(null, null);
    }

    /**
     * <p>Constructor con todos los atributos de la clase.</p>
     *
     * @param usuario    {@link String} el identificador
     * @param contrasena {@link String} la contraseña correspondiente al identificador
     */
    public AccesoModelo(String usuario, String contrasena) {
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
