package es.clinica.podologia.controladores;

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
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.PacientesModelo;
import es.clinica.podologia.servicios.PacientesService;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesPropiedades;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import es.clinica.podologia.vistas.PacientesEdicionView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para el listado de Pacientes.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class PacientesListadoController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(PacientesListadoController.class);
    
    @Value("${pacientes.eliminacion.confirmacion}")
    private String confirmacionEliminacion;
    
    @Value("${pacientes.eliminacion.true}")
    private String eliminacionCorrecta;
    
    @Value("${pacientes.eliminacion.false}")
    private String eliminacionIncorrecta;
    
    @Value("${spring.config.import}")
    private List<String> propiedadesExternas;
    
    @Autowired
    private PacientesService pacientesService;
    
    @Autowired
    private BeansComponent beansComponent;
    
    private ObservableList<PacientesModelo> listadoPacientes = FXCollections.observableArrayList();
    
    private FilteredList<PacientesModelo> listadoPacientesFiltrado;
    
    private PacientesModelo modeloSeleccionado;
    
    private PacientesEdicionController pacientesEdicionController;
    
    private FileBasedConfigurationBuilder<FileBasedConfiguration> constructor;
    
    @FXML
    private TextField dniPacienteTextField;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField direccionTextField;
    @FXML
    private TextField telefonoTextField;

    @FXML
    private Pagination paginacionTabla;
    
    // Listas desplegables
    @FXML
    private ComboBox<Integer> tamanioPaginacionComboBox;
    
    // Tabla
    @FXML
    private TableView<PacientesModelo> pacientesTableView;
    @FXML
    private TableColumn<PacientesModelo, String> dniPacienteColumn;
    @FXML
    private TableColumn<PacientesModelo, String> nombreColumn;
    @FXML
    private TableColumn<PacientesModelo, String> apellidosColumn;
    @FXML
    private TableColumn<PacientesModelo, String> direccionColumn;
    @FXML
    private TableColumn<PacientesModelo, String> telefonoColumn;
    
    @FXML
    private Button nuevoButton;
    @FXML
    private Button editarButton;
    @FXML
    private Button eliminarButton;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista del listado de Pacientes.</p>
     */
    @FXML
    public void initialize() {
	
	cargarEstado();
	
	List<PacientesModelo> listado = pacientesService.listarPacientes();
	
	listadoPacientes.clear();
	listadoPacientes.addAll(listado);
	
	inicializarTabla();
	
	pacientesTableView.setItems(listadoPacientes);
	
        cambiarPaginacion(0, tamanioPaginacionComboBox.getValue());
        paginacionTabla.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> cambiarPaginacion(newValue.intValue(), tamanioPaginacionComboBox.getValue()));
        
        habilitarBotonesFila(null);
        
        pacientesTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> habilitarBotonesFila(newValue));
        
	
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
		
		List<Integer> paginaciones = UtilidadesConversores.convertirArrayCadenasListaEnteros(configuracion.getStringArray(Constantes.ESTADOS_PACIENTES_PAGINACIONES));
		
		Integer paginacion = configuracion.getInteger(
			Constantes.ESTADOS_PACIENTES_PAGINACION, 
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
	
	listadoPacientesFiltrado = new FilteredList<>(listadoPacientes, p -> true);
	cargarFiltros();
	dniPacienteColumn.setCellValueFactory(dato -> dato.getValue().dniPacienteProperty());
	nombreColumn.setCellValueFactory(dato -> dato.getValue().nombreProperty());
	apellidosColumn.setCellValueFactory(dato -> dato.getValue().apellidosProperty());
	direccionColumn.setCellValueFactory(dato -> dato.getValue().direccionProperty());
	telefonoColumn.setCellValueFactory(dato -> dato.getValue().telefonoProperty());
    }
    
    /**
     * <p>Cargar la tabla con los filtros y </p>
     * 
     * @param indice {@link Integer} índice de la paginación
     * @param limite {@link Integer} límite de la paginación
     */
    private void cambiarPaginacion(Integer indice, Integer limite) {
	
        int numeroPaginas = (int) (Math.ceil(listadoPacientesFiltrado.size() * 1.0 / tamanioPaginacionComboBox.getValue()));
        paginacionTabla.setPageCount(numeroPaginas);

        Integer indiceDesde = indice * limite;
        Integer indiceHasta = Math.min(indiceDesde + limite, listadoPacientesFiltrado.size());
        Integer indiceMinimo = Math.min(indiceHasta, listadoPacientesFiltrado.size());
        
        SortedList<PacientesModelo> datosOrdenados = new SortedList<>(FXCollections.observableArrayList(listadoPacientesFiltrado.subList(Math.min(indiceDesde, indiceMinimo), indiceMinimo)));
        datosOrdenados.comparatorProperty().bind(pacientesTableView.comparatorProperty());

        pacientesTableView.setItems(datosOrdenados);

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
	
	// Filtro por el DNI del paciente
	dniPacienteTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoPacientesFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getDniPaciente().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
	// Filtro por el nombre del paciente
	nombreTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoPacientesFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getNombre().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
	// Filtro por los apellidos del paciente
	apellidosTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoPacientesFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getApellidos().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
	// Filtro por la dirección del paciente
	direccionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoPacientesFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getDireccion().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
	// Filtro por el número de teléfono del paciente
	telefonoTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoPacientesFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getTelefono().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
    }
    
    /**
     * <p>Método para abrir la vista de edición para dar de alta un nuevo paciente.</p>
     */
    @FXML
    private void crearPaciente() {
	
	UtilidadesVentanasEmergentes.abrirVentanaEmergente(PacientesEdicionView.class, Constantes.PACIENTES_EDICION_CONTROLLER, Accion.ALTA);
	
	pacientesEdicionController = (PacientesEdicionController) beansComponent.obtenerControlador(Constantes.PACIENTES_EDICION_CONTROLLER);
	pacientesEdicionController.setModelo(null);
	pacientesEdicionController.initialize();
	
    }
    
    /**
     * <p>Método para abrir la vista de edición para modificar un paciente seleccionado de la tabla.</p>
     */
    @FXML
    private void editarPaciente() {
	
	UtilidadesVentanasEmergentes.abrirVentanaEmergente(PacientesEdicionView.class, Constantes.PACIENTES_EDICION_CONTROLLER, Accion.EDICION);
	
	pacientesEdicionController = (PacientesEdicionController) beansComponent.obtenerControlador(Constantes.PACIENTES_EDICION_CONTROLLER);
	pacientesEdicionController.setModelo(modeloSeleccionado);
	pacientesEdicionController.initialize();

    }
    
    /**
     * <p>Método para eliminar un paciente seleccionado de la tabla.</p>
     */
    @FXML
    private void eliminarPaciente() {
	
	// Mostrar alerta de confirmación
	Optional<ButtonType> confirmacion = UtilidadesAlertas.mostrarAlertaConfirmacion(confirmacionEliminacion);
	
	// Borrar en caso de que se haya pulsado el botón de aceptar
	if(confirmacion.isPresent() && confirmacion.get() == ButtonType.OK) {
	    
	    // Elimiar el registro
	    Boolean resultado = pacientesService.eliminarPaciente(modeloSeleccionado.getDniPaciente());

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
     * <p>Método que habilita o deshabilita los botones asociados a acciones a nivel de fila de la tabla: </p>
     * <ul>
     * <li>{@code editarButton}: para editar el paciente seleccionado.</li>
     * <li>{@code eliminarButton}: para eliminar el paciente seleccionado.</li>
     * </ul>
     * 
     * <p>Adicionalmente, asigna el modelo seleccionado al atributo que se utiliza en los métodos de dichos botones.</p>
     * 
     * @param modelo {@link PacientesModelo} objeto de paciente
     */
    private void habilitarBotonesFila(PacientesModelo modelo) {
	
	// Se asigan el modelo seleccionado al que se pasará la vista
	modeloSeleccionado = modelo;
	
	// Habilidar o deshabilitar los botones dependiendo de si se ha seleccionado o no algo en la tabla
	editarButton.setDisable(modelo != null ? Boolean.FALSE : Boolean.TRUE);
	eliminarButton.setDisable(modelo != null ? Boolean.FALSE : Boolean.TRUE);
	
    }

}
