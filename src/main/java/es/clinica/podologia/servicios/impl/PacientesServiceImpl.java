package es.clinica.podologia.servicios.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.entidades.Pacientes;
import es.clinica.podologia.modelos.PacientesModelo;
import es.clinica.podologia.repositorios.PacientesRepository;
import es.clinica.podologia.servicios.PacientesService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesConversores;

/**
 * <p>Implementación de la interfaz del servicio de la tabla {@code pacientes}.</p>
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
	return convertirListadoEntidadesListadoModelos(pacientesRepository.findAll());
    }
    
    /**
     * <p>Método que retorna un registro buscado por su identificador.</p>
     */
    @Override
    public PacientesModelo encontrarPaciente(Integer idPaciente) {

	// Declarar el modelo que se va a retornar al final del método
	PacientesModelo modelo = null;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if(idPaciente != null) {
	    
	    // Recuperar el tratamiento correspondiente a ese identificador
	    Optional<Pacientes> entidad = pacientesRepository.findById(idPaciente);
	    
	    // Convertir la entidad recuperada en un modelo
	    modelo = convertirEntidadModelo(entidad.isPresent() ? entidad.get() : null);
	    
	}
	
	// Retornar el modelo
	return modelo;
	
    }

    /**
     * <p>Método que comprueba si un paciente existe.</p>
     */
    @Override
    public Boolean comprobarExistenciaPaciente(Integer idPaciente) {

	// Inicializar el booleano que indicará si el registro existe en la tabla
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if(idPaciente != null) {
	    
	    // Comprobar si el tratamiento existe
	    resultado = pacientesRepository.existsById(idPaciente);
	    
	}
	
	// Retornar el resultado de la búsqueda
	return resultado;
	
    }
    
    /**
     * <p>Método que retorna un registro buscado por su identificador DNI.</p>
     */
    @Override
    public PacientesModelo encontrarPacienteDNI(String dniPaciente) {
	
	// Declarar el modelo que se va a retornar al final del método
	PacientesModelo modelo = null;
	
	// Comprobar que el identificador DNI pasado como parámetro NO es nulo
	if(StringUtils.isNotBlank(dniPaciente)) {
	    
	    // Recuperar el tratamiento correspondiente a ese identificador
	    Optional<Pacientes> entidad = pacientesRepository.findByDniPaciente(dniPaciente);
	    
	    // Convertir la entidad recuperada en un modelo
	    modelo = convertirEntidadModelo(entidad.isPresent() ? entidad.get() : null);
	    
	}
	
	// Retornar el modelo
	return modelo;
    }

    /**
     * <p>Método que comprueba si un paciente existe por su DNI.</p>
     */
    @Override
    public Boolean comprobarExistenciaPacienteDNI(String dniPaciente) {

	// Inicializar el booleano que indicará si el registro existe en la tabla
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el identificador DNI pasado como parámetro NO es nulo
	if(StringUtils.isNotBlank(dniPaciente)) {
	    
	    // Comprobar si el tratamiento existe
	    resultado = pacientesRepository.existsByDniPaciente(dniPaciente);
	    
	}
	
	// Retornar el resultado de la búsqueda
	return resultado;
	
    }

    /**
     * <p>Método que inserta o actualiza un registro de la tabla.</p>
     */
    @Override
    public Boolean insertarActualizarPaciente(PacientesModelo modelo) {
	
	// Inicializar el booleano que indicará si la inserción/actualización se ha realizado con éxito
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el modelo pasado como parámetro NO es nulo
	if (modelo != null) {
	    
	    // Convertir el modelo en una entidad
	    Pacientes entidad = convertirModeloEntidad(modelo);
	    
	    // Guarda la entidad en la base de datos y persiste definitivamente los cambios
	    resultado = pacientesRepository.saveAndFlush(entidad) != null;
	    
	}
	
	// Retornar el resultado de la inserción/actualización
	return resultado;
	
    }

    /**
     * <p>Método que elimina un registro de la tabla.</p>
     */
    @Override
    public Boolean eliminarPaciente(Integer idPaciente) {

	// Inicializar el booleano que indicará si la eliminación se ha realizado con éxito
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if(idPaciente != null) {
	    
	    // Eliminar el registro que coincida con el identificador
	    pacientesRepository.deleteById(idPaciente);
	    
	    // Persistir los cambios
	    pacientesRepository.flush();
	    
	    // Comprobar si se ha eliminado correctamente
	    resultado = !pacientesRepository.existsById(idPaciente);
	    
	}
	
	// Retornar el resultado de la eliminación
	return resultado;
	
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
    private PacientesModelo convertirEntidadModelo(Pacientes entidad) {
	
	// Inicializar el modelo que se va retornar al final
	PacientesModelo modelo = new PacientesModelo();
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(entidad != null) {
	    
	    modelo.setIdPaciente(entidad.getIdPaciente());
	    modelo.setDniPaciente(entidad.getDniPaciente());
	    modelo.setNombre(entidad.getNombre());
	    modelo.setApellidos(entidad.getApellidos());
	    modelo.setFechaNacimiento(UtilidadesConversores.convertirCadenaFecha(entidad.getFechaNacimiento()));
	    modelo.setDireccion(entidad.getDireccion());
	    modelo.setTelefono(entidad.getTelefono());
	    modelo.setNombreAdjunto(entidad.getNombreAdjunto());
	    modelo.setAdjunto(Utilidades.comprobarArrayByteNulo(entidad.getAdjunto()));
	    
	}
	
	// Retornar el modelo convertido
	return modelo;
	
    }
    
    /**
     * <p>Método que convierte un objeto de tipo modelo en uno de tipo entidad.<p>
     * 
     * @param entidad {@link PacientesModelo} modelo que se quiere convertir
     * 
     * @return {@link Pacientes} entidad resultante
     * 
     * @see PacientesModelo
     * @see Pacientes
     */
    private Pacientes convertirModeloEntidad(PacientesModelo modelo) {
	
	// Declarar la entidad que se va retornar al final
	Pacientes entidad = null;
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(modelo != null) {
	    
	    // Inicializar la entidad
	    entidad = new Pacientes();
	    
	    entidad.setIdPaciente(modelo.getIdPaciente());
	    entidad.setDniPaciente(modelo.getDniPaciente());
	    entidad.setNombre(modelo.getNombre());
	    entidad.setApellidos(modelo.getApellidos());
	    entidad.setFechaNacimiento(UtilidadesConversores.convertirFechaCadena(modelo.getFechaNacimiento()));
	    entidad.setDireccion(modelo.getDireccion());
	    entidad.setTelefono(modelo.getTelefono());
	    entidad.setNombreAdjunto(modelo.getNombreAdjunto());
	    entidad.setAdjunto(Utilidades.comprobarArrayByteNulo(modelo.getAdjunto()));
	    
	}
	
	// Retornar la entidad convertida
	return entidad;
	
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
    private List<PacientesModelo> convertirListadoEntidadesListadoModelos(List<Pacientes> listaEntidades) {
	
	// Inicializar el listado de modelos que se va a retornar
	List<PacientesModelo> listaModelos = new ArrayList<>();
	
	// Comprobar que el lsitado de entidades NO es nulo ni está vacío
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listaEntidades))) {
	    
	    // Recorrer el listado de entidades e ir añadiéndolo al listado de modelos
	    listaEntidades.forEach(entidad -> listaModelos.add(convertirEntidadModelo(entidad)));
	    
	}
	
	// Retornar el listado de modelos
	return listaModelos;
	
    }

}
