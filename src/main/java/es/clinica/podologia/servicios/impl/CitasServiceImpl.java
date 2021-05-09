package es.clinica.podologia.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.entidades.Citas;
import es.clinica.podologia.repositorios.CitasRepository;
import es.clinica.podologia.servicios.CitasService;

/**
 * <p>Implementación de la interfaz del servicio de la tabla {@code citas}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Service
public class CitasServiceImpl implements CitasService {
    
    @Autowired
    private CitasRepository citasRepository;

    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     */
    @Override
    public List<Citas> listarCitas() {
	return citasRepository.findAll();
    }

}
