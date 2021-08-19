package es.clinica.podologia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * <p>Modelo para la tabla {@code citas}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Entity
@Table(name = "citas")
public class Citas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita", nullable = false, unique = true)
    private Integer idCita;

    @ManyToOne
    @JoinColumn(name = "dni_paciente", updatable = true, nullable = false)
    private Pacientes paciente;

    @OneToOne
    @JoinColumn(name = "dni_sanitario", updatable = true, nullable = false)
    private Sanitarios sanitario;

    @OneToOne
    @JoinColumn(name = "id_tratamiento", updatable = true, nullable = false)
    private Tratamientos tratamiento;

    @Column(name = "fecha", nullable = true, unique = false)
    private Long fecha;

    @Column(name = "hora_inicio", nullable = true, unique = false)
    private Long horaDesde;

    @Column(name = "hora_fin", nullable = true, unique = false)
    private Long horaHasta;

    @Column(name = "observaciones", length = 100, nullable = true, unique = false)
    private String observaciones;

    /**
     * <p>Constructor vacío.</p>
     */
    public Citas() {
	// Constructor vacío de la entidad
    }

    public Integer getIdCita() {
	return idCita;
    }

    public void setIdCita(Integer idCita) {
	this.idCita = idCita;
    }

    public Pacientes getPaciente() {
	return paciente;
    }

    public void setPaciente(Pacientes paciente) {
	this.paciente = paciente;
    }

    public Sanitarios getSanitario() {
	return sanitario;
    }

    public void setSanitario(Sanitarios sanitario) {
	this.sanitario = sanitario;
    }

    public Tratamientos getTratamiento() {
	return tratamiento;
    }

    public void setTratamiento(Tratamientos tratamiento) {
	this.tratamiento = tratamiento;
    }

    public Long getFecha() {
	return fecha;
    }

    public void setFecha(Long fecha) {
	this.fecha = fecha;
    }

    public Long getHoraDesde() {
	return horaDesde;
    }

    public void setHoraDesde(Long horaDesde) {
	this.horaDesde = horaDesde;
    }

    public Long getHoraHasta() {
	return horaHasta;
    }

    public void setHoraHasta(Long horaHasta) {
	this.horaHasta = horaHasta;
    }

    public String getObservaciones() {
	return observaciones;
    }

    public void setObservaciones(String observaciones) {
	this.observaciones = observaciones;
    }

}
