package es.clinica.podologia.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.TratamientosModelo;
import es.clinica.podologia.servicios.TratamientosService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    
    @FXML
    private Label tituloLabel;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextArea descripcionTextArea;

    @FXML
    private Button aceptarButton;
    
    @FXML
    private Button cancelarButton;
    
    @Autowired
    private TratamientosService tratamientoService;
    
    // Modelo sobre el que se trabajará la vista
    private TratamientosModelo modelo;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de tratamientos.</p>
     */
    @FXML
    public void initialize() {
	
	// Si el modelo es nulo, se trata de una nueva alta, en caso contrario es una modificación
	if(modelo == null) {
	    prepararAlta();
	} else {
	    prepararModificacion();
	}
	

        
    }
    
    /**
     * <p>Método invocado como un evento para guardar el tratamiento.</p>
     */
    @FXML
    private void guardarTratamiento() {
	
	// Comprobar si el modelo es nulo
	if(modelo != null) {
	    
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
	
    }
    
    /**
     * <p>Método donde se realizarán todos los preparativos para inicializar la vista para una modificación.</p>
     */
    private void prepararModificacion() {
	
	// Aplicar el título de la vista
	UtilidadesVentanasEmergentes.getDialogStage().setTitle(Utilidades.comprobarCadena(tituloEdicionVista, ""));
	
	// Etiqueta con el título del formulario
	tituloLabel.setText(tituloAltaVista);
	
    }

    public TratamientosModelo getModelo() {
        return modelo;
    }

    public void setModelo(TratamientosModelo modelo) {
        this.modelo = modelo;
    }

}
