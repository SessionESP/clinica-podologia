package es.clinica.podologia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import es.clinica.podologia.constantes.Constantes;

/**
 * <p>
 * Modelo para la tabla {@code sanitarios}.
 * </p>
 *
 * @author Ignacio Rafael
 *
 */
@Entity
@Table(name = "sanitarios")
public class Sanitarios {

    @Id
    @Column(name = "dni_sanitario", length = 20, nullable = false, unique = true)
    private String dniSanitario;

    @Column(name = "nombre", length = 50, nullable = true, unique = false)
    private String nombre;

    @Column(name = "apellidos", length = 50, nullable = true, unique = false)
    private String apellidos;

    @Column(name = "especialidad", length = 50, nullable = true, unique = false)
    private String especialidad;

    /**
     * <p>
     * Constructor vacío.
     * </p>
     */
    public Sanitarios() {

    }

    public String getDniSanitario() {
	return dniSanitario;
    }

    public void setDniSanitario(String dniSanitario) {
	this.dniSanitario = dniSanitario;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getApellidos() {
	return apellidos;
    }

    public void setApellidos(String apellidos) {
	this.apellidos = apellidos;
    }

    public String getEspecialidad() {
	return especialidad;
    }

    public void setEspecialidad(String especialidad) {
	this.especialidad = especialidad;
    }
    
    /**
     * <p>Nombre y apellidos formateados</p>
     */
    @Override
    public String toString() {
	return this.nombre + Constantes.ESPACIO + this.apellidos;
    }

}
