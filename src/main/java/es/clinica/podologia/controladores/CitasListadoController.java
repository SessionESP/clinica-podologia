package es.clinica.podologia.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.clinica.podologia.formateadores.CitasModeloFecha;
import es.clinica.podologia.formateadores.RecogedorFechas;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.CitasModelo;
import es.clinica.podologia.servicios.CitasService;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
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
    private TableView<CitasModelo> citasTableView;
    @FXML
    private TableColumn<CitasModelo, Integer> identificadorColumn;
    @FXML
    private TableColumn<CitasModelo, String> nombrePacienteColumn;
    @FXML
    private TableColumn<CitasModelo, String> nombreSanitarioColumn;
    @FXML
    private TableColumn<CitasModelo, String> nombreTratamientoColumn;
    @FXML
    private TableColumn<CitasModelo, LocalDate> fechaColumn;
    @FXML
    private TableColumn<CitasModelo, LocalTime> horaInicioColumn;
    @FXML
    private TableColumn<CitasModelo, LocalTime> horaFinColumn;
    @FXML
    private Pagination paginacionTabla;

    // Botones
    @FXML
    private Button buscarButton;
    
    // Listas desplegables
    @FXML
    private ComboBox<Integer> tamanioPaginacionComboBox;
    
    
    @Autowired
    private CitasService citasService;
    
    private ObservableList<CitasModelo> listadoCitas = FXCollections.observableArrayList();
    
    private FilteredList<CitasModelo> listadoCitasFiltrado;
    
    
    
    @FXML
    public void initialize() {
	
	// no se cargan los valores en la lista desplegable
	ObservableList<Integer> opciones = FXCollections.observableArrayList();
        opciones.addAll(5, 10, 15);
	tamanioPaginacionComboBox = new ComboBox<>(opciones);
	tamanioPaginacionComboBox.setValue(5);
	
	List<CitasModelo> listado = citasService.listarCitas();
	
	listadoCitas.addAll(listado);
	
	inicializarTabla();
	
	citasTableView.setItems(listadoCitas);
	
	fechaDesdeDatePicker.setConverter(new RecogedorFechas());
	fechaHastaDatePicker.setConverter(new RecogedorFechas());
	
        int totalPage = (int) (Math.ceil(listadoCitas.size() * 1.0 / tamanioPaginacionComboBox.getValue()));
        paginacionTabla.setPageCount(totalPage);
        paginacionTabla.setCurrentPageIndex(0);
        cambiarPaginacion(0, tamanioPaginacionComboBox.getValue());
        paginacionTabla.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> cambiarPaginacion(newValue.intValue(), tamanioPaginacionComboBox.getValue()));
        
    }
    
    /**
     * <p>Inicializar la tabla.</p>
     */
    private void inicializarTabla() {
	
	listadoCitasFiltrado = new FilteredList<>(listadoCitas, p -> true);
	cargarFiltros();
	identificadorColumn.setCellValueFactory(dato -> dato.getValue().idCitaProperty().asObject());
	nombrePacienteColumn.setCellValueFactory(dato -> dato.getValue().pacienteProperty());
	nombreSanitarioColumn.setCellValueFactory(dato -> dato.getValue().sanitarioProperty());
	nombreTratamientoColumn.setCellValueFactory(dato -> dato.getValue().tratamientoProperty());
	fechaColumn.setCellValueFactory(dato -> dato.getValue().fechaProperty());
	fechaColumn.setCellFactory(dato -> new CitasModeloFecha());
	horaInicioColumn.setCellValueFactory(dato -> dato.getValue().horaDesdeProperty());
	horaFinColumn.setCellValueFactory(dato -> dato.getValue().horaHastaProperty());
    }
    
    /**
     * <p>Cargar la tabla con los filtros y </p>
     * 
     * @param indice {@link Integer} indice de la paginación
     * @param limite {@link Integer} límite de la paginación
     */
    private void cambiarPaginacion(Integer indice, Integer limite) {

        Integer indiceDesde = indice * limite;
        Integer indiceHasta = Math.min(indiceDesde + limite, listadoCitas.size());
        Integer indiceMinimo = Math.min(indiceHasta, listadoCitasFiltrado.size());
        
        SortedList<CitasModelo> datosOrdenados = new SortedList<>(FXCollections.observableArrayList(listadoCitasFiltrado.subList(Math.min(indiceDesde, indiceMinimo), indiceMinimo)));
        datosOrdenados.comparatorProperty().bind(citasTableView.comparatorProperty());

        citasTableView.setItems(datosOrdenados);

    }
    
    /**
     * <p>Cambiar la paginación de la tabla.</p>
     * 
     * @param event {@link ActionEvent} 
     */
    @FXML
    private void cambiarSeleccionTamanioPaginacion(ActionEvent event) {
	cambiarPaginacion(0, tamanioPaginacionComboBox.getValue());
    }
    
    /**
     * <p>Cargar las cajas de texto correspondientes con filtros para aplicar en el listado de la tabla.</p>
     */
    private void cargarFiltros() {
	
	// TODO: NO FUNCIONA. Filtro por el identificador de la cita
//	identificadorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//	    listadoCitasFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty();
//	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
//	});
	    
	    identificadorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
		listadoCitasFiltrado.setPredicate(cita -> {
	                if (newValue == null || newValue.isEmpty()) {
	                    return true;
	                } else if( UtilidadesConversores.enteroCadena(cita.getIdCita()).toLowerCase().contains(newValue)) {
	                    return true;
	                }
	                    
	                return false;
	            });
		cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	        });
	
	// TODO: NO FUNCIONA. filtros de intervalo de fechas
	listadoCitasFiltrado.predicateProperty().bind(Bindings.createObjectBinding(() -> {

	    LocalDate fechaMinima = fechaDesdeDatePicker.getValue();
	    LocalDate fechaMaxima = fechaHastaDatePicker.getValue();

	    final LocalDate finalMinima = fechaMinima == null ? LocalDate.MIN : fechaMinima;
	    final LocalDate finalMaxima = fechaMaxima == null ? LocalDate.MAX : fechaMaxima;
	    
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());

	    return ti -> !finalMinima.isAfter(ti.getFecha()) && !finalMaxima.isBefore(ti.getFecha());
	}, fechaDesdeDatePicker.valueProperty(), fechaHastaDatePicker.valueProperty()));
	
	// Filtro por el nombre y apellidos del paciente
	pacienteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoCitasFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getPaciente().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
	// Filtro por el nombre y apellidos del paciente
	sanitarioTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoCitasFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getSanitario().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
	// Filtro por el nombre del tratamiento del paciente
	tratamientoTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoCitasFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getTratamiento().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
    }

}
