package es.clinica.podologia.modelos;

import java.time.LocalDate;

import es.clinica.podologia.constantes.Constantes;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBlobProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <p>Modelo para la tabla {@code pacientes}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public class PacientesModelo {
    
    private IntegerProperty idPaciente;
    
    private StringProperty dniPaciente;

    private StringProperty nombre;

    private StringProperty apellidos;
    
    private ObjectProperty<LocalDate> fechaNacimiento;

    private StringProperty direccion;
    
    private StringProperty telefono;
    
    private StringProperty nombreAdjunto;
    
    private SimpleBlobProperty adjunto;

    /**
     * <p>Constructor vacío.</p>
     * <p>Inicializa todos los atributos de la clase para evitar {@code NullPointerException}.</p>
     */
    public PacientesModelo() {
	this.idPaciente = new SimpleIntegerProperty();
	this.dniPaciente = new SimpleStringProperty();
	this.nombre = new SimpleStringProperty();
	this.apellidos = new SimpleStringProperty();
	this.fechaNacimiento = new SimpleObjectProperty<>();
	this.direccion = new SimpleStringProperty();
	this.telefono = new SimpleStringProperty();
	this.nombreAdjunto = new SimpleStringProperty();
	this.adjunto = new SimpleBlobProperty(Constantes.CADENA_VACIA.getBytes());
    }
    
    public Integer getIdPaciente() {
        return idPaciente.get();
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente.set(idPaciente);
    }
    
    public IntegerProperty idPacienteProperty() {
	return this.idPaciente;
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
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento.get();
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento.set(fechaNacimiento);
    }
    
    public ObjectProperty<LocalDate> fechaNacimientoProperty() {
	return this.fechaNacimiento;
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
    
    public String getNombreAdjunto() {
	return nombreAdjunto.get();
    }

    public void setNombreAdjunto(String nombreAdjunto) {
	this.nombreAdjunto.set(nombreAdjunto);
    }
    
    public StringProperty nombreAdjuntoProperty() {
	return this.nombreAdjunto;
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
     * <p>Método que retorna el DNI y el nombre completo del paciente para búsquedas.</p>
     * 
     * @return {@link String} DNI concatenado con el nombre completo
     * 
     * @see PacientesModelo#dniPaciente
     * @see PacientesModelo#toString()
     */
    public String buscar() {
	return this.dniPaciente.get() + Constantes.GUION_ESPACIADO + toString();
    }
    
    
    /**
     * <p>Nombre y apellidos formateados.</p>
     */
    @Override
    public String toString() {
	return this.nombre.get() + Constantes.ESPACIO + this.apellidos.get();
    }

}
