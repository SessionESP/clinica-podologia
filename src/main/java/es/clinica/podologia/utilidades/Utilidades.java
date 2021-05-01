package es.clinica.podologia.utilidades;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Clase con métodos estáticos generales de uso común a lo largo de la aplicación.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class Utilidades {
    
    /**
     * <p>Constructor privado vacío.</p>
     */
    private Utilidades() {
	throw new IllegalStateException("Constructor privado de la clase de utilidades.");
    }
    
    /**
     * <p>Método quecomprueba si una cadena de caracteres es nula, retornando un valor por defecto en caso de que lo sea.</p>
     * 
     * @param cadena {@link String} cadena que se quiere comprobar
     * @param defecto {@link String} cadena por defecto que se retornará en caso de que sea nula
     * 
     * @return {@link String} retorna el primer parámetro en caso de que <b>NO</b> sea nulo, el segundo en caso de que lo sea
     */
    public static String comprobarCadena(String cadena, String defecto) {
	return StringUtils.isNotBlank(cadena) ? cadena : defecto;
    }

}
