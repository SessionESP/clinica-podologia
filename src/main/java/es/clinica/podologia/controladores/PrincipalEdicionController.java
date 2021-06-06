package es.clinica.podologia.controladores;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesNavegacion;
import es.clinica.podologia.vistas.AgendaEdicionView;
import es.clinica.podologia.vistas.CitasListadoView;
import es.clinica.podologia.vistas.PacientesListadoView;
import es.clinica.podologia.vistas.SanitariosListadoView;
import es.clinica.podologia.vistas.TratamientosListadoView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * <p>Controlador para la vista principal de la aplicación.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class PrincipalEdicionController {

    @FXML
    private AnchorPane agendaAnchorPane;
    
    @FXML
    private AnchorPane citasAnchorPane;
    
    @FXML
    private AnchorPane pacientesAnchorPane;
    
    @FXML
    private AnchorPane sanitariosAnchorPane;
    
    @FXML
    private AnchorPane tratamientosAnchorPane;

    /**
     * <p>Método que se ejecuta al inicializarse la vista principal de la aplicación.</p>
     */
    @FXML
    public void initialize() {

	agendaAnchorPane.getChildren().add(UtilidadesNavegacion.cargarVista(AgendaEdicionView.class));
	
	citasAnchorPane.getChildren().add(UtilidadesNavegacion.cargarVista(CitasListadoView.class));
	
	pacientesAnchorPane.getChildren().add(UtilidadesNavegacion.cargarVista(PacientesListadoView.class));
	
	sanitariosAnchorPane.getChildren().add(UtilidadesNavegacion.cargarVista(SanitariosListadoView.class));
	
	tratamientosAnchorPane.getChildren().add(UtilidadesNavegacion.cargarVista(TratamientosListadoView.class));

    }

}
