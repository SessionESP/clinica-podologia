package es.clinica.podologia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.javafx.jfxsupport.GUIState;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para los Tratamientos.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class TratamientosEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(TratamientosEdicionController.class);
    
    @Value("${tratamientos.alta.titulo}")
    private String tituloAltaVista;
    
    @Value("${tratamientos.edicion.titulo}")
    private String tituloEdicionVista;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextArea descripcionTextArea;

    @FXML
    private Button aceptarButton;
    
    @FXML
    private Button cancelarButton;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de tratamientos.</p>
     */
    @FXML
    public void initialize() {
	
	// Aplicar el título de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloAltaVista, ""));
        
    }
    
    /**
     * <p>Método invocado como un evento para guardar el tratamiento.</p>
     */
    @FXML
    private void aceptarTratamiento() {

    }
    
    /**
     * <p>Método invocado como un evento para cerrar la ventana emergente.</p>
     */
    @FXML
    private void cancelarTratamiento() {
	
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();

    }

}
