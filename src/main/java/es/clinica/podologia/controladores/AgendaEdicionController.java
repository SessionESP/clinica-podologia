package es.clinica.podologia.controladores;

import java.time.LocalDate;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;

/**
 * <p>Controlador para la vista de la agenda de la aplicación.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class AgendaEdicionController {
    
    @FXML
    private AnchorPane agendaAnchorPane;
    
    @FXML
    private DatePicker fechaDatePicker;
    
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la agenda de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	fechaDatePicker.setValue(LocalDate.now());
	
	fechaDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            UtilidadesAlertas.mostrarAlertaInformativa("Fecha seleccionada: " + newValue);
        });
	
    }

}
