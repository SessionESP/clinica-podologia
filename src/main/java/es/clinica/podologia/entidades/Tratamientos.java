package es.clinica.podologia.entidades;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p>Modelo para la tabla {@code tratamientos}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Entity
@Table(name = "tratamientos")
public class Tratamientos {

    @Id
    @Column(name = "id_tratamiento", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    private Integer idTratamiento;

    @Column(name = "nombre", length = 50, nullable = true, unique = false)
    private String nombre;

    @Column(name = "descripcion", length = 100, nullable = true, unique = false)
    private String descripcion;
    
    @Column(name = "color", length = 10, nullable = true, unique = false)
    private String color;
    
    @Column(name = "precio", precision = 5, scale = 2, nullable = true, unique = false)
    private BigDecimal precio;

    /**
     * <p>Constructor vacío.</p>
     */
    public Tratamientos() {
	// Constructor vacío de la entidad
    }

    /**
     * <p>Constructor con todos los atributos de la clase.</p>
     * 
     * @param idTratamiento 	{@link Integer} identificador único del tratamiento
     * @param nombre 		{@link String} nombre del tratamientos
     * @param descripcion 	{@link String} descripción del tratamiento
     * @param color 		{@link String} color del tratamiento para identificarlo
     * @param descripcion 	{@link BigDecimal} precio del tratamiento
     */
    public Tratamientos(Integer idTratamiento, String nombre, String descripcion, String color, BigDecimal precio) {
	super();
	this.idTratamiento = idTratamiento;
	this.nombre = nombre;
	this.descripcion = descripcion;
	this.color = color;
	this.precio = precio;
    }

    public Integer getIdTratamiento() {
	return idTratamiento;
    }

    public void setIdTratamiento(Integer idTratamiento) {
	this.idTratamiento = idTratamiento;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public String getDescripcion() {
	return descripcion;
    }

    public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

}
