package es.clinica.podologia.controladores;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.formateadores.RecogedorFechas;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.SanitariosModelo;
import es.clinica.podologia.servicios.SanitariosService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView<ObservableList<String>> agendaTableView = new TableView<>();
    
    @FXML
    private DatePicker fechaDatePicker;
    
    @Autowired
    private SanitariosService sanitariosService;
    
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la agenda de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	fechaDatePicker.setValue(LocalDate.now());
	
	fechaDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            UtilidadesAlertas.mostrarAlertaInformativa("Fecha seleccionada: " + newValue);
        });
	
	fechaDatePicker.setConverter(new RecogedorFechas());
	
	generarTabla();
	
    }
    
    /**
     * <p>Método que genera la tabla con toda la información de la agenda para un día.</p>
     */
    private void generarTabla() {
	generarColumnas();
    }
    
    /**
     * <p>Método que genera las columnas de la tabla de la agenda.</p>
     */
    private void generarColumnas() {
	
	List<SanitariosModelo> listadoSanitarios = sanitariosService.listarSanitarios();
	
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(listadoSanitarios))) {
	    
	    listadoSanitarios.forEach(sanitario -> generarColumna(agendaTableView, sanitario.toString()));
	    
	}
	
    }
    
    private void generarColumna(TableView<ObservableList<String>> tabla, String nombreColumna) {
	    
	    TableColumn<ObservableList<String>, String> columna = new TableColumn<>(Utilidades.comprobarCadena(nombreColumna, Constantes.CADENA_VACIA));
	    //columna.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));
	    tabla.getColumns().add(columna);

	
    }
    
    /**
     * <p>Método que genera las filas de la tabla de la agenda.</p>
     */
    private void generarFilass() {
	
    }

}
