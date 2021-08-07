package es.clinica.podologia.controladores;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.componentes.BeansComponent;
import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.CitasModelo;
import es.clinica.podologia.servicios.CitasService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesControles;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesPropiedades;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * <p>Controlador para la configuración de la aplicación.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class ConfiguracionEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(ConfiguracionEdicionController.class);
    
    @Autowired
    private BeansComponent beansComponent;
    
    @Autowired
    private CitasService citasService;
    
    @Value("${spring.config.import}")
    private List<String> propiedadesExternas;
    
    @Value("${configuracion.edicion.guardar}")
    private String guardadoCorrecto;
    
    @Value("${configuracion.edicion.error}")
    private String guardadoError;
    
    @Value("${configuracion.edicion.citas.confirmacion}")
    private String citasConfirmacion;
    
    @Value("${configuracion.edicion.citas.informacion}")
    private String citasInformacion;
    
    @Value("${configuracion.edicion.citas.error}")
    private String citasError;
    
    @Value("${configuracion.edicion.citas.true}")
    private String citasTrue;
    
    @Value("${configuracion.edicion.citas.false}")
    private String citasFalse;
    
    @FXML
    private ComboBox<LocalTime> aperturaComboBox;
    @FXML
    private ComboBox<LocalTime> cierreComboBox;
    @FXML
    private ComboBox<String> tiempoComboBox;
    
    @FXML
    private TextField duracionCitasTextField;
    
    @FXML
    private Button eliminarCitasPasadasButton;
    @FXML
    private Button guardarButton;
    @FXML
    private Button salirButton;
    
    private LocalTime apertura;
    
    private LocalTime cierre;
    
    private Integer duracionCitas;
    
    private String tiempo;
    
    private FileBasedConfigurationBuilder<FileBasedConfiguration> constructor;
 
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la configuración de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	// Inicializar el constructor con los parámetros del fichero externo
	constructor = UtilidadesPropiedades.crearConstructor(new Parameters(), propiedadesExternas.get(0), Constantes.COMA);
	
	try {
	    
	    // Comprobar que el constructor y los parámetros NO son nulos
	    if (constructor != null && constructor.getConfiguration() != null) {

		// Guardar la información del fichero de configuración en un objeto
		FileBasedConfiguration configuracion = constructor.getConfiguration();
		
		// Listas desplegables con el horario de la clínica
		cargarHorarios(configuracion);

		// Duración de las citas
		cargarDuracionCitas(configuracion);

		// Configuración del borrado de citas pasadas
		cargarConfiguracionEliminacionCitas(configuracion);
	    }

	} catch (ConfigurationException excepcion) {

	    // Error al intentar guardar las propiedades del fichero en un objeto
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}


	    
	
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#aperturaComboBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarApertura(Event evento) {
	apertura = aperturaComboBox.getSelectionModel().getSelectedItem();
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#cierreComboBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarCierre(Event evento) {
	cierre = cierreComboBox.getSelectionModel().getSelectedItem();
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#duracionCitasTextField}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarDuracionCitas(Event evento) {
	duracionCitas = UtilidadesConversores.convertirCadenaEntero(duracionCitasTextField.getText());
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#tiempoComboBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarTiempo(Event evento) {
	tiempo = tiempoComboBox.getSelectionModel().getSelectedItem();
    }
    
    /**
     * <p>Método invocado como un evento para eliminar las citas pasadas.</p>
     * 
     * @see CitasService#listarCitasPorRangoDeFechas(java.time.LocalDate, java.time.LocalDate)
     * @see CitasService#eliminarCita(Integer)
     */
    @FXML
    private void eliminarCitasPasadas() {
	
	// Calcular el rango de fechas
	LocalDate fechaFin = calcularFechaFin();
	LocalDate fechaInicio = calcularFechaInicio(fechaFin);
	
	// Listar todas las citas dentro del rango de fechas
	List<CitasModelo> listadoCitasEliminar = citasService.listarCitasPorRangoDeFechas(fechaInicio, fechaFin);

	// Comprobar que el listado NO es nulo NI está vacío
	if (Boolean.TRUE.equals(Utilidades.comprobarColeccion(listadoCitasEliminar))) {
	    
	    Optional<ButtonType> alerta = UtilidadesAlertas.mostrarAlerta(
			AlertType.CONFIRMATION, 
			MessageFormat.format(citasConfirmacion, listadoCitasEliminar.size()), 
			ButtonType.YES, 
			ButtonType.NO);
	    
	    if(alerta.isPresent() && alerta.get() == ButtonType.YES) {
		
		// Eliminar las citas comprendidas dentro del rango de fechas
		Boolean resultado = citasService.eliminarCitasPorRangoDeFechas(fechaInicio, fechaFin);
		
		if (Boolean.TRUE.equals(resultado)) {
		    UtilidadesAlertas.mostrarAlertaError(citasTrue);
		} else {
		    UtilidadesAlertas.mostrarAlertaError(citasFalse);
		}
	    }

	} else {

	    UtilidadesAlertas.mostrarAlertaError(citasError);

	}

	
    }
    
    
    /**
     * <p>Método invocado como un evento para guardar todas las propiedades de la ventana emergente en el archivo de propiedades {@code configuracion.properties}</p>
     */
    @FXML
    private void guardar() {
	guardarPropiedades();
    }
    
    /**
     * <p>Método invocado como un evento para cerrar la ventana emergente.</p>
     */
    @FXML
    private void salir() {
	initialize();
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();
	AgendaEdicionController controlador = (AgendaEdicionController)beansComponent.obtenerControlador("agendaEdicionController");
	controlador.initialize();
    }
    
    /**
     * <p>Método que carga las listas desplegables para seleccionar la hora de apertura y cierre.</p>
     * 
     * @param configuracion {@link FileBasedConfiguration} información del fichero de propiedades en un objeto
     */
    private void cargarHorarios(FileBasedConfiguration configuracion) {
	
	// Hora de apertura
	apertura = UtilidadesConversores.convertirCadenaHora(configuracion.getString(
		Constantes.CONFIGURACION_HORA_APERTURA, 
		Constantes.CONFIGURACION_APERTURA_DEFECTO));

	// Hora de cierre
	cierre = UtilidadesConversores.convertirCadenaHora(configuracion.getString(
		Constantes.CONFIGURACION_HORA_CIERRE, 
		Constantes.CONFIGURACION_CIERRE_DEFECTO));

	// Inicializar el listado de las horas
	List<LocalTime> listadoHorarios = new ArrayList<>();

	// Cargar el listado con las veinticuatro horas del día
	for (int i = 0; i < 24; i++) {
	    listadoHorarios.add(LocalTime.MIN.plusHours(UtilidadesConversores.convertirEnteroLong(i)));
	}

	// Cargar todas las horas del día en el selector de hora de apertura
	aperturaComboBox.getItems().addAll(listadoHorarios);

	// Asignar el valor de apertura si existe dentro de las posibilidades o un valor por defecto en caso contrario
	aperturaComboBox.setValue(listadoHorarios.contains(apertura) ? apertura : listadoHorarios.get(9));

	// Cargar todas las horas del día en el selector de hora de cierre
	cierreComboBox.getItems().addAll(listadoHorarios);

	// Asignar el valor de cierre si existe dentro de las posibilidades o un valor por defecto en caso contrario
	cierreComboBox.setValue(listadoHorarios.contains(cierre) ? cierre : listadoHorarios.get(21));
	
    }
    
    
    /**
     * <p>Método que carga la duración de las citas desde el fichero de configuración e inicializa su formateador.</p>
     * 
     * @param configuracion {@link FileBasedConfiguration} información del fichero de propiedades en un objeto
     */
    private void cargarDuracionCitas(FileBasedConfiguration configuracion) {
	
	// Duración de las citas en minutos
	duracionCitas = configuracion.getInteger(
		Constantes.CONFIGURACION_DURACION, 
		Constantes.CONFIGURACION_DURACION_DEFECTO);

	duracionCitasTextField.setTextFormatter(UtilidadesControles.formateador(Constantes.PATRON_NUMEROS_ENTEROS, 2));
	duracionCitasTextField.setText(UtilidadesConversores.convertirEnteroCadena(duracionCitas));
	
    }
    
    
    /**
     * <p>Método que carga la configuración de la eliminación de citas de la base de datos.</p>
     * 
     * @param configuracion {@link FileBasedConfiguration} información del fichero de propiedades en un objeto
     */
    private void cargarConfiguracionEliminacionCitas(FileBasedConfiguration configuracion) {
	    
	// Listado de opciones del comboBox
	String[] arrayOpciones = {
		Constantes.CONFIGURACION_ELIMINACION_CITAS_ULTIMA_SEMANA, 
		Constantes.CONFIGURACION_ELIMINACION_CITAS_ULTIMO_MES, 
		Constantes.CONFIGURACION_ELIMINACION_CITAS_ULTIMO_ANIO};
	List<String> listadoOpciones = UtilidadesConversores.convertirArrayCadenasListaCadenas(arrayOpciones);
	tiempoComboBox.getItems().clear();
	tiempoComboBox.getItems().addAll(listadoOpciones);
	
	// Opción seleccionada
	String opcionSeleccionada = configuracion.getString(Constantes.CONFIGURACION_ELIMINACION_CITAS, Constantes.CONFIGURACION_ELIMINACION_CITAS_ULTIMA_SEMANA);
	tiempoComboBox.setValue(opcionSeleccionada);
	
    }
    

    /**
     * <p>Método que guarda todas las propiedades de la ventana.</p>
     */
    private void guardarPropiedades() {
	
	try {
	    
	    // Setear el valor de cada control en el fichero de propiedades externo
	    constructor.getConfiguration().setProperty(Constantes.CONFIGURACION_HORA_APERTURA, UtilidadesConversores.convertirHoraCadena(apertura));
	    constructor.getConfiguration().setProperty(Constantes.CONFIGURACION_HORA_CIERRE, UtilidadesConversores.convertirHoraCadena(cierre));
	    constructor.getConfiguration().setProperty(Constantes.CONFIGURACION_DURACION, UtilidadesConversores.convertirEnteroCadena(duracionCitas));
	    constructor.getConfiguration().setProperty(Constantes.CONFIGURACION_ELIMINACION_CITAS, Utilidades.comprobarCadena(tiempo, Constantes.CONFIGURACION_ELIMINACION_CITAS_ULTIMA_SEMANA));
	    
	    // Guardar
	    constructor.save();
	    
	    // Informar de que se ha guardado correctamente
	    UtilidadesAlertas.mostrarAlertaInformativa(guardadoCorrecto);

	} catch (ConfigurationException excepcion) {

	    // Error al intentar guardar las propiedades del fichero en un objeto
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}
	
    }
    
    /**
     * <p>Método que calcula la fecha inicial del rango para eliminar las citas.</p>
     * <p>Se calcula restando una semana, un mes o un año a la fecha de ayer.</p>
     * 
     * @param fechaFin {@link LocalDate} fecha inicial para la que se quiere eliminar las citas
     * @return {@link LocalDate} 
     */
    private LocalDate calcularFechaInicio(LocalDate fechaFin) {
	
	// Inicializar la fecha que se va a retornar al final del método
	LocalDate fechaInicio = null;
	
	// Comprobar que la fecha fin pasada como parámetro NO es nula
	if(fechaFin != null) {
	    
	    switch(tiempo) {
	    case Constantes.CONFIGURACION_ELIMINACION_CITAS_ULTIMA_SEMANA: 
		fechaInicio = fechaFin.minusWeeks(1L);
		break;
	    case Constantes.CONFIGURACION_ELIMINACION_CITAS_ULTIMO_MES: 
		fechaInicio = fechaFin.minusMonths(1L);
		break;
	    case Constantes.CONFIGURACION_ELIMINACION_CITAS_ULTIMO_ANIO: 
		fechaInicio = fechaFin.minusYears(1L);
		break;
	    default: return fechaInicio = fechaFin;
	    
	    }
	    
	}
	
	return fechaInicio;
    }
    
    /**
     * <p>Método que calcula la fecha final del rango para eliminar las citas.</p>
     * <p>Siempre se retornará la fecha del día de ayer, porque se conservarán las citas actuales y futuras.</p>
     * 
     * @return {@link LocalDate} la fecha de ayer
     */
    private LocalDate calcularFechaFin() {
	return LocalDate.now().minusDays(1L);
    }


}
