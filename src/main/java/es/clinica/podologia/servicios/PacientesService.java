package es.clinica.podologia.servicios;

import java.util.List;

import es.clinica.podologia.modelos.PacientesModelo;

/**
 * <p>Interfaz del servicio de la tabla {@code pacientes}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface PacientesService {
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     * 
     * @return {@link List} {@link PacientesModelo} listado de Pacientes
     */
    public List<PacientesModelo> listarPacientes();

}
