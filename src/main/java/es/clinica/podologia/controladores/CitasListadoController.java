package es.clinica.podologia.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.componentes.BeansComponent;
import es.clinica.podologia.constantes.Accion;
import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.formateadores.CitasModeloFecha;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.CitasModelo;
import es.clinica.podologia.servicios.CitasService;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesPropiedades;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import es.clinica.podologia.vistas.CitasEdicionView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para el listado de Citas.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class CitasListadoController {

    private static final Logger TRAZAS = LoggerFactory.getLogger(CitasListadoController.class);
    
    @Value("${citas.eliminacion.confirmacion}")
    private String confirmacionEliminacion;
    
    @Value("${citas.eliminacion.true}")
    private String eliminacionCorrecta;
    
    @Value("${citas.eliminacion.false}")
    private String eliminacionIncorrecta;
    
    @Value("${spring.config.import}")
    private List<String> propiedadesExternas;
    
    @Autowired
    private CitasService citasService;
    
    @Autowired
    private BeansComponent beansComponent;
    
    private ObservableList<CitasModelo> listadoCitas = FXCollections.observableArrayList();
    
    private FilteredList<CitasModelo> listadoCitasFiltrado;
    
    private CitasModelo modeloSeleccionado;
    
    private CitasEdicionController citasEdicionController;
    
    private FileBasedConfigurationBuilder<FileBasedConfiguration> constructor;
    
    // Filtros
    @FXML
    private TextField pacienteTextField;
    @FXML
    private TextField sanitarioTextField;
    @FXML
    private TextField tratamientoTextField;
    @FXML
    private DatePicker fechaDesdeDatePicker;
    @FXML
    private DatePicker fechaHastaDatePicker;
    
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
    
    // Paginación de la tabla
    @FXML
    private Pagination paginacionTabla;
    @FXML
    private ComboBox<Integer> tamanioPaginacionComboBox;
    
    // Botones
    @FXML
    private Button nuevoButton;
    @FXML
    private Button editarButton;
    @FXML
    private Button eliminarButton;
    @FXML
    private Button refrescarButton;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista del listado de Citas.</p>
     */
    @FXML
    public void initialize() {
	
	cargarEstado();
	
	List<CitasModelo> listado = citasService.listarCitasPorRangoDeFechas(fechaDesdeDatePicker.getValue(), fechaHastaDatePicker.getValue());
	
	listadoCitas.clear();
	listadoCitas.addAll(listado);
	
	inicializarTabla();
	
	citasTableView.setItems(listadoCitas);
	
        cambiarPaginacion(0, tamanioPaginacionComboBox.getValue());
        paginacionTabla.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> cambiarPaginacion(newValue.intValue(), tamanioPaginacionComboBox.getValue()));
        
        habilitarBotonesFila(null);
        
        citasTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> habilitarBotonesFila(newValue));
        
	
    }
    
    /**
     * <p>Cargar el estado del formulario.</p>
     */
    private void cargarEstado() {
	
	// Inicializar el constructor con los parámetros del fichero externo
	constructor = UtilidadesPropiedades.crearConstructor(new Parameters(), propiedadesExternas.get(1), Constantes.COMA);
	
	try {
	    
	    // Comprobar que el constructor y los parámetros NO son nulos
	    if (constructor != null && constructor.getConfiguration() != null) {

		// Guardar la información del fichero de configuración en un objeto
		FileBasedConfiguration configuracion = constructor.getConfiguration();
		
		List<Integer> paginaciones = UtilidadesConversores.convertirArrayCadenasListaEnteros(configuracion.getStringArray(Constantes.ESTADOS_CITAS_PAGINACIONES));
		
		Integer paginacion = configuracion.getInteger(
			Constantes.ESTADOS_CITAS_PAGINACION, 
			Constantes.ESTADOS_PAGINACION_DEFECTO_10);
		
	    }

	} catch (ConfigurationException excepcion) {

	    // Error al intentar guardar las propiedades del fichero en un objeto
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}
	
	ObservableList<Integer> opciones = FXCollections.observableArrayList();
        opciones.addAll(Arrays.asList(10, 20, 30, 40, 50));
	tamanioPaginacionComboBox.setItems(opciones);
	tamanioPaginacionComboBox.setValue(10);
	
    }
    
    /**
     * <p>Inicializar la tabla.</p>
     */
    private void inicializarTabla() {
	
	listadoCitasFiltrado = new FilteredList<>(listadoCitas, p -> true);
	
	cargarFiltros();
	
	identificadorColumn.setCellValueFactory(dato -> dato.getValue().idCitaProperty().asObject());
	nombrePacienteColumn.setCellValueFactory(dato -> dato.getValue().nombrePacienteProperty());
	nombreSanitarioColumn.setCellValueFactory(dato -> dato.getValue().nombreSanitarioProperty());
	nombreTratamientoColumn.setCellValueFactory(dato -> dato.getValue().nombreTratamientoProperty());
	fechaColumn.setCellValueFactory(dato -> dato.getValue().fechaProperty());
	fechaColumn.setCellFactory(dato -> new CitasModeloFecha());
	horaInicioColumn.setCellValueFactory(dato -> dato.getValue().horaDesdeProperty());
	horaFinColumn.setCellValueFactory(dato -> dato.getValue().horaHastaProperty());
	
    }
    
    /**
     * <p>Cargar la tabla con los filtros y </p>
     * 
     * @param indice {@link Integer} índice de la paginación
     * @param limite {@link Integer} límite de la paginación
     */
    private void cambiarPaginacion(Integer indice, Integer limite) {
	
        int numeroPaginas = (int) (Math.ceil(listadoCitasFiltrado.size() * 1.0 / tamanioPaginacionComboBox.getValue()));
        paginacionTabla.setPageCount(numeroPaginas);

        Integer indiceDesde = indice * limite;
        Integer indiceHasta = Math.min(indiceDesde + limite, listadoCitasFiltrado.size());
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
	if(tamanioPaginacionComboBox.getValue() != null) {
	    cambiarPaginacion(0, tamanioPaginacionComboBox.getValue());
	}
    }
    
    /**
     * <p>Cargar las cajas de texto correspondientes con filtros para aplicar en el listado de la tabla.</p>
     */
    private void cargarFiltros() {
	
	// Filtro por el nombre y apellidos del paciente
	pacienteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoCitasFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getNombrePaciente().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
		
	// Filtro por el nombre y apellidos del paciente
	sanitarioTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoCitasFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getNombreSanitario().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
		
	// Filtro por el nombre del tratamiento del paciente
	tratamientoTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoCitasFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getNombreTratamiento().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
    }
    
    /**
     * <p>Método para abrir la vista de edición para dar de alta un nuevo paciente.</p>
     */
    @FXML
    private void crearCita() {
	
	UtilidadesVentanasEmergentes.abrirVentanaEmergente(CitasEdicionView.class, Constantes.CITAS_EDICION_CONTROLLER, Accion.ALTA);
	
	citasEdicionController = (CitasEdicionController) beansComponent.obtenerControlador(Constantes.CITAS_EDICION_CONTROLLER);
	citasEdicionController.setModelo(null);
	citasEdicionController.initialize();
	
    }
    
    /**
     * <p>Método para abrir la vista de edición para modificar un paciente seleccionado de la tabla.</p>
     */
    @FXML
    private void editarCita() {
	
	UtilidadesVentanasEmergentes.abrirVentanaEmergente(CitasEdicionView.class, Constantes.CITAS_EDICION_CONTROLLER, Accion.EDICION);
	
	citasEdicionController = (CitasEdicionController) beansComponent.obtenerControlador(Constantes.CITAS_EDICION_CONTROLLER);
	citasEdicionController.setModelo(modeloSeleccionado);
	citasEdicionController.initialize();

    }
    
    /**
     * <p>Método para eliminar un paciente seleccionado de la tabla.</p>
     */
    @FXML
    private void eliminarCita() {
	
	// Mostrar alerta de confirmación
	Optional<ButtonType> confirmacion = UtilidadesAlertas.mostrarAlertaConfirmacion(confirmacionEliminacion);
	
	// Borrar en caso de que se haya pulsado el botón de aceptar
	if(confirmacion.isPresent() && confirmacion.get() == ButtonType.OK) {
	    
	    // Elimiar el registro
	    Boolean resultado = citasService.eliminarCita(modeloSeleccionado.getIdCita());

	    // Comprobar si se ha realizaco correctamente el guardado del tratamiento
	    if (Boolean.TRUE.equals(resultado)) {

		// El tratamiento se ha eliminado
		UtilidadesAlertas.mostrarAlertaInformativa(eliminacionCorrecta);
		initialize();

	    } else {

		// El tratamiento no se ha eliminado
		UtilidadesAlertas.mostrarAlertaError(eliminacionIncorrecta);

	    }
	    
	}

    }
    
    /**
     * <p>Método para refrescar la vista.</p>
     */
    @FXML
    private void refrescar() {
	initialize();
    }
    
    /**
     * <p>Método que habilita o deshabilita los botones asociados a acciones a nivel de fila de la tabla: </p>
     * <ul>
     * <li>{@code editarButton}: para editar la cita seleccionada.</li>
     * <li>{@code eliminarButton}: para eliminar la cita seleccionada.</li>
     * </ul>
     * 
     * <p>Adicionalmente, asigna el modelo seleccionado al atributo que se utiliza en los métodos de dichos botones.</p>
     * 
     * @param modelo {@link CitasModelo} objeto de paciente
     */
    private void habilitarBotonesFila(CitasModelo modelo) {
	
	// Se asigan el modelo seleccionado al que se pasará la vista
	modeloSeleccionado = modelo;
	
	// Habilidar o deshabilitar los botones dependiendo de si se ha seleccionado o no algo en la tabla
	editarButton.setDisable(modelo != null ? Boolean.FALSE : Boolean.TRUE);
	eliminarButton.setDisable(modelo != null ? Boolean.FALSE : Boolean.TRUE);
	
    }

}
