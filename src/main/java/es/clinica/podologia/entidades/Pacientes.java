package es.clinica.podologia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * <p>
 * Modelo para la tabla {@code pacientes}.
 * </p>
 *
 * @author Ignacio Rafael
 *
 */
@Entity
@Table(name = "pacientes")
public class Pacientes {

    @Id
    @Column(name = "dni_paciente", length = 20, nullable = false, unique = true)
    private String dniPaciente;

    @Column(name = "nombre", length = 50, nullable = true, unique = false)
    private String nombre;

    @Column(name = "apellidos", length = 50, nullable = true, unique = false)
    private String apellidos;

    @Column(name = "direccion", length = 100, nullable = true, unique = false)
    private String direccion;

    @Column(name = "telefono", length = 20, nullable = true, unique = false)
    private String telefono;

    @Lob
    private byte[] adjunto;

    /**
     * <p>
     * Constructor vacío.
     * </p>
     */
    public Pacientes() {

    }

    public String getDniPaciente() {
	return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
	this.dniPaciente = dniPaciente;
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

    public String getDireccion() {
	return direccion;
    }

    public void setDireccion(String direccion) {
	this.direccion = direccion;
    }

    public String getTelefono() {
	return telefono;
    }

    public void setTelefono(String telefono) {
	this.telefono = telefono;
    }

    public byte[] getAdjunto() {
	return adjunto;
    }

    public void setAdjunto(byte[] adjunto) {
	this.adjunto = adjunto;
    }

}
