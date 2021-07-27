package es.clinica.podologia.controladores;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.clinica.podologia.componentes.BeansComponent;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.CitasModelo;
import es.clinica.podologia.servicios.CitasService;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    
    @Autowired
    private BeansComponent beansComponent;
    
    @Autowired
    private CitasService citasService;
    
    // Modelo sobre el que se trabajará la vista
    private CitasModelo modelo;
    
    // Este atributo indicará si se trata de una inserción o de una modificación
    private Boolean modo;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Citas.</p>
     */
    @FXML
    public void initialize() {
	
	TRAZAS.info("Vista de: " + this.getClass().getName());
	
    }
    
    @FXML
    private void crearPacienteRapido() {
	TRAZAS.info("Método crearPacienteRapido");
    }
    
    @FXML
    private void crearSanitarioRapido() {
	TRAZAS.info("Método crearSanitarioRapido");
    }
    
    @FXML
    private void crearTratamientoRapido() {
	TRAZAS.info("Método crearTratamientoRapido");
    }
    
    @FXML
    private void guardarCita() {
	TRAZAS.info("Método guardarCita");
    }
    
    @FXML
    private void cancelarCita() {
	
	modelo = null;
	
	// Cerrar la ventana emergente
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();
	
    }
    
    public CitasModelo getModelo() {
        return modelo;
    }

    public void setModelo(CitasModelo modelo) {
        this.modelo = modelo;
    }

}
