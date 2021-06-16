package es.clinica.podologia.servicios.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.entidades.Pacientes;
import es.clinica.podologia.modelos.PacientesModelo;
import es.clinica.podologia.repositorios.PacientesRepository;
import es.clinica.podologia.servicios.PacientesService;
import es.clinica.podologia.utilidades.Utilidades;

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
    public List<PacientesModelo> listarPacientes() {
	return listadoEntidadesListadoModelos(pacientesRepository.findAll());
    }
    
    /**
     * <p>Método que convierte un objeto de tipo entidad en uno de tipo modelo.<p>
     * 
     * @param entidad {@link Pacientes} entidad que se quiere convertir
     * 
     * @return {@link PacientesModelo} modelo resultante
     * 
     * @see Pacientes
     * @see PacientesModelo
     */
    private PacientesModelo entidadModelo(Pacientes entidad) {
	
	// Inicializar el modelo que se va retornar al final
	PacientesModelo modelo = new PacientesModelo();
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(entidad != null) {
	    
	    modelo.setDniPaciente(entidad.getDniPaciente());
	    modelo.setNombre(entidad.getNombre());
	    modelo.setApellidos(entidad.getApellidos());
	    modelo.setDireccion(entidad.getDireccion());
	    modelo.setTelefono(entidad.getTelefono());
	    // TODO: sustituir cuando la base de datos tenga valores en esta columna
	    // modelo.setAdjunto(entidad.getAdjunto());
	    modelo.setAdjunto(Constantes.CADENA_VACIA.getBytes());
	    
	}
	
	// Retornar el modelo convertido
	return modelo;
	
    }
    
    /**
     * <p>Método que convierte un listado de entidades en un listado de modelos.</p>
     * 
     * @param lista {@link List} {@link Pacientes} listado de entidades que se quiere convertir
     * 
     * @return {@link List} {@link PacientesModelo} listado de entidades resultante
     * 
     * @see List#forEach(java.util.function.Consumer)
     */
    private List<PacientesModelo> listadoEntidadesListadoModelos(List<Pacientes> listaEntidades) {
	
	// Inicializar el listado de modelos que se va a retornar
	List<PacientesModelo> listaModelos = new ArrayList<PacientesModelo>();
	
	// Comprobar que el lsitado de entidades NO es nulo ni está vacío
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listaEntidades))) {
	    
	    // Recorrer el listado de entidades e ir añadiéndolo al listado de modelos
	    listaEntidades.forEach(entidad -> listaModelos.add(entidadModelo(entidad)));
	    
	}
	
	// Retornar el listado de modelos
	return listaModelos;
	
    }

}
