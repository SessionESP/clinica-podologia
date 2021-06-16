package es.clinica.podologia.controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.SanitariosModelo;
import es.clinica.podologia.servicios.SanitariosService;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;

/**
 * <p>Controlador para los Sanitarios.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class SanitariosListadoController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SanitariosListadoController.class);
    
    @Autowired
    private SanitariosService sanitariosService;
    
    private ObservableList<SanitariosModelo> listadoSanitarios = FXCollections.observableArrayList();
    
    private FilteredList<SanitariosModelo> listadoSanitariosFiltrado;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista del listado de Sanitarios.</p>
     */
    @FXML
    public void initialize() {
	
	UtilidadesAlertas.mostrarAlertaInformativa("Vista de: " + this.getClass().getName());
	
	List<SanitariosModelo> listado = sanitariosService.listarSanitarios();
	
	listadoSanitarios.addAll(listado);
	
	LOGGER.info("Se han recuperado " + listado.size() + " sanitarios.");
	
    }

}
