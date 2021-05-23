package es.clinica.podologia.utilidades;

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

}
