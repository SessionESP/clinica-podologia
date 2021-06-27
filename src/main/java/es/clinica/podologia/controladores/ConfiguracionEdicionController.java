package es.clinica.podologia.controladores;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesControles;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para la configuración de la aplicación.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class ConfiguracionEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(ConfiguracionEdicionController.class);
    
    @Value("${spring.config.import}")
    private String propiedadesExternas;
    
    @Value("${clinica.horario.apertura:09:00}")
    private LocalTime apertura;
    
    @Value("${clinica.horario.cierre:21:00}")
    private LocalTime cierre;
    
    @Value("${clinica.citas.duracion:30}")
    private Integer duracionCitas;
    
    @Value("${clinica.citas.eliminacion.pasadas:true}")
    private Boolean eliminacionCitas;
    
    @Value("${clinica.citas.eliminacion.fisica:false}")
    private Boolean eliminacionFisica;
    
    @FXML
    private ComboBox<LocalTime> aperturaComboBox;
    
    @FXML
    private ComboBox<LocalTime> cierreComboBox;
    
    @FXML
    private TextField duracionCitasTextField;
    
    @FXML
    private CheckBox eliminacionCitasCheckBox;
    
    @FXML
    private CheckBox eliminacionFisicaCheckBox;
    
    @FXML
    private Button guardarButton;
    
    @FXML
    private Button salirButton;
    
    
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la configuración de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	TRAZAS.debug("Vista de: " + this.getClass().getName());
	
	// Listas desplegables con el horario de la clínica
	cargarHorarios();
	
	// Duración de las citas
	cargarDuracionCitas();

	// Configuración del borrado de citas pasadas
	cargarConfiguracionEliminacionCitas();
	
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#aperturaComboBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarApertura(Event evento) {
	apertura = aperturaComboBox.getSelectionModel().getSelectedItem();
	guardarPropiedad("clinica.horario.apertura", UtilidadesConversores.horaCadena(aperturaComboBox.getSelectionModel().getSelectedItem()));
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#aperturaComboBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarCierre(Event evento) {
	TRAZAS.debug("cambiarCierre: ");
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#aperturaComboBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarDuracionCitas(Event evento) {
	TRAZAS.debug("cambiarDuracionCitas: ");
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#aperturaComboBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void eliminarCitas(Event evento) {
	TRAZAS.debug("eliminarCitas: ");
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#aperturaComboBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void eliminarFisicamente(Event evento) {
	TRAZAS.debug("eliminarFisicamente: ");
    }
    
    /**
     * <p>Método invocado como un evento para guardar todas las propiedades de la ventana emergente en el archivo de propiedades {@code configuracion.properties}</p>
     */
    @FXML
    private void guardar() {
	TRAZAS.debug("guardar: ");
    }
    
    /**
     * <p>Método invocado como un evento para cerrar la ventana emergente.</p>
     */
    @FXML
    private void salir() {
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();
    }
    
    /**
     * <p>Método que carga las listas desplegables para seleccionar la hora de apertura y cierre.</p>
     */
    private void cargarHorarios() {
	
	// Inicializar el listado de las horas
	List<LocalTime> listadoHorarios = new ArrayList<>();
	
	// Cargar el listado con las veinticuatro horas del día
	for(int i = 0; i < 24; i++) {
	    listadoHorarios.add(LocalTime.MIN.plusHours(UtilidadesConversores.enteroLong(i)));
	}
	
	// Cargar todas las horas del día en el selector de hora de apertura
	aperturaComboBox.getItems().addAll(listadoHorarios);
	
	// Asignar el valor de apertura si existe dentro de las posibilidades o un valor por defecto en caso contrario
	aperturaComboBox.setValue(listadoHorarios.contains(apertura) ? apertura : listadoHorarios.get(9));
	
	// Cargar todas las horas del día en el selector de hora de cierre
	cierreComboBox.getItems().addAll(listadoHorarios);
	
	// Asignar el valor de cierre si existe dentro de las posibilidades o un valor por defecto en caso contrario
	cierreComboBox.setValue(listadoHorarios.contains(cierre) ? cierre : listadoHorarios.get(21));
    }
    
    /**
     * <p>Método que carga la duración de las citas desde el fichero de configuración e inicializa su formateador.</p>
     */
    private void cargarDuracionCitas() {
	
	duracionCitasTextField.setTextFormatter(UtilidadesControles.formateador(Constantes.PATRON_NUMEROS_ENTEROS, 2));
	duracionCitasTextField.setText(UtilidadesConversores.enteroCadena(duracionCitas));
	
    }
    
    /**
     * <p>Método que carga la configuración de la eliminación de citas de la base de datos.</p>
     */
    private void cargarConfiguracionEliminacionCitas() {
	
	eliminacionCitasCheckBox.setSelected(eliminacionCitas);
	eliminacionFisicaCheckBox.setSelected(eliminacionFisica);
    }
    
    /**
     * <p>Método que guarda una propiedad en el archivo donde se guardan las propiedades de {@code configuration.properties}. </p>
     * 
     * @param nombre {@link String} identificador de la propiedad
     * @param valor {@link String} nuevo valor que se asignará a la propiedad
     */
    private void guardarPropiedad(String nombre, String valor) {
	
//	PropertiesConfiguration properties = new PropertiesConfiguration(url);
//	
//        try {
//
//            Properties propiedades = new Properties();
//            propiedades.load(ConfiguracionEdicionController.class.getClassLoader().getResourceAsStream("configuracion.properties"));
//            
//	    if (StringUtils.isNotBlank(nombre)) {
//		
//		propiedades.setProperty(nombre, valor);
//		propiedades.store(null, valor);
//
//	    }
//
//        } catch (IOException excepcion) {
//            TRAZAS.debug(excepcion.getMessage());
//            UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
//        }
	
    }

}
