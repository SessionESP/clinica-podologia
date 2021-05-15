package es.clinica.podologia.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.entidades.Sanitarios;
import es.clinica.podologia.repositorios.SanitariosRepository;
import es.clinica.podologia.servicios.SanitariosService;

/**
 * <p>Implementaci�n de la interfaz del servicio de la tabla {@code sanitarios}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Service
public class SanitariosServiceImpl implements SanitariosService {
    
    @Autowired
    private SanitariosRepository sanitariosRepository;

    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     */
    @Override
    public List<Sanitarios> listarSanitarios() {
	return sanitariosRepository.findAll();
    }

}
