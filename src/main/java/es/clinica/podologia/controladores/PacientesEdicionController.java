package es.clinica.podologia.controladores;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.componentes.BeansComponent;
import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.PacientesModelo;
import es.clinica.podologia.servicios.PacientesService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * <p>Controlador para la edición de Pacientes.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class PacientesEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(PacientesEdicionController.class);
    
    @Value("${pacientes.alta.titulo}")
    private String tituloAltaVista;
    
    @Value("${pacientes.edicion.titulo}")
    private String tituloEdicionVista;
    
    @Value("${pacientes.alta.guardado.true}")
    private String altaCorrecta;
    
    @Value("${pacientes.alta.guardado.false}")
    private String altaIncorrecta;
    
    @Value("${pacientes.modificacion.guardado.true}")
    private String modificacionCorrecta;
    
    @Value("${pacientes.modificacion.guardado.false}")
    private String modificacionIncorrecta;
    
    @Value("${pacientes.seleccionar.ficha}")
    private String tituloSelectorFicha;
    
    @Value("${clinica.directorio.inicial}")
    private String directorioInicial;
    
    @Value("${clinica.adjunto.temporal}")
    private String adjuntoTemporal;
    
    @Value("${pacientes.abrir.sinadjunto}")
    private String sinAdjunto;
    
    @FXML
    private Label tituloLabel;

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
    private DatePicker fechaNacimientoDatePicker;
    
    @FXML
    private Label nombreAdjuntoLabel;
    @FXML
    private Label edadLabel;
    
    @FXML
    private Button aceptarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Button seleccionarButton;
    @FXML
    private Button abrirButton;
    
    @FXML
    private ImageView verAdjuntoImageView;
    
    @Autowired
    private BeansComponent beansComponent;
    
    @Autowired
    private PacientesService pacienteService;
    
    // Modelo sobre el que se trabajará la vista
    private PacientesModelo modelo;
    
    // Este atributo indicará si se trata de una inserción o de una modificación
    private Boolean modo;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Pacientes.</p>
     */
    @FXML
    public void initialize() {
	
	// Comprobar si el modelo es nulo
	if(modelo != null) {
	    
	    // En caso de que NO sea nulo, comprobar si existe
	    modo = pacienteService.comprobarExistenciaPaciente(modelo.getIdPaciente());
	    
	    if(Boolean.TRUE.equals(modo)) {
		
		// Si existe, se trarta de una actualización
		prepararModificacion();
		
	    } else {
		
		// Si NO existe, se trarta de una inserción
		prepararAlta();
	    }
	    
	} else {
	    
	    // Si el modelo es nulo, se trata de una inserción
	    prepararAlta();
	    
	}
	
	// Cargar los formateadores de cada una de las cajas de texto
	cargarFormateadores();

	// Carga los Escuchadores de los controles de la vista
	cargarEscuchadores();
	
    }
    
    @FXML
    private void seleccionarAdjunto() {
	
	// Instanciar el selector de fiecheros
	FileChooser selectorFichero = new FileChooser();
	
	// Establecer el título del selector de ficheros
	selectorFichero.setTitle(tituloSelectorFicha);
	
	// Establecer el directorio inicial del selector de ficheros
	selectorFichero.setInitialDirectory(new File(directorioInicial));
	
	// Establecer el filtro de ficheros para que únicamente permita archivos .PDF
	selectorFichero.getExtensionFilters().addAll(new ExtensionFilter("Archivos PDF", "*.pdf"));
	
	// Abrir el selector de ficheros
	File archivo = selectorFichero.showOpenDialog(new Stage());

	// Comprobar si se ha seleccionado un archivo válido
	if (Utilidades.comprobarArchivo(archivo) && Utilidades.comprobarUbicacionArchivo(archivo.getAbsolutePath())) {
	    asignarAdjunto(archivo);
	} else {
	    desasignarAdjunto();
	}
	
    }
    
    /**
     * <p>Método invocado como un evento para abrir el adjunto del paciente.</p>
     */
    @FXML
    private void abrirAdjunto() {
	
	// Comprobar que el modelo y su adjunto NO son nulos
	if(modelo != null && Utilidades.comprobarArrayByte(modelo.getAdjunto())) {
	    
	    // Ubicación temporal y nombre del archivo para poder ser abierto por el lector PDF del sistema
	    String ubicacionFichero = adjuntoTemporal.concat(modelo.getNombreAdjunto());
	    
	    // Convertir el array de bytes del modelo en un fichero .PDF
	    File archivo = UtilidadesConversores.convertirArrayBytesFichero(modelo.getAdjunto(), new File(ubicacionFichero).getAbsolutePath());
	    
	    try {
		
		// Abrir el fichero .PDF con el programa por defecto instalado en el ordenador
		Process proceso = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + archivo.getAbsolutePath());
		proceso.waitFor();

	    } catch (InterruptedException | IOException excepcion) {
		// Error intentando abrir el adjunto
		TRAZAS.error(excepcion.getMessage());
		excepcion.printStackTrace();
		UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
		Thread.currentThread().interrupt();
	    }
	    
	} else {
	    UtilidadesAlertas.mostrarAlertaInformativa(sinAdjunto);
	}
	
    }
    
    /**
     * <p>Método invocado como un evento para guardar el paciente.</p>
     */
    @FXML
    private void guardarPaciente() {
	
	try {
	    
	    // Comprobar si el modelo es nulo
	    if (modelo != null) {
		
		// Setear los valores de las cajas de texto en los atributos del modelo
		modelo.setDniPaciente(dniPacienteTextField.getText());
		modelo.setNombre(nombreTextField.getText());
		modelo.setFechaNacimiento(fechaNacimientoDatePicker.getValue());
		modelo.setApellidos(apellidosTextField.getText());
		modelo.setDireccion(direccionTextField.getText());
		modelo.setTelefono(telefonoTextField.getText());
		
		// En el caso del adjunto, se gestiona en otros métodos de manera independiente

		// Guardar el tratamiento
		Boolean resultado = pacienteService.insertarActualizarPaciente(modelo);

		// Comprobar si se ha realizaco correctamente el guardado del paciente
		if (Boolean.TRUE.equals(resultado)) {

		    // El paciente se ha guardado bien
		    UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? modificacionCorrecta : altaCorrecta);
		    
		    PacientesListadoController pacientesListadoController = (PacientesListadoController) beansComponent.obtenerControlador(Constantes.PACIENTES_LISTADO_CONTROLLER);
		    pacientesListadoController.initialize();
		    cancelarPaciente();

		} else {

		    // El paciente no se ha guardado
		    UtilidadesAlertas.mostrarAlertaError(Boolean.TRUE.equals(modo) ? modificacionIncorrecta : altaIncorrecta);

		}

	    }
	    
	} catch (Exception excepcion) {
	    
	    // Error al intentar guardar el tratamiento
	    TRAZAS.error(excepcion.getMessage());
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
	    
	}
	
    }
    
    /**
     * <p>Método invocado como un evento para cerrar la ventana emergente.</p>
     */
    @FXML
    private void cancelarPaciente() {
	
	// Comprobar que el modelo NO es nulo
	if (modelo != null) {
	    
	    // Eliminar el archivo temporal, si es que hubiese llegado a generarse
	    UtilidadesConversores.eliminarArchivo(UtilidadesConversores.convertirArrayBytesFichero(modelo.getAdjunto(),
		    new File(adjuntoTemporal.concat(Utilidades.comprobarCadena(modelo.getNombreAdjunto(), Constantes.CADENA_VACIA))).getAbsolutePath()));
	    
	}
	
	// Limpiar el modelo
	modelo = null;
	
	// Cerrar la ventana emergente
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();
	
    }
    
    /**
     * <p>Método donde se realizarán todos los preparativos para inicializar la vista para un alta nueva.</p>
     */
    private void prepararAlta() {
	
	// Inicializar el objeto modelo
	modelo = new PacientesModelo();
	
	// Aplicar el título de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloAltaVista, ""));
	
	// Etiqueta con el título del formulario
	tituloLabel.setText(tituloAltaVista);
	
	// Habilitar el cuadro de texto con el DNI, que es la clave primaria de la tabla
	dniPacienteTextField.setDisable(Boolean.FALSE);
	
	// Inicializar todas las cajas de texto vacías
	dniPacienteTextField.clear();
	nombreTextField.clear();
	apellidosTextField.clear();
	fechaNacimientoDatePicker.setValue(null);
	direccionTextField.clear();
	telefonoTextField.clear();
	
	// Limpiar el atributo del archivo adjunto
	desasignarAdjunto();
	
    }
    
    /**
     * <p>Método donde se realizarán todos los preparativos para inicializar la vista para una modificación.</p>
     */
    private void prepararModificacion() {
	
	// Aplicar el título de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloEdicionVista, ""));
	
	// Etiqueta con el título del formulario
	tituloLabel.setText(tituloEdicionVista);
	
	// Inicializar todas las cajas de texto con los valores de los atributos del modelo
	dniPacienteTextField.setText(modelo.getDniPaciente());
	nombreTextField.setText(modelo.getNombre());
	apellidosTextField.setText(modelo.getApellidos());
	fechaNacimientoDatePicker.setValue(modelo.getFechaNacimiento());
	edadLabel.setText(UtilidadesConversores.imprimirEdad(modelo.getFechaNacimiento()));
	direccionTextField.setText(modelo.getDireccion());
	telefonoTextField.setText(modelo.getTelefono());
	
	// Comprobar si el modelo actual tiene un archivo adjunto
	if(Boolean.TRUE.equals(Utilidades.comprobarArrayByte(modelo.getAdjunto()))) {
	    
	    // Ubicación temporal y nombre del archivo para poder ser abierto por el lector PDF del sistema
	    String ubicacionFichero = adjuntoTemporal.concat(modelo.getNombreAdjunto());
	    asignarAdjunto(UtilidadesConversores.convertirArrayBytesFichero(modelo.getAdjunto(), new File(ubicacionFichero).getAbsolutePath()));
	    
	} else {
	    desasignarAdjunto();
	}
	
    }
    
    /**
     * <p>Método que asigna el adjunto seleccionado al atributo correspondiente del modelo y actualiza el campo de la vista.</p>
     * 
     * @param archivo {@link File} archivo que se va a adjuntar al modelo
     */
    private void asignarAdjunto(File archivo) {
	nombreAdjuntoLabel.setText(archivo.getName());
	verAdjuntoImageView.setVisible(Boolean.TRUE);
	modelo.setNombreAdjunto(archivo.getName());
	modelo.setAdjunto(UtilidadesConversores.convertirFicheroArrayBytes(archivo));
    }
    
    private void cargarFormateadores() {
//	dniPacienteTextField.setTextFormatter(UtilidadesControles.formateador(Constantes.PATRON_DNI, 9));
    }
    
    private void cargarEscuchadores() {
	
	// Recalcular la edad cada vez que cambia la fecha de nacimiento
	fechaNacimientoDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> edadLabel.setText(UtilidadesConversores.imprimirEdad(newValue)));
	
    }
    
    /**
     * <p>Método que desasigna el adjunto seleccionado al atributo correspondiente del modelo y actualiza el campo de la vista.</p>
     */
    private void desasignarAdjunto() {
	nombreAdjuntoLabel.setText(Constantes.CADENA_VACIA);
	verAdjuntoImageView.setVisible(Boolean.FALSE);
	modelo.setAdjunto(Constantes.CADENA_VACIA.getBytes());
    }
    
    public PacientesModelo getModelo() {
        return modelo;
    }

    public void setModelo(PacientesModelo modelo) {
        this.modelo = modelo;
    }

}
