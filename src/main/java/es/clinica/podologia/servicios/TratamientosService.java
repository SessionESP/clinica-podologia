package es.clinica.podologia.servicios;

import java.util.List;

import es.clinica.podologia.modelos.TratamientosModelo;

/**
 * <p>Interfaz del servicio de la tabla {@code tratamientos}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface TratamientosService {
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     * 
     * @return {@link List} {@link TratamientosModelo} listado de Tratamientos
     */
    public List<TratamientosModelo> listarTratamientos();

}
