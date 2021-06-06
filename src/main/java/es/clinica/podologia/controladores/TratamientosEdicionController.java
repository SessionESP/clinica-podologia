package es.clinica.podologia.controladores;

import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.javafx.jfxsupport.GUIState;
import es.clinica.podologia.utilidades.Utilidades;
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
    
    @Value("${tratamientos.listado.alta.titulo}")
    private String tituloAltaVista;
    
    @Value("${tratamientos.listado.edicion.titulo}")
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
        GUIState.getStage().setTitle(Utilidades.comprobarCadena(tituloAltaVista, ""));
        
    }
    
    @FXML
    private void aceptarTratamiento() {

    }
    
    @FXML
    private void cancelarTratamiento() {

    }

}
