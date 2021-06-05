package es.clinica.podologia.servicios;

import java.time.LocalDate;
import java.util.List;

import es.clinica.podologia.modelos.CitasModelo;

/**
 * <p>Interfaz del servicio de la vista {@code vista_citas}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface CitasService {
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     * 
     * @return {@link List} {@link CitasModelo} listado de Citas
     */
    public List<CitasModelo> listarCitas();
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista para una determinada fecha.</p>
     * 
     * @param fecha {@link LocalDate} fecha para la que se quiere buscar las citas
     * 
     * @return {@link List} {@link CitasModelo} listado de Citas
     */
    public List<CitasModelo> listarCitasPorFecha(LocalDate fecha);

}
