package es.clinica.podologia.controladores;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.fxml.FXML;

/**
 * <p>Controlador para los Sanitarios.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class SanitariosListadoController {
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista del listado de Sanitarios.</p>
     */
    @FXML
    public void initialize() {
	
	UtilidadesAlertas.mostrarAlertaInformativa("Vista de: " + this.getClass().getName());
	
    }

}
