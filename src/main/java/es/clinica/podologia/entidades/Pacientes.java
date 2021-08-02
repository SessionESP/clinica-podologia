package es.clinica.podologia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import es.clinica.podologia.constantes.Constantes;

/**
 * <p>Modelo para la tabla {@code pacientes}.</p>
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
    
    @Column(name = "fecha_nacimiento", nullable = true, unique = false)
    private String fechaNacimiento;

    @Column(name = "direccion", length = 100, nullable = true, unique = false)
    private String direccion;

    @Column(name = "telefono", length = 20, nullable = true, unique = false)
    private String telefono;
    
    @Column(name = "nombre_adjunto", length = 50, nullable = true, unique = false)
    private String nombreAdjunto;

    @Column(name = "adjunto", columnDefinition = "LONGVARBINARY")
    private byte[] adjunto;

    /**
     * <p>Constructor vacío.</p>
     */
    public Pacientes() {
	// Constructor vacío de la entidad
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
    
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
    
    public String getNombreAdjunto() {
        return nombreAdjunto;
    }

    public void setNombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
    }

    public byte[] getAdjunto() {
	return adjunto;
    }

    public void setAdjunto(byte[] adjunto) {
	this.adjunto = adjunto;
    }

    /**
     * <p>Nombre y apellidos formateados</p>
     */
    @Override
    public String toString() {
	return this.nombre + Constantes.ESPACIO + this.apellidos;
    }

}
