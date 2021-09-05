package es.clinica.podologia.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.entidades.Tratamientos;
import es.clinica.podologia.modelos.TratamientosModelo;
import es.clinica.podologia.repositorios.TratamientosRepository;
import es.clinica.podologia.servicios.TratamientosService;
import es.clinica.podologia.utilidades.Utilidades;

/**
 * <p>Implementación de la interfaz del servicio de la tabla {@code tratamientos}.</p>
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
	return convertirListadoEntidadesListadoModelos(tratamientosRepository.findAll());
    }
    
    /**
     * <p>Método que retorna un registro buscado por su identificador</p>
     */
    @Override
    public TratamientosModelo encontrarTratamiento(Integer identificador) {
	
	// Declarar el modelo que se va a retornar al final del método
	TratamientosModelo modelo = null;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if(identificador != null) {
	    
	    // Recuperar el tratamiento correspondiente a ese identificador
	    Optional<Tratamientos> entidad = tratamientosRepository.findById(identificador);
	    
	    // Convertir la entidad recuperada en un modelo
	    modelo = convertirEntidadModelo(entidad.isPresent() ? entidad.get() : null);
	    
	}
	
	// Retornar el modelo
	return modelo;
    }
    
    /**
     * <p>Método que comprueba si un tratamiento existe.</p>
     */
    @Override
    public Boolean comprobarExistenciaTratamiento(Integer identificador) {
	
	// Inicializar el booleano que indicará si el registro existe en la tabla
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if(identificador != null) {
	    
	    // Comprobar si el tratamiento existe
	    resultado = tratamientosRepository.existsById(identificador);
	    
	}
	
	// Retornar el resultado de la búsqueda
	return resultado;
    }

    /**
     * <p>Método que inserta o actualiza un registro de la tabla.</p>
     */
    @Override
    public Boolean insertarActualizarTratamiento(TratamientosModelo modelo) {
	
	// Inicializar el booleano que indicará si la inserción/actualización se ha realizado con éxito
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el modelo pasado como parámetro NO es nulo
	if (modelo != null) {
	    
	    // Convertir el modelo en una entidad
	    Tratamientos entidad = convertirModeloEntidad(modelo);
	    
	    // Guarda la entidad en la base de datos y persiste definitivamente los cambios
	    resultado = tratamientosRepository.saveAndFlush(entidad) != null;
	    
	}
	
	// Retornar el resultado de la inserción/actualización
	return resultado;
	
    }
    
    /**
     * <p>Método que inserta o actualiza un registro de la tabla.</p>
     * <p>En el alta rápida se necesita obtener el identificador del tratamiento.</p>
     */
    @Override
    public TratamientosModelo insertarActualizarTratamientoRapido(TratamientosModelo modelo) {
	
	// Inicializar el modelo que indicará si la inserción/actualización se ha realizado con éxito
	TratamientosModelo resultado = null;
	
	// Comprobar que el modelo pasado como parámetro NO es nulo
	if (modelo != null) {
	    
	    // Convertir el modelo en una entidad
	    Tratamientos entidad = convertirModeloEntidad(modelo);
	    
	    // Guarda la entidad en la base de datos y persiste definitivamente los cambios
	    resultado = convertirEntidadModelo(tratamientosRepository.saveAndFlush(entidad));
	    
	}
	
	// Retornar el resultado de la inserción/actualización
	return resultado;
	
    }

    /**
     * <p>Método que elimina un registro de la tabla.</p>
     */
    @Override
    public Boolean eliminarTratamiento(Integer identificador) {
	
	// Inicializar el booleano que indicará si la eliminación se ha realizado con éxito
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if (identificador != null) {
	    
	    // Eliminar el registro que coincida con el identificador
	    tratamientosRepository.deleteById(identificador);
	    
	    // Persistir los cambios
	    tratamientosRepository.flush();
	    
	    // Comprobar si se ha eliminado correctamente
	    resultado = !tratamientosRepository.existsById(identificador);
	    
	}
	
	// Retornar el resultado de la eliminación
	return resultado;
	
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
    private TratamientosModelo convertirEntidadModelo(Tratamientos entidad) {
	
	// Declarar el modelo que se va retornar al final
	TratamientosModelo modelo = null;
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(entidad != null) {
	    
	    // Inicializar el modelo
	    modelo = new TratamientosModelo();
	    
	    modelo.setIdTratamiento(entidad.getIdTratamiento());
	    modelo.setNombre(entidad.getNombre());
	    modelo.setDescripcion(entidad.getDescripcion());
	    modelo.setColor(entidad.getColor());
	    modelo.setPrecio(entidad.getPrecio());
	    
	}
	
	// Retornar el modelo convertido
	return modelo;
	
    }
    
    /**
     * <p>Método que convierte un objeto de tipo modelo en uno de tipo entidad.<p>
     * 
     * @param entidad {@link TratamientosModelo} modelo que se quiere convertir
     * 
     * @return {@link Tratamientos} entidad resultante
     * 
     * @see TratamientosModelo
     * @see Tratamientos
     */
    private Tratamientos convertirModeloEntidad(TratamientosModelo modelo) {
	
	// Declarar la entidad que se va retornar al final
	Tratamientos entidad = null;
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(modelo != null) {
	    
	    // Inicializar la entidad
	    entidad = new Tratamientos();
	    
	    entidad.setIdTratamiento(modelo.getIdTratamiento());
	    entidad.setNombre(modelo.getNombre());
	    entidad.setDescripcion(modelo.getDescripcion());
	    entidad.setColor(modelo.getColor());
	    entidad.setPrecio(modelo.getPrecio());
	    
	}
	
	// Retornar la entidad convertida
	return entidad;
	
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
    private List<TratamientosModelo> convertirListadoEntidadesListadoModelos(List<Tratamientos> listaEntidades) {
	
	// Inicializar el listado de modelos que se va a retornar
	List<TratamientosModelo> listaModelos = new ArrayList<>();
	
	// Comprobar que el lsitado de entidades NO es nulo ni está vacío
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listaEntidades))) {
	    
	    // Recorrer el listado de entidades e ir añadiéndolo al listado de modelos
	    listaEntidades.forEach(entidad -> listaModelos.add(entidad != null ? convertirEntidadModelo(entidad) : new TratamientosModelo()));
	    
	}
	
	// Retornar el listado de modelos
	return listaModelos;
	
    }

}
