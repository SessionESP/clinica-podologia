package es.clinica.podologia.controladores;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.fxml.FXML;

/**
 * <p>Controlador para el listado de Pacientes.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class PacientesListadoController {
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista del listado de Pacientes.</p>
     */
    @FXML
    public void initialize() {
	
	UtilidadesAlertas.mostrarAlertaInformativa("Vista de: " + this.getClass().getName());
	
    }

}
