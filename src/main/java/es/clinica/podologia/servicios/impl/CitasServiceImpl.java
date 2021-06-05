package es.clinica.podologia.servicios.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.entidades.Citas;
import es.clinica.podologia.modelos.CitasModelo;
import es.clinica.podologia.repositorios.CitasRepository;
import es.clinica.podologia.servicios.CitasService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesConversores;

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
    public List<CitasModelo> listarCitas() {
	return listadoEntidadesListadoModelos(citasRepository.findAll());
    }
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista para una determinada fecha.</p>
     */
    @Override
    public List<CitasModelo> listarCitasPorFecha(LocalDate fecha) {
	
	// Inicializar el listado que se va a retornar al final de la ejecución del método
	List<CitasModelo> listado = new ArrayList<CitasModelo>();
	
	// Comprobar que la fecha pasada como prámetro NO es nula
	if(fecha != null) {
	    
	    // Realizar la consulta y conversión
	    listado = listadoEntidadesListadoModelos(citasRepository.findByFecha(UtilidadesConversores.fechaCadena(fecha)));
	}
	
	return listado;
    }
    
    /**
     * <p>Método que convierte un objeto de tipo entidad en uno de tipo modelo.<p>
     * 
     * @param entidad {@link Citas} entidad que se quiere convertir
     * 
     * @return {@link CitasModelo} modelo resultante
     * 
     * @see Citas
     * @see CitasModelo
     */
    private CitasModelo entidadModelo(Citas entidad) {
	
	// Inicializar el modelo que se va retornar al final
	CitasModelo modelo = new CitasModelo();
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(entidad != null) {
	    
	    modelo.setIdCita(entidad.getIdCita());
	    modelo.setPaciente(entidad.getPaciente() != null ? entidad.getPaciente().toString() : Constantes.CADENA_VACIA);
	    modelo.setSanitario(entidad.getSanitario() != null ? entidad.getSanitario().toString() : Constantes.CADENA_VACIA);
	    modelo.setTratamiento(entidad.getTratamiento() != null ? entidad.getTratamiento().getNombre() : Constantes.CADENA_VACIA);
	    modelo.setFecha(UtilidadesConversores.cadenaFecha(entidad.getFecha()));
	    modelo.setHoraDesde(UtilidadesConversores.cadenaHora(entidad.getHoraDesde()));
	    modelo.setHoraHasta(UtilidadesConversores.cadenaHora(entidad.getHoraHasta()));
	    modelo.setObservaciones(entidad.getObservaciones());
	    
	}
	
	// Retornar el modelo convertido
	return modelo;
	
    }
    
    /**
     * <p>Método que convierte un listado de entidades en un listado de modelos.</p>
     * 
     * @param lista {@link List} {@link Citas} listado de entidades que se quiere convertir
     * 
     * @return {@link List} {@link CitasModelo} listado de entidades resultante
     * 
     * @see List#forEach(java.util.function.Consumer)
     */
    private List<CitasModelo> listadoEntidadesListadoModelos(List<Citas> listaEntidades) {
	
	// Inicializar el listado de modelos que se va a retornar
	List<CitasModelo> listaModelos = new ArrayList<CitasModelo>();
	
	// Comprobar que el lsitado de entidades NO es nulo ni está vacío
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listaEntidades))) {
	    
	    // Recorrer el listado de entidades e ir añadiéndolo al listado de modelos
	    listaEntidades.forEach(entidad -> listaModelos.add(entidadModelo(entidad)));
	    
	}
	
	// Retornar el listado de modelos
	return listaModelos;
	
    }

}
