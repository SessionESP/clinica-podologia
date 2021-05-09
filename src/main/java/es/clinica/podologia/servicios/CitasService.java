package es.clinica.podologia.servicios;

import java.util.List;

import es.clinica.podologia.entidades.Citas;

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
     * @return {@link List} {@link Citas} listado de Citas
     */
    public List<Citas> listarCitas();

}
