package es.clinica.podologia.servicios.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.entidades.Tratamientos;
import es.clinica.podologia.repositorios.TratamientosRepository;
import es.clinica.podologia.servicios.TratamientosService;

/**
 * <p>Implementaci�n de la interfaz del servicio de la tabla {@code tratamientos}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Service
public class TratamientosServiceImpl implements TratamientosService {
    
    @Autowired
    private TratamientosRepository tratamientosRepository;

    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     */
    @Override
    public List<Tratamientos> listarTratamientos() {
	return tratamientosRepository.findAll();
    }

}
