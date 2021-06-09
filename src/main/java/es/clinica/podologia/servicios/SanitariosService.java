package es.clinica.podologia.servicios;

import java.util.List;

import es.clinica.podologia.modelos.SanitariosModelo;

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
     * @return {@link List} {@link SanitariosModelo} listado de Sanitarios
     */
    public List<SanitariosModelo> listarSanitarios();

}
