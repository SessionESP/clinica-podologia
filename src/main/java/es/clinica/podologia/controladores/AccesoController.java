package es.clinica.podologia.controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.constantes.Accion;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.javafx.jfxsupport.GUIState;
import es.clinica.podologia.servicios.AccesoService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesNavegacion;
import es.clinica.podologia.vistas.CitasListadoView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * <p>Controlador para el Acceso.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class AccesoController {
    
    @Value("${acceso.consulta.titulo}")
    private String tituloVista;

    @FXML
    private TextField usuarioTextField;

    @FXML
    private PasswordField contrasenaPasswordField;

    @FXML
    private Button aceptarButton;
    
    @FXML
    private Button salirButton;

    @Autowired
    private AccesoService accesoService;
    
    @FXML
    public void initialize() {
	
	// Aplicar el título de la vista
        GUIState.getStage().setTitle(Utilidades.comprobarCadena(tituloVista, ""));
        
    }

    @FXML
    private void autenticarUsuario() {

	// Realizar la consulta en la tabla de la base de datos
	Boolean autenticado = accesoService.autenticarUsuario(usuarioTextField.getText(), contrasenaPasswordField.getText());

	// Generar la alerta correspondiente dependiendo del resultado de la consulta
	Optional<ButtonType> alerta = Boolean.TRUE.equals(autenticado) ? UtilidadesAlertas.mostrarAlertaConfirmacion("Acceso concedido") : UtilidadesAlertas.mostrarAlertaError("Acceso denegado");

	// Comprobar sii la conulta ha retornado 'true' y el usuario ha pulsado el botón 'aceptar' en la alerta de confirmación
	if (Boolean.TRUE.equals(autenticado) && Boolean.TRUE.equals(alerta.isPresent()) && alerta.get() == ButtonType.OK) {

	    // Mostrar la vista principal de la aplicación
	    UtilidadesNavegacion.mostrarVista(CitasListadoView.class, "CitasListadoController", Accion.CONSULTA);
	}
    }
    
    @FXML
    private void salir() {
	
	Optional<ButtonType> alerta = UtilidadesAlertas.mostrarAlerta(
		AlertType.WARNING, 
		"¿Deseas cerrar la aplicación?", 
		ButtonType.YES, 
		ButtonType.NO);
	
	if(alerta.get() == ButtonType.YES) {
	    Platform.exit();
	}

    }
    
    @FXML
    private void pulsarIntro(KeyEvent evento) {

	// Comprobar que se ha pulsado la tecla "Intro"
	if (evento.getCode() == KeyCode.ENTER) {
	    
	    // Autenticar el usuario como si se hubiera pulsado el botón Aceptar de la pantalla
	    autenticarUsuario();

	}

    }

}
