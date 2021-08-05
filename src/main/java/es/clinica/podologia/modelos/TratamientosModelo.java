package es.clinica.podologia.modelos;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <p>Modelo para la tabla {@code tratamientos}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public class TratamientosModelo {
    
    private IntegerProperty idTratamiento;

    private StringProperty nombre;

    private StringProperty descripcion;

    /**
     * <p>Constructor vacío.</p>
     * <p>Inicializa todos los atributos de la clase para evitar {@code NullPointerException}.</p>
     */
    public TratamientosModelo() {
	this.idTratamiento = new SimpleIntegerProperty();
	this.nombre = new SimpleStringProperty();
	this.descripcion = new SimpleStringProperty();
    }

    public Integer getIdTratamiento() {
	return idTratamiento.get();
    }

    public void setIdTratamiento(Integer idTratamiento) {
	this.idTratamiento.set(idTratamiento);
    }
    
    public IntegerProperty idTratamientoProperty() {
	return this.idTratamiento;
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

    public String getDescripcion() {
	return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
	this.descripcion.set(descripcion);
    }
    
    public StringProperty descripcionProperty() {
	return this.descripcion;
    }
    
    /**
     * <p>Identificador del tratamiento y el nombre separados por un guión.</p>
     */
    @Override
    public String toString() {
	return UtilidadesConversores.convertirEnteroCadena(this.idTratamiento.get()) + Constantes.GUION_ESPACIADO + this.nombre.get();
    }

}
