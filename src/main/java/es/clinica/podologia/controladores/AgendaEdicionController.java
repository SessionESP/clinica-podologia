package es.clinica.podologia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.CitasModelo;
import es.clinica.podologia.modelos.PacientesModelo;
import es.clinica.podologia.modelos.SanitariosModelo;
import es.clinica.podologia.modelos.TratamientosModelo;
import es.clinica.podologia.servicios.PacientesService;
import es.clinica.podologia.servicios.SanitariosService;
import es.clinica.podologia.servicios.TratamientosService;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesNavegacion;
import es.clinica.podologia.vistas.AgendaSanitariosEdicionView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * <p>Controlador para la vista de la agenda de la aplicación.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class AgendaEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(AgendaEdicionController.class);
    
    
    @Autowired
    private PacientesService pacientesService;
    
    @Autowired
    private SanitariosService sanitariosService;
    
    @Autowired
    private TratamientosService tratamientosService;
    
    @FXML
    private AnchorPane agendaAnchorPane;
    
    @FXML
    private Button anadirAgendaButton;
    
    @FXML
    private TextField identificadorCitaTextField;
    @FXML
    private TextField pacienteCitaTextField;
    @FXML
    private TextField sanitarioCitaTextField;
    @FXML
    private TextField tratamientoCitaTextField;
    @FXML
    private TextField fechaCitaTextField;
    @FXML
    private TextField horaInicioCitaTextField;
    @FXML
    private TextField horaFinCitaTextField;
    @FXML
    private TextArea observacionesCitaTextArea;
    
    @FXML
    private TextField identificadorPacienteTextField;
    @FXML
    private TextField dniPacienteTextField;
    @FXML
    private TextField nombrePacienteTextField;
    @FXML
    private TextField apellidosPacienteTextField;
    @FXML
    private TextField fechaNacimientoPacienteTextField;
    @FXML
    private Label edadPacienteLabel;
    @FXML
    private TextField direccionPacienteTextField;
    @FXML
    private TextField telefonoPacienteTextField;
    @FXML
    private TextField nombreAdjuntoPacienteCitaTextField;

    
    @FXML
    private TextField identificadorSanitarioTextField;
    @FXML
    private TextField dniSanitarioTextField;
    @FXML
    private TextField nombreSanitarioTextField;
    @FXML
    private TextField apellidosSanitarioTextField;
    @FXML
    private TextField especialidadSanitarioTextField;
    
    @FXML
    private TextField identificadorTratamientoTextField;
    @FXML
    private TextField nombreTratamientoTextField;
    @FXML
    private TextArea descripcionTratamientoTextArea;
    @FXML
    private ColorPicker colorTratamientoColorPicker;
    @FXML
    private TextField precioTratamientoTextField;

    private CitasModelo modeloSeleccionado;

    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la agenda de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	// Añadir la vista que contiene las tres agendas
	agendaAnchorPane.getChildren().add(UtilidadesNavegacion.cargarVista(AgendaSanitariosEdicionView.class));
	
	limpiarDetalle();
	
    }

    
    public CitasModelo getModeloSeleccionado() {
        return modeloSeleccionado;
    }

    public void setModeloSeleccionado(CitasModelo modeloSeleccionado) {
        this.modeloSeleccionado = modeloSeleccionado;
    }
    
    
    /**
     * <p>En este método se procederá a cargar todos los controles del detalle de la vista.</p>
     */
    public void cargarDetalle() {
	
	TRAZAS.debug("Cargar el detalle de una cita.");
	
	// Comprobar que se ha seleccionado una cita
	if (modeloSeleccionado != null) {
	    
	    // CITA
	    identificadorCitaTextField.setText(UtilidadesConversores.convertirEnteroCadena(modeloSeleccionado.getIdCita()));
	    pacienteCitaTextField.setText(modeloSeleccionado.getDniPaciente());
	    sanitarioCitaTextField.setText(modeloSeleccionado.getDniSanitario());
	    tratamientoCitaTextField.setText(UtilidadesConversores.convertirEnteroCadena(modeloSeleccionado.getIdTratamiento()));
	    fechaCitaTextField.setText(UtilidadesConversores.convertirFechaCadena(modeloSeleccionado.getFecha()));
	    horaInicioCitaTextField.setText(UtilidadesConversores.convertirHoraCadena(modeloSeleccionado.getHoraDesde()));
	    horaFinCitaTextField.setText(UtilidadesConversores.convertirHoraCadena(modeloSeleccionado.getHoraHasta()));
	    observacionesCitaTextArea.setText(modeloSeleccionado.getObservaciones());

	    // PACIENTE DE LA CITA
	    PacientesModelo modeloPaciente = pacientesService.
		    encontrarPacienteDNI(modeloSeleccionado.getDniPaciente());
	    
	    // Comprobar que se he recuperado un paciente
	    if (modeloPaciente != null) {
		identificadorPacienteTextField.setText(UtilidadesConversores.convertirEnteroCadena(modeloPaciente.getIdPaciente()));
		dniPacienteTextField.setText(modeloPaciente.getDniPaciente());
		nombrePacienteTextField.setText(modeloPaciente.getNombre());
		apellidosPacienteTextField.setText(modeloPaciente.getApellidos());
		fechaNacimientoPacienteTextField.setText(UtilidadesConversores.convertirFechaCadena(modeloPaciente.getFechaNacimiento()));
		edadPacienteLabel.setText(UtilidadesConversores.imprimirEdad(modeloPaciente.getFechaNacimiento()));
		direccionPacienteTextField.setText(modeloPaciente.getDireccion());
		telefonoPacienteTextField.setText(modeloPaciente.getTelefono());
		nombreAdjuntoPacienteCitaTextField.setText(modeloPaciente.getNombreAdjunto());
	    }

	    // SANITARIO DE LA CITA
	    SanitariosModelo modeloSanitario = sanitariosService
		    .encontrarSanitarioDNI(modeloSeleccionado.getDniSanitario());
	    
	    // Comprobar que se ha recuperado un sanitario
	    if (modeloSanitario != null) {
		identificadorSanitarioTextField.setText(UtilidadesConversores.convertirEnteroCadena(modeloSanitario.getIdSanitario()));
		dniSanitarioTextField.setText(modeloSanitario.getDniSanitario());
		nombreSanitarioTextField.setText(modeloSanitario.getNombre());
		apellidosSanitarioTextField.setText(modeloSanitario.getApellidos());
		especialidadSanitarioTextField.setText(modeloSanitario.getEspecialidad());
	    }

	    // TRATAMIENTO DE LA CITA
	    TratamientosModelo modeloTratamientos = tratamientosService
		    .encontrarTratamiento(modeloSeleccionado.getIdTratamiento());
	    
	    // Comprobar que se ha recuperado un tratamiento
	    if (modeloTratamientos != null) {
		identificadorTratamientoTextField.setText(UtilidadesConversores.convertirEnteroCadena(modeloTratamientos.getIdTratamiento()));
		nombreTratamientoTextField.setText(modeloTratamientos.getNombre());
		descripcionTratamientoTextArea.setText(modeloTratamientos.getDescripcion());
		colorTratamientoColorPicker.setValue(UtilidadesConversores.convertirHexadecimalColor(modeloTratamientos.getColor()));
		precioTratamientoTextField.setText(UtilidadesConversores.convertirDecimalMoneda(modeloTratamientos.getPrecio()));
	    }

	}
	
    }
    
    /**
     * <p>En este método se procederá a limpiar todos los controles del detalle de la vista.</p>
     */
    public void limpiarDetalle() {
	
	TRAZAS.debug("Limpiar el detalle de una cita.");
	
	// Inicializar a null el modelo seleccionado
	modeloSeleccionado = null;

	// CITA
	identificadorCitaTextField.clear();
	pacienteCitaTextField.clear();
	sanitarioCitaTextField.clear();
	tratamientoCitaTextField.clear();
	fechaCitaTextField.clear();
	horaInicioCitaTextField.clear();
	horaFinCitaTextField.clear();
	observacionesCitaTextArea.clear();

	// PACIENTE DE LA CITA
	identificadorPacienteTextField.clear();
	dniPacienteTextField.clear();
	nombrePacienteTextField.clear();
	apellidosPacienteTextField.clear();
	fechaNacimientoPacienteTextField.clear();
	edadPacienteLabel.setText(Constantes.CADENA_VACIA);
	direccionPacienteTextField.clear();
	telefonoPacienteTextField.clear();
	nombreAdjuntoPacienteCitaTextField.clear();

	// SANITARIO DE LA CITA
	identificadorSanitarioTextField.clear();
	dniSanitarioTextField.clear();
	nombreSanitarioTextField.clear();
	apellidosSanitarioTextField.clear();
	especialidadSanitarioTextField.clear();

	// TRATAMIENTO DE LA CITA
	identificadorTratamientoTextField.clear();
	nombreTratamientoTextField.clear();
	descripcionTratamientoTextArea.clear();
	colorTratamientoColorPicker.setValue(Color.WHITE);
	precioTratamientoTextField.clear();

    }

}
