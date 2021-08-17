package es.clinica.podologia.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.formateadores.DatePickerFormatted;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.CitasModelo;
import es.clinica.podologia.modelos.SanitariosModelo;
import es.clinica.podologia.servicios.CitasService;
import es.clinica.podologia.servicios.SanitariosService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesPropiedades;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * <p>Controlador para la vista de la agenda de la aplicación.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class AgendaEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(AgendaEdicionController.class);
    
    
    @Autowired
    private CitasService citasService;
    
    @Autowired
    private SanitariosService sanitariosService;
    
    @FXML
    private AnchorPane agendaAnchorPane;
    
    @FXML
    private TableView<List<String>> agendaTableView = new TableView<>();
    
    @FXML
    private ComboBox<SanitariosModelo> sanitarioFiltroComboBox;
    @FXML
    private DatePicker fechaFiltroDatePicker;
    
    @FXML
    private TextField identificadorCitaTextField;
    @FXML
    private TextField pacienteCitaTextField;
    @FXML
    private TextField sanitarioCitaTextField;
    @FXML
    private TextField tratamientoCitaTextField;
    @FXML
    private TextField fechaCitaTextField;
    @FXML
    private TextField horaInicioCitaTextField;
    @FXML
    private TextField horaFinCitaTextField;
    
    @FXML
    private TextField dniPacienteTextField;
    @FXML
    private TextField nombrePacienteTextField;
    @FXML
    private TextField apellidosPacienteTextField;
    @FXML
    private TextField fechaNacimientoPacienteTextField;
    @FXML
    private TextField direccionPacienteTextField;
    @FXML
    private TextField telefonoPacienteTextField;
    
    @FXML
    private Label edadPacienteLabel;
    
    @FXML
    private TextField dniSanitarioTextField;
    @FXML
    private TextField nombreSanitarioTextField;
    @FXML
    private TextField apellidosSanitarioTextField;
    @FXML
    private TextField especialidadSanitarioTextField;
    
    @FXML
    private TextField identificadorTratamientoTextField;
    @FXML
    private TextField nombreTratamientoTextField;
    @FXML
    private TextArea descripcionTratamientoTextArea;
    

    
    @FXML
    private TextArea observacionesTextArea;
    
    @Value("${spring.config.import}")
    private List<String> propiedadesExternas;
    
    @Value("${agenda.edicion.columna1}")
    private String columna1;
    
    private LocalTime apertura;
    
    private LocalTime cierre;
    
    private Integer duracionCitas;
    
    private CitasModelo modeloSeleccionado;
    
    
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la agenda de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	// Inicializar el constructor con los parámetros del fichero externo
	FileBasedConfigurationBuilder<FileBasedConfiguration> constructor = UtilidadesPropiedades.crearConstructor(new Parameters(), propiedadesExternas.get(0), Constantes.COMA);
	
	try {

	    // Comprobar que el constructor y los parámetros NO son nulos
	    if (constructor != null && constructor.getConfiguration() != null) {

		// Guardar la información del fichero de configuración en un objeto
		FileBasedConfiguration configuracion = constructor.getConfiguration();

		// Hora de apertura
		apertura = UtilidadesConversores.convertirCadenaHora(configuracion.getString(
			Constantes.CONFIGURACION_HORA_APERTURA, 
			Constantes.CONFIGURACION_APERTURA_DEFECTO));

		// Hora de cierre
		cierre = UtilidadesConversores.convertirCadenaHora(configuracion.getString(
			Constantes.CONFIGURACION_HORA_CIERRE, 
			Constantes.CONFIGURACION_CIERRE_DEFECTO));

		// Duración de las citas en minutos
		duracionCitas = configuracion.getInteger(
			Constantes.CONFIGURACION_DURACION, 
			Constantes.CONFIGURACION_DURACION_DEFECTO);

	    }

	} catch (ConfigurationException excepcion) {

	    // Error al intentar guardar las propiedades del fichero en un objeto
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}
	
	fechaFiltroDatePicker.setValue(LocalDate.now());
	
	fechaFiltroDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            UtilidadesAlertas.mostrarAlertaInformativa("Fecha seleccionada: " + newValue);
        });
	
	fechaFiltroDatePicker.setConverter(new DatePickerFormatted());
	
	// Encuentra todos los sanitarios
	List<SanitariosModelo> listadoSanitarios = sanitariosService.listarSanitarios();
	
	sanitarioFiltroComboBox.getItems().clear();
	sanitarioFiltroComboBox.getItems().addAll(listadoSanitarios);
	sanitarioFiltroComboBox.getSelectionModel().selectFirst();
	
	generarTabla();
	
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code AgendaEdicionController#fechaFiltroDatePicker}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarFecha(Event evento) {
	generarTabla();
    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code AgendaEdicionController#sanitarioFiltroComBox}</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @FXML
    private void cambiarSanitario(Event evento) {
	generarTabla();
    }
    
    /**
     * <p>Método que genera la tabla con toda la información de la agenda para un día.</p>
     */
    private void generarTabla() {
	
	// Generar las columnas de la tabla
	generarColumnas();
	
	// Generar las filas de la tabla
	generarFilas();
	
	agendaTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> seleccionarFila(newValue));
    }
    
    /**
     * <p>Método que genera las columnas de la tabla de la agenda.</p>
     */
    private void generarColumnas() {
	
	// Limpiar la tabla de columnas anteriores, por si las tuviera (caso de refrescar la vista)
	agendaTableView.getColumns().clear();
	
	// Genera la primera columna con las horas
	generarColumna(agendaTableView, columna1, 0);
	
	// Generar una segunda columna con las citas del sanitario filtrado
	generarColumna(agendaTableView, sanitarioFiltroComboBox.getValue().toString(), 1);
	
    }
    
    /**
     * <p>Método que genera una columna dentro de la tabla de la agenda.</p>
     * 
     * @param tabla {@link TableView} {@link List} {@link String} tabla donde se va a generar la columna
     * @param nombreColumna {@link String} nombre de la columna
     * @param indice {@link String} índice de la columna
     */
    private void generarColumna(TableView<List<String>> tabla, String nombreColumna, Integer indice) {
	
	//Inicializar la columna con su nombre
	TableColumn<List<String>, String> columna = new TableColumn<>(Utilidades.comprobarCadena(nombreColumna, Constantes.CADENA_VACIA));
	
	// Determinar el valor dentro de la fila que le corresponde
	columna.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().get(indice)));
	
	// Añadir la columna a la tabla
	tabla.getColumns().add(columna);
	    

	
    }
    
    /**
     * <p>Método que genera las filas de la tabla de la agenda.</p>
     */
    @SuppressWarnings("unchecked")
    private void generarFilas() {
	
	// Calcular el número de filas que se van a generar
	Integer numeroFilas = Math.floorDiv(UtilidadesConversores.calcularDiferenciaMinutos(apertura, cierre), duracionCitas);
	
	// Limpiar la tabla de filas anteriores, por si las tuviera (caso de refrescar la vista)
	agendaTableView.getItems().clear();
	
	// Iterar sobre el número de filas obtenido
	for (int i = 0; i < numeroFilas; i++) {
	    
	    // Comprobar que la tabla tiene columnas
	    if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(agendaTableView.getColumns()))) {
		
		// Añadir la fila a la tabla
		agendaTableView.getItems().addAll(generarFila(agendaTableView.getColumns().size(), i));
		
	    }

	}
	
    }
    
    /**
     * <p>Método que genera los valores de una fila de la tabla.</p>
     * 
     * @param numeroColumnas {@link Integer} número de columnas que tiene la tabla
     * @param numeroFila {@link Integer} número de la fila
     * 
     * @return {@link List} {@link String} listado de valores de la fila
     */
    private List<String> generarFila(Integer numeroColumnas, Integer numeroFila) {

	// Inicializar el array con los valores de la fila que se va a retornar al final del método
	List<String> valoresFila = new ArrayList<>();
	
	// Primera columna con las horas de las citas
	String hora = UtilidadesConversores.convertirHoraCadena(apertura.plusMinutes(UtilidadesConversores.convertirEnteroLong(numeroFila * duracionCitas)));
	valoresFila.add(hora);
	
	// Iterar sobre el resto de columnas
	for(int i = 1; i < agendaTableView.getColumns().size(); i++) {
	    
	    // Buscar la cita correspondiente
	    CitasModelo cita = citasService.encontrarCitaPorFechaHoraSanitario(
		    fechaFiltroDatePicker.getValue(), 
		    UtilidadesConversores.convertirCadenaHora(hora), 
		    sanitarioFiltroComboBox.getValue().getDniSanitario());
	    
	    valoresFila.add(cita != null ? UtilidadesConversores.convertirEnteroCadena(cita.getIdCita()) : Constantes.LIBRE);
	    
	}
	
	// Retornar el array con los valores de la fila
	return valoresFila;
	
    }
    
    /**
     * <p>Guardar el modelo seleccionado</p>
     * 
     * <p>Adicionalmente, asigna el modelo seleccionado al atributo que se utiliza en los métodos de dichos botones.</p>
     * 
     * @param modelo {@link CitasModelo} objeto de paciente
     */
    private void seleccionarFila(List<String> fila) {
	
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(fila)) && Boolean.FALSE.equals(Utilidades.compararCadenas(fila.get(1), Constantes.LIBRE))) {
	    modeloSeleccionado = citasService.encontrarCita(UtilidadesConversores.convertirCadenaEntero(fila.get(1)));
	    TRAZAS.info("Paciente: " + modeloSeleccionado.getNombrePaciente() + "\nSanitario: " + modeloSeleccionado.getNombreSanitario());
	}
	
    }

}
