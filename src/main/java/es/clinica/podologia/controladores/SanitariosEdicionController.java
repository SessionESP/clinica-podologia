package es.clinica.podologia.controladores;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.fxml.FXML;

/**
 * <p>Controlador para la edición de Sanitarios.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class SanitariosEdicionController {
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Sanitarios.</p>
     */
    @FXML
    public void initialize() {
	
	UtilidadesAlertas.mostrarAlertaInformativa("Vista de: " + this.getClass().getName());
	
    }

}
