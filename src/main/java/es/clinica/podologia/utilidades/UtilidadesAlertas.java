package es.clinica.podologia.utilidades;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * <p>Clase con métodos estáticos para invocar alertas de uso común a lo largo de toda la aplicación.</p>
 * 
 * @author Ignacio Rafael
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
     * <p>Alerta de tipo <b>NONE</b>.</p>
     * 
     * @param mensaje {@link String} cadena de caracteres informativa de la alerta
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     */
    public static Optional<ButtonType> mostrarAlertaPlana(String mensaje) {
	Alert alerta = new Alert(AlertType.NONE, Utilidades.comprobarCadena(mensaje, ""));
	return alerta.showAndWait();
    }
    
    /**
     * <p>Alerta de tipo <b>INFORMATION</b>.</p>
     * 
     * @param mensaje {@link String} cadena de caracteres informativa
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     */
    public static Optional<ButtonType> mostrarAlertaInformativa(String mensaje) {
	Alert alerta = new Alert(AlertType.INFORMATION, Utilidades.comprobarCadena(mensaje, ""));
	return alerta.showAndWait();
    }
    
    /**
     * <p>Alerta de tipo <b>WARNING</b>.</p>
     * 
     * @param mensaje {@link String} cadena de caracteres informativa de la advertencia
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     */
    public static Optional<ButtonType> mostrarAlertaAdvertencia(String mensaje) {
	Alert alerta = new Alert(AlertType.WARNING, Utilidades.comprobarCadena(mensaje, ""));
	return alerta.showAndWait();
    }
    
    /**
     * <p>Alerta de tipo <b>CONFIRMATION</b>.</p>
     * 
     * @param mensaje {@link String} cadena de caracteres informativa de la confirmación
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     */
    public static Optional<ButtonType> mostrarAlertaConfirmacion(String mensaje) {
	Alert alerta = new Alert(AlertType.CONFIRMATION, Utilidades.comprobarCadena(mensaje, ""));
	return alerta.showAndWait();
    }
    
    /**
     * <p>Alerta de tipo <b>ERROR</b>.</p>
     * 
     * @param mensaje {@link String} cadena de caracteres informativa del error
     * 
     * @return {@link Optional} {@link ButtonType} el botón que se ha pulsado en la alerta
     */
    public static Optional<ButtonType> mostrarAlertaError(String mensaje) {
	Alert alerta = new Alert(AlertType.ERROR, Utilidades.comprobarCadena(mensaje, ""));
	return alerta.showAndWait();
    }

}
