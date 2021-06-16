package es.clinica.podologia.controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.PacientesModelo;
import es.clinica.podologia.servicios.PacientesService;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;

/**
 * <p>Controlador para el listado de Pacientes.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class PacientesListadoController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PacientesListadoController.class);
    
    @Autowired
    private PacientesService pacientesService;
    
    private ObservableList<PacientesModelo> listadoPacientes = FXCollections.observableArrayList();
    
    private FilteredList<PacientesModelo> listadoPacientesFiltrado;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista del listado de Pacientes.</p>
     */
    @FXML
    public void initialize() {
	
	UtilidadesAlertas.mostrarAlertaInformativa("Vista de: " + this.getClass().getName());
	
	List<PacientesModelo> listado = pacientesService.listarPacientes();
	
	listadoPacientes.addAll(listado);
	
	LOGGER.info("Se han recuperado " + listado.size() + " pacientes.");
	
    }

}
