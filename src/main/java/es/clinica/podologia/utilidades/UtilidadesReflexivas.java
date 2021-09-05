package es.clinica.podologia.utilidades;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Clase con métodos estáticos feflexivos de uso común a lo largo de toda la aplicación.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class UtilidadesReflexivas {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(UtilidadesReflexivas.class);
    
    /**
     * <p>Constructor privado vacío.</p>
     */
    private UtilidadesReflexivas() {
	throw new IllegalStateException("Constructor privado de la clase de utilidades reflexivas.");
    }
    
    
    /**
     * <p>Método que recupera un determinado atributo dentro de una clase.</p>
     * 
     * @param nombreObjeto {@link String} donde se quiere buscar el atributo
     * @param nombreAtributo {@link String} el nombre del atributo que se quiere buscar
     * 
     * @return {@link Field} atributo recuperado, será necesario "castearlo" a su objeto correspondiente 
     */
    public static Field obtenerAtributo(String nombreObjeto, String nombreAtributo) {
	
	// Inicializar el campo que se va a retornar al final del método
	Field campo = null;
	
	// Comprobar que los dos parámetros de entrada NO son nulos NI están vacíos
	if(StringUtils.isNotBlank(nombreObjeto) && StringUtils.isNotBlank(nombreAtributo)) {
	    
	    try {
		
		// Buscar un campo dentro del objeto con el nombre del atributo
		campo = Class.forName(nombreObjeto).getDeclaredField(nombreAtributo);
		
		// Permitir acceder a los atributos privados
		campo.setAccessible(Boolean.TRUE);
		
	    } catch (NoSuchFieldException | SecurityException | ClassNotFoundException excepcion) {
		
		// Imprimer la traza de error
		UtilidadesTrazas.imprimirTrazaError(TRAZAS, excepcion.getMessage());
		
		// Imprimir el mensaje de error completo
		excepcion.printStackTrace();
	    }
	    
	}
	
	// Retornar el campo recuperado
	return campo;
	
    }

}
