package es.clinica.podologia.controladores;

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
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Citas.</p>
     */
    @FXML
    public void initialize() {
	
	UtilidadesAlertas.mostrarAlertaInformativa("Vista de: " + this.getClass().getName());
	
    }

}
