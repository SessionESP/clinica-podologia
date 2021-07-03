package es.clinica.podologia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.clinica.podologia.constantes.Accion;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesNavegacion;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import es.clinica.podologia.vistas.AgendaEdicionView;
import es.clinica.podologia.vistas.CitasListadoView;
import es.clinica.podologia.vistas.ConfiguracionEdicionView;
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
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(PrincipalEdicionController.class);

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
    
    /**
     * <p>Método que abre la configuración de la aplicación.</p>
     */
    @FXML
    private void abrirConfiguracion() {
	
	UtilidadesVentanasEmergentes.abrirVentanaEmergente(ConfiguracionEdicionView.class, "ConfiguracionEdicionController", Accion.EDICION);
	
    }
    
    /**
     * <p>Método que abre la información principal de la aplicación.</p>
     */
    @FXML
    private void abrirAcercaDe() {
	
	UtilidadesAlertas.mostrarAlertaConfirmacion("Podología y fisioterapia 1.0. https://github.com/SessionESP/clinica-podologia.");
	
    }

}
