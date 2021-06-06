package es.clinica.podologia.controladores;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.fxml.FXML;

/**
 * <p>Controlador para la configuraci�n de la aplicaci�n.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class ConfiguracionEdicionController {
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la configuración de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	UtilidadesAlertas.mostrarAlertaInformativa("Vista de: " + this.getClass().getName());
	
    }

}
