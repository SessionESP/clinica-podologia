package es.clinica.podologia.controladores;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.componentes.BeansComponent;
import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.PacientesModelo;
import es.clinica.podologia.modelos.TratamientosModelo;
import es.clinica.podologia.servicios.TratamientosService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * <p>Controlador para la edición de Pacientes.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class PacientesEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(PacientesEdicionController.class);
    
    @Value("${pacientes.seleccionar.ficha}")
    private String tituloSelectorFicha;
    
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
    
    @FXML
    private Button abrirButton;
    
    @Autowired
    private BeansComponent beansComponent;
    
    @Autowired
    private TratamientosService tratamientoService;
    
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
	
	modelo = new PacientesModelo();
	
    }
    
    @FXML
    private void seleccionarAdjunto() {
	
	FileChooser selectorFichero = new FileChooser();
	File fichero = selectorFichero.showOpenDialog(new Stage());
	selectorFichero.setTitle(tituloSelectorFicha);
	selectorFichero.setInitialDirectory(new File("C:\\"));
	selectorFichero.getExtensionFilters().addAll(new ExtensionFilter("Ficheros PDF", "*.pdf"));

	if (fichero != null) {
	    nombreAdjuntoLabel.setText(fichero.getName());
	} else {
	    nombreAdjuntoLabel.setText(Constantes.CADENA_VACIA);
	}
	
    }
    
    @FXML
    private void abrirAdjunto() {
	
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
