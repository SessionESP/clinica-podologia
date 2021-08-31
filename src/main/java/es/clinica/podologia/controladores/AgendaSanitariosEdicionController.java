package es.clinica.podologia.controladores;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import es.clinica.podologia.vistas.CitasEdicionView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * <p>Controlador para la vista de la agenda de cada sanitario de la aplicación.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class AgendaSanitariosEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(AgendaSanitariosEdicionController.class);
    
    @Autowired
    private BeansComponent beansComponent;
    
    @Autowired
    private CitasService citasService;
    @Autowired
    private SanitariosService sanitariosService;
    
    @FXML
    private ComboBox<SanitariosModelo> sanitarioFiltro1ComboBox;
    @FXML
    private ComboBox<SanitariosModelo> sanitarioFiltro2ComboBox;
    @FXML
    private ComboBox<SanitariosModelo> sanitarioFiltro3ComboBox;
    
    @FXML
    private DatePicker fechaFiltro1DatePicker;
    @FXML
    private DatePicker fechaFiltro2DatePicker;
    @FXML
    private DatePicker fechaFiltro3DatePicker;
    
    @FXML
    private TableView<List<String>> agendaSanitarios1TableView = new TableView<>();
    @FXML
    private TableView<List<String>> agendaSanitarios2TableView = new TableView<>();
    @FXML
    private TableView<List<String>> agendaSanitarios3TableView = new TableView<>();
    
    @Value("${spring.config.import}")
    private List<String> propiedadesExternas;
    
    @Value("${agenda.edicion.columna1}")
    private String columna1;
    
    private LocalTime apertura;
    private LocalTime cierre;
    
    private Integer duracionCitas;
    
    private CitasModelo modeloSeleccionado;
   
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la agenda de un sanitario de la aplicación.</p>
     */
    @FXML
    public void initialize() {
	
	cargarParametrosCitas();
	
	// Encuentra todos los sanitarios
	List<SanitariosModelo> listadoSanitarios = sanitariosService.listarSanitarios();
	
	// Cargar los filtros de las tres agendas
	cargarFiltros(sanitarioFiltro1ComboBox, listadoSanitarios, fechaFiltro1DatePicker);
	cargarFiltros(sanitarioFiltro2ComboBox, listadoSanitarios, fechaFiltro2DatePicker);
	cargarFiltros(sanitarioFiltro3ComboBox, listadoSanitarios, fechaFiltro3DatePicker);
	
	cargarEstado();
	
	// Generar cada una de las agendas
	generarTabla(agendaSanitarios1TableView, sanitarioFiltro1ComboBox, fechaFiltro1DatePicker);
	generarTabla(agendaSanitarios2TableView, sanitarioFiltro2ComboBox, fechaFiltro2DatePicker);
	generarTabla(agendaSanitarios3TableView, sanitarioFiltro3ComboBox, fechaFiltro3DatePicker);
	
	limpiarDetalle();
	
    }
    
    /**
     * <p>Cargar el estado del formulario.</p>
     */
    private void cargarParametrosCitas() {
	
	// Inicializar el constructor con los parámetros del fichero externo
	FileBasedConfigurationBuilder<FileBasedConfiguration> constructor = 
		UtilidadesPropiedades.crearConstructor(new Parameters(), propiedadesExternas.get(0), Constantes.COMA);
	
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

    }
    
    /**
     * <p>Cargar el estado del formulario.</p>
     */
    private void cargarEstado() {

//	// Inicializar el constructor con los parámetros del fichero externo
//	FileBasedConfigurationBuilder<FileBasedConfiguration> constructor = UtilidadesPropiedades.crearConstructor(new Parameters(), propiedadesExternas.get(1),
//		Constantes.COMA);
//
//	// Inicializar con valores por defecto
//	List<Integer> paginaciones = Arrays.asList(10, 20, 30, 40, 50);
//	Integer identificadorSanitario = Constantes.ESTADOS_PAGINACION_DEFECTO_10;
//
//	try {
//
//	    // Comprobar que el constructor y los parámetros NO son nulos
//	    if (constructor != null && constructor.getConfiguration() != null) {
//
//		// Guardar la información del fichero de configuración en un objeto
//		FileBasedConfiguration configuracion = constructor.getConfiguration();
//
//		paginaciones = UtilidadesConversores.convertirArrayCadenasListaEnteros(
//			configuracion.getStringArray(Constantes.ESTADOS_CITAS_PAGINACIONES));
//
//		paginacion = configuracion.getInteger(Constantes.ESTADOS_CITAS_PAGINACION,
//			Constantes.ESTADOS_PAGINACION_DEFECTO_10);
//
//	    }
//
//	} catch (ConfigurationException excepcion) {
//
//	    // Error al intentar guardar las propiedades del fichero en un objeto
//	    TRAZAS.error(excepcion.getMessage());
//	    excepcion.printStackTrace();
//	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
//
//	}
//
//	ObservableList<Integer> opciones = FXCollections.observableArrayList();
//	opciones.addAll(paginaciones);
//	tamanioPaginacionComboBox.setItems(opciones);
//	tamanioPaginacionComboBox.setValue(paginacion);

    }
    
    /**
     * <p>Método invocado como un evento cuando cambia el valor de {@code AgendaEdicionController#fechaFiltroDatePicker} o {@code AgendaEdicionController#sanitarioFiltroComBox}.</p>
     * <p>Dependiendo de el control desde donde se haya invocado regenerará una tabla u otra.</p>
     * 
     * @param evento {@link ActionEvent} parámetro con la información asociada al evento
     */
    @SuppressWarnings("unchecked")
    @FXML
    private void regenerarTabla(Event evento) {

	// Comprobar que el evento NO es nulo
	if (evento != null) {
	    // Obtener el numero del identificador del control que ha activado el evento

	    String identificador = null;

	    if (evento.getSource() instanceof DatePicker) {

		// Es una instancia de DatePicker
		identificador = Utilidades.comprobarCadenaNula(((DatePicker) evento.getSource()).getId())
			.replaceAll(Constantes.PATRON_TODO_MENOS_ENTEROS.toString(), Constantes.CADENA_VACIA);

	    } else {

		// Es una instancia de ComboBox por descarte
		identificador = Utilidades
			.comprobarCadenaNula(((ComboBox<SanitariosModelo>) evento.getSource()).getId())
			.replaceAll(Constantes.PATRON_TODO_MENOS_ENTEROS.toString(), Constantes.CADENA_VACIA);
	    }

	    // Regenerar la tabla correspondiente
	    switch (identificador) {
	    case Constantes.NUMERO_UNO:
		generarTabla(agendaSanitarios1TableView, sanitarioFiltro1ComboBox, fechaFiltro1DatePicker);
		break;
	    case Constantes.NUMERO_DOS:
		generarTabla(agendaSanitarios2TableView, sanitarioFiltro2ComboBox, fechaFiltro2DatePicker);
		break;
	    case Constantes.NUMERO_TRES:
		generarTabla(agendaSanitarios3TableView, sanitarioFiltro3ComboBox, fechaFiltro3DatePicker);
		break;
	    default:
		break;
	    }

	}

    }
    
    
    /**
     * <p>Método que carga los filtros de una agenda.</p>
     * <p>La lista desplegable siempre se inicializará con el primer valor de la lista y la fecha será por defecto la del sistema.</p>
     * 
     * @param sanitarioFiltroComboBox {@link ComboBox}<{@link SanitariosModelo}> lista desplegable con un listado de sanitarios
     * @param listadoSanitarios {@link List}<{@link SanitariosModelo}> listado de sanitarios
     * @param fechaFiltroDatePicker {@link DatePicker} control específico para recofer fechas
     */
    private void cargarFiltros(ComboBox<SanitariosModelo> listaDesplegable, List<SanitariosModelo> listadoSanitarios, DatePicker recogedorFecha) {
	
	// Comprobar que los parámetros de la lista desplegable NO son nulos
	if(listaDesplegable != null && Utilidades.comprobarColeccion(listadoSanitarios)) {
	    
	    // Cargar e inicializar la lista desplegable
	    listaDesplegable.getItems().clear();
	    listaDesplegable.getItems().addAll(listadoSanitarios);
	    listaDesplegable.getSelectionModel().selectFirst();
	    
	}
	
	// Comprobar que el parámetro con el control de la fecha NO es nulo
	if(recogedorFecha != null) {
	    
	    // Cargar la fecha del sistema y formatear las fechas
	    recogedorFecha.setValue(LocalDate.now());
	    recogedorFecha.setConverter(new DatePickerFormatted());
	    
	}
	
    }
    
    /**
     * <p>Método que genera la tabla con toda la información de la agenda para un día.</p>
     * 
     * @param tabla {@link TableView}<{@link List}<{@link String}>> tabla con la información de la agenda
     * @param sanitarioFiltroComboBox {@link ComboBox}<{@link SanitariosModelo}> lista desplegable con un listado de sanitarios
     */
    private void generarTabla(TableView<List<String>> tabla, ComboBox<SanitariosModelo> listaDesplegable, DatePicker recogedorFecha) {
	
	if (tabla != null) {
	    
	    // Generar las columnas de la tabla
	    generarColumnas(tabla, listaDesplegable);

	    // Generar las filas de la tabla
	    generarFilas(tabla, listaDesplegable, recogedorFecha);

	    // Escuchador para cargar una cita cuando una línea es seleccionada
	    tabla.getSelectionModel().selectedItemProperty().
	    	addListener((observable, valorAntiguo, valorNuevo) -> seleccionarFila(valorNuevo));
	    
	    // Escuchador del doble clic para editar una cita
	    tabla.setOnMouseClicked((MouseEvent evento) -> {
		if (evento.getButton().equals(MouseButton.PRIMARY) && evento.getClickCount() == 2) {
		    editarCita(tabla.getSelectionModel().getSelectedItem(), evento);
		}
	    });
	    
	    // El color de fondo de la fila se cambiará dependiendo del que se haya definido en el tratamiento de la cita
	    tabla.setRowFactory(tv -> new TableRow<List<String>>() {
		@Override
		protected void updateItem(List<String> item, boolean empty) {
		    
		    super.updateItem(item, empty);

		    // Establecer como color de fondo el que se ha asignado al tratamiento
		    if (Boolean.TRUE.equals(Utilidades.comprobarColeccion(item)) && 
			    Boolean.FALSE.equals(Utilidades.compararCadenas(item.get(2), Constantes.COLOR_BLANCO_HEXADECIMAL))) {
			setStyle("-fx-background-color: " + Utilidades.comprobarCadena(item.get(2), Constantes.COLOR_BLANCO_HEXADECIMAL) + ";");
		    } else {
			setStyle("-fx-background-color: " +  Constantes.COLOR_BLANCO_HEXADECIMAL + ";");
		    }

		}
	    });
	 
	    
	}
	

    }
    
    /**
     * <p>Método que genera las columnas de la tabla de la agenda.</p>
     * 
     * @param tabla {@link TableView}<{@link List}<{@link String}>> tabla con la información de la agenda
     * @param sanitarioFiltroComboBox {@link ComboBox}<{@link SanitariosModelo}> lista desplegable con un listado de sanitarios
     */
    private void generarColumnas(TableView<List<String>> tabla, ComboBox<SanitariosModelo> listaDesplegable) {
	
	// Limpiar la tabla de columnas anteriores, por si las tuviera (caso de refrescar la vista)
	tabla.getColumns().clear();
	
	// Genera la primera columna con las horas
	generarColumna(tabla, columna1, 0, Boolean.TRUE);
	
	// Generar una segunda columna con las citas del sanitario filtrado
	generarColumna(tabla, listaDesplegable.getValue().toString(), 1, Boolean.TRUE);
	
	// Genera la primera columna con las horas
	generarColumna(tabla, columna1, 2, Boolean.FALSE);
	
    }
    
    /**
     * <p>Método que genera una columna dentro de la tabla de la agenda.</p>
     * 
     * @param tabla {@link TableView}<{@link List}<{@link String}>> tabla donde se va a generar la columna
     * @param nombreColumna {@link String} nombre de la columna
     * @param indice {@link String} índice de la columna
     * @param visible {@link Boolean} visibilidad de la columna: {@code true} para hacerla visible y {@code false} para hacerla invisible
     */
    private void generarColumna(TableView<List<String>> tabla, String nombreColumna, Integer indice, Boolean visible) {
	
	//Inicializar la columna con su nombre
	TableColumn<List<String>, String> columna = new TableColumn<>(Utilidades.comprobarCadena(nombreColumna, Constantes.CADENA_VACIA));
	
	// Determinar el valor dentro de la fila que le corresponde
	columna.setCellValueFactory(dato -> new SimpleStringProperty(dato.getValue().get(indice)));
	
	columna.setVisible(Boolean.TRUE.equals(visible) ? visible : Boolean.FALSE);
	
	// Añadir la columna a la tabla
	tabla.getColumns().add(columna);
	
    }
    
    /**
     * <p>Método que genera las filas de la tabla de la agenda.</p>
     * 
     * @param tabla {@link TableView}<{@link List}<{@link String}>> tabla donde se va a generar la columna
     * @param listaDesplegable {@link ComboBox}<{@link SanitariosModelo}> lista desplegable con un listado de sanitarios
     * @param recogedorFecha {@link DatePicker} control específico para recofer fechas
     */
    @SuppressWarnings("unchecked")
    private void generarFilas(
	    TableView<List<String>> tabla, 
	    ComboBox<SanitariosModelo> listaDesplegable, 
	    DatePicker recogedorFecha) {
	
	// Calcular el número de filas que se van a generar
	Integer numeroFilas = Math.floorDiv(UtilidadesConversores.calcularDiferenciaMinutos(apertura, cierre), duracionCitas);
	
	// Limpiar la tabla de filas anteriores, por si las tuviera (caso de refrescar la vista)
	tabla.getItems().clear();
	
	// Iterar sobre el número de filas obtenido
	for (int i = 0; i < numeroFilas; i++) {
	    
	    // Comprobar que la tabla tiene columnas
	    if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(tabla.getColumns()))) {
		
		// Añadir la fila a la tabla
		tabla.getItems().addAll(generarFila(i, tabla, listaDesplegable, recogedorFecha));
		
	    }

	}
	
    }
    
    /**
     * <p>Método que genera los valores de una fila de la tabla.</p>
     * 
     * @param numeroFila {@link Integer} número de la fila
     * @param tabla {@link TableView}<{@link List}<{@link String}>> tabla donde se va a generar la columna
     * @param listaDesplegable {@link ComboBox}<{@link SanitariosModelo}> lista desplegable con un listado de sanitarios
     * @param recogedorFecha {@link DatePicker} control específico para recofer fechas
     * 
     * @return {@link List}<{@link String}> listado de valores de la fila
     */
    private List<String> generarFila(
	    Integer numeroFila, 
	    TableView<List<String>> tabla, 
	    ComboBox<SanitariosModelo> listaDesplegable, 
	    DatePicker recogedorFecha) {

	// Inicializar el array con los valores de la fila que se va a retornar al final del método
	List<String> valoresFila = new ArrayList<>();
	
	// Primera columna con las horas de las citas
	String hora = UtilidadesConversores.convertirHoraCadena(apertura.plusMinutes(UtilidadesConversores.convertirEnteroLong(numeroFila * duracionCitas)));
	valoresFila.add(hora);
	
	// Iterar sobre el resto de columnas
	for(int i = 1; i < tabla.getColumns().size(); i++) {
	    
	    // Buscar la cita correspondiente
	    CitasModelo cita = citasService.encontrarCitaPorFechaHoraSanitario(
		    recogedorFecha.getValue(), 
		    UtilidadesConversores.convertirCadenaHora(hora), 
		    listaDesplegable.getValue().getDniSanitario());
	    
	    valoresFila.add(cita != null ? cita.toString() : Constantes.LIBRE);
	    valoresFila.add(cita != null ? cita.getColorTratamiento() : Constantes.COLOR_BLANCO_HEXADECIMAL);
	    
	}
	
	// Retornar el array con los valores de la fila
	return valoresFila;
	
    }
    
    /**
     * <p>Guardar el modelo seleccionado.</p>
     * 
     * <p>Adicionalmente, asigna el modelo seleccionado al atributo que se utiliza en los métodos de dichos botones.</p>
     * 
     * @param modelo {@link List}<{@link String}> listado de valores de la fila
     */
    private void seleccionarFila(List<String> fila) {
	
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(fila)) && Boolean.FALSE.equals(Utilidades.compararCadenas(fila.get(1), Constantes.LIBRE))) {
	    modeloSeleccionado = citasService.encontrarCita(UtilidadesConversores.convertirCadenaEntero(fila.get(1).split(Constantes.GUION_ESPACIADO)[0]));
	    cargarDetalle();
	} else {
	    limpiarDetalle();
	}
	
    }
    
    /**
     * <p>Guardar el modelo seleccionado.</p>
     * 
     * <p>Adicionalmente, asigna el modelo seleccionado al atributo que se utiliza en los métodos de dichos botones.</p>
     * 
     * @param fila {@link List}<{@link String}> listado de valores de la fila
     * @param evento {@link MouseEvent} asociado al escuchador del doble click
     */
    private void editarCita(List<String> fila, MouseEvent evento) {
	
	// Comprobar que los parámetros de entrada NO son nulos
	if(Boolean.TRUE.equals(Utilidades.comprobarColeccion(fila)) && evento != null) {
	    
	    // Controlador de la vista donde se editan las citas
	    CitasEdicionController citasEdicionController;
	    
	    // Comprobar si se trata de una fila con o sin cita
	    if(Boolean.TRUE.equals(Utilidades.compararCadenas(fila.get(1), Constantes.LIBRE))) {
		
		// Es un espacio SIN cita, se procede a dar de alta
		UtilidadesVentanasEmergentes.abrirVentanaEmergente(CitasEdicionView.class, Constantes.CITAS_EDICION_CONTROLLER,
			Accion.EDICION);

		citasEdicionController = (CitasEdicionController) beansComponent
			.obtenerControlador(Constantes.CITAS_EDICION_CONTROLLER);
		CitasModelo modeloAlta = generarModeloAlta(fila, evento);
		citasEdicionController.setModelo(modeloAlta);
		citasEdicionController.initialize();
		
	    } else {
		
		// Es un espacio CON cita, se procede a dar editar
		UtilidadesVentanasEmergentes.abrirVentanaEmergente(CitasEdicionView.class, Constantes.CITAS_EDICION_CONTROLLER,
			Accion.EDICION);

		citasEdicionController = (CitasEdicionController) beansComponent
			.obtenerControlador(Constantes.CITAS_EDICION_CONTROLLER);
		citasEdicionController.setModelo(modeloSeleccionado);
		citasEdicionController.initialize();
		
	    }
	    
	}
	
    }
    
    /**
     * <p>Método que genera un modelo de cita para el una alta desde la agenda cuando se ha hecho doble clic sobre una fila sin cita.</p>
     * 
     * @param fila {@link List}<{@link String}> listado de valores de la fila
     * @param evento {@link MouseEvent} asociado al escuchador del doble click
     * 
     * @return {@link CitasModelo} modelo de citas para un alta desde la agenda
     */
    private CitasModelo generarModeloAlta(List<String> fila, MouseEvent evento) {
	
	// Inicializar el modelo que se va retornar al final del método
	CitasModelo modeloAlta = new CitasModelo();
	
	// Comprobar que el evento pasado como parámetro NO es nulo
	if(evento != null) {
	    
	    // Obtener el identificador numérico de la tabla donde se ha hecho doble click
	    String identificador = Utilidades.comprobarCadenaNula(((TableView<?>) evento.getSource()).getId())
		    .replaceAll(Constantes.PATRON_TODO_MENOS_ENTEROS.toString(), Constantes.CADENA_VACIA);

	    // Establecer los valores por defecto en el alta dependiendo de la tabla de la agenda que se haya pulsado
	    switch (identificador) {
	    case Constantes.NUMERO_UNO:
		modeloAlta.setDniSanitario(sanitarioFiltro1ComboBox.getValue().getDniSanitario());
		modeloAlta.setNombreSanitario(sanitarioFiltro1ComboBox.getValue().toString());
		modeloAlta.setFecha(fechaFiltro1DatePicker.getValue());
		break;
	    case Constantes.NUMERO_DOS:
		modeloAlta.setDniSanitario(sanitarioFiltro2ComboBox.getValue().getDniSanitario());
		modeloAlta.setNombreSanitario(sanitarioFiltro2ComboBox.getValue().toString());
		modeloAlta.setFecha(fechaFiltro2DatePicker.getValue());
		break;
	    case Constantes.NUMERO_TRES:
		modeloAlta.setDniSanitario(sanitarioFiltro3ComboBox.getValue().getDniSanitario());
		modeloAlta.setNombreSanitario(sanitarioFiltro3ComboBox.getValue().toString());
		modeloAlta.setFecha(fechaFiltro3DatePicker.getValue());
		break;
	    default:
		break;
	    }
	    
	    // Establecer la hora inicial de la cita en el alta
	    modeloAlta.setHoraDesde(UtilidadesConversores.convertirCadenaHora(fila.get(0)));
	    
	}
	
	// Retornar el modelo de alta con los atributos necesarios establecidos
	return modeloAlta;
	
    }
    
    /**
     * <p>En este método se procederá a cargar todos los controles del detalle de la vista.</p>
     */
    public void cargarDetalle() {
	
	AgendaEdicionController agendaEdicionController = (AgendaEdicionController) beansComponent.obtenerControlador(Constantes.AGENDA_EDICION_CONTROLLER);
	agendaEdicionController.setModeloSeleccionado(modeloSeleccionado);
	agendaEdicionController.cargarDetalle();
	
    }
    
    /**
     * <p>En este método se procederá a limpiar todos los controles del detalle de la vista de la agenda.</p>
     */
    public void limpiarDetalle() {
	
	AgendaEdicionController agendaEdicionController = (AgendaEdicionController) beansComponent.obtenerControlador(Constantes.AGENDA_EDICION_CONTROLLER);
	agendaEdicionController.limpiarDetalle();
	
    }
    
}
