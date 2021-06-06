package es.clinica.podologia.controladores;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.fxml.FXML;

/**
 * <p>Controlador para la edición de Pacientes.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class PacientesEdicionController {
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Pacientes.</p>
     */
    @FXML
    public void initialize() {
	
	UtilidadesAlertas.mostrarAlertaInformativa("Vista de: " + this.getClass().getName());
	
    }

}
