package es.clinica.podologia.controladores;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesControles;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesPropiedades;
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
    
    @Value("${configuracion.edicion.error}")
    private String errorGuardado;
    
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
    
    private LocalTime apertura;
    
    private LocalTime cierre;
    
    private Integer duracionCitas;
    
    private Boolean eliminacionCitas;
    
    private Boolean eliminacionFisica;
    
    private FileBasedConfigurationBuilder<FileBasedConfiguration> constructor;
 
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la configuración de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	// Inicializar el constructor con los parámetros del fichero externo
	constructor = UtilidadesPropiedades.crearConstructor(new Parameters(), propiedadesExternas, Constantes.COMA);
	
	try {
	    
	    // Comprobar que el constructor y los parámetros NO son nulos
	    if (constructor != null && constructor.getConfiguration() != null) {

		// Guardar la información del fichero de configuración en un objeto
		FileBasedConfiguration configuracion = constructor.getConfiguration();
		
		// Listas desplegables con el horario de la clínica
		cargarHorarios(configuracion);

		// Duración de las citas
		cargarDuracionCitas(configuracion);

		// Configuración del borrado de citas pasadas
		cargarConfiguracionEliminacionCitas(configuracion);
	    }

	} catch (ConfigurationException excepcion) {

	    // Error al intentar guardar las propiedades del fichero en un objeto
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}


	    
	
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#aperturaComboBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarApertura(Event evento) {
	apertura = aperturaComboBox.getSelectionModel().getSelectedItem();
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#cierreComboBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarCierre(Event evento) {
	cierre = cierreComboBox.getSelectionModel().getSelectedItem();
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#duracionCitasTextField}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarDuracionCitas(Event evento) {
	duracionCitas = UtilidadesConversores.cadenaEntero(duracionCitasTextField.getText());
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#eliminacionCitasCheckBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void eliminarCitas(Event evento) {
	eliminacionCitas = eliminacionCitasCheckBox.isSelected();
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code ConfiguracionEdicionController#eliminacionFisicaCheckBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void eliminarFisicamente(Event evento) {
	eliminacionFisica = eliminacionFisicaCheckBox.isSelected();
    }
    
    /**
     * <p>Método invocado como un evento para guardar todas las propiedades de la ventana emergente en el archivo de propiedades {@code configuracion.properties}</p>
     */
    @FXML
    private void guardar() {
	guardarPropiedades();
    }
    
    /**
     * <p>Método invocado como un evento para cerrar la ventana emergente.</p>
     */
    @FXML
    private void salir() {
	initialize();
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();
    }
    
    /**
     * <p>Método que carga las listas desplegables para seleccionar la hora de apertura y cierre.</p>
     * 
     * @param configuracion {@link FileBasedConfiguration} información del fichero de propiedades en un objeto
     */
    private void cargarHorarios(FileBasedConfiguration configuracion) {
	
	// Hora de apertura
	apertura = UtilidadesConversores.cadenaHora(configuracion.getString(
		Constantes.CONFIGURACION_HORA_APERTURA, 
		Constantes.CONFIGURACION_APERTURA_DEFECTO));

	// Hora de cierre
	cierre = UtilidadesConversores.cadenaHora(configuracion.getString(
		Constantes.CONFIGURACION_HORA_CIERRE, 
		Constantes.CONFIGURACION_CIERRE_DEFECTO));

	// Inicializar el listado de las horas
	List<LocalTime> listadoHorarios = new ArrayList<>();

	// Cargar el listado con las veinticuatro horas del día
	for (int i = 0; i < 24; i++) {
	    listadoHorarios.add(LocalTime.MIN.plusHours(UtilidadesConversores.enteroLong(i)));
	}

	// Cargar todas las horas del día en el selector de hora de apertura
	aperturaComboBox.getItems().addAll(listadoHorarios);

	// Asignar el valor de apertura si existe dentro de las posibilidades o un valor
	// por defecto en caso contrario
	aperturaComboBox.setValue(listadoHorarios.contains(apertura) ? apertura : listadoHorarios.get(9));

	// Cargar todas las horas del día en el selector de hora de cierre
	cierreComboBox.getItems().addAll(listadoHorarios);

	// Asignar el valor de cierre si existe dentro de las posibilidades o un valor
	// por defecto en caso contrario
	cierreComboBox.setValue(listadoHorarios.contains(cierre) ? cierre : listadoHorarios.get(21));
	
    }
    
    
    /**
     * <p>Método que carga la duración de las citas desde el fichero de configuración e inicializa su formateador.</p>
     * 
     * @param configuracion {@link FileBasedConfiguration} información del fichero de propiedades en un objeto
     */
    private void cargarDuracionCitas(FileBasedConfiguration configuracion) {
	
	// Duración de las citas en minutos
	duracionCitas = configuracion.getInteger(
		Constantes.CONFIGURACION_DURACION, 
		Constantes.CONFIGURACION_DURACION_DEFECTO);

	duracionCitasTextField.setTextFormatter(UtilidadesControles.formateador(Constantes.PATRON_NUMEROS_ENTEROS, 2));
	duracionCitasTextField.setText(UtilidadesConversores.enteroCadena(duracionCitas));
	
    }
    
    
    /**
     * <p>Método que carga la configuración de la eliminación de citas de la base de datos.</p>
     * 
     * @param configuracion {@link FileBasedConfiguration} información del fichero de propiedades en un objeto
     */
    private void cargarConfiguracionEliminacionCitas(FileBasedConfiguration configuracion) {
	    
	// Eliminar citas pasadas, mantenimiento básico de la aplicación
	eliminacionCitas = configuracion.getBoolean(Constantes.CONFIGURACION_ELIMINACION_CITAS_PASADAS, Boolean.TRUE);

	// Tipo de eliminación de las citas: true es un eliminado físico y false uno lógico que pasa las citas pasadas a un histórico
	eliminacionFisica = configuracion.getBoolean(Constantes.CONFIGURACION_ELIMINACION_FISICA_CITAS, Boolean.TRUE);

	eliminacionCitasCheckBox.setSelected(eliminacionCitas);
	eliminacionFisicaCheckBox.setSelected(eliminacionFisica);
	
    }
    

    /**
     * <p>Método que guarda todas las propiedades de la ventana.</p>
     */
    private void guardarPropiedades() {
	
	try {
	    
	    // Setear el valor de cada control en el fichero de propiedades externo
	    constructor.getConfiguration().setProperty(Constantes.CONFIGURACION_HORA_APERTURA, UtilidadesConversores.horaCadena(apertura));
	    constructor.getConfiguration().setProperty(Constantes.CONFIGURACION_HORA_CIERRE, UtilidadesConversores.horaCadena(cierre));
	    constructor.getConfiguration().setProperty(Constantes.CONFIGURACION_DURACION, UtilidadesConversores.enteroCadena(duracionCitas));
	    constructor.getConfiguration().setProperty(Constantes.CONFIGURACION_ELIMINACION_CITAS_PASADAS, UtilidadesConversores.booleanoCadena(eliminacionCitas));
	    constructor.getConfiguration().setProperty(Constantes.CONFIGURACION_ELIMINACION_FISICA_CITAS, UtilidadesConversores.booleanoCadena(eliminacionFisica));
	    
	    // Guardar
	    constructor.save();
	    

	} catch (ConfigurationException excepcion) {

	    // Error al intentar guardar las propiedades del fichero en un objeto
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}
	
    }


}
