package es.clinica.podologia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.componentes.BeansComponent;
import es.clinica.podologia.componentes.ValidacionesComponent;
import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.SanitariosModelo;
import es.clinica.podologia.servicios.SanitariosService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesControles;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para la edición de Sanitarios.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class SanitariosEdicionController {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(SanitariosEdicionController.class);
    
    @Value("${sanitarios.alta.titulo}")
    private String tituloAltaVista;
    
    @Value("${sanitarios.edicion.titulo}")
    private String tituloEdicionVista;
    
    @Value("${sanitarios.alta.guardado.true}")
    private String altaCorrecta;
    
    @Value("${sanitarios.alta.guardado.false}")
    private String altaIncorrecta;
    
    @Value("${sanitarios.modificacion.guardado.true}")
    private String modificacionCorrecta;
    
    @Value("${sanitarios.modificacion.guardado.false}")
    private String modificacionIncorrecta;
    
    @Value("${sanitarios.error.dni.vacio}")
    private String errorDniSanitarioVacio;
    
    
    @FXML
    private Label tituloLabel;

    @FXML
    private TextField dniSanitarioTextField;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField especialidadTextField;
    
    @FXML
    private Button aceptarButton;
    
    @FXML
    private Button cancelarButton;
    
    @Autowired
    private BeansComponent beansComponent;
    
    @Autowired
    private ValidacionesComponent validacionesComponent;
    
    @Autowired
    private SanitariosService sanitarioService;
    
    // Modelo sobre el que se trabajará la vista
    private SanitariosModelo modelo;
    
    // Este atributo indicará si se trata de una inserción o de una modificación
    private Boolean modo;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Sanitarios.</p>
     */
    @FXML
    public void initialize() {
	
	// Comprobar si el modelo es nulo
	if(modelo != null) {
	    
	    // En caso de que NO sea nulo, comprobar si existe
	    modo = sanitarioService.comprobarExistenciaSanitario(modelo.getIdSanitario());
	    
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
	
	// Cargar los validadores de cada una de las cajas de texto que lo requieran
	cargarValidadores();
	
    }
    
    /**
     * <p>Método invocado como un evento para guardar el sanitario.</p>
     */
    @FXML
    private void guardarSanitario() {
	
	try {
	    
	    // Comprobar si el modelo es nulo
	    if (modelo != null) {
		
		// Setear los valores de las cajas de texto en los atributos del modelo
		modelo.setDniSanitario(dniSanitarioTextField.getText());
		modelo.setNombre(nombreTextField.getText());
		modelo.setApellidos(apellidosTextField.getText());
		modelo.setEspecialidad(especialidadTextField.getText());

		// Guardar el tratamiento
		Boolean resultado = sanitarioService.insertarActualizarSanitario(modelo);

		// Comprobar si se ha realizaco correctamente el guardado del sanitario
		if (Boolean.TRUE.equals(resultado)) {

		    // El sanitario se ha guardado bien
		    UtilidadesAlertas.mostrarAlertaInformativa(Boolean.TRUE.equals(modo) ? modificacionCorrecta : altaCorrecta);
		    
		    SanitariosListadoController sanitariosListadoController = (SanitariosListadoController) beansComponent.obtenerControlador(Constantes.SANITARIOS_LISTADO_CONTROLLER);
		    sanitariosListadoController.initialize();
		    cancelarSanitario();

		} else {

		    // El sanitario no se ha guardado
		    UtilidadesAlertas.mostrarAlertaError(Boolean.TRUE.equals(modo) ? modificacionIncorrecta : altaIncorrecta);

		}

	    }
	    
	} catch (Exception excepcion) {
	    
	    // Error al intentar guardar el sanitario
	    validacionesComponent.visualizarError(excepcion, TRAZAS);
	    
	}
	
    }
    
    /**
     * <p>Método invocado como un evento para cerrar la ventana emergente.</p>
     */
    @FXML
    private void cancelarSanitario() {
	
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
	modelo = new SanitariosModelo();
	
	// Aplicar el título de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloAltaVista, ""));
	
	// Etiqueta con el título del formulario
	tituloLabel.setText(tituloAltaVista);
	
	// Habilitar el cuadro de texto con el DNI, que es la clave primaria de la tabla
	dniSanitarioTextField.setDisable(Boolean.FALSE);
	
	// Inicializar todas las cajas de texto vacías
	dniSanitarioTextField.clear();
	nombreTextField.clear();
	apellidosTextField.clear();
	especialidadTextField.clear();
	
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
	dniSanitarioTextField.setText(modelo.getDniSanitario());
	nombreTextField.setText(modelo.getNombre());
	apellidosTextField.setText(modelo.getApellidos());
	especialidadTextField.setText(modelo.getEspecialidad());
	
    }
    
    
    /**
     * <p>En este método se prepararán los formateadores de todos los controles de la vista.</p>
     */
    private void cargarFormateadores() {
	
	dniSanitarioTextField.setTextFormatter(UtilidadesControles.cargarFormateadorSinPatron(Constantes.LIMITE_20));
	nombreTextField.setTextFormatter(UtilidadesControles.cargarFormateadorSinPatron(Constantes.LIMITE_50));
	apellidosTextField.setTextFormatter(UtilidadesControles.cargarFormateadorSinPatron(Constantes.LIMITE_50));
	especialidadTextField.setTextFormatter(UtilidadesControles.cargarFormateadorSinPatron(Constantes.LIMITE_50));
	
    }
    
    /**
     * <p>Método donde se van a cargar los validadores de cada un ode los campos de la vista.</p>
     */
    private void cargarValidadores() {
	
	
	UtilidadesControles.cargarValidadorNulo(dniSanitarioTextField, errorDniSanitarioVacio);
	
    }
    
    public SanitariosModelo getModelo() {
        return modelo;
    }

    public void setModelo(SanitariosModelo modelo) {
        this.modelo = modelo;
    }

}
