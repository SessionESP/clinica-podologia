package es.clinica.podologia.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.CitasModel;
import es.clinica.podologia.servicios.CitasService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para el listado de citas.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class CitasListadoController {
    
    // Filtros
    @FXML
    private TextField identificadorTextField;
    @FXML
    private DatePicker fechaDesdeDatePicker;
    @FXML
    private DatePicker fechaHastaDatePicker;
    @FXML
    private TextField pacienteTextField;
    @FXML
    private TextField sanitarioTextField;
    @FXML
    private TextField tratamientoTextField;
    
    // Tabla
    @FXML
    private TableView<CitasModel> citasTableView;
    @FXML
    private TableColumn<CitasModel, Integer> identificadorColumn;
    @FXML
    private TableColumn<CitasModel, String> nombrePacienteColumn;
    @FXML
    private TableColumn<CitasModel, String> nombreSanitarioColumn;
    @FXML
    private TableColumn<CitasModel, String> nombreTratamientoColumn;
    @FXML
    private TableColumn<CitasModel, LocalDate> fechaColumn;
    @FXML
    private TableColumn<CitasModel, LocalTime> horaInicioColumn;
    @FXML
    private TableColumn<CitasModel, LocalTime> horaFinColumn;

    // Botones
    @FXML
    private Button aceptarButton;
    @FXML
    private Button cancelarButton;
    
    
    @Autowired
    private CitasService citasService;
    
    private ObservableList<CitasModel> listadoCitas = FXCollections.observableArrayList();
    
    
    
    @FXML
    public void initialize() {
	
	List<CitasModel> listado = citasService.listarCitas();
	
	listadoCitas.addAll(listado);
	
	inicializarTabla();
	
	citasTableView.setItems(listadoCitas);
        
    }
    
    /**
     * <p>Inicializar la tabla.</p>
     */
    private void inicializarTabla() {
	identificadorColumn.setCellValueFactory(dato -> dato.getValue().idCitaProperty().asObject());
	nombrePacienteColumn.setCellValueFactory(dato -> dato.getValue().pacienteProperty());
	nombreSanitarioColumn.setCellValueFactory(dato -> dato.getValue().sanitarioProperty());
	nombreTratamientoColumn.setCellValueFactory(dato -> dato.getValue().tratamientoProperty());
	fechaColumn.setCellValueFactory(dato -> dato.getValue().fechaProperty());
	horaInicioColumn.setCellValueFactory(dato -> dato.getValue().horaDesdeProperty());
	horaFinColumn.setCellValueFactory(dato -> dato.getValue().horaHastaProperty());
    }

}
