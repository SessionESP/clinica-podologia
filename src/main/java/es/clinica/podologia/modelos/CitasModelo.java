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
public class CitasModelo {
    
    private IntegerProperty idCita;
    
    private StringProperty dniPaciente;

    private StringProperty nombrePaciente;
    
    private StringProperty dniSanitario;

    private StringProperty nombreSanitario;
    
    private IntegerProperty idTratamiento;

    private StringProperty nombreTratamiento;
    
    private StringProperty colorTratamiento;

    private ObjectProperty<LocalDate> fecha;

    private ObjectProperty<LocalTime> horaDesde;

    private ObjectProperty<LocalTime> horaHasta;

    private StringProperty observaciones;
    
    /**
     * <p>Constructor vacío.</p>
     * <p>Inicializa todos los atributos de la clase para evitar {@code NullPointerException}.</p>
     */
    public CitasModelo() {
	this.idCita = new SimpleIntegerProperty();
	this.dniPaciente = new SimpleStringProperty();
	this.nombrePaciente = new SimpleStringProperty();
	this.dniSanitario = new SimpleStringProperty();
	this.nombreSanitario = new SimpleStringProperty();
	this.idTratamiento = new SimpleIntegerProperty();
	this.nombreTratamiento = new SimpleStringProperty();
	this.colorTratamiento = new SimpleStringProperty();
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
    
    public IntegerProperty idCitaProperty() {
	return this.idCita;
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
    
    public String getNombrePaciente() {
        return nombrePaciente.get();
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente.set(nombrePaciente);
    }
    
    public StringProperty nombrePacienteProperty() {
	return this.nombrePaciente;
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
    
    public String getNombreSanitario() {
        return nombreSanitario.get();
    }

    public void setNombreSanitario(String nombreSanitario) {
        this.nombreSanitario.set(nombreSanitario);
    }
    
    public StringProperty nombreSanitarioProperty() {
	return this.nombreSanitario;
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

    public String getNombreTratamiento() {
        return nombreTratamiento.get();
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento.set(nombreTratamiento);
    }
    
    public StringProperty nombreTratamientoProperty() {
	return this.nombreTratamiento;
    }
    
    public String getColorTratamiento() {
        return colorTratamiento.get();
    }

    public void setColorTratamiento(String colorTratamiento) {
        this.colorTratamiento.set(colorTratamiento);
    }
    
    public StringProperty colorTratamientoProperty() {
	return this.colorTratamiento;
    }

    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }
    
    public ObjectProperty<LocalDate> fechaProperty() {
	return this.fecha;
    }

    public LocalTime getHoraDesde() {
        return horaDesde.get();
    }

    public void setHoraDesde(LocalTime horaDesde) {
        this.horaDesde.set(horaDesde);
    }
    
    public ObjectProperty<LocalTime> horaDesdeProperty() {
	return this.horaDesde;
    }

    public LocalTime getHoraHasta() {
        return horaHasta.get();
    }

    public void setHoraHasta(LocalTime horaHasta) {
        this.horaHasta.set(horaHasta);
    }
    
    public ObjectProperty<LocalTime> horaHastaProperty() {
	return this.horaHasta;
    }

    public String getObservaciones() {
        return observaciones.get();
    }

    public void setObservaciones(String observaciones) {
        this.observaciones.set(observaciones);
    }
    
    public StringProperty observacionesProperty() {
	return this.observaciones;
    }

}