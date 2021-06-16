package es.clinica.podologia.controladores;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.TratamientosModelo;
import es.clinica.podologia.servicios.TratamientosService;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;

/**
 * <p>Controlador para los Tratamientos.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class TratamientosListadoController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TratamientosListadoController.class);
    
    @Autowired
    private TratamientosService tratamientosService;
    
    private ObservableList<TratamientosModelo> listadoTratamientos = FXCollections.observableArrayList();
    
    private FilteredList<TratamientosModelo> listadoTratamientosFiltrado;
    
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
	
	List<TratamientosModelo> listado = tratamientosService.listarTratamientos();
	
	listadoTratamientos.addAll(listado);
	
	LOGGER.info("Se han recuperado " + listado.size() + " tratamientos.");
	
    }
    
    @FXML
    private void seleccionarTratamiento() {

    }
    
    @FXML
    private void cancelarTratamiento() {

    }

}
