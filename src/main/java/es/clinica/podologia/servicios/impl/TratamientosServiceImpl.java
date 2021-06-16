package es.clinica.podologia.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.entidades.Tratamientos;
import es.clinica.podologia.modelos.TratamientosModelo;
import es.clinica.podologia.repositorios.TratamientosRepository;
import es.clinica.podologia.servicios.TratamientosService;
import es.clinica.podologia.utilidades.Utilidades;

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
    public List<TratamientosModelo> listarTratamientos() {
	return listadoEntidadesListadoModelos(tratamientosRepository.findAll());
    }
    
    /**
     * <p>Método que convierte un objeto de tipo entidad en uno de tipo modelo.<p>
     * 
     * @param entidad {@link Tratamientos} entidad que se quiere convertir
     * 
     * @return {@link TratamientosModelo} modelo resultante
     * 
     * @see Tratamientos
     * @see TratamientosModelo
     */
    private TratamientosModelo entidadModelo(Tratamientos entidad) {
	
	// Inicializar el modelo que se va retornar al final
	TratamientosModelo modelo = new TratamientosModelo();
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(entidad != null) {
	    
	    modelo.setIdTratamiento(entidad.getIdTratamiento());
	    modelo.setNombre(entidad.getNombre());
	    modelo.setDescripcion(entidad.getDescripcion());
	    
	}
	
	// Retornar el modelo convertido
	return modelo;
	
    }
    
    /**
     * <p>Método que convierte un listado de entidades en un listado de modelos.</p>
     * 
     * @param lista {@link List} {@link Tratamientos} listado de entidades que se quiere convertir
     * 
     * @return {@link List} {@link TratamientosModelo} listado de entidades resultante
     * 
     * @see List#forEach(java.util.function.Consumer)
     */
    private List<TratamientosModelo> listadoEntidadesListadoModelos(List<Tratamientos> listaEntidades) {
	
	// Inicializar el listado de modelos que se va a retornar
	List<TratamientosModelo> listaModelos = new ArrayList<TratamientosModelo>();
	
	// Comprobar que el lsitado de entidades NO es nulo ni está vacío
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listaEntidades))) {
	    
	    // Recorrer el listado de entidades e ir añadiéndolo al listado de modelos
	    listaEntidades.forEach(entidad -> listaModelos.add(entidadModelo(entidad)));
	    
	}
	
	// Retornar el listado de modelos
	return listaModelos;
	
    }

}
