package es.clinica.podologia.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.entidades.Sanitarios;
import es.clinica.podologia.modelos.SanitariosModelo;
import es.clinica.podologia.repositorios.SanitariosRepository;
import es.clinica.podologia.servicios.SanitariosService;
import es.clinica.podologia.utilidades.Utilidades;

/**
 * <p>Implementación de la interfaz del servicio de la tabla {@code sanitarios}.</p>
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
    public List<SanitariosModelo> listarSanitarios() {
	return listadoEntidadesListadoModelos(sanitariosRepository.findAll());
    }
    
    /**
     * <p>Método que convierte un objeto de tipo entidad en uno de tipo modelo.<p>
     * 
     * @param entidad {@link Sanitarios} entidad que se quiere convertir
     * 
     * @return {@link SanitariosModelo} modelo resultante
     * 
     * @see Sanitarios
     * @see SanitariosModelo
     */
    private SanitariosModelo entidadModelo(Sanitarios entidad) {
	
	// Inicializar el modelo que se va retornar al final
	SanitariosModelo modelo = new SanitariosModelo();
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(entidad != null) {
	    
	    modelo.setDniSanitario(entidad.getDniSanitario());
	    modelo.setNombre(entidad.getNombre());
	    modelo.setApellidos(entidad.getApellidos());
	    modelo.setEspecialidad(entidad.getEspecialidad());
	    
	}
	
	// Retornar el modelo convertido
	return modelo;
	
    }
    
    /**
     * <p>Método que convierte un listado de entidades en un listado de modelos.</p>
     * 
     * @param lista {@link List} {@link Sanitarios} listado de entidades que se quiere convertir
     * 
     * @return {@link List} {@link SanitariosModelo} listado de entidades resultante
     * 
     * @see List#forEach(java.util.function.Consumer)
     */
    private List<SanitariosModelo> listadoEntidadesListadoModelos(List<Sanitarios> listaEntidades) {
	
	// Inicializar el listado de modelos que se va a retornar
	List<SanitariosModelo> listaModelos = new ArrayList<SanitariosModelo>();
	
	// Comprobar que el lsitado de entidades NO es nulo ni está vacío
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listaEntidades))) {
	    
	    // Recorrer el listado de entidades e ir añadiéndolo al listado de modelos
	    listaEntidades.forEach(entidad -> listaModelos.add(entidadModelo(entidad)));
	    
	}
	
	// Retornar el listado de modelos
	return listaModelos;
	
    }

}
