package es.clinica.podologia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.fxml.FXML;

/**
 * <p>Controlador para las Citas.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class CitasEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(CitasEdicionController.class);
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Citas.</p>
     */
    @FXML
    public void initialize() {
	
	TRAZAS.info("Vista de: " + this.getClass().getName());
	
    }

}
