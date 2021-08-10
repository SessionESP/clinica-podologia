package es.clinica.podologia.controladores;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.clinica.podologia.constantes.Accion;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.servicios.AccesoService;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesControles;
import es.clinica.podologia.utilidades.UtilidadesNavegacion;
import es.clinica.podologia.vistas.PrincipalEdicionView;
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
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(AccesoController.class);

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
        // Método donde se realizan las operaciones necesarias para la inicialización la vista
	TRAZAS.info("Inicialización de la aplicación desde la vista de acceso.");
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
	    UtilidadesNavegacion.mostrarVista(PrincipalEdicionView.class, "PrincipalEdicionController", Accion.EDICION);
	}
    }
    
    @FXML
    private void salir() {
	
	// Alerta que pide confirmación para salir realmente de la aplicación
	Optional<ButtonType> alerta = UtilidadesAlertas.mostrarAlerta(
		AlertType.WARNING, 
		"¿Deseas cerrar la aplicación?", 
		ButtonType.YES, 
		ButtonType.NO);
	
	// Si se ha pulsado el botón afirmativo, salir de la aplicación
	if(alerta.isPresent() && alerta.get() == ButtonType.YES) {
	    Platform.exit();
	}

    }
    
    @FXML
    private void pulsarIntro(KeyEvent evento) {

	// Comprobar que se ha pulsado la tecla "Intro"
	if (Boolean.TRUE.equals(UtilidadesControles.pulsarTecla(evento, KeyCode.ENTER))) {
	    
	    // Autenticar el usuario como si se hubiera pulsado el botón Aceptar de la pantalla
	    autenticarUsuario();

	}

    }

}
