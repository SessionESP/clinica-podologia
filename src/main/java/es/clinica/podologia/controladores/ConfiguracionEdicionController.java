package es.clinica.podologia.controladores;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * <p>Controlador para la configuración de la aplicación.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class ConfiguracionEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(ConfiguracionEdicionController.class);
    
    @Value("${clinica.horario.apertura}")
    private LocalTime apertura;
    
    @Value("${clinica.horario.cierre}")
    private LocalTime cierre;
    
    @Value("${clinica.citas.duracion}")
    private Integer duracionCitas;
    
    @Value("${clinica.citas.pasadas}")
    private Boolean eliminacionPasadas;
    
    @Value("${clinica.citas.tipo.eliminacion}")
    private Boolean tipoEliminacion;
    
    @Value("${clinica.citas.dias.eliminacion}")
    private Integer diasEliminacion;
    
    @FXML
    private ComboBox<LocalTime> aperturaComboBox;
    
    @FXML
    private ComboBox<LocalTime> cierreComboBox;
    
    @FXML
    private ComboBox<Integer> duracionCitasComboBox;
    
    @FXML
    private ComboBox<Boolean> eliminacionPasadasComboBox;
    
    @FXML
    private ComboBox<Boolean> tipoEliminacionComboBox;
    
    @FXML
    private ComboBox<Integer> diasEliminacionComboBox;
    
    @FXML
    private Button guardarButton;
    
    @FXML
    private Button salirButton;
    
    
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la configuración de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	TRAZAS.debug("Vista de: " + this.getClass().getName());
	
    }
    
    @FXML
    private void guardar() {
	
    }
    
    @FXML
    private void salir() {
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();
    }

}
