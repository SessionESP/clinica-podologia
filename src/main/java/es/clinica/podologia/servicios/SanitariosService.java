package es.clinica.podologia.servicios;

import java.util.List;

import es.clinica.podologia.entidades.Sanitarios;

/**
 * <p>Interfaz del servicio de la tabla {@code sanitarios}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface SanitariosService {
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     * 
     * @return {@link List} {@link Sanitarios} listado de Sanitarios
     */
    public List<Sanitarios> listarSanitarios();

}
