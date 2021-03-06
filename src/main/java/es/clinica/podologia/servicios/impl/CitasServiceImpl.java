package es.clinica.podologia.servicios.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesConversores;

/**
 * <p>Implementación de la interfaz del servicio de la tabla {@code citas}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Service
public class CitasServiceImpl implements CitasService {
    
    @Value("${pacientes.error.dni.vacio}")
    private String errorDniPacienteVacio;
    
    @Value("${pacientes.error.dni.noexiste}")
    private String errorDniPacienteNoExiste;
    
    @Value("${citas.error.coincidencia.paciente}")
    private String errorCoincidenciaPaciente;
    
    @Value("${sanitarios.error.dni.vacio}")
    private String errorDniSanitarioVacio;
    
    @Value("${sanitarios.error.dni.noexiste}")
    private String errorDniSanitarioNoExiste;
    
    @Value("${citas.error.coincidencia.sanitario}")
    private String errorCoincidenciaSanitario;
    
    @Value("${tratamientos.error.id.vacio}")
    private String errorIdTratamientoVacio;
    
    @Value("${tratamientos.error.id.noexiste}")
    private String errorIdTratamientoNoExiste;
    
    @Value("${citas.error.fecha.vacio}")
    private String errorFechaVacia;
    
    @Value("${citas.error.hora1.vacio}")
    private String errorHoraDesdeVacia;
    
    @Value("${citas.error.hora2.vacio}")
    private String errorHoraHastaVacia;
    
    @Value("${citas.error.rangohoras}")
    private String errorRangoHoras;
    
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
	    listado = convertirListadoEntidadesListadoModelos(
		    citasRepository.findByFecha(UtilidadesConversores.convertirFechaLong(fecha)));
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
	    
	    Optional<Sanitarios> sanitario = sanitariosRepository.findByDniSanitario(dniSanitario);
	    
	    if(Boolean.TRUE.equals(sanitario.isPresent())) {
		
		// Realizar la consulta y conversión
		listado = convertirListadoEntidadesListadoModelos(
			citasRepository.findByFechaAndSanitario(UtilidadesConversores.convertirFechaLong(fecha), sanitario.get()));
		
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
	    Optional<Sanitarios> sanitario = sanitariosRepository.findByDniSanitario(dniSanitario);
	    
	    // Comprobar que se ha recuperado un sanitario con el DNI pasado como parámetro de entrada
	    if(Boolean.TRUE.equals(sanitario.isPresent())) {
		
		// Realizar la consulta por fecha, hora desde, hora hasta y sanitario
		List<CitasModelo> listadoCitas = convertirListadoEntidadesListadoModelos(
			citasRepository.findByFechaAndHoraDesdeLessThanEqualAndHoraHastaGreaterThanAndSanitario(
				UtilidadesConversores.convertirFechaLong(fecha), 
				UtilidadesConversores.convertirHoraLong(fecha, hora), 
				UtilidadesConversores.convertirHoraLong(fecha, hora), 
				sanitario.get()));
		
		// Si el listado No está vacío, se recupera el primero de los registros
		modelo = Boolean.TRUE.equals(Utilidades.comprobarColeccion(listadoCitas)) ? listadoCitas.get(0) : null;
		
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
	    
	    // Validar el modelo
	    String errores = validar(modelo);
	    
	    // Comprobar que no se han producido errores a la hora de de informar los campos del formulario
	    if(Boolean.TRUE.equals(StringUtils.isBlank(errores))) {
		
		    // Convertir el modelo en una entidad
		    Citas entidad = convertirModeloEntidad(modelo);
		    
		    // Guarda la entidad en la base de datos y persiste definitivamente los cambios
		    resultado = citasRepository.saveAndFlush(entidad) != null;
		
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
	CitasModelo modelo = null;
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if(entidad != null) {
	    
	    // Instanciar el modelo
	    modelo = new CitasModelo();
	    
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
	    modelo.setColorTratamiento(entidad.getTratamiento() != null ? 
		    Utilidades.comprobarCadena(entidad.getTratamiento().getColor(), Constantes.CADENA_VACIA) : Constantes.COLOR_BLANCO_HEXADECIMAL);
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
     * @param modelo {@link CitasModelo} modelo que se quiere convertir
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
		    pacientesRepository.findByDniPaciente(modelo.getDniPaciente()).orElse(null) : null);
	    entidad.setSanitario(StringUtils.isNotBlank(modelo.getDniSanitario()) ? 
		    sanitariosRepository.findByDniSanitario(modelo.getDniSanitario()).orElse(null) : null);
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
	    listaEntidades.forEach(entidad -> listaModelos.add(entidad != null ? convertirEntidadModelo(entidad) : new CitasModelo()));
	    
	}
	
	// Retornar el listado de modelos
	return listaModelos;
	
    }
    
    
    /**
     * <p>Método donde se validarán todos los atributos del modelo.</p>
     * 
     * @param modelo {@link CitasModelo} modelo que se va a validar
     * 
     * @return {@link String} cadena de errores, si está vacía, es que el modelo se puede guardar en al base de datos
     */
    private String validar(CitasModelo modelo) {
	
	// Inicializar el objeto donde se concatenarán todos los errores que se encuentren
	StringJoiner errores = new StringJoiner(Constantes.SALTO_LINEA);
	
	// Comprobar que el modelo NO es nulo
	if(modelo != null) {

	    String erroresPaciente = validarPaciente(modelo);
	    if(StringUtils.isNotBlank(erroresPaciente)) {
		errores.add(erroresPaciente);
	    }
	    
	    String erroresSanitario = validarSanitario(modelo);
	    if(StringUtils.isNotBlank(erroresSanitario)) {
		errores.add(erroresSanitario);
	    }
	    
	    String erroresTratamiento = validarTratamiento(modelo);
	    if(StringUtils.isNotBlank(erroresTratamiento)) {
		errores.add(erroresTratamiento);
	    }
	    
	    if(modelo.getFecha() == null) {
		errores.add(errorFechaVacia);
	    }
	    
	    String erroresHoras = validarHoras(modelo);
	    if(StringUtils.isNotBlank(erroresHoras)) {
		errores.add(erroresHoras);
	    }
	    
	}
	
	// Retornar la cadena de errores generada
	return errores.toString();
	
    }
    
    /**
     * <p>Método donde se validarán los atributos que se corresponden con el paciente.</p>
     * 
     * @param modelo {@link CitasModelo} modelo que se va a validar
     * 
     * @return {@link String} cadena de errores, si está vacía, es que el modelo se puede guardar en al base de datos
     */
    private String validarPaciente(CitasModelo modelo) {
	
	// Inicializar la cadena que se retornará al final del método
	String error = null;
	
	// Comprobar que si el DNI está vacío y si existe en la base de datos
	if (StringUtils.isBlank(modelo.getDniPaciente())) {
	    error = errorDniPacienteVacio;
	} else if (pacientesRepository.findByDniPaciente(modelo.getDniPaciente()).isEmpty()) {
	    error = errorDniPacienteNoExiste;
	} else if(Boolean.TRUE.equals(coincidenciaPaciente(convertirModeloEntidad(modelo)))){
	    error = errorCoincidenciaPaciente;
	}
	
	// Retornar la cadena de errores
	return error;
	
    }
    
    /**
     * <p>Método donde se validará que el paciente no tenga citas el mismo día y a la misma hora.</p>
     * 
     * @param modelo {@link Citas} entidad que se va a validar
     * 
     * @return {@link Boolean} retornará {@code true} en caso de que encuentre citas coincidentes
     */
    private Boolean coincidenciaPaciente(Citas entidad) {
	
	// Inicializar la variable que se va a retornar al final del método
	Boolean coincidencias = Boolean.FALSE;
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if (entidad != null) {
	    
	    // Inicializar el listado de citas solapadas
	    List<Citas> listadoCitasSolapadas = null;
	    
	    // Si el identificador de la cita NO es nulo, hay que excluirlo de la búsqueda
	    if(entidad.getIdCita() != null && entidad.getIdCita() != 0) {
		
		// Búsqueda de citas solapadas excluyendo la cita que se pretende modificar
		listadoCitasSolapadas = citasRepository.findByIdCitaNotAndFechaAndHoraDesdeLessThanAndHoraHastaGreaterThanAndPaciente(
			entidad.getIdCita(), 
			entidad.getFecha(), 
			entidad.getHoraHasta(), 
			entidad.getHoraDesde(), 
			entidad.getPaciente());
		
	    } else {
		
		// Búsqueda de citas solapadas a la que se pretende dar de alta
		listadoCitasSolapadas = citasRepository.findByFechaAndHoraDesdeLessThanAndHoraHastaGreaterThanAndPaciente(
			entidad.getFecha(), 
			entidad.getHoraHasta(), 
			entidad.getHoraDesde(), 
			entidad.getPaciente());
		
	    }
		
	    // Comprobar si el listado contiene alguna cita que se pueda solapar
	    if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listadoCitasSolapadas))) {
		coincidencias = Boolean.TRUE;
	    }
	    
	}
	
	// Retornar el resultado del método
	return coincidencias;
	
    }
    
    /**
     * <p>Método donde se validarán los atributos que se corresponden con el sanitario.</p>
     * 
     * @param modelo {@link CitasModelo} modelo que se va a validar
     * 
     * @return {@link String} cadena de errores, si está vacía, es que el modelo se puede guardar en al base de datos
     */
    private String validarSanitario(CitasModelo modelo) {
	
	// Inicializar la cadena que se retornará al final del método
	String error = null;
	
	// Comprobar que si el DNI está vacío y si existe en la base de datos
	if (StringUtils.isBlank(modelo.getDniSanitario())) {
	    error = errorDniSanitarioVacio;
	} else if (sanitariosRepository.findByDniSanitario(modelo.getDniSanitario()).isEmpty()) {
	    error = errorDniSanitarioNoExiste;
	} else if(Boolean.TRUE.equals(coincidenciaSanitario(convertirModeloEntidad(modelo)))){
	    error = errorCoincidenciaSanitario;
	}
	
	// Retornar la cadena de errores
	return error;
	
    }
    
    /**
     * <p>Método donde se validará que el sanitario no tenga citas el mismo día y a la misma hora.</p>
     * 
     * @param modelo {@link Citas} entidad que se va a validar
     * 
     * @return {@link Boolean} retornará {@code true} en caso de que encuentre citas coincidentes
     */
    private Boolean coincidenciaSanitario(Citas entidad) {
	
	// Inicializar la variable que se va a retornar al final del método
	Boolean coincidencias = Boolean.FALSE;
	
	// Comprobar que la entidad pasada como parámetro NO es nula
	if (entidad != null) {
	    
	    // Inicializar el listado de citas solapadas
	    List<Citas> listadoCitasSolapadas = null;
	    
	    // Si el identificador de la cita NO es nulo, hay que excluirlo de la búsqueda
	    if(entidad.getIdCita() != null && entidad.getIdCita() != 0) {
		
		// Búsqueda de citas solapadas excluyendo la cita que se pretende modificar
		listadoCitasSolapadas = citasRepository.findByIdCitaNotAndFechaAndHoraDesdeLessThanAndHoraHastaGreaterThanAndSanitario(
			entidad.getIdCita(), 
			entidad.getFecha(), 
			entidad.getHoraHasta(), 
			entidad.getHoraDesde(), 
			entidad.getSanitario());
		
	    } else {
		
		// Búsqueda de citas solapadas a la que se pretende dar de alta
		listadoCitasSolapadas = citasRepository.findByFechaAndHoraDesdeLessThanAndHoraHastaGreaterThanAndSanitario(
			entidad.getFecha(), 
			entidad.getHoraHasta(), 
			entidad.getHoraDesde(), 
			entidad.getSanitario());
		
	    }
		
	    // Comprobar si el listado contiene alguna cita que se pueda solapar
	    if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listadoCitasSolapadas))) {
		coincidencias = Boolean.TRUE;
	    }
	    
	}
	
	// Retornar el resultado del método
	return coincidencias;
	
    }
    
    /**
     * <p>Método donde se validarán los atributos que se corresponden con el tratamiento.</p>
     * 
     * @param modelo {@link CitasModelo} modelo que se va a validar
     * 
     * @return {@link String} cadena de errores, si está vacía, es que el modelo se puede guardar en al base de datos
     */
    private String validarTratamiento(CitasModelo modelo) {
	
	// Inicializar la cadena que se retornará al final del método
	String error = null;
	
	// Comprobar que si el identificador es nulo/cero y si existe el identificador en la base de datos
	if (modelo.getIdTratamiento() == null || modelo.getIdTratamiento() == 0 || StringUtils.isBlank(modelo.getNombreTratamiento())) {
	    error = errorIdTratamientoVacio;
	} else if (tratamientosRepository.findById(modelo.getIdTratamiento()).isEmpty()) {
	    error = errorIdTratamientoNoExiste;
	}
	
	// Retornar la cadena de errores
	return error;
	
    }
    
    /**
     * <p>Método donde se validarán el rango de horas de la cita.</p>
     * 
     * @param modelo {@link CitasModelo} modelo que se va a validar
     * 
     * @return {@link String} cadena de errores, si está vacía, es que el modelo se puede guardar en al base de datos
     */
    private String validarHoras(CitasModelo modelo) {

	// Inicializar el objeto donde se concatenarán todos los errores que se encuentren
	StringJoiner errores = new StringJoiner(Constantes.SALTO_LINEA);

	if (modelo.getHoraDesde() == null) {
	    errores.add(errorHoraDesdeVacia);
	}

	if (modelo.getHoraHasta() == null) {
	    errores.add(errorHoraHastaVacia);
	}
	
	// Comprobar que la hora desde no es igual ni mayor que la hora hasta
	if(modelo.getHoraDesde() != null && modelo.getHoraHasta() != null && 
		modelo.getHoraDesde().compareTo(modelo.getHoraHasta()) >= 0) {
	    errores.add(errorRangoHoras);
	}

	// Retornar la cadena de errores generada
	return errores.toString();

    }

}
