package es.clinica.podologia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import javafx.fxml.FXML;

/**
 * <p>Controlador para la edición de Sanitarios.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class SanitariosEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(SanitariosEdicionController.class);
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Sanitarios.</p>
     */
    @FXML
    public void initialize() {
	
	TRAZAS.info("Vista de: " + this.getClass().getName());
	
    }

}
