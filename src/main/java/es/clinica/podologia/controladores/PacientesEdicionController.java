package es.clinica.podologia.controladores;

import java.awt.Desktop;
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
import es.clinica.podologia.servicios.TratamientosService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesConversores;
import es.clinica.podologia.utilidades.UtilidadesVentanasEmergentes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    
    @Value("${pacientes.seleccionar.ficha}")
    private String tituloSelectorFicha;
    
    @Value("${clinica.directorio.inicial}")
    private String directorioInicial;
    
    @Value("${clinica.adjunto.temporal}")
    private String adjuntoTemporal;
    
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
    private Label nombreAdjuntoLabel;
    
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
    private TratamientosService tratamientoService;
    
    // Modelo sobre el que se trabajará la vista
    private PacientesModelo modelo;
    
    // Este atributo indicará si se trata de una inserción o de una modificación
    private Boolean modo;
    
    /**
     * <p>Método que se ejecuta al inicializarse la vista de la edición de Pacientes.</p>
     */
    @FXML
    public void initialize() {
	
	TRAZAS.info("Vista de: " + this.getClass().getName());
	
	modelo = new PacientesModelo();
	
	desasignarAdjunto();
	

	
    }
    
    @FXML
    private void seleccionarAdjunto() {
	
	FileChooser selectorFichero = new FileChooser();
	selectorFichero.setTitle(tituloSelectorFicha);
	selectorFichero.setInitialDirectory(new File(directorioInicial));
	selectorFichero.getExtensionFilters().addAll(new ExtensionFilter("Ficheros PDF", "*.pdf"));
	File archivo = selectorFichero.showOpenDialog(new Stage());

	if (Utilidades.comprobarArchivo(archivo) && Utilidades.comprobarUbicacionArchivo(archivo.getAbsolutePath())) {
	    asignarAdjunto(archivo);
	} else {
	    desasignarAdjunto();
	}
	
    }
    
    /**
     * <p>Método invocado como un evento abrir el adjunto del paciente.</p>
     */
    @FXML
    private void abrirAdjunto() {
	
	if(modelo != null && Utilidades.comprobarArrayByte(modelo.getAdjunto())) {
	    
	    String ubicacionFichero = adjuntoTemporal.concat("prueba.pdf");
	    
	    File archivo = UtilidadesConversores.convertirArrayBytesFichero(modelo.getAdjunto(), new File(ubicacionFichero).getAbsolutePath());
	    
	    try {
		
		// Abrir el fichero .PDF con el programa por defecto instalado en el ordenador

		Process proceso = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + ubicacionFichero);
		proceso.waitFor();

	    } catch (InterruptedException | IOException excepcion) {
		// Error intentando abrir el adjunto
		TRAZAS.error(excepcion.getMessage());
		excepcion.printStackTrace();
		UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
	    }
	    
	}
	
    }
    
    /**
     * <p>Método invocado como un evento para guardar el paciente.</p>
     */
    @FXML
    private void guardarPaciente() {
	
    }
    
    /**
     * <p>Método invocado como un evento para cerrar la ventana emergente.</p>
     */
    @FXML
    private void cancelarPaciente() {
	
	modelo = null;
	
	UtilidadesVentanasEmergentes.cerrarVentanaEmergente();
	
    }
    
    /**
     * <p>Método que asigna el adjunto seleccionado al atributo correspondiente del modelo y actualiza el campo de la vista.</p>
     * 
     * @param archivo {@link File} archivo que se va a adjuntar al modelo
     */
    private void asignarAdjunto(File archivo) {
	nombreAdjuntoLabel.setText(archivo.getName());
	verAdjuntoImageView.setVisible(Boolean.TRUE);
	modelo.setAdjunto(UtilidadesConversores.convertirFicheroArrayBytes(archivo));
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
