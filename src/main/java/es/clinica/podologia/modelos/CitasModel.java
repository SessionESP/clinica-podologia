package es.clinica.podologia.modelos;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * <p>Modelo para la tabla {@code citas}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public class CitasModel {
    
    private IntegerProperty idCita;

    private StringProperty paciente;

    private StringProperty sanitario;

    private StringProperty tratamiento;

    private ObjectProperty<LocalDate> fecha;

    private ObjectProperty<LocalTime> horaDesde;

    private ObjectProperty<LocalTime> horaHasta;

    private StringProperty observaciones;
    
    /**
     * <p>Constructor vacío.</p>
     */
    public CitasModel() {
	this.idCita = new SimpleIntegerProperty();
	this.paciente = new SimpleStringProperty();
	this.sanitario = new SimpleStringProperty();
	this.tratamiento = new SimpleStringProperty();
	this.fecha = new SimpleObjectProperty<>();
	this.horaDesde = new SimpleObjectProperty<>();
	this.horaHasta = new SimpleObjectProperty<>();
	this.observaciones = new SimpleStringProperty();
    }

    public Integer getIdCita() {
        return idCita.get();
    }

    public void setIdCita(Integer idCita) {
        this.idCita.set(idCita);
    }

    public String getPaciente() {
        return paciente.get();
    }

    public void setPaciente(String paciente) {
        this.paciente.set(paciente);
    }

    public String getSanitario() {
        return sanitario.get();
    }

    public void setSanitario(String sanitario) {
        this.sanitario.set(sanitario);
    }

    public String getTratamiento() {
        return tratamiento.get();
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento.set(tratamiento);
    }

    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }

    public LocalTime getHoraDesde() {
        return horaDesde.get();
    }

    public void setHoraDesde(LocalTime horaDesde) {
        this.horaDesde.set(horaDesde);
    }

    public LocalTime getHoraHasta() {
        return horaHasta.get();
    }

    public void setHoraHasta(LocalTime horaHasta) {
        this.horaHasta.set(horaHasta);
    }

    public String getObservaciones() {
        return observaciones.get();
    }

    public void setObservaciones(String observaciones) {
        this.observaciones.set(observaciones);
    }

}