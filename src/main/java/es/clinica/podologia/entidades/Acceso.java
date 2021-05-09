package es.clinica.podologia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>Modelo para la vista {@code acceso}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Entity
@Table(name = "acceso")
public class Acceso {

	@Id
	@Column(name = "usuario", length = 20, nullable = true, unique = false)
	private String usuario;
	
	@Column(name = "contrasena", length = 20, nullable = true, unique = false)
	private String contrasena;


	/**
	 * <p>Constructor vacío.</p>
	 */
	public Acceso() {
		
	}

	/**
	 * <p>Constructor con todos los atributos de la clase.</p>
	 *
	 * @param usuario {@link String} el identificador
	 * @param contrasena {@link String} la contraseña correspondiente al identificador
	 */
	public Acceso(String usuario, String contrasena) {
		super();
		this.usuario = usuario;
		this.contrasena = contrasena;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

}