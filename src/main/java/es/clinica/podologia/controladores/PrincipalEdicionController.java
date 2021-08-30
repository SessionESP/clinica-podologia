package es.clinica.podologia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.clinica.podologia.componentes.BeansComponent;
import es.clinica.podologia.constantes.Accion;
import es.clinica.podologia.constantes.Constantes;
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
    
    @Autowired
    private BeansComponent beansComponent;

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
    
    private Boolean agendaCargada;

    /**
     * <p>Método que se ejecuta al inicializarse la vista principal de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	TRAZAS.info("Se inicializa la vista principal de la aplicación.");

	agendaCargada = agendaAnchorPane.getChildren().add(UtilidadesNavegacion.cargarVista(AgendaEdicionView.class));
	
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
    
    /**
     * <p>Método que inicializa la vista de la agenda.</p>
     */
    @FXML
    private void inicializarAgenda() {
	
	// Primera hay que comprobar si ya está cargada la vista, para evitar inicializarla antes de que se hayan cargado los controles
	if (Boolean.TRUE.equals(agendaCargada)) {
	    
	    AgendaEdicionController agendaEdicionController = (AgendaEdicionController) beansComponent.obtenerControlador(Constantes.AGENDA_EDICION_CONTROLLER);
	    agendaEdicionController.initialize();
	    
	    AgendaSanitariosEdicionController agendaSanitariosEdicionController = (AgendaSanitariosEdicionController) beansComponent.obtenerControlador(Constantes.AGENDA_SANITARIOS_EDICION_CONTROLLER);
	    agendaSanitariosEdicionController.initialize();
	    
	}
	
    }
    
    /**
     * <p>Método que inicializa la vista de las citas.</p>
     */
    @FXML
    private void inicializarCitas() {
	
	CitasListadoController citasListadoController = (CitasListadoController) beansComponent.obtenerControlador(Constantes.CITAS_LISTADO_CONTROLLER);
	citasListadoController.initialize();
	
    }
    
    /**
     * <p>Método que inicializa la vista de los pacientes.</p>
     */
    @FXML
    private void inicializarPacientes() {
	
	PacientesListadoController pacientesListadoController = (PacientesListadoController) beansComponent.obtenerControlador(Constantes.PACIENTES_LISTADO_CONTROLLER);
	pacientesListadoController.initialize();
	
    }
    
    /**
     * <p>Método que inicializa la vista de los sanitarios.</p>
     */
    @FXML
    private void inicializarSanitarios() {
	
	SanitariosListadoController sanitariosListadoController = (SanitariosListadoController) beansComponent.obtenerControlador(Constantes.SANITARIOS_LISTADO_CONTROLLER);
	sanitariosListadoController.initialize();
	
    }
    
    /**
     * <p>Método que inicializa la vista de los tratamientos.</p>
     */
    @FXML
    private void inicializarTratamientos() {
	
	TratamientosListadoController tratamientosListadoController = (TratamientosListadoController) beansComponent.obtenerControlador(Constantes.TRATAMIENTOS_LISTADO_CONTROLLER);
	tratamientosListadoController.initialize();
	
    }

}
