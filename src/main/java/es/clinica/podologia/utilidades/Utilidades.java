package es.clinica.podologia.utilidades;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import es.clinica.podologia.constantes.Constantes;

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
     * <p>Método que comprueba si una cadena de caracteres es nula, retornando un valor por defecto en caso de que lo sea.</p>
     * 
     * @param cadena {@link String} cadena que se quiere comprobar
     * @param defecto {@link String} cadena por defecto que se retornará en caso de que sea nula
     * 
     * @return {@link String} retorna el primer parámetro en caso de que <b>NO</b> sea nulo, el segundo en caso de que lo sea
     * 
     * @see StringUtils#isNotBlank(CharSequence)
     */
    public static String comprobarCadena(String cadena, String defecto) {
	return StringUtils.isNotBlank(cadena) ? cadena : defecto;
    }
    
    /**
     * <p>Método que comprueba si una cadena de caracteres es nula, retornando una cadena vacía en caso de que lo sea</p>
     * 
     * @param cadena {@link String} cadena que se quiere comprobar
     * 
     * @return {@link String} retorna el primer parámetro en caso de que <b>NO</b> sea nulo, la cadena vacía en caso de que lo sea
     * 
     * @see StringUtils#isNotBlank(CharSequence)
     * @see Constantes#CADENA_VACIA
     */
    public static String comprobarCadenaNula(String cadena) {
	return comprobarCadena(cadena, Constantes.CADENA_VACIA);
    }
    
    
    /**
     * <p>Método que compara dos cadenas de caracteres.</>
     * 
     * @param cadena1 {@link String} primera cadena de caracteres que se quiere comparar
     * @param cadena2 {@link String} segunda cadena de caracteres que se quiere comparar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que ambas cadenas sean iguales
     * 
     * @see String#equals(Object)
     * @see Utilidades#compararCadenas(String, String)
     * @see Constantes#CADENA_VACIA
     */
    public static Boolean compararCadenas(String cadena1, String cadena2) {
	return comprobarCadena(cadena1, Constantes.CADENA_VACIA).equals(comprobarCadena(cadena2, Constantes.CADENA_VACIA));
    }
    
    
    /**
     * <p>Método que comprueba si una colección es <b>NULA</b> o está vacía.</p>
     * 
     * @param coleccion {@link Collection} colección que se quiere comprobar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que la colección <b>NO</b> sea nula <b>NI</b> esté vacía
     * 
     * @see Collection
     */
    public static Boolean comprobarColeccion(Collection<?> coleccion) {
	return coleccion != null && !coleccion.isEmpty();
    }
    
    
    /**
     * <p>Método que comprueba si un array es <b>NULO</b> o está vacío.</p>
     * 
     * @param coleccion {@link Object} array de objetos que se quiere comprobar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el array <b>NO</b> sea nulo <b>NI</b> esté vacío
     */
    public static Boolean comprobarArray(Object[] array) {
	return array != null && array.length > 0;
    }
    
    
    /**
     * <p>Convertir una cadena de caracteres en una fecha.</p>
     * 
     * @param cadena {@link String} cadena de caracteres que se quiere convertir
     * 
     * @return {@link LocalDate} fecha convertida
     * 
     * @see DateTimeFormatter
     */
    public static LocalDate cadenaFecha(String cadena) {
	
//	DateTimeFormatter formateador = DateTimeFormatter.ISO_LOCAL_DATE;
	DateTimeFormatter formateador = DateTimeFormatter.ofPattern(Constantes.PATRON_FECHA);
	return LocalDate.parse(cadena, formateador);
	
    }
    
    
    /**
     * <p>Convertir una cadena de caracteres en una hora.</p>
     * 
     * @param cadena {@link String} cadena de caracteres que se quiere convertir
     * 
     * @return {@link LocalTime} hora convertida
     * 
     * @see DateTimeFormatter
     */
    public static LocalTime cadenaHora(String cadena) {
	
//	DateTimeFormatter formateador = DateTimeFormatter.ISO_LOCAL_TIME;
	DateTimeFormatter formateador = DateTimeFormatter.ofPattern(Constantes.PATRON_HORA);
	return LocalTime.parse(cadena, formateador);
	
    }

}
