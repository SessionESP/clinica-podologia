package es.clinica.podologia.controladores;

import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.fxml.FXML;

/**
 * <p>Controlador para los Tratamientos.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class TratamientosListadoController {
    
    @Value("${tratamientos.listado.alta.titulo}")
    private String tituloAltaVista;
    
    @Value("${tratamientos.listado.edicion.titulo}")
    private String tituloEdicionVista;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista del listado de Tratamientos.</p>
     */
    @FXML
    public void initialize() {
	
	UtilidadesAlertas.mostrarAlertaInformativa("Vista de: " + this.getClass().getName());
	
    }
    
    @FXML
    private void seleccionarTratamiento() {

    }
    
    @FXML
    private void cancelarTratamiento() {

    }

}
