package es.clinica.podologia.controladores;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.controlsfx.control.textfield.TextFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.componentes.BeansComponent;
import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.CitasModelo;
import es.clinica.podologia.modelos.PacientesModelo;
import es.clinica.podologia.modelos.SanitariosModelo;
import es.clinica.podologia.modelos.TratamientosModelo;
import es.clinica.podologia.servicios.CitasService;
import es.clinica.podologia.servicios.PacientesService;
import es.clinica.podologia.servicios.SanitariosService;
import es.clinica.podologia.servicios.TratamientosService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesControles;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesPropiedades;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para las Citas.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class CitasEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(CitasEdicionController.class);
    
    @Value("${citas.alta.titulo}")
    private String tituloAltaVista;
    
    @Value("${citas.edicion.titulo}")
    private String tituloEdicionVista;
    
    
    @Value("${citas.alta.guardado.true}")
    private String citasAltaCorrecta;
    
    @Value("${citas.alta.guardado.false}")
    private String citasAltaIncorrecta;
    
    @Value("${citas.modificacion.guardado.true}")
    private String citasModificacionCorrecta;
    
    @Value("${citas.modificacion.guardado.false}")
    private String citasModificacionIncorrecta;
    
    
    @Value("${pacientes.alta.guardado.true}")
    private String pacientesAltaCorrecta;
    
    @Value("${pacientes.alta.guardado.false}")
    private String pacientesAltaIncorrecta;
    
    @Value("${pacientes.modificacion.guardado.true}")
    private String pacientesModificacionCorrecta;
    
    @Value("${pacientes.modificacion.guardado.false}")
    private String pacientesModificacionIncorrecta;
    
    
    @Value("${sanitarios.alta.guardado.true}")
    private String sanitariosAltaCorrecta;
    
    @Value("${sanitarios.alta.guardado.false}")
    private String sanitariosAltaIncorrecta;
    
    @Value("${sanitarios.modificacion.guardado.true}")
    private String sanitariosModificacionCorrecta;
    
    @Value("${sanitarios.modificacion.guardado.false}")
    private String sanitariosModificacionIncorrecta;
    
    
    @Value("${tratamientos.alta.guardado.true}")
    private String tratamientosAltaCorrecta;
    
    @Value("${tratamientos.alta.guardado.false}")
    private String tratamientosAltaIncorrecta;
    
    @Value("${tratamientos.modificacion.guardado.true}")
    private String tratamientosModificacionCorrecta;
    
    @Value("${tratamientos.modificacion.guardado.false}")
    private String tratamientosModificacionIncorrecta;
    
    
    @Value("${spring.config.import}")
    private List<String> propiedadesExternas;
    
    
    @Autowired
    private BeansComponent beansComponent;
    
    @Autowired
    private CitasService citasService;
    
    @Autowired
    private PacientesService pacientesService;
    
    @Autowired
    private SanitariosService sanitariosService;
    
    @Autowired
    private TratamientosService tratamientosService;
    
    
    @FXML
    private Label tituloLabel;
    
    @FXML
    private TextField idCitaTextField;
    @FXML
    private TextField dniPacienteTextField;
    @FXML
    private TextField nombrePacienteTextField;
    @FXML
    private TextField dniSanitarioTextField;
    @FXML
    private TextField nombreSanitarioTextField;
    @FXML
    private TextField nombreTratamientoTextField;
    
    @FXML
    private DatePicker fechaDatePicker;
    
    @FXML
    private ComboBox<LocalTime> horaInicioComboBox;
    @FXML
    private ComboBox<LocalTime> horaFinComboBox;
    
    @FXML
    private TextArea observacionesTextArea;
    
    @FXML
    private Button nuevoPacienteRapidoButton;
    @FXML
    private Button nuevoSanitarioRapidoButton;
    @FXML
    private Button nuevoTratamientoRapidoButton;
    @FXML
    private Button aceptarButton;
    @FXML
    private Button cancelarButton;
    

    // Modelo sobre el que se trabajará la vista
    private CitasModelo modelo;
    
    // Modelos de paciente, sanitario y tratamiento
    private PacientesModelo paciente;
    private SanitariosModelo sanitario;
    private TratamientosModelo tratamiento;
    
    // Listados de paciente, sanitario y tratamiento
    private List<PacientesModelo> listadoPacientes;
    private List<SanitariosModelo> listadoSanitarios;
    private List<TratamientosModelo> listadoTratamientos;
    
    
    // Este atributo indicará si se trata de una inserción o de una modificación
    private Boolean modo;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Citas.</p>
     */
    @FXML
    public void initialize() {
	
	cargarAutocompletados();
	
	cargarHorarios();
	
	// Comprobar si el modelo es nulo
	if(modelo != null) {
	    
	    // Cargar los formateadores de cada una de las cajas de texto
	    cargarFormateadores();
	    
	    // En caso de que NO sea nulo, comprobar si existe
	    modo = citasService.comprobarExistenciaCita(modelo.getIdCita());
	    
	    if(Boolean.TRUE.equals(modo)) {
		
		// Si existe, se trarta de una actualización
		prepararModificacion();
	    } else {
		
		// Si NO existe, se trarta de una inserción
		prepararAlta();
	    }
	    
	} else {
	    
	    // Si el modelo es nulo, se trata de una inserción
	    prepararAlta();
	    
	}
	
    }
    
    /**
     * <p>Método que guarda un {@code paciente} de forma rápida en esta vista.</p>
     */
    @FXML
    private void crearPacienteRapido() {
	
	try {
	    
	    // Inicializar el modelo
	    paciente = new PacientesModelo();
		
	    // Setear los valores de las cajas de texto en los atributos del modelo
	    paciente.setDniPaciente(dniPacienteTextField.getText());
	    paciente.setNombre(nombrePacienteTextField.getText());

	    // Guardar el tratamiento
	    Boolean resultado = pacientesService.insertarActualizarPaciente(paciente);

	    // Comprobar si se ha realizaco correctamente el guardado del paciente
	    if (Boolean.TRUE.equals(resultado)) {

		// El sanitario se ha guardado bien
		UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? pacientesAltaCorrecta : pacientesModificacionCorrecta);

		PacientesListadoController pacientesListadoController = (PacientesListadoController) beansComponent.obtenerControlador(Constantes.PACIENTES_LISTADO_CONTROLLER);
		pacientesListadoController.initialize();
		cargarAutocompletadoPacientes();

	    } else {

		// El sanitario no se ha guardado
		UtilidadesAlertas.mostrarAlertaError(
			Boolean.TRUE.equals(modo) ? pacientesAltaIncorrecta : pacientesModificacionIncorrecta);

	    }

	    
	} catch (Exception excepcion) {
	    
	    // Error al intentar guardar el paciente
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
	    
	}
	
    }
    
    /**
     * <p>Método que guarda un {@code sanitario} de forma rápida en esta vista.</p>
     */
    @FXML
    private void crearSanitarioRapido() {

	try {
	    
	    // Inicializar el modelo
	    sanitario = new SanitariosModelo();

	    // Setear los valores de las cajas de texto en los atributos del modelo
	    sanitario.setDniSanitario(dniSanitarioTextField.getText());
	    sanitario.setNombre(nombreSanitarioTextField.getText());

	    // Guardar el tratamiento
	    Boolean resultado = sanitariosService.insertarActualizarSanitario(sanitario);

	    // Comprobar si se ha realizaco correctamente el guardado del sanitario
	    if (Boolean.TRUE.equals(resultado)) {

		// El sanitario se ha guardado bien
		UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? sanitariosAltaCorrecta : sanitariosModificacionCorrecta);

		SanitariosListadoController sanitariosListadoController = (SanitariosListadoController) beansComponent.obtenerControlador(Constantes.SANITARIOS_LISTADO_CONTROLLER);
		sanitariosListadoController.initialize();
		cargarAutocompletadoSanitarios();

	    } else {

		// El sanitario no se ha guardado
		UtilidadesAlertas.mostrarAlertaError(
			Boolean.TRUE.equals(modo) ? sanitariosAltaIncorrecta : sanitariosModificacionIncorrecta);

	    }

	    
	} catch (Exception excepcion) {
	    
	    // Error al intentar guardar el paciente
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
	    
	}
	
    }
    
    /**
     * <p>Método que guarda un {@code tratamiento} de forma rápida en esta vista.</p>
     */
    @FXML
    private void crearTratamientoRapido() {
	
	try {
	    
	    // Inicializar el modelo
	    tratamiento = new TratamientosModelo();

	    // Setear el valor de las caja de texto en el atributo del modelo
	    tratamiento.setNombre(nombreSanitarioTextField.getText());

	    // Guardar el tratamiento
	    Boolean resultado = tratamientosService.insertarActualizarTratamiento(tratamiento);

	    // Comprobar si se ha realizaco correctamente el guardado del tratamiento
	    if (Boolean.TRUE.equals(resultado)) {

		// El sanitario se ha guardado bien
		UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? tratamientosAltaCorrecta : tratamientosModificacionCorrecta);

		TratamientosListadoController tratamientosListadoController = (TratamientosListadoController) beansComponent.obtenerControlador(Constantes.TRATAMIENTOS_LISTADO_CONTROLLER);
		tratamientosListadoController.initialize();
		cargarAutocompletadoTratamientos();

	    } else {

		// El sanitario no se ha guardado
		UtilidadesAlertas.mostrarAlertaError(
			Boolean.TRUE.equals(modo) ? tratamientosAltaIncorrecta : tratamientosModificacionIncorrecta);

	    }

	    
	} catch (Exception excepcion) {
	    
	    // Error al intentar guardar el paciente
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
	    
	}
    }
    
    /**
     * <p>Método invocado como un evento para guardar el sanitario.</p>
     */
    @FXML
    private void guardarCita() {
	
	try {
	    
	    // Comprobar si el modelo es nulo
	    if (modelo != null) {
		
		// Setear los valores de las cajas de texto en los atributos del modelo
		modelo.setDniPaciente(paciente.getDniPaciente());
		modelo.setDniSanitario(sanitario.getDniSanitario());
		modelo.setIdTratamiento(tratamiento.getIdTratamiento());
		modelo.setFecha(fechaDatePicker.getValue());
		modelo.setHoraDesde(horaInicioComboBox.getValue());
		modelo.setHoraHasta(horaFinComboBox.getValue());
		modelo.setObservaciones(observacionesTextArea.getText());

		// Guardar el tratamiento
		Boolean resultado = citasService.insertarActualizarCita(modelo);

		// Comprobar si se ha realizaco correctamente el guardado del sanitario
		if (Boolean.TRUE.equals(resultado)) {

		    // El sanitario se ha guardado bien
		    UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? citasAltaCorrecta : citasModificacionCorrecta);
		    
		    CitasListadoController citasListadoController = (CitasListadoController) beansComponent.obtenerControlador(Constantes.CITAS_LISTADO_CONTROLLER);
		    citasListadoController.initialize();
		    cancelarCita();

		} else {

		    // El sanitario no se ha guardado
		    UtilidadesAlertas.mostrarAlertaError(Boolean.TRUE.equals(modo) ? citasAltaIncorrecta : citasModificacionIncorrecta);

		}

	    }
	    
	} catch (Exception excepcion) {
	    
	    // Error al intentar guardar la cita
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
	    
	}
	
    }
    
    @FXML
    private void cancelarCita() {
	
	modelo = null;
	
	paciente = null;
	sanitario = null;
	tratamiento = null;
	
	// Cerrar la ventana emergente
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();
	
    }
    
    /**
     * <p>Método que carga los todos los autocompletados.</p>
     */
    private void cargarAutocompletados() {
	
	cargarAutocompletadoPacientes();
	cargarAutocompletadoSanitarios();
	cargarAutocompletadoTratamientos();
	
    }
    
    /**
     * <p>Método que carga los autocompletados de los controles de {@code pacientes}.</p>
     */
    private void cargarAutocompletadoPacientes() {
	
	listadoPacientes = pacientesService.listarPacientes();
	TextFields.bindAutoCompletion(dniPacienteTextField, listadoPacientes.stream().map(PacientesModelo::getDniPaciente).collect(Collectors.toList()));
	TextFields.bindAutoCompletion(nombrePacienteTextField, listadoPacientes.stream().map(PacientesModelo::toString).collect(Collectors.toList()));
	
    }
    
    /**
     * <p>Método que carga los autocompletados de los controles de {@code sanitarios}.</p>
     */
    private void cargarAutocompletadoSanitarios() {
	
	listadoSanitarios = sanitariosService.listarSanitarios();
	TextFields.bindAutoCompletion(dniSanitarioTextField, listadoSanitarios.stream().map(SanitariosModelo::getDniSanitario).collect(Collectors.toList()));
	TextFields.bindAutoCompletion(nombreSanitarioTextField, listadoSanitarios.stream().map(SanitariosModelo::toString).collect(Collectors.toList()));
	
    }
    
    /**
     * <p>Método que carga los autocompletados de los controles de {@code tratamientos}.</p>
     */
    private void cargarAutocompletadoTratamientos() {
	
	listadoTratamientos = tratamientosService.listarTratamientos();
	TextFields.bindAutoCompletion(nombreTratamientoTextField, listadoTratamientos.stream().map(TratamientosModelo::getNombre).collect(Collectors.toList()));
	
    }
    
    /**
     * <p>Método donde se realizarán todos los preparativos para inicializar la vista para un alta nueva.</p>
     */
    private void prepararAlta() {
	
	// Inicializar el objeto modelo
	modelo = new CitasModelo();
	
	// Aplicar el título de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloAltaVista, ""));
	
	// Etiqueta con el título del formulario
	tituloLabel.setText(tituloAltaVista);
	
	// Habilitar el cuadro de texto con el DNI, que es la clave primaria de la tabla
	idCitaTextField.setDisable(Boolean.FALSE);
	
	// Inicializar todas las cajas de texto vacías
	idCitaTextField.setText(null);
	dniPacienteTextField.setText(Constantes.CADENA_VACIA);
	nombrePacienteTextField.setText(Constantes.CADENA_VACIA);
	dniSanitarioTextField.setText(Constantes.CADENA_VACIA);
	nombreSanitarioTextField.setText(Constantes.CADENA_VACIA);
	nombreTratamientoTextField.setText(Constantes.CADENA_VACIA);
	fechaDatePicker.setValue(null);
	horaInicioComboBox.setValue(null);
	horaFinComboBox.setValue(null);
	observacionesTextArea.setText(Constantes.CADENA_VACIA);
	
    }
    
    /**
     * <p>Método donde se realizarán todos los preparativos para inicializar la vista para una modificación.</p>
     */
    private void prepararModificacion() {
	
	// Aplicar el título de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloEdicionVista, ""));
	
	// Etiqueta con el título del formulario
	tituloLabel.setText(tituloEdicionVista);
	
	// Deshabilitar el cuadro de texto con el DNI, que es la clave primaria de la tabla
	idCitaTextField.setDisable(Boolean.TRUE);
	
	// Inicializar todas las cajas de texto con los valores de los atributos del modelo
	idCitaTextField.setText(UtilidadesConversores.convertirEnteroCadena(modelo.getIdCita()));
	dniPacienteTextField.setText(modelo.getDniPaciente());
	nombrePacienteTextField.setText(modelo.getNombrePaciente());
	dniSanitarioTextField.setText(modelo.getDniSanitario());
	nombreSanitarioTextField.setText(modelo.getNombreSanitario());
	nombreTratamientoTextField.setText(modelo.getNombreTratamiento());
	fechaDatePicker.setValue(modelo.getFecha());
	horaInicioComboBox.setValue(modelo.getHoraDesde());
	horaFinComboBox.setValue(modelo.getHoraHasta());
	observacionesTextArea.setText(modelo.getObservaciones());
	
    }
    
    
    /**
     * <p>Método que carga las listas desplegables para seleccionar la hora de inicio y la hora de fin de la cita.</p>
     */
    private void cargarHorarios() {
	
	// Inicializar el constructor con los parámetros del fichero externo
	FileBasedConfigurationBuilder<FileBasedConfiguration> constructor = UtilidadesPropiedades.crearConstructor(new Parameters(), propiedadesExternas.get(0), Constantes.COMA);
	
	try {

	    // Comprobar que el constructor y los parámetros NO son nulos
	    if (constructor != null && constructor.getConfiguration() != null) {

		// Guardar la información del fichero de configuración en un objeto
		FileBasedConfiguration configuracion = constructor.getConfiguration();

		// Hora de apertura
		LocalTime apertura = UtilidadesConversores.convertirCadenaHora(configuracion.getString(
			Constantes.CONFIGURACION_HORA_APERTURA, 
			Constantes.CONFIGURACION_APERTURA_DEFECTO));

		// Hora de cierre
		LocalTime cierre = UtilidadesConversores.convertirCadenaHora(configuracion.getString(
			Constantes.CONFIGURACION_HORA_CIERRE, 
			Constantes.CONFIGURACION_CIERRE_DEFECTO));

		// Duración de las citas en minutos
		Integer duracionCitas = configuracion.getInteger(
			Constantes.CONFIGURACION_DURACION, 
			Constantes.CONFIGURACION_DURACION_DEFECTO);
		
		// Calcular el número de filas que se van a generar
		Integer numeroElementos = Math.floorDiv(UtilidadesConversores.calcularDiferenciaMinutos(apertura, cierre), duracionCitas);
		
		// Limpiar la tabla de filas anteriores, por si las tuviera (caso de refrescar la vista)
		horaInicioComboBox.getItems().clear();
		horaFinComboBox.getItems().clear();
		
		// Iterar sobre el número de filas obtenido
		for (int i = 0; i < numeroElementos; i++) {
		    horaInicioComboBox.getItems().add(apertura.plusMinutes(UtilidadesConversores.convertirEnteroLong(i * duracionCitas)));
		    horaFinComboBox.getItems().add(apertura.plusMinutes(UtilidadesConversores.convertirEnteroLong(i * duracionCitas)));
		}

	    }

	} catch (ConfigurationException excepcion) {

	    // Error al intentar acceder a
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}
	
    }
    
    
    private void cargarFormateadores() {
	dniSanitarioTextField.setTextFormatter(UtilidadesControles.formateador(Constantes.PATRON_DNI, 9));
    }
    
    
    public CitasModelo getModelo() {
        return modelo;
    }

    public void setModelo(CitasModelo modelo) {
        this.modelo = modelo;
    }

}
