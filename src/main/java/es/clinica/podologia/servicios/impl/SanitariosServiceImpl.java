package es.clinica.podologia.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.entidades.Sanitarios;
import es.clinica.podologia.modelos.SanitariosModelo;
import es.clinica.podologia.repositorios.SanitariosRepository;
import es.clinica.podologia.servicios.SanitariosService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;

/**
 * <p>Implementación de la interfaz del servicio de la tabla {@code sanitarios}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Service
public class SanitariosServiceImpl implements SanitariosService {
    
    @Value("${sanitarios.error.dni.vacio}")
    private String errorDniSanitarioVacio;
    
    @Autowired
    private SanitariosRepository sanitariosRepository;

    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     */
    @Override
    public List<SanitariosModelo> listarSanitarios() {
	return convertirListadoEntidadesListadoModelos(sanitariosRepository.findAll());
    }
    
    /**
     * <p>Método que retorna un registro buscado por su identificador.</p>
     */
    @Override
    public SanitariosModelo encontrarSanitario(Integer idSanitario) {
	
	// Declarar el modelo que se va a retornar al final del método
	SanitariosModelo modelo = null;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if(idSanitario != null) {
	    
	    // Recuperar el tratamiento correspondiente a ese identificador
	    Optional<Sanitarios> entidad = sanitariosRepository.findById(idSanitario);
	    
	    // Convertir la entidad recuperada en un modelo
	    modelo = convertirEntidadModelo(entidad.isPresent() ? entidad.get() : null);
	    
	}
	
	// Retornar el modelo
	return modelo;
    }

    /**
     * <p>Método que comprueba si un Sanitario existe.</p>
     */
    @Override
    public Boolean comprobarExistenciaSanitario(Integer idSanitario) {

	// Inicializar el booleano que indicará si el registro existe en la tabla
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if(idSanitario != null) {
	    
	    // Comprobar si el tratamiento existe
	    resultado = sanitariosRepository.existsById(idSanitario);
	    
	}
	
	// Retornar el resultado de la búsqueda
	return resultado;
	
    }
    
    /**
     * <p>Método que retorna un registro buscado por su identificador DNI.</p>
     */
    @Override
    public SanitariosModelo encontrarSanitarioDNI(String dniSanitario) {
	
	// Declarar el modelo que se va a retornar al final del método
	SanitariosModelo modelo = null;
	
	// Comprobar que el identificador DNI pasado como parámetro NO es nulo
	if(Boolean.TRUE.equals(StringUtils.isNotBlank(dniSanitario))) {
	    
	    // Recuperar el tratamiento correspondiente a ese identificador
	    Optional<Sanitarios> entidad = sanitariosRepository.findByDniSanitario(dniSanitario);
	    
	    // Convertir la entidad recuperada en un modelo
	    modelo = convertirEntidadModelo(entidad.isPresent() ? entidad.get() : null);
	    
	}
	
	// Retornar el modelo
	return modelo;
    }

    /**
     * <p>Método que comprueba si un sanitario existe por su DNI.</p>
     */
    @Override
    public Boolean comprobarExistenciaSanitarioDNI(String dniSanitario) {
	
	// Inicializar el booleano que indicará si el registro existe en la tabla
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if(Boolean.TRUE.equals(StringUtils.isNotBlank(dniSanitario))) {
	    
	    // Comprobar si el tratamiento existe
	    resultado = sanitariosRepository.existsByDniSanitario(dniSanitario);
	    
	}
	
	// Retornar el resultado de la búsqueda
	return resultado;
    }

    /**
     * <p>Método que inserta o actualiza un registro de la tabla.</p>
     */
    @Override
    public Boolean insertarActualizarSanitario(SanitariosModelo modelo) {
	
	// Inicializar el booleano que indicará si la inserción/actualización se ha realizado con éxito
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el modelo pasado como parámetro NO es nulo
	if (modelo != null) {
	    
	    // Validar el modelo
	    String errores = validar(modelo);
	    
	    // Comprobar que no se han producido errores a la hora de de informar los campos del formulario
	    if(Boolean.TRUE.equals(StringUtils.isBlank(errores))) {
		
		// Convertir el modelo en una entidad
		Sanitarios entidad = convertirModeloEntidad(modelo);

		// Guarda la entidad en la base de datos y persiste definitivamente los cambios
		resultado = sanitariosRepository.saveAndFlush(entidad) != null;
		
	    } else {
		
		// Mostrar la lista de errores detectados
		UtilidadesAlertas.mostrarAlertaError(errores);
		
	    }
	    
	}
	
	// Retornar el resultado de la inserción/actualización
	return resultado;
	
    }

    /**
     * <p>Método que elimina un registro de la tabla.</p>
     */
    @Override
    public Boolean eliminarSanitario(Integer idSanitario) {

	// Inicializar el booleano que indicará si la eliminación se ha realizado con éxito
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el identificador DNI pasado como parámetro NO es nulo
	if(idSanitario != null) {
	    
	    // Eliminar el registro que coincida con el identificador
	    sanitariosRepository.deleteById(idSanitario);
	    
	    // Persistir los cambios
	    sanitariosRepository.flush();
	    
	    // Comprobar si se ha eliminado correctamente
	    resultado = !sanitariosRepository.existsById(idSanitario);
	    
	}
	
	// Retornar el resultado de la eliminación
	return resultado;
	
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
    private SanitariosModelo convertirEntidadModelo(Sanitarios entidad) {
	
	// Inicializar el modelo que se va retornar al final
	SanitariosModelo modelo = null;
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(entidad != null) {
	    
	    // Instanciar el modelo
	    modelo = new SanitariosModelo();
	    
	    modelo.setIdSanitario(entidad.getIdSanitario());
	    modelo.setDniSanitario(entidad.getDniSanitario());
	    modelo.setNombre(entidad.getNombre());
	    modelo.setApellidos(entidad.getApellidos());
	    modelo.setEspecialidad(entidad.getEspecialidad());
	    
	}
	
	// Retornar el modelo convertido
	return modelo;
	
    }
    
    /**
     * <p>Método que convierte un objeto de tipo modelo en uno de tipo entidad.<p>
     * 
     * @param entidad {@link SanitariosModelo} modelo que se quiere convertir
     * 
     * @return {@link Sanitarios} entidad resultante
     * 
     * @see SanitariosModelo
     * @see Sanitarios
     */
    private Sanitarios convertirModeloEntidad(SanitariosModelo modelo) {
	
	// Declarar la entidad que se va retornar al final
	Sanitarios entidad = null;
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(modelo != null) {
	    
	    // Inicializar la entidad
	    entidad = new Sanitarios();
	    
	    entidad.setIdSanitario(modelo.getIdSanitario());
	    entidad.setDniSanitario(modelo.getDniSanitario());
	    entidad.setNombre(modelo.getNombre());
	    entidad.setApellidos(modelo.getApellidos());
	    entidad.setEspecialidad(modelo.getEspecialidad());
	    
	}
	
	// Retornar la entidad convertida
	return entidad;
	
    }
    
    /**
     * <p>Método que convierte un listado de entidades en un listado de modelos.</p>
     * 
     * @param lista {@link List} {@link Sanitarios} listado de entidades que se quiere convertir
     * 
     * @return {@link List}<{@link SanitariosModelo}> listado de entidades resultante
     * 
     * @see List#forEach(java.util.function.Consumer)
     */
    private List<SanitariosModelo> convertirListadoEntidadesListadoModelos(List<Sanitarios> listaEntidades) {
	
	// Inicializar el listado de modelos que se va a retornar
	List<SanitariosModelo> listaModelos = new ArrayList<>();
	
	// Comprobar que el lsitado de entidades NO es nulo ni está vacío
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listaEntidades))) {
	    
	    // Recorrer el listado de entidades e ir añadiéndolo al listado de modelos
	    listaEntidades.forEach(entidad -> listaModelos.add(entidad != null ? convertirEntidadModelo(entidad) : new SanitariosModelo()));
	    
	}
	
	// Retornar el listado de modelos
	return listaModelos;
	
    }
    
    /**
     * <p>Método donde se validarán todos los atributos del modelo.</p>
     * 
     * @param modelo {@link SanitariosModelo} modelo que se va a validar
     * 
     * @return {@link String} cadena de errores, si está vacía, es que el modelo se puede guardar en al base de datos
     */
    private String validar(SanitariosModelo modelo) {
	
	// Inicializar el objeto donde se concatenarán todos los errores que se encuentren
	StringJoiner errores = new StringJoiner(Constantes.SALTO_LINEA);
	
	// Comprobar que el modelo NO es nulo
	if(modelo != null && StringUtils.isBlank(modelo.getDniSanitario())) {
	    errores.add(errorDniSanitarioVacio);
	}
	
	// Retornar la cadena de errores generada
	return errores.toString();
	
    }

}
