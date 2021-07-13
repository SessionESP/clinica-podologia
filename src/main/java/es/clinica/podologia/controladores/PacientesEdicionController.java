package es.clinica.podologia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.PacientesModelo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para la edición de Pacientes.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class PacientesEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(PacientesEdicionController.class);
    
    @FXML
    private Label tituloLabel;

    @FXML
    private TextField dniPacienteTextField;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField direccionTextField;
    @FXML
    private TextField telefonoTextField;
    
    @FXML
    private Label nombreAdjuntoLabel;
    
    @FXML
    private Button aceptarButton;
    
    @FXML
    private Button cancelarButton;
    
    @FXML
    private Button seleccionarButton;
    
    // Modelo sobre el que se trabajará la vista
    private PacientesModelo modelo;
    
    // Este atributo indicará si se trata de una inserción o de una modificación
    private Boolean modo;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Pacientes.</p>
     */
    @FXML
    public void initialize() {
	
	TRAZAS.info("Vista de: " + this.getClass().getName());
	
    }
    
    @FXML
    private void seleccionarAdjunto() {
	
    }
    
    @FXML
    private void guardarPaciente() {
	
    }
    
    @FXML
    private void cancelarPaciente() {
	
    }
    
    public PacientesModelo getModelo() {
        return modelo;
    }

    public void setModelo(PacientesModelo modelo) {
        this.modelo = modelo;
    }

}
