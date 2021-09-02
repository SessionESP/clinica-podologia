package es.clinica.podologia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.componentes.BeansComponent;
import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.TratamientosModelo;
import es.clinica.podologia.servicios.TratamientosService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesControles;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * <p>Controlador para los Tratamientos.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class TratamientosEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(TratamientosEdicionController.class);
    
    @Value("${tratamientos.alta.titulo}")
    private String tituloAltaVista;
    
    @Value("${tratamientos.edicion.titulo}")
    private String tituloEdicionVista;
    
    @Value("${tratamientos.alta.guardado.true}")
    private String altaCorrecta;
    
    @Value("${tratamientos.alta.guardado.false}")
    private String altaIncorrecta;
    
    @Value("${tratamientos.modificacion.guardado.true}")
    private String modificacionCorrecta;
    
    @Value("${tratamientos.modificacion.guardado.false}")
    private String modificacionIncorrecta;
    
    @FXML
    private Label tituloLabel;

    @FXML
    private TextField nombreTextField;
    @FXML
    private TextArea descripcionTextArea;
    @FXML
    private ColorPicker colorColorPicker;
    @FXML
    private TextField precioTextField;

    @FXML
    private Button aceptarButton;
    @FXML
    private Button cancelarButton;
    
    @Autowired
    private BeansComponent beansComponent;
    
    @Autowired
    private TratamientosService tratamientoService;
    
    // Modelo sobre el que se trabajará la vista
    private TratamientosModelo modelo;
    
    // Este atributo indicará si se trata de una inserción o de una modificación
    private Boolean modo;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de tratamientos.</p>
     */
    @FXML
    public void initialize() {
	
	// Comprobar si el modelo es nulo
	if(modelo != null) {
	    
	    // En caso de que NO sea nulo, comprobar si existe
	    modo = tratamientoService.comprobarExistenciaTratamiento(modelo.getIdTratamiento());
	    
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
	
	// Cargar los formateadores de todos los controles de la vista
	cargarFormateadores();
	
    }
    
    /**
     * <p>Método invocado como un evento para guardar el tratamiento.</p>
     */
    @FXML
    private void guardarTratamiento() {
	
	try {
	    
	    // Comprobar si el modelo es nulo
	    if (modelo != null) {
		
		modelo.setNombre(nombreTextField.getText());
		modelo.setDescripcion(descripcionTextArea.getText());
		modelo.setColor(UtilidadesConversores.convertirColorHexadecimal(colorColorPicker.getValue()));
		modelo.setPrecio(UtilidadesConversores.convertirCadenaDecimal(precioTextField.getText()));

		// Guardar el tratamiento
		Boolean resultado = tratamientoService.insertarActualizarTratamiento(modelo);

		// Comprobar si se ha realizaco correctamente el guardado del tratamiento
		if (Boolean.TRUE.equals(resultado)) {

		    // El tratamiento se ha guardado bien
		    UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? modificacionCorrecta : altaCorrecta);
		    
		    TratamientosListadoController tratamientosListadoController = (TratamientosListadoController) beansComponent.obtenerControlador(Constantes.TRATAMIENTOS_LISTADO_CONTROLLER);
		    tratamientosListadoController.initialize();
		    cancelarTratamiento();

		} else {

		    // El tratamiento no se ha guardado
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
    private void cancelarTratamiento() {
	
	modelo = null;
	
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();

    }
    
    /**
     * <p>Método donde se realizarán todos los preparativos para inicializar la vista para un alta nueva.</p>
     */
    private void prepararAlta() {
	
	// Inicializar el objeto modelo
	modelo = new TratamientosModelo();
	
	// Aplicar el título de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloAltaVista, ""));
	
	// Etiqueta con el título del formulario
	tituloLabel.setText(tituloAltaVista);
	
	nombreTextField.clear();
	descripcionTextArea.clear();
	colorColorPicker.setValue(Color.WHITE);
	precioTextField.clear();
	
    }
    
    /**
     * <p>Método donde se realizarán todos los preparativos para inicializar la vista para una modificación.</p>
     */
    private void prepararModificacion() {
	
	// Aplicar el título de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloEdicionVista, ""));
	
	// Etiqueta con el título del formulario
	tituloLabel.setText(tituloAltaVista);
	
	nombreTextField.setText(modelo.getNombre());
	descripcionTextArea.setText(modelo.getDescripcion());
	colorColorPicker.setValue(UtilidadesConversores.convertirHexadecimalColor(modelo.getColor()));
	precioTextField.setText(UtilidadesConversores.convertirDecimalCadena(modelo.getPrecio()));
	
    }
    
    /**
     * <p>En este método se prepararán los formateadores de todos los controles de la vista.</p>
     */
    private void cargarFormateadores() {
	
	nombreTextField.setTextFormatter(UtilidadesControles.formateadorSinPatron(Constantes.LIMITE_50));
	descripcionTextArea.setTextFormatter(UtilidadesControles.formateadorSinPatron(Constantes.LIMITE_100));
	precioTextField.setTextFormatter(UtilidadesControles.formateadorConPatron(Constantes.PATRON_NUMEROS_ENTEROS, Constantes.LIMITE_3));
	
    }

    public TratamientosModelo getModelo() {
        return modelo;
    }

    public void setModelo(TratamientosModelo modelo) {
        this.modelo = modelo;
    }

}
