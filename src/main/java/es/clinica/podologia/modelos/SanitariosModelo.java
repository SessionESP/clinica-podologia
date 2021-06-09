package es.clinica.podologia.modelos;

import es.clinica.podologia.constantes.Constantes;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <p>Modelo para la tabla {@code sanitarios}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public class SanitariosModelo {
    
    private StringProperty dniSanitario;

    private StringProperty nombre;

    private StringProperty apellidos;

    private StringProperty especialidad;

    /**
     * <p>Constructor vacío.</p>
     * <p>Inicializa todos los atributos de la clase para evitar {@code NullPointerException}.</p>
     */
    public SanitariosModelo() {
	this.dniSanitario = new SimpleStringProperty();
	this.nombre = new SimpleStringProperty();
	this.apellidos = new SimpleStringProperty();
	this.especialidad = new SimpleStringProperty();
    }

    public String getDniSanitario() {
	return dniSanitario.get();
    }

    public void setDniSanitario(String dniSanitario) {
	this.dniSanitario.set(dniSanitario);
    }
    
    public StringProperty dniSanitarioProperty() {
	return this.dniSanitario;
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

    public String getEspecialidad() {
	return especialidad.get();
    }

    public void setEspecialidad(String especialidad) {
	this.especialidad.set(especialidad);
    }
    
    public StringProperty especialidadProperty() {
	return this.especialidad;
    }
    
    
    /**
     * <p>Nombre y apellidos formateados</p>
     */
    @Override
    public String toString() {
	return this.nombre.get() + Constantes.ESPACIO + this.apellidos.get();
    }

}
