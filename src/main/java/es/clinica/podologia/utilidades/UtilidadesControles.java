package es.clinica.podologia.utilidades;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * <p>Clase con métodos estáticos para los controles de uso común a lo largo de toda la aplicación.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class UtilidadesControles {
    
    /**
     * <p>Constructor privado vacío.</p>
     */
    private UtilidadesControles() {
	throw new IllegalStateException("Constructor privado de la clase de utilidades.");
    }
    
    
    /**
     * <p>Método que comprueba si se ha pulsado una tecla.</p>
     * 
     * @param evento {@link KeyEvent} evento del control
     * @param tecla {@link KeyCode} tecla que se quiere comprobar si se ha pulsado
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que se haya pulsado la tecla pasada como parámetro
     */
    public static Boolean pulsarTecla(KeyEvent evento, KeyCode tecla) {
	
	// Comprobar que los parámetros de entrada NO son nulos y que se ha pulsado la tecla correspondiente
	if(evento != null && tecla != null && evento.getCode() == tecla) {
	    
	    // Retornar true
	    return Boolean.TRUE;
	    
	}
	
	// Si llega aquí, es que alguno de los parámetros es nulo o no se ha pulsado la tecla, se retorna false
	return Boolean.FALSE;

    }
    
    /**
     * <p>Método que genera un formateador para una caja de texto sin ningún patrón. Únicamente controlará el número de caracteres</p>
     * <p>Útil para cajas de texto donde no se requiere validar que tengan un determinado formato</p>
     * 
     * @param numeroCaracteres {@link Integer} número máximo de caracteres del campo
     * 
     * @return {@link TextFormatter} {@link String} 
     */
    public static TextFormatter<String> cargarFormateadorSinPatron(Integer numeroCaracteres) {
	
	// Filtro que añade un número máximo de caracteres
	UnaryOperator<TextFormatter.Change> filtro = cambios -> cambios.getControlNewText().length() <= numeroCaracteres ? cambios : null;
	
	// Retornar un formateador con el filtro creado
	return new TextFormatter<>(filtro);
	
    }
    
    /**
     * <p>Método que genera un formateador para una caja de texto de acuerdo a un patrón y un límite de caracteres.</p>
     * 
     * @param patron {@link Pattern} patrón por el que se filtrará
     * @param numeroCaracteres {@link Integer} número máximo de caracteres del campo
     * 
     * @return {@link TextFormatter} {@link String} 
     */
    public static TextFormatter<String> cargarFormateadorConPatron(Pattern patron, Integer numeroCaracteres) {
	
	// Filtro que añade un patrón determinado y un número máximo de caracteres
	UnaryOperator<TextFormatter.Change> filtro = 
		cambios -> 
			cambios.getControlNewText().matches(patron.toString()) && cambios.getControlNewText().length() <= numeroCaracteres ? 
			cambios : 
			null;
	
	// Retornar un formateador con el filtro creado
	return new TextFormatter<>(filtro);
	
    }
    
    /**
     * <p>Método que carga un evento en una caja de Texto cuando pierde el foco, comprobando que no esté vacía</p>
     * 
     * @param cajaTexto {@link TextField} donde se quiere cargar el evento que hace la comprobación
     * @param mensaje {@link String} lo que mostrará a alerta de error
     * @param devolverFoco {@link Boolean} si se queire evitar que el usuario avance a otro campo
     */
    public static void cargarValidadorNulo(TextField cajaTexto, String mensaje, Boolean devolverFoco) {

	// Comprobar que la caja de texto NO es nula
	if (cajaTexto != null) {

	    // Añadir el escuchador
	    cajaTexto.focusedProperty().addListener((valor, valorAntiguo, valorNuevo) -> {
		
		// Comprobar que la caja de texto ha perdido el foco y esta vacía
		if (Boolean.FALSE.equals(valorNuevo) && StringUtils.isBlank(cajaTexto.getText())) {
		    
		    // Mostrar alerta de error
		    UtilidadesAlertas.mostrarAlertaError(mensaje);
		    
		    // Comprobar el parámetro del foco
		    if(Boolean.TRUE.equals(devolverFoco)) {
			
			// Devuelve el foco a la caja de texto
			cajaTexto.requestFocus();
			
		    }
		    
		}
	    });

	}

    }

}
