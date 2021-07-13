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
import es.clinica.podologia.modelos.TratamientosModelo;
import es.clinica.podologia.servicios.TratamientosService;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesPropiedades;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import es.clinica.podologia.vistas.TratamientosEdicionView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para los Tratamientos.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class TratamientosListadoController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(TratamientosListadoController.class);
    
    @Value("${tratamientos.eliminacion.confirmacion}")
    private String confirmacionEliminacion;
    
    @Value("${tratamientos.eliminacion.true}")
    private String eliminacionCorrecta;
    
    @Value("${tratamientos.eliminacion.false}")
    private String eliminacionIncorrecta;
    
    @Value("${spring.config.import}")
    private String propiedadesExternas;
    
    @Autowired
    private TratamientosService tratamientosService;
    
    @Autowired
    private BeansComponent beansComponent;
    
    private ObservableList<TratamientosModelo> listadoTratamientos = FXCollections.observableArrayList();
    
    private FilteredList<TratamientosModelo> listadoTratamientosFiltrado;
    
    private TratamientosModelo modeloSeleccionado;
    
    private TratamientosEdicionController tratamientosEdicionController;
    
    private FileBasedConfigurationBuilder<FileBasedConfiguration> constructor;
    
    @FXML
    private TextField busquedaTextField;
    @FXML
    private TextArea descripcionTextArea;
    @FXML
    private Pagination paginacionTabla;
    
    // Listas desplegables
    @FXML
    private ComboBox<Integer> tamanioPaginacionComboBox;
    
    // Tabla
    @FXML
    private TableView<TratamientosModelo> tratamientosTableView;
    @FXML
    private TableColumn<TratamientosModelo, String> nombreColumn;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista del listado de Tratamientos.</p>
     */
    @FXML
    public void initialize() {
	
	cargarEstado();
	
	List<TratamientosModelo> listado = tratamientosService.listarTratamientos();
	
	listadoTratamientos.clear();
	listadoTratamientos.addAll(listado);
	
	inicializarTabla();
	
	tratamientosTableView.setItems(listadoTratamientos);
	
        cambiarPaginacion(0, tamanioPaginacionComboBox.getValue());
        paginacionTabla.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> cambiarPaginacion(newValue.intValue(), tamanioPaginacionComboBox.getValue()));
        
        // Inicializar la descripción
        mostrarDescripcion(null);
        
        tratamientosTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> mostrarDescripcion(newValue));
        
	
    }
    
    /**
     * <p>Cargar el estado del formulario.</p>
     */
    private void cargarEstado() {
	
	// Inicializar el constructor con los parámetros del fichero externo
	constructor = UtilidadesPropiedades.crearConstructor(new Parameters(), propiedadesExternas, Constantes.COMA);
	
	try {
	    
	    // Comprobar que el constructor y los parámetros NO son nulos
	    if (constructor != null && constructor.getConfiguration() != null) {

		// Guardar la información del fichero de configuración en un objeto
		FileBasedConfiguration configuracion = constructor.getConfiguration();
		
		List<Integer> paginaciones = UtilidadesConversores.convertirArrayCadenasListaEnteros(configuracion.getStringArray(Constantes.ESTADOS_TRATAMIENTOS_PAGINACIONES));
		
		Integer paginacion = configuracion.getInteger(
			Constantes.ESTADOS_TRATAMIENTOS_PAGINACION, 
			Constantes.ESTADOS_PAGINACION_DEFECTO_5);
		
	    }

	} catch (ConfigurationException excepcion) {

	    // Error al intentar guardar las propiedades del fichero en un objeto
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}
	
	ObservableList<Integer> opciones = FXCollections.observableArrayList();
        opciones.addAll(Arrays.asList(5, 10, 15));
	tamanioPaginacionComboBox.setItems(opciones);
	tamanioPaginacionComboBox.setValue(5);
	
    }
    
    /**
     * <p>Inicializar la tabla.</p>
     */
    private void inicializarTabla() {
	
	listadoTratamientosFiltrado = new FilteredList<>(listadoTratamientos, p -> true);
	cargarFiltros();
	nombreColumn.setCellValueFactory(dato -> dato.getValue().nombreProperty());
    }
    
    /**
     * <p>Cargar la tabla con los filtros y </p>
     * 
     * @param indice {@link Integer} índice de la paginación
     * @param limite {@link Integer} límite de la paginación
     */
    private void cambiarPaginacion(Integer indice, Integer limite) {
	
        int numeroPaginas = (int) (Math.ceil(listadoTratamientosFiltrado.size() * 1.0 / tamanioPaginacionComboBox.getValue()));
        paginacionTabla.setPageCount(numeroPaginas);

        Integer indiceDesde = indice * limite;
        Integer indiceHasta = Math.min(indiceDesde + limite, listadoTratamientosFiltrado.size());
        Integer indiceMinimo = Math.min(indiceHasta, listadoTratamientosFiltrado.size());
        
        SortedList<TratamientosModelo> datosOrdenados = new SortedList<>(FXCollections.observableArrayList(listadoTratamientosFiltrado.subList(Math.min(indiceDesde, indiceMinimo), indiceMinimo)));
        datosOrdenados.comparatorProperty().bind(tratamientosTableView.comparatorProperty());

        tratamientosTableView.setItems(datosOrdenados);

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
	busquedaTextField.textProperty().addListener((observable, oldValue, newValue) -> {
	    listadoTratamientosFiltrado.setPredicate(cita -> newValue == null || newValue.isEmpty() || cita.getNombre().toLowerCase().contains(newValue.toLowerCase()));
	    cambiarPaginacion(paginacionTabla.getCurrentPageIndex(), tamanioPaginacionComboBox.getValue());
	});
	
    }
    
    @FXML
    private void crearTratamiento() {
	
	UtilidadesVentanasEmergentes.abrirVentanaEmergente(TratamientosEdicionView.class, Constantes.TRATAMIENTOS_EDICION_CONTROLLER, Accion.ALTA);
	
	tratamientosEdicionController = (TratamientosEdicionController) beansComponent.obtenerControlador(Constantes.TRATAMIENTOS_EDICION_CONTROLLER);
	tratamientosEdicionController.setModelo(null);
	tratamientosEdicionController.initialize();
	
    }
    
    @FXML
    private void editarTratamiento() {
	
	UtilidadesVentanasEmergentes.abrirVentanaEmergente(TratamientosEdicionView.class, Constantes.TRATAMIENTOS_EDICION_CONTROLLER, Accion.EDICION);
	
	tratamientosEdicionController = (TratamientosEdicionController) beansComponent.obtenerControlador(Constantes.TRATAMIENTOS_EDICION_CONTROLLER);
	tratamientosEdicionController.setModelo(modeloSeleccionado);
	tratamientosEdicionController.initialize();

    }
    
    @FXML
    private void eliminarTratamiento() {
	
	// Mostrar alerta de confirmación
	Optional<ButtonType> confirmacion = UtilidadesAlertas.mostrarAlertaConfirmacion(confirmacionEliminacion);
	
	// Borrar en caso de que se haya pulsado el botón de aceptar
	if(confirmacion.isPresent() && confirmacion.get() == ButtonType.OK) {
	    
	    // Elimiar el registro
	    Boolean resultado = tratamientosService.eliminarTratamiento(modeloSeleccionado.getIdTratamiento());

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
     * <p>Método que muestra la descripción de un tratamiento seleccionado.</p>
     * 
     * @param modelo {@link TratamientosModelo} objeto de tratamiento
     */
    private void mostrarDescripcion(TratamientosModelo modelo) {
	
	modeloSeleccionado = modelo;
	
	// Comprobar que el objeto modelo pasado como parámetro NO es nulo
	if(modelo != null) {
	    
	    // Si el objeto NO es nulo, mostrar el valor atributo de la descripción en el área de texto
	    descripcionTextArea.setText(modelo.getDescripcion());
	    
	} else {
	    
	    // Si el objeto modelo es nulo, vaciar el área de texto
	    descripcionTextArea.setText(Constantes.CADENA_VACIA);
	    
	}
	
    }

}
