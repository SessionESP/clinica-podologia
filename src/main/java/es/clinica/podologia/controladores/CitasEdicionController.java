package es.clinica.podologia.controladores;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.componentes.BeansComponent;
import es.clinica.podologia.componentes.ValidacionesComponent;
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
    private ValidacionesComponent validacionesComponent;
    
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
    private TextField dniPacienteTextField;
    @FXML
    private TextField nombrePacienteTextField;
    @FXML
    private TextField dniSanitarioTextField;
    @FXML
    private TextField nombreSanitarioTextField;
    @FXML
    private TextField identificadorTratamientoTextField;
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
    

    // Modelo sobre el que se trabajar?? la vista
    private CitasModelo modelo;
    
    // Modelos de paciente, sanitario y tratamiento
    private PacientesModelo modeloPaciente;
    private SanitariosModelo modeloSanitario;
    private TratamientosModelo modeloTratamiento;
    
    // Este atributo indicar?? si se trata de una inserci??n o de una modificaci??n
    private Boolean modo;
    
    /**
     * <p>M??todo que se ejecuta al inicializarse la vista de la edici??n de Citas.</p>
     */
    @FXML
    public void initialize() {
	
	cargarAutocompletados();
	
	cargarHorarios();
	
	// Comprobar si el modelo es nulo
	if(modelo != null) {
	    
	    // En caso de que NO sea nulo, comprobar si existe
	    modo = citasService.comprobarExistenciaCita(modelo.getIdCita());
	    
	    if(Boolean.TRUE.equals(modo)) {
		
		// Si existe, se trata de una actualizaci??n
		prepararModificacion();
		
	    } else {
		
		// Si NO existe, se trata de una inserci??n desde la agenda, por lo que es necesario cargar ciertos valores del modelo
		prepararAltaAgenda();
	    }
	    
	} else {
	    
	    // Si el modelo es nulo, se trata de una inserci??n
	    prepararAlta();
	    
	}
	
	// Cargar los formateadores de cada una de las cajas de texto
	cargarFormateadores();
	
    }
    
    /**
     * <p>M??todo que guarda un {@code paciente} de forma r??pida en esta vista.</p>
     */
    @FXML
    private void crearPacienteRapido() {
	
	try {
	    
	    // Inicializar el modelo
	    modeloPaciente = new PacientesModelo();
		
	    // Setear los valores de las cajas de texto en los atributos del modelo
	    modeloPaciente.setDniPaciente(dniPacienteTextField.getText());
	    modeloPaciente.setNombre(nombrePacienteTextField.getText());

	    // Guardar el tratamiento
	    Boolean resultado = pacientesService.insertarActualizarPaciente(modeloPaciente);

	    // Comprobar si se ha realizaco correctamente el guardado del paciente
	    if (Boolean.TRUE.equals(resultado)) {

		// El sanitario se ha guardado bien
		UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? pacientesModificacionCorrecta : pacientesAltaCorrecta);

		PacientesListadoController pacientesListadoController = (PacientesListadoController) beansComponent.obtenerControlador(Constantes.PACIENTES_LISTADO_CONTROLLER);
		pacientesListadoController.initialize();
		cargarAutocompletadoPacientes();

	    } else {

		// El sanitario no se ha guardado
		UtilidadesAlertas.mostrarAlertaError(
			Boolean.TRUE.equals(modo) ? pacientesModificacionIncorrecta : pacientesAltaIncorrecta);

	    }

	    
	} catch (Exception excepcion) {
	    
	    // Error al intentar guardar el paciente
	    validacionesComponent.visualizarError(excepcion, TRAZAS);
	    
	}
	
    }
    
    /**
     * <p>M??todo que guarda un {@code sanitario} de forma r??pida en esta vista.</p>
     */
    @FXML
    private void crearSanitarioRapido() {

	try {
	    
	    // Inicializar el modelo
	    modeloSanitario = new SanitariosModelo();

	    // Setear los valores de las cajas de texto en los atributos del modelo
	    modeloSanitario.setDniSanitario(dniSanitarioTextField.getText());
	    modeloSanitario.setNombre(nombreSanitarioTextField.getText());

	    // Guardar el tratamiento
	    Boolean resultado = sanitariosService.insertarActualizarSanitario(modeloSanitario);

	    // Comprobar si se ha realizaco correctamente el guardado del sanitario
	    if (Boolean.TRUE.equals(resultado)) {

		// El sanitario se ha guardado bien
		UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? sanitariosModificacionCorrecta : sanitariosAltaCorrecta);

		SanitariosListadoController sanitariosListadoController = (SanitariosListadoController) beansComponent.obtenerControlador(Constantes.SANITARIOS_LISTADO_CONTROLLER);
		sanitariosListadoController.initialize();
		cargarAutocompletadoSanitarios();

	    } else {

		// El sanitario no se ha guardado
		UtilidadesAlertas.mostrarAlertaError(
			Boolean.TRUE.equals(modo) ? sanitariosModificacionIncorrecta : sanitariosAltaIncorrecta);

	    }

	    
	} catch (Exception excepcion) {
	    
	    // Error al intentar guardar el paciente
	    validacionesComponent.visualizarError(excepcion, TRAZAS);
	    
	}
	
    }
    
    /**
     * <p>M??todo que guarda un {@code tratamiento} de forma r??pida en esta vista.</p>
     */
    @FXML
    private void crearTratamientoRapido() {
	
	try {
	    
	    // Inicializar el modelo
	    modeloTratamiento = new TratamientosModelo();

	    // Setear los valores de las cajas de texto en los atributos del modelo
	    modeloTratamiento.setIdTratamientoCadena(identificadorTratamientoTextField.getText());
	    modeloTratamiento.setNombre(nombreTratamientoTextField.getText());

	    // Guardar el tratamiento
	    Boolean resultado = tratamientosService.insertarActualizarTratamiento(modeloTratamiento);

	    // Comprobar si se ha realizaco correctamente el guardado del tratamiento
	    if (Boolean.TRUE.equals(resultado)) {

		// El sanitario se ha guardado bien
		UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? tratamientosModificacionCorrecta : tratamientosAltaCorrecta);

		TratamientosListadoController tratamientosListadoController = (TratamientosListadoController) beansComponent.obtenerControlador(Constantes.TRATAMIENTOS_LISTADO_CONTROLLER);
		tratamientosListadoController.initialize();
		cargarAutocompletadoTratamientos();

	    } else {

		// El sanitario no se ha guardado
		UtilidadesAlertas.mostrarAlertaError(
			Boolean.TRUE.equals(modo) ? tratamientosModificacionIncorrecta : tratamientosAltaIncorrecta);

	    }

	    
	} catch (Exception excepcion) {
	    
	    // Error al intentar guardar el tratamiento
	    validacionesComponent.visualizarError(excepcion, TRAZAS);
	    
	}
	
    }
    
    /**
     * <p>M??todo invocado como un evento para guardar el sanitario.</p>
     */
    @FXML
    private void guardarCita() {
	
	try {
	    
	    // Comprobar si el modelo es nulo
	    if (modelo != null) {
		
		// Setear los valores de las cajas de texto en los atributos del modelo
		cargarModeloPaciente(dniPacienteTextField.getText());
		modelo.setDniPaciente(modeloPaciente.getDniPaciente());
		cargarModeloSanitario(dniSanitarioTextField.getText());
		modelo.setDniSanitario(modeloSanitario.getDniSanitario());
		cargarModeloTratamiento(identificadorTratamientoTextField.getText());
		modelo.setIdTratamiento(UtilidadesConversores.convertirCadenaEnteroDefecto(identificadorTratamientoTextField.getText(), 0));
		modelo.setNombreTratamiento(nombreTratamientoTextField.getText());
		modelo.setFecha(fechaDatePicker.getValue());
		modelo.setHoraDesde(horaInicioComboBox.getValue());
		modelo.setHoraHasta(horaFinComboBox.getValue());
		modelo.setObservaciones(observacionesTextArea.getText());

		// Guardar el tratamiento
		Boolean resultado = citasService.insertarActualizarCita(modelo);

		// Comprobar si se ha realizaco correctamente el guardado del sanitario
		if (Boolean.TRUE.equals(resultado)) {

		    // La cita se ha guardado bien
		    UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? citasModificacionCorrecta : citasAltaCorrecta);
		    
		    // Refresca el listado de citas
		    CitasListadoController citasListadoController = (CitasListadoController) beansComponent.obtenerControlador(Constantes.CITAS_LISTADO_CONTROLLER);
		    citasListadoController.initialize();
		    
		    // Refresca la agenda
		    AgendaSanitariosEdicionController agendaSanitariosEdicionController = (AgendaSanitariosEdicionController) beansComponent.obtenerControlador(Constantes.AGENDA_SANITARIOS_EDICION_CONTROLLER);
		    agendaSanitariosEdicionController.initialize();
		    
		    cancelarCita();

		} else {

		    // El sanitario no se ha guardado
		    UtilidadesAlertas.mostrarAlertaError(Boolean.TRUE.equals(modo) ? citasModificacionIncorrecta : citasAltaIncorrecta);

		}

	    }
	    
	} catch (Exception excepcion) {
	    
	    // Error al intentar guardar la cita
	    validacionesComponent.visualizarError(excepcion, TRAZAS);
	    
	}
	
    }
    
    @FXML
    private void cancelarCita() {
	
	modelo = null;
	
	modeloPaciente = null;
	modeloSanitario = null;
	modeloTratamiento = null;
	
	// Cerrar la ventana emergente
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();
	
    }
    
    /**
     * <p>M??todo que carga los todos los autocompletados.</p>
     */
    private void cargarAutocompletados() {
	
	cargarAutocompletadoPacientes();
	cargarAutocompletadoSanitarios();
	cargarAutocompletadoTratamientos();
	
    }
    
    
    /**
     * <p>M??todo que carga los autocompletados de los controles de {@code pacientes}.</p>
     */
    private void cargarAutocompletadoPacientes() {
	
	// Obtener un listado con todos los pacientes de la tabla
	List<PacientesModelo> listadoPacientes = pacientesService.listarPacientes();
	
	// Instanciar el autocompletado del DNI del paciente
	AutoCompletionBinding<String> dniPacienteAutocompletado = 
		TextFields.bindAutoCompletion(dniPacienteTextField, listadoPacientes.stream().map(PacientesModelo::getDniPaciente).collect(Collectors.toList()));
	dniPacienteAutocompletado.setOnAutoCompleted(event -> cargarModeloPaciente(event.getCompletion()));
	
	// Instanciar el autocompletado del DNI y nombre del paciente
	AutoCompletionBinding<String> nombrePacienteAutocompletado = 
		TextFields.bindAutoCompletion(nombrePacienteTextField, listadoPacientes.stream().map(PacientesModelo::buscar).collect(Collectors.toList()));
	nombrePacienteAutocompletado.setOnAutoCompleted(event -> cargarModeloPaciente(event.getCompletion()));
    }
    
    /**
     * <p>M??todo que carga el modelo de pacientes.</p>
     * 
     * @param paciente {@link String} cadena que puede venir con el {@code DNI} o con el {@code DNI - Nombre Completo}
     */
    private void cargarModeloPaciente(String paciente) {
	
	// Comprobar que el par??metro de entrada NO es nulo Ni est?? vac??o
	if (Boolean.TRUE.equals(StringUtils.isNotBlank(paciente))) {
	    
	    // Realizar b??squeda para cargar el paciente
	    modeloPaciente = pacientesService.encontrarPacienteDNI(paciente.split(Constantes.GUION_ESPACIADO)[0]);
	    
	    // Comprobar si la consulta ha devuelto un modelo
	    if (modeloPaciente != null) {
		
		// Cargar los valores correspondientes en las cajas de texto
		dniPacienteTextField.setText(modeloPaciente.getDniPaciente());
		nombrePacienteTextField.setText(modeloPaciente.toString());
		
	    } else {
		
		// Es un paciente que NO existe en la base de datos
		modeloPaciente = new PacientesModelo();
		modeloPaciente.setDniPaciente(dniPacienteTextField.getText());
		modeloPaciente.setNombre(nombrePacienteTextField.getText());
		
	    }
	}
    }
    
    
    /**
     * <p>M??todo que carga los autocompletados de los controles de {@code sanitarios}.</p>
     */
    private void cargarAutocompletadoSanitarios() {
	
	// Obtener un listado con todos los sanitarios de la tabla
	List<SanitariosModelo> listadoSanitarios = sanitariosService.listarSanitarios();
	
	// Instanciar el autocompletado del DNI del sanitario
	AutoCompletionBinding<String> dniSanitarioAutocompletado = 
		TextFields.bindAutoCompletion(dniSanitarioTextField, listadoSanitarios.stream().map(SanitariosModelo::getDniSanitario).collect(Collectors.toList()));
	dniSanitarioAutocompletado.setOnAutoCompleted(event -> cargarModeloSanitario(event.getCompletion()));
	
	// Instanciar el autocompletado del DNI y nombre del sanitario
	AutoCompletionBinding<String> nombreSanitarioAutocompletado = 
		TextFields.bindAutoCompletion(nombreSanitarioTextField, listadoSanitarios.stream().map(SanitariosModelo::buscar).collect(Collectors.toList()));
	nombreSanitarioAutocompletado.setOnAutoCompleted(event -> cargarModeloSanitario(event.getCompletion()));
	
    }
    
    /**
     * <p>M??todo que carga el modelo de sanitarios.</p>
     * 
     * @param sanitario {@link String} cadena que puede venir con el {@code DNI} o con el {@code DNI - Nombre Completo}
     */
    private void cargarModeloSanitario(String sanitario) {
	
	// Comprobar que el par??metro de entrada NO es nulo Ni est?? vac??o
	if (Boolean.TRUE.equals(StringUtils.isNotBlank(sanitario))) {
	    
	    // Realizar b??squeda para cargar el sanitario
	    modeloSanitario = sanitariosService.encontrarSanitarioDNI(sanitario.split(Constantes.GUION_ESPACIADO)[0]);
	    
	    // Comprobar si la consulta ha devuelto un modelo
	    if (modeloSanitario != null) {
		
		// Cargar los valores correspondientes en las cajas de texto
		dniSanitarioTextField.setText(modeloSanitario.getDniSanitario());
		nombreSanitarioTextField.setText(modeloSanitario.toString());
		
	    } else {
		
		// Es un sanitario que NO existe en la base de datos
		modeloSanitario = new SanitariosModelo();
		modeloSanitario.setDniSanitario(dniSanitarioTextField.getText());
		modeloSanitario.setNombre(nombreSanitarioTextField.getText());
		
	    }
	}
    }
    
    /**
     * <p>M??todo que carga los autocompletados de los controles de {@code tratamientos}.</p>
     */
    private void cargarAutocompletadoTratamientos() {
	
	// Obtener un listado con todos los tratamientos de la tabla
	List<TratamientosModelo> listadoTratamientos = tratamientosService.listarTratamientos();
	
	// Instanciar el autocompletado del identificador del tratamiento
	AutoCompletionBinding<String> identificadorTratamientoAutocompletado = 
		TextFields.bindAutoCompletion(identificadorTratamientoTextField, listadoTratamientos.stream().map(TratamientosModelo::getIdTratamientoCadena).collect(Collectors.toList()));
	identificadorTratamientoAutocompletado.setOnAutoCompleted(event -> cargarModeloTratamiento(event.getCompletion()));
	
	// Instanciar el autocompletado del identificador y el nombre del tratamiento
	AutoCompletionBinding<String> nombreTratamientoAutocompletado = 
		TextFields.bindAutoCompletion(nombreTratamientoTextField, listadoTratamientos.stream().map(TratamientosModelo::toString).collect(Collectors.toList()));
	nombreTratamientoAutocompletado.setOnAutoCompleted(event -> cargarModeloTratamiento(event.getCompletion()));
	
    }
    
    /**
     * <p>M??todo que carga el modelo de tratamientos.</p>
     * 
     * @param sanitario {@link String} cadena que viene con el {@code Identificador del tratamiento - Nombre del tratamiento}
     */
    private void cargarModeloTratamiento(String tratamiento) {
	
	// Comprobar que el par??metro de entrada NO es nulo Ni est?? vac??o
	if (Boolean.TRUE.equals(StringUtils.isNotBlank(tratamiento))) {
	    
	    // Realizar b??squeda para cargar el tratamiento
	    modeloTratamiento = tratamientosService.encontrarTratamiento(UtilidadesConversores.convertirCadenaEntero(tratamiento.split(Constantes.GUION_ESPACIADO)[0]));
	    
	    // Comprobar si la consulta ha devuelto un modelo
	    if (modeloTratamiento != null) {
		
		// Cargar los valores correspondientes en las cajas de texto
		identificadorTratamientoTextField.setText(modeloTratamiento.getIdTratamientoCadena());
		nombreTratamientoTextField.setText(modeloTratamiento.getNombre());
		
	    } else {
		
		// Es un tratamiento que NO existe en la base de datos
		modeloTratamiento = new TratamientosModelo();
		modeloTratamiento.setIdTratamientoCadena(identificadorTratamientoTextField.getText());
		modeloTratamiento.setNombre(nombreTratamientoTextField.getText());
		
	    }
	}
    }
    
    /**
     * <p>M??todo donde se realizar??n todos los preparativos para inicializar la vista para un alta nueva.</p>
     */
    private void prepararAlta() {
	
	// Inicializar el objeto modelo de la cita
	modelo = new CitasModelo();
	
	// Inicializar los objetos modelo que dependen de la cita
	modeloPaciente = new PacientesModelo();
	modeloSanitario = new SanitariosModelo();
	modeloTratamiento = new TratamientosModelo();
	
	// Aplicar el t??tulo de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloAltaVista, ""));
	
	// Etiqueta con el t??tulo del formulario
	tituloLabel.setText(tituloAltaVista);
	
	// Inicializar todas las cajas de texto vac??as
	dniPacienteTextField.clear();
	nombrePacienteTextField.clear();
	dniSanitarioTextField.clear();
	nombreSanitarioTextField.clear();
	identificadorTratamientoTextField.clear();
	nombreTratamientoTextField.clear();
	fechaDatePicker.setValue(null);
	horaInicioComboBox.setValue(null);
	horaFinComboBox.setValue(null);
	observacionesTextArea.clear();
	
    }
    
    /**
     * <p>M??todo donde se realizar??n todos los preparativos para inicializar la vista para un alta nueva desde la agenda.</p>
     */
    private void prepararAltaAgenda() {
	
	// Inicializar los objetos modelo que dependen de la cita
	modeloPaciente = new PacientesModelo();
	modeloTratamiento = new TratamientosModelo();
	
	// Aplicar el t??tulo de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloAltaVista, ""));
	
	// Etiqueta con el t??tulo del formulario
	tituloLabel.setText(tituloAltaVista);
	
	// Inicializar todas las cajas de texto vac??as
	dniPacienteTextField.clear();
	nombrePacienteTextField.clear();
	cargarModeloSanitario(modelo.getDniSanitario());
	identificadorTratamientoTextField.clear();
	nombreTratamientoTextField.clear();
	fechaDatePicker.setValue(modelo.getFecha());
	horaInicioComboBox.setValue(modelo.getHoraDesde());
	horaFinComboBox.setValue(null);
	observacionesTextArea.clear();
	
    }
    
    /**
     * <p>M??todo donde se realizar??n todos los preparativos para inicializar la vista para una modificaci??n.</p>
     */
    private void prepararModificacion() {
	
	// Aplicar el t??tulo de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloEdicionVista, ""));
	
	// Etiqueta con el t??tulo del formulario
	tituloLabel.setText(tituloEdicionVista);
	
	// Inicializar todas las cajas de texto, as?? como los modelos con los valores de los atributos del modelo principal de la cita
	cargarModeloPaciente(modelo.getDniPaciente());
	cargarModeloSanitario(modelo.getDniSanitario());
	cargarModeloTratamiento(UtilidadesConversores.convertirEnteroCadena(modelo.getIdTratamiento()));
	fechaDatePicker.setValue(modelo.getFecha());
	horaInicioComboBox.setValue(modelo.getHoraDesde());
	horaFinComboBox.setValue(modelo.getHoraHasta());
	observacionesTextArea.setText(modelo.getObservaciones());
	
    }
    
    
    /**
     * <p>M??todo que carga las listas desplegables para seleccionar la hora de inicio y la hora de fin de la cita.</p>
     */
    private void cargarHorarios() {
	
	// Inicializar el constructor con los par??metros del fichero externo
	FileBasedConfigurationBuilder<FileBasedConfiguration> constructor = 
		UtilidadesPropiedades.crearConstructor(new Parameters(), propiedadesExternas.get(0), Constantes.COMA);
	
	try {

	    // Comprobar que el constructor y los par??metros NO son nulos
	    if (constructor != null && constructor.getConfiguration() != null) {

		// Guardar la informaci??n del fichero de configuraci??n en un objeto
		FileBasedConfiguration configuracion = constructor.getConfiguration();

		// Hora de apertura
		LocalTime apertura = UtilidadesConversores.convertirCadenaHora(configuracion.getString(
			Constantes.CONFIGURACION_HORA_APERTURA, 
			Constantes.CONFIGURACION_APERTURA_DEFECTO));

		// Hora de cierre
		LocalTime cierre = UtilidadesConversores.convertirCadenaHora(configuracion.getString(
			Constantes.CONFIGURACION_HORA_CIERRE, 
			Constantes.CONFIGURACION_CIERRE_DEFECTO));

		// Duraci??n de las citas en minutos
		Integer duracionCitas = configuracion.getInteger(
			Constantes.CONFIGURACION_DURACION, 
			Constantes.CONFIGURACION_DURACION_DEFECTO);
		
		// Calcular el n??mero de filas que se van a generar
		Integer numeroElementos = Math.floorDiv(UtilidadesConversores.calcularDiferenciaMinutos(apertura, cierre), duracionCitas);
		
		// Limpiar la tabla de filas anteriores, por si las tuviera (caso de refrescar la vista)
		horaInicioComboBox.getItems().clear();
		horaFinComboBox.getItems().clear();
		
		// Iterar sobre el n??mero de filas obtenido
		for (int i = 0; i < numeroElementos; i++) {
		    horaInicioComboBox.getItems().add(apertura.plusMinutes(UtilidadesConversores.convertirEnteroLong(i * duracionCitas)));
		    horaFinComboBox.getItems().add(apertura.plusMinutes(UtilidadesConversores.convertirEnteroLong((i + 1) * duracionCitas)));
		}

	    }

	} catch (ConfigurationException excepcion) {

	    // Error al intentar acceder a
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}
	
    }
    
    
    /**
     * <p>En este m??todo se preparar??n los formateadores de todos los controles de la vista.</p>
     */
    private void cargarFormateadores() {
	
	dniPacienteTextField.setTextFormatter(UtilidadesControles.cargarFormateadorSinPatron(Constantes.LIMITE_20));
	nombrePacienteTextField.setTextFormatter(UtilidadesControles.cargarFormateadorSinPatron(Constantes.LIMITE_100 + 1));
	dniSanitarioTextField.setTextFormatter(UtilidadesControles.cargarFormateadorSinPatron(Constantes.LIMITE_20));
	nombreSanitarioTextField.setTextFormatter(UtilidadesControles.cargarFormateadorSinPatron(Constantes.LIMITE_100 + 1));
	identificadorTratamientoTextField.setTextFormatter(UtilidadesControles.cargarFormateadorConPatron(Constantes.PATRON_NUMEROS_ENTEROS, Constantes.LIMITE_5));
	nombreTratamientoTextField.setTextFormatter(UtilidadesControles.cargarFormateadorSinPatron(Constantes.LIMITE_50));
	observacionesTextArea.setTextFormatter(UtilidadesControles.cargarFormateadorSinPatron(Constantes.LIMITE_100));
	
    }
    
    
    public CitasModelo getModelo() {
        return modelo;
    }

    public void setModelo(CitasModelo modelo) {
        this.modelo = modelo;
    }

}
