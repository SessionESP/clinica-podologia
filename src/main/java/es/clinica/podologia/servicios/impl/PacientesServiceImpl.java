package es.clinica.podologia.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.entidades.Pacientes;
import es.clinica.podologia.repositorios.PacientesRepository;
import es.clinica.podologia.servicios.PacientesService;

/**
 * <p>Implementaci�n de la interfaz del servicio de la tabla {@code pacientes}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Service
public class PacientesServiceImpl implements PacientesService {
    
    @Autowired
    private PacientesRepository pacientesRepository;

    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     */
    @Override
    public List<Pacientes> listarPacientes() {
	return pacientesRepository.findAll();
    }

}
