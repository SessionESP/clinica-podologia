package es.clinica.podologia.servicios.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.entidades.Citas;
import es.clinica.podologia.entidades.Sanitarios;
import es.clinica.podologia.modelos.CitasModelo;
import es.clinica.podologia.repositorios.CitasRepository;
import es.clinica.podologia.repositorios.PacientesRepository;
import es.clinica.podologia.repositorios.SanitariosRepository;
import es.clinica.podologia.repositorios.TratamientosRepository;
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
    
    @Autowired
    private PacientesRepository pacientesRepository;
    
    @Autowired
    private SanitariosRepository sanitariosRepository;
    
    @Autowired
    private TratamientosRepository tratamientosRepository;

    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     */
    @Override
    public List<CitasModelo> listarCitas() {
	return convertirListadoEntidadesListadoModelos(citasRepository.findAll());
    }
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista para una determinada fecha.</p>
     */
    @Override
    public List<CitasModelo> listarCitasPorFecha(LocalDate fecha) {
	
	// Inicializar el listado que se va a retornar al final de la ejecución del método
	List<CitasModelo> listado = new ArrayList<>();
	
	// Comprobar que la fecha pasada como prámetro NO es nula
	if(fecha != null) {
	    
	    // Realizar la consulta y conversión
	    listado = convertirListadoEntidadesListadoModelos(citasRepository.findByFecha(UtilidadesConversores.convertirFechaLong(fecha)));
	}
	
	return listado;
    }
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista para un determinado rango de fechas.</p>
     */
    @Override
    public List<CitasModelo> listarCitasPorRangoDeFechas(LocalDate fechaInicial, LocalDate fechaFinal) {
	
	// Inicializar el listado que se va a retornar al final de la ejecución del método
	List<CitasModelo> listado = null;
	
	// Convertir las fechas pasadas como parámetros a cadenas de caracteres
	Long inicio = UtilidadesConversores.convertirFechaLong(fechaInicial);
	Long fin = UtilidadesConversores.convertirFechaLong(fechaFinal);
	
	// Realizar una u otra búsqueda en función de los parámetros que NO sean nulos
	if(inicio != null && fin != null) {
	    
	    // Ambas fechas NO son nulas
	    listado = convertirListadoEntidadesListadoModelos(citasRepository.findByFechaBetween(inicio, fin));
	    
	} else if(inicio == null && fin != null) {
	    
	    // La fecha inicial es nula
	    listado = convertirListadoEntidadesListadoModelos(citasRepository.findByFechaBefore(fin));
	    
	} else if(inicio != null) {
	    
	    // La fecha final es nula
	    listado = convertirListadoEntidadesListadoModelos(citasRepository.findByFechaAfter(inicio));
	    
	} else {
	    
	    // Ambas fechas son nulas
	    listado = listarCitas();
	}
	
	// Retornar el listado
	return listado;
    }
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista para una determinada fecha y sanitario.</p>
     */
    @Override
    public List<CitasModelo> listarCitasPorFechaYSanitario(LocalDate fecha, String dniSanitario) {

	// Inicializar el listado que se va a retornar al final de la ejecución del método
	List<CitasModelo> listado = new ArrayList<>();
	
	// Comprobar que los parámetros de entrada NO son nulos
	if(fecha != null && StringUtils.isNotBlank(dniSanitario)) {
	    
	    Optional<Sanitarios> sanitario = sanitariosRepository.findById(dniSanitario);
	    
	    if(Boolean.TRUE.equals(sanitario.isPresent())) {
		
		// Realizar la consulta y conversión
		listado = convertirListadoEntidadesListadoModelos(citasRepository.findByFechaAndSanitario(UtilidadesConversores.convertirFechaLong(fecha), sanitario.get()));
		
	    }
	    
	}
	
	// Retornar el listado
	return listado;
	
    }

    /**
     * <p>Método que retorna un registro buscado por su identificador.</p>
     */
    @Override
    public CitasModelo encontrarCita(Integer identificador) {

	// Declarar el modelo que se va a retornar al final del método
	CitasModelo modelo = null;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if(identificador != null) {
	    
	    // Recuperar el tratamiento correspondiente a ese identificador
	    Optional<Citas> entidad = citasRepository.findById(identificador);
	    
	    // Convertir la entidad recuperada en un modelo
	    modelo = convertirEntidadModelo(entidad.isPresent() ? entidad.get() : null);
	    
	}
	
	// Retornar el modelo
	return modelo;
	
    }
    
    /**
     * <p>Método que encuentra una cita filtrando por una fecha, una hora y un sanitario</p>
     */
    @Override
    public CitasModelo encontrarCitaPorFechaHoraSanitario(LocalDate fecha, LocalTime hora, String dniSanitario) {
	
	// Declarar el modelo que se va a retornar al final del método
	CitasModelo modelo = null;
	
	// Comprobar que los parámetros de entrada NO son nulos
	if(fecha != null && hora != null && StringUtils.isNotBlank(dniSanitario)) {
	    
	    // Buscar el sanitario correspondiente al DNI pasado como parámetro
	    Optional<Sanitarios> sanitario = sanitariosRepository.findById(dniSanitario);
	    
	    // Comprobar que se ha recuperado un sanitario con el DNI pasado como parámetro de entrada
	    if(Boolean.TRUE.equals(sanitario.isPresent())) {
		
		// Realizar la consulta por fecha y sanitario
		List<CitasModelo> listadoCitas = convertirListadoEntidadesListadoModelos(citasRepository.findByFechaAndSanitario(UtilidadesConversores.convertirFechaLong(fecha), sanitario.get()));
		
		// Buscar la cita dentro del listado
		modelo = buscarCitaPorHora(listadoCitas , hora);
		
	    }
	    
	}
	
	// Retornar el modelo
	return modelo;
    }

    /**
     * <p>Método que comprueba si una cita existe.</p>
     */
    @Override
    public Boolean comprobarExistenciaCita(Integer identificador) {
	
	// Inicializar el booleano que indicará si el registro existe en la tabla
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if(identificador != null) {
	    
	    // Comprobar si la cita existe
	    resultado = citasRepository.existsById(identificador);
	    
	}
	
	// Retornar el resultado de la búsqueda
	return resultado;
    }

    /**
     * <p>Método que inserta o actualiza un registro de la tabla.</p>
     */
    @Override
    public Boolean insertarActualizarCita(CitasModelo modelo) {

	// Inicializar el booleano que indicará si la inserción/actualización se ha realizado con éxito
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el modelo pasado como parámetro NO es nulo
	if (modelo != null) {
	    
	    // Convertir el modelo en una entidad
	    Citas entidad = convertirModeloEntidad(modelo);
	    
	    // Guarda la entidad en la base de datos y persiste definitivamente los cambios
	    resultado = citasRepository.saveAndFlush(entidad) != null;
	    
	}
	
	// Retornar el resultado de la inserción/actualización
	return resultado;
	
    }

    /**
     * <p>Método que elimina un registro de la tabla.</p>
     */
    @Override
    public Boolean eliminarCita(Integer identificador) {
	
	// Inicializar el booleano que indicará si la eliminación se ha realizado con éxito
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el identificador pasado como parámetro NO es nulo
	if (identificador != null) {
	    
	    // Eliminar el registro que coincida con el identificador
	    citasRepository.deleteById(identificador);
	    
	    // Persistir los cambios
	    citasRepository.flush();
	    
	    // Comprobar si se ha eliminado correctamente
	    resultado = !citasRepository.existsById(identificador);
	    
	}
	
	// Retornar el resultado de la eliminación
	return resultado;
	
    }
    
    /**
     * <p>Método que elimina un grupo de citas dentro de un determinado rango de fechas.</p>
     */
    @Override
    public Boolean eliminarCitasPorRangoDeFechas(LocalDate fechaInicial, LocalDate fechaFinal) {
	
	// Inicializar el booleano que indicará si la eliminación se ha realizado con éxito
	Boolean resultado = Boolean.FALSE;
	
	if (fechaInicial != null && fechaFinal != null) {
	    
	    // Convertir las fechas pasadas como parámetros a cadenas de caracteres
		Long inicio = UtilidadesConversores.convertirFechaLong(fechaInicial);
		Long fin = UtilidadesConversores.convertirFechaLong(fechaFinal);
	    
	    // Eliminar las citas que estén dentro del rango
	    citasRepository.deleteByFechaBetween(inicio, fin);
	    
	    // Persistir los cambios
	    citasRepository.flush();
	    
	    // Comprobar si se ha eliminado correctamente
	    resultado = !Utilidades.comprobarColeccion(citasRepository.findByFechaBetween(inicio, fin));
	    
	}
	
	// Retornar el resultado de la eliminación
	return resultado;
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
    private CitasModelo convertirEntidadModelo(Citas entidad) {
	
	// Inicializar el modelo que se va retornar al final
	CitasModelo modelo = new CitasModelo();
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(entidad != null) {
	    
	    modelo.setIdCita(entidad.getIdCita());
	    modelo.setDniPaciente(entidad.getPaciente() != null ? 
		    Utilidades.comprobarCadena(entidad.getPaciente().getDniPaciente(), Constantes.CADENA_VACIA) : Constantes.CADENA_VACIA);
	    modelo.setNombrePaciente(entidad.getPaciente() != null ? 
		    Utilidades.comprobarCadena(entidad.getPaciente().toString(), Constantes.CADENA_VACIA) : Constantes.CADENA_VACIA);
	    modelo.setDniSanitario(entidad.getSanitario() != null ? 
		    Utilidades.comprobarCadena(entidad.getSanitario().getDniSanitario(), Constantes.CADENA_VACIA) : Constantes.CADENA_VACIA);
	    modelo.setNombreSanitario(entidad.getSanitario() != null ? 
		    Utilidades.comprobarCadena(entidad.getSanitario().toString(), Constantes.CADENA_VACIA) : Constantes.CADENA_VACIA);
	    modelo.setIdTratamiento(entidad.getTratamiento() != null ? entidad.getTratamiento().getIdTratamiento() : null);
	    modelo.setNombreTratamiento(entidad.getTratamiento() != null ? 
		    Utilidades.comprobarCadena(entidad.getTratamiento().getNombre(), Constantes.CADENA_VACIA) : Constantes.CADENA_VACIA);
	    modelo.setFecha(UtilidadesConversores.convertirLongFecha(entidad.getFecha()));
	    modelo.setHoraDesde(UtilidadesConversores.convertirLongHora(entidad.getHoraDesde()));
	    modelo.setHoraHasta(UtilidadesConversores.convertirLongHora(entidad.getHoraHasta()));
	    modelo.setObservaciones(entidad.getObservaciones());
	    
	}
	
	// Retornar el modelo convertido
	return modelo;
	
    }
    
    /**
     * <p>Método que convierte un objeto de tipo modelo en uno de tipo entidad.<p>
     * 
     * @param entidad {@link CitasModelo} modelo que se quiere convertir
     * 
     * @return {@link Citas} entidad resultante
     * 
     * @see CitasModelo
     * @see Citas
     */
    private Citas convertirModeloEntidad(CitasModelo modelo) {
	
	// Declarar la entidad que se va retornar al final
	Citas entidad = null;
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(modelo != null) {
	    
	    // Inicializar la entidad
	    entidad = new Citas();
	    
	    entidad.setIdCita(modelo.getIdCita());
	    entidad.setPaciente(StringUtils.isNotBlank(modelo.getDniPaciente()) ? 
		    pacientesRepository.findById(modelo.getDniPaciente()).orElse(null) : null);
	    entidad.setSanitario(StringUtils.isNotBlank(modelo.getDniSanitario()) ? 
		    sanitariosRepository.findById(modelo.getDniSanitario()).orElse(null) : null);
	    entidad.setTratamiento(Boolean.TRUE.equals(modelo.getIdTratamiento() != null) ? 
		    tratamientosRepository.findById(modelo.getIdTratamiento()).orElse(null) : null);
	    entidad.setFecha(UtilidadesConversores.convertirFechaLong(modelo.getFecha()));
	    entidad.setHoraDesde(UtilidadesConversores.convertirHoraLong(modelo.getFecha(), modelo.getHoraDesde()));
	    entidad.setHoraHasta(UtilidadesConversores.convertirHoraLong(modelo.getFecha(), modelo.getHoraHasta()));
	    entidad.setObservaciones(modelo.getObservaciones());
	    
	}
	
	// Retornar la entidad convertida
	return entidad;
	
    }
    
    /**
     * <p>Método que convierte un listado de entidades en un listado de modelos.</p>
     * 
     * @param lista {@link List}<{@link Citas}> listado de entidades que se quiere convertir
     * 
     * @return {@link List} {@link CitasModelo} listado de entidades resultante
     * 
     * @see List#forEach(java.util.function.Consumer)
     */
    private List<CitasModelo> convertirListadoEntidadesListadoModelos(List<Citas> listaEntidades) {
	
	// Inicializar el listado de modelos que se va a retornar
	List<CitasModelo> listaModelos = new ArrayList<>();
	
	// Comprobar que el lsitado de entidades NO es nulo ni está vacío
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listaEntidades))) {
	    
	    // Recorrer el listado de entidades e ir añadiéndolo al listado de modelos
	    listaEntidades.forEach(entidad -> listaModelos.add(convertirEntidadModelo(entidad)));
	    
	}
	
	// Retornar el listado de modelos
	return listaModelos;
	
    }
    
    /**
     * <p>Busca una cita dentro de un listado acotado por fecha y sanitario filtrando por una hora</p>
     * 
     * @param listaModelos {@link List}<{@link Citas}> listado de entidades que se quiere convertir
     * @param hora {@link LocalTime} hora de la cita que se quiere buscar
     * 
     * @return {@link CitasModelo} cita encontrada
     */
    private CitasModelo buscarCitaPorHora(List<CitasModelo> listaModelos, LocalTime hora) {
	
	// Declarar el modelo que se va retornar al final del método
	CitasModelo modelo = null;
	
	// Comprobar que los parámetros NO son nulos
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listaModelos)) && hora != null) {
	    
	    // La hora debe ser menor/igual que la hora desde y menor que la hora hasta
	    modelo = listaModelos.stream()
		    .filter(cita -> hora.compareTo(cita.getHoraDesde()) >= 0 && hora.compareTo(cita.getHoraHasta()) < 0)
		    .findAny()
		    .orElse(null);
	    
	}
	
	// Retornar la entidad encontrada
	return modelo;
	
    }

}
