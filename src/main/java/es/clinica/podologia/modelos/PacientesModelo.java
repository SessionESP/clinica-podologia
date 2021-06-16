package es.clinica.podologia.modelos;

import es.clinica.podologia.constantes.Constantes;
import javafx.beans.property.SimpleBlobProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <p>Modelo para la tabla {@code pacientes}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public class PacientesModelo {
    
    private StringProperty dniPaciente;

    private StringProperty nombre;

    private StringProperty apellidos;

    private StringProperty direccion;
    
    private StringProperty telefono;
    
    private SimpleBlobProperty adjunto;

    /**
     * <p>Constructor vacío.</p>
     * <p>Inicializa todos los atributos de la clase para evitar {@code NullPointerException}.</p>
     */
    public PacientesModelo() {
	this.dniPaciente = new SimpleStringProperty();
	this.nombre = new SimpleStringProperty();
	this.apellidos = new SimpleStringProperty();
	this.direccion = new SimpleStringProperty();
	this.telefono = new SimpleStringProperty();
	this.adjunto = new SimpleBlobProperty(Constantes.CADENA_VACIA.getBytes());
    }

    public String getDniPaciente() {
	return dniPaciente.get();
    }

    public void setDniPaciente(String dniPaciente) {
	this.dniPaciente.set(dniPaciente);
    }
    
    public StringProperty dniPacienteProperty() {
	return this.dniPaciente;
    }

    public String getNombre() {
	return nombre.get();
    }

    public void setNombre(String nombre) {
	this.nombre.set(nombre);
    }
    
    public StringProperty nombreProperty() {
	return this.nombre;
    }

    public String getApellidos() {
	return apellidos.get();
    }

    public void setApellidos(String apellidos) {
	this.apellidos.set(apellidos);
    }
    
    public StringProperty apellidosProperty() {
	return this.apellidos;
    }

    public String getDireccion() {
	return direccion.get();
    }

    public void setDireccion(String direccion) {
	this.direccion.set(direccion);
    }
    
    public StringProperty direccionProperty() {
	return this.direccion;
    }
    
    public String getTelefono() {
	return telefono.get();
    }

    public void setTelefono(String telefono) {
	this.telefono.set(telefono);
    }
    
    public StringProperty telefonoProperty() {
	return this.telefono;
    }
    
    public byte[] getAdjunto() {
	return adjunto.get();
    }

    public void setAdjunto(byte[] adjunto) {
	this.adjunto.set(adjunto);
    }
    
    public SimpleBlobProperty adjuntoProperty() {
	return this.adjunto;
    }
    
    
    /**
     * <p>Nombre y apellidos formateados</p>
     */
    @Override
    public String toString() {
	return this.nombre.get() + Constantes.ESPACIO + this.apellidos.get();
    }

}
