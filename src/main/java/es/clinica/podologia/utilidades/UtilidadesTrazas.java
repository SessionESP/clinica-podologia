package es.clinica.podologia.utilidades;

import org.slf4j.Logger;

/**
 * <p>Clase con métodos estáticos de trazas de uso común a lo largo de la aplicación.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class UtilidadesTrazas {
    
    /**
     * <p>Constructor privado vacío.</p>
     */
    private UtilidadesTrazas() {
	throw new IllegalStateException("Constructor privado de la clase de utilidades de trazas.");
    }
    
    /**
     * <p>Método que comprueba si el nivel de error es el adecuado antes de imprimir una traza informativa.</p>
     * 
     * @param trazas {@link Logger} interfaz de trazas para visualizar
     * @param mensaje {@link String} error que se quiere imprimir
     */
    public static void imprimirTrazaInformativa(Logger trazas, String mensaje) {
	
	// Comprobar que los parámetros de entrada NO son nulos
	if(trazas != null && trazas.isInfoEnabled()) {
	    
	    // Imprimir la traza informativa
	    trazas.info(mensaje);
	    
	}
	
    }
    
    /**
     * <p>Método que comprueba si el nivel de error es el adecuado antes de imprimir una traza de depuración.</p>
     * 
     * @param trazas {@link Logger} interfaz de trazas para visualizar
     * @param mensaje {@link String} error que se quiere imprimir
     */
    public static void imprimirTrazaDepuracion(Logger trazas, String mensaje) {
	
	// Comprobar que los parámetros de entrada NO son nulos
	if(trazas != null && trazas.isDebugEnabled()) {
	    
	    // Imprimir la traza de depuración
	    trazas.debug(mensaje);
	    
	}
	
    }
    
    /**
     * <p>Método que comprueba si el nivel de error es el adecuado antes de imprimir una traza de advertencia.</p>
     * 
     * @param trazas {@link Logger} interfaz de trazas para visualizar
     * @param mensaje {@link String} error que se quiere imprimir
     */
    public static void imprimirTrazaAdvertencia(Logger trazas, String mensaje) {
	
	// Comprobar que los parámetros de entrada NO son nulos
	if(trazas != null && trazas.isWarnEnabled()) {
	    
	    // Imprimir la traza de advertencia
	    trazas.warn(mensaje);
	    
	}
	
    }
    
    /**
     * <p>Método que comprueba si el nivel de error es el adecuado antes de imprimir una traza de error.</p>
     * 
     * @param trazas {@link Logger} interfaz de trazas para visualizar
     * @param mensaje {@link String} error que se quiere imprimir
     */
    public static void imprimirTrazaError(Logger trazas, String mensaje) {
	
	// Comprobar que los parámetros de entrada NO son nulos
	if(trazas != null && trazas.isErrorEnabled()) {
	    
	    // Imprimir la traza de error
	    trazas.error(mensaje);
	    
	}
	
    }

}
