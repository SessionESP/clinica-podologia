package es.clinica.podologia.utilidades;

import java.util.Optional;

import es.clinica.podologia.JavaFxApplicationSupport;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * <p>Clase con métodos estáticos para invocar alertas de uso común a lo largo de toda la aplicación.</p>
 * 
 * @author Ignacio Rafael
 * 
 * @see Alert
 *
 */
public class UtilidadesAlertas {
    
    /**
     * <p>Constructor privado vacío.</p>
     */
    private UtilidadesAlertas() {
	throw new IllegalStateException("Constructor privado de la clase de utilidades de alertas.");
    }
    
    /**
     * <p>Alerta configurable.</p>
     * 
     * @param tipoAlerta {@link AlertType} tipo de alerta que se quiere generar
     * @param mensaje {@link String} cadena de caracteres con la información de la alerta
     * @param botones {@link ButtonType} botones con los que se quiere que se abra la alerta
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     */
    public static Optional<ButtonType> mostrarAlerta(AlertType tipoAlerta, String mensaje, ButtonType... botones) {
	Alert alerta = new Alert(tipoAlerta, Utilidades.comprobarCadena(mensaje, ""), botones);
	((Stage) alerta.getDialogPane().getScene().getWindow()).getIcons().addAll(JavaFxApplicationSupport.getIconos());
	return alerta.showAndWait();
    }
    
    /**
     * <p>Alerta de tipo <b>NONE</b>.</p>
     * 
     * @param mensaje {@link String} cadena de caracteres informativa de la alerta
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     * 
     * @see UtilidadesAlertas#mostrarAlerta(AlertType, String, ButtonType...)
     */
    public static Optional<ButtonType> mostrarAlertaPlana(String mensaje) {
	Alert alerta = new Alert(AlertType.NONE, Utilidades.comprobarCadena(mensaje, ""));
	((Stage) alerta.getDialogPane().getScene().getWindow()).getIcons().addAll(JavaFxApplicationSupport.getIconos());
	return alerta.showAndWait();
    }
    
    /**
     * <p>Alerta de tipo <b>INFORMATION</b>.</p>
     * 
     * @param mensaje {@link String} cadena de caracteres informativa
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     * 
     * @see UtilidadesAlertas#mostrarAlerta(AlertType, String, ButtonType...)
     */
    public static Optional<ButtonType> mostrarAlertaInformativa(String mensaje) {
	Alert alerta = new Alert(AlertType.INFORMATION, Utilidades.comprobarCadena(mensaje, ""));
	((Stage) alerta.getDialogPane().getScene().getWindow()).getIcons().addAll(JavaFxApplicationSupport.getIconos());
	return alerta.showAndWait();
    }
    
    /**
     * <p>Alerta de tipo <b>WARNING</b>.</p>
     * 
     * @param mensaje {@link String} cadena de caracteres informativa de la advertencia
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     * 
     * @see UtilidadesAlertas#mostrarAlerta(AlertType, String, ButtonType...)
     */
    public static Optional<ButtonType> mostrarAlertaAdvertencia(String mensaje) {
	Alert alerta = new Alert(AlertType.WARNING, Utilidades.comprobarCadena(mensaje, ""));
	((Stage) alerta.getDialogPane().getScene().getWindow()).getIcons().addAll(JavaFxApplicationSupport.getIconos());
	return alerta.showAndWait();
    }
    
    /**
     * <p>Alerta de tipo <b>CONFIRMATION</b>.</p>
     * 
     * @param mensaje {@link String} cadena de caracteres informativa de la confirmación
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     * 
     * @see UtilidadesAlertas#mostrarAlerta(AlertType, String, ButtonType...)
     */
    public static Optional<ButtonType> mostrarAlertaConfirmacion(String mensaje) {
	Alert alerta = new Alert(AlertType.CONFIRMATION, Utilidades.comprobarCadena(mensaje, ""));
	((Stage) alerta.getDialogPane().getScene().getWindow()).getIcons().addAll(JavaFxApplicationSupport.getIconos());
	return alerta.showAndWait();
    }
    
    /**
     * <p>Alerta de tipo <b>ERROR</b>.</p>
     * 
     * @param mensaje {@link String} cadena de caracteres informativa del error
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     * 
     * @see UtilidadesAlertas#mostrarAlerta(AlertType, String, ButtonType...)
     */
    public static Optional<ButtonType> mostrarAlertaError(String mensaje) {
	Alert alerta = new Alert(AlertType.ERROR, Utilidades.comprobarCadena(mensaje, ""));
	((Stage) alerta.getDialogPane().getScene().getWindow()).getIcons().addAll(JavaFxApplicationSupport.getIconos());
	return alerta.showAndWait();
    }

}
