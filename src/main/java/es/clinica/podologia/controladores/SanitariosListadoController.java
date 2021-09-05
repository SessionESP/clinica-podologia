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
import es.clinica.podologia.modelos.SanitariosModelo;
import es.clinica.podologia.servicios.SanitariosService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesPropiedades;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import es.clinica.podologia.vistas.SanitariosEdicionView;
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
 * <p>Controlador para el listado de Sanitarios.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class SanitariosListadoController {
    
private static final Logger TRAZAS = LoggerFactory.getLogger(SanitariosListadoController.class);
    
    @Value("${sanitarios.eliminacion.confirmacion}")
    private String confirmacionEliminacion;
    
    @Value("${sanitarios.eliminacion.true}")
    private String eliminacionCorrecta;
    
    @Value("${sanitarios.eliminacion.false}")
    private String eliminacionIncorrecta;
    
    @Value("${spring.config.import}")
    private List<String> propiedadesExternas;
    
    @Autowired
    private SanitariosService sanitariosService;
    
    @Autowired
    private BeansComponent beansComponent;
    
    private ObservableList<SanitariosModelo> listadoSanitarios = FXCollections.observableArrayList();
    
    private FilteredList<SanitariosModelo> listadoSanitariosFiltrado;
    
    private SanitariosModelo modeloSeleccionado;
    
    private SanitariosEdicionController sanitariosEdicionController;
    
    private FileBasedConfigurationBuilder<FileBasedConfiguration> constructor;
    
    @FXML
    private TextField dniSanitarioTextField;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField especialidadTextField;

    @FXML
    private Pagination paginacionTabla;
    
    // Listas desplegables
    @FXML
    private ComboBox<Integer> tamanioPaginacionComboBox;
    
    // Tabla
    @FXML
    private TableView<SanitariosModelo> sanitariosTableView;
    @FXML
    private TableColumn<SanitariosModelo, String> dniSanitarioColumn;
    @FXML
    private TableColumn<SanitariosModelo, String> nombreColumn;
    @FXML
    private TableColumn<SanitariosModelo, String> apellidosColumn;
    @FXML
    private TableColumn<SanitariosModelo, String> especialidadColumn;
    
    @FXML
    private Button nuevoButton;
    @FXML
    private Button editarButton;
    @FXML
    private Button eliminarButton;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista del listado de Sanitarios.</p>
     */
    @FXML
    public void initialize() {

	cargarEstado();

	List<SanitariosModelo> listado = sanitariosService.listarSanitarios();

	listadoSanitarios.clear();

	if (Boolean.TRUE.equals(Utilidades.comprobarColeccion(listado))) {

	    listadoSanitarios.addAll(listado);

	    inicializarTabla();

	    sanitariosTableView.setItems(listadoSanitarios);

	    cambiarPaginacion(0, tamanioPaginacionComboBox.getValue());
	    paginacionTabla.currentPageIndexProperty().addListener((observable, oldValue,
		    newValue) -> cambiarPaginacion(newValue.intValue(), tamanioPaginacionComboBox.getValue()));

	    habilitarBotonesFila(null);

	    sanitariosTableView.getSelectionModel().selectedItemProperty()
		    .addListener((observable, oldValue, newValue) -> habilitarBotonesFila(newValue));

	}

    }
    
    /**
     * <p>Cargar el estado del formulario.</p>
     */
    private void cargarEstado() {
	
	// Inicializar el constructor con los parámetros del fichero externo
	constructor = UtilidadesPropiedades.crearConstructor(new Parameters(), propiedadesExternas.get(1), Constantes.COMA);
	
	// Inicializar con valores por defecto
	List<Integer> paginaciones = Arrays.asList(5, 10, 15);
	Integer paginacion = Constantes.ESTADOS_PAGINACION_DEFECTO_5;
	
	try {
	    
	    // Comprobar que el constructor y los parámetros NO son nulos
	    if (constructor != null && constructor.getConfiguration() != null) {

		// Guardar la información del fichero de configuración en un objeto
		FileBasedConfiguration configuracion = constructor.getConfiguration();
		
		paginaciones = UtilidadesConversores.convertirArrayCadenasListaEnteros(configuracion.getStringArray(Constantes.ESTADOS_SANITARIOS_PAGINACIONES));
		
		paginacion = configuracion.getInteger(
			Constantes.ESTADOS_SANITARIOS_PAGINACION, 
			Constantes.ESTADOS_PAGINACION_DEFECTO_10);
		
	    }

	} catch (ConfigurationException excepcion) {

	    // Error al intentar guardar las propiedades del fichero en un objeto
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}
	
	ObservableList<Integer> opciones = FXCollections.observableArrayList();
        opciones.addAll(paginaciones);
	tamanioPaginacionComboBox.setItems(opciones);
	tamanioPaginacionComboBox.setValue(paginacion);
	
    }
    
    /**
     * <p>Inicializar la tabla.</p>
     */
    private void inicializarTabla() {
	
	listadoSanitariosFiltrado = new FilteredList<>(listadoSanitarios, p -> true);
	cargarFiltros();
	dniSanitarioColumn.setCellValueFactory(dato -> dato.getValue().dniSanitarioProperty());
	nombreColumn.setCellValueFactory(dato -> dato.getValue().nombreProperty());
	apellidosColumn.setCellValueFactory(dato -> dato.getValue().apellidosProperty());
	especialidadColumn.setCellValueFactory(dato -> dato.getValue().especialidadProperty());
    }
    
    /**
     * <p>Cargar la tabla con los filtros y </p>
     * 
     * @param indice {@link Integer} índice de la paginación
     * @param limite {@link Integer} límite de la paginación
     */
    private void cambiarPaginacion(Integer indice, Integer limite) {
	
        int numeroPaginas = (int) (Math.ceil(listadoSanitariosFiltrado.size() * 1.0 / tamanioPaginacionComboBox.getValue()));
        paginacionTabla.setPageCount(numeroPaginas);

        Integer indiceDesde = indice * limite;
        Integer indiceHasta = Math.min(indiceDesde + limite, listadoSanitariosFiltrado.size());
        Integer indiceMinimo = Math.min(indiceHasta, listadoSanitariosFiltrado.size());
        
        SortedList<SanitariosModelo> datosOrdenados = new SortedList<>(FXCollections.observableArrayList(listadoSanitariosFiltrado.subList(Math.min(indiceDesde, indiceMinimo), indiceMinimo)));
        datosOrdenados.comparatorProperty().bind(sanitariosTableView.comparatorProperty());

        sanitariosTableView.setItems(datosOrdenados);

    }
    
    /**
     * <p>Cambiar la paginación de la tabla.</p>
     * 
     * @param event {@link ActionEvent} 
     */
    @FXML
    public void cambiarSeleccionTamanioPaginacion(ActionEvent event) {
	
	// Comprobar el el combobox tiene un valor seleccionado
	if(tamanioPaginacionComboBox.getValue() != null) {
	    
	    // Cambiar la paginación de la tabla
	    cambiarPaginacion(0, tamanioPaginacionComboBox.getValue());
	    
	    // Guardar la paginación como estado de la vista
	    UtilidadesPropiedades.guardarPropiedad(constructor, Constantes.ESTADOS_SANITARIOS_PAGINACION, tamanioPaginacionComboBox.getValue());
	}
    }
    
    /**
     * <p>Cargar las cajas de texto correspondientes con filtros para aplicar en el listado de la tabla.</p>
     */
    private void cargarFiltros() {
	
	// Filtro por el DNI del sanitario
	dniSanitarioTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoSanitariosFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getDniSanitario().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
	// Filtro por el nombre del sanitarios
	nombreTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoSanitariosFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getNombre().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
	// Filtro por los apellidos del sanitario
	apellidosTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoSanitariosFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getApellidos().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
	// Filtro por la dirección del sanitario
	especialidadTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoSanitariosFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getEspecialidad().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
    }
    
    /**
     * <p>Método para abrir la vista de edición para dar de alta un nuevo sanitario.</p>
     */
    @FXML
    private void crearSanitario() {
	
	UtilidadesVentanasEmergentes.abrirVentanaEmergente(SanitariosEdicionView.class, Constantes.SANITARIOS_EDICION_CONTROLLER, Accion.ALTA);
	
	sanitariosEdicionController = (SanitariosEdicionController) beansComponent.obtenerControlador(Constantes.SANITARIOS_EDICION_CONTROLLER);
	sanitariosEdicionController.setModelo(null);
	sanitariosEdicionController.initialize();
	
    }
    
    /**
     * <p>Método para abrir la vista de edición para modificar un sanitarios seleccionado de la tabla.</p>
     */
    @FXML
    private void editarSanitario() {
	
	UtilidadesVentanasEmergentes.abrirVentanaEmergente(SanitariosEdicionView.class, Constantes.SANITARIOS_EDICION_CONTROLLER, Accion.EDICION);
	
	sanitariosEdicionController = (SanitariosEdicionController) beansComponent.obtenerControlador(Constantes.SANITARIOS_EDICION_CONTROLLER);
	sanitariosEdicionController.setModelo(modeloSeleccionado);
	sanitariosEdicionController.initialize();

    }
    
    /**
     * <p>Método para eliminar un sanitario seleccionado de la tabla.</p>
     */
    @FXML
    private void eliminarSanitario() {
	
	// Mostrar alerta de confirmación
	Optional<ButtonType> confirmacion = UtilidadesAlertas.mostrarAlertaConfirmacion(confirmacionEliminacion);
	
	// Borrar en caso de que se haya pulsado el botón de aceptar
	if(confirmacion.isPresent() && confirmacion.get() == ButtonType.OK) {
	    
	    // Elimiar el registro
	    Boolean resultado = sanitariosService.eliminarSanitario(modeloSeleccionado.getIdSanitario());

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
     * <li>{@code editarButton}: para editar el sanitario seleccionado.</li>
     * <li>{@code eliminarButton}: para eliminar el sanitario seleccionado.</li>
     * </ul>
     * 
     * <p>Adicionalmente, asigna el modelo seleccionado al atributo que se utiliza en los métodos de dichos botones.</p>
     * 
     * @param modelo {@link SanitariosModelo} objeto de sanitario
     */
    private void habilitarBotonesFila(SanitariosModelo modelo) {
	
	// Se asigan el modelo seleccionado al que se pasará la vista
	modeloSeleccionado = modelo;
	
	// Habilidar o deshabilitar los botones dependiendo de si se ha seleccionado o no algo en la tabla
	editarButton.setDisable(modelo != null ? Boolean.FALSE : Boolean.TRUE);
	eliminarButton.setDisable(modelo != null ? Boolean.FALSE : Boolean.TRUE);
	
    }

}
