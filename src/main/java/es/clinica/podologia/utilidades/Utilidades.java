package es.clinica.podologia.utilidades;

import java.io.File;
import java.util.Collection;
import java.util.Map;

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
     * @param array {@link Object}{} array de objetos que se quiere comprobar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el array <b>NO</b> sea nulo <b>NI</b> esté vacío
     */
    public static Boolean comprobarArray(Object[] array) {
	return array != null && array.length > 0;
    }
    
    
    /**
     * <p>Método que comprueba si un array de {@code byte} es <b>NULO</b> o está vacío.</p>
     * 
     * @param array {@link byte}[] array de objetos que se quiere comprobar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el array <b>NO</b> sea nulo <b>NI</b> esté vacío
     */
    public static Boolean comprobarArrayByte(byte[] array) {
	return array != null && array.length > 0;
    }
    
    
    /**
     * <p>Método que retornar un array de {@code byte} en caso de que sea nulo.</p>
     * 
     * @param array {@link byte}[] array de bytes que se quiere comprobar
     * 
     * @return {@link byte}[] retorna el mismo array si  <b>NO ES NULO</b> o uno vacío en caso de que lo sea
     */
    public static byte[] comprobarArrayByteNulo(byte[] array) {
	return comprobarArrayByte(array) ? array : Constantes.CADENA_VACIA.getBytes();
    }
    
    
    /**
     * <p>Método que comprueba si una mapa es <b>NULO</b> o está vacío.</p>
     * 
     * @param coleccion {@link Map} mapa que se quiere comprobar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el mapa <b>NO</b> sea nulo <b>NI</b> esté vacío
     * 
     * @see Map
     */
    public static Boolean comprobarMapa(Map<?, ?> mapa) {
	return mapa != null && !mapa.isEmpty();
    }
    
    
    /**
     * <p>Método que comprueba si un archivo {@code File} es nulo o está vacío.</p>
     * 
     * @param archivo {@link File} archivo que se quiere comprobar
     * 
     * @return {@link Boolean} {@code true} en caso de que el archivo <b>NO</b> sea nulo <b>NI</b> esté vacío
     */
    public static Boolean comprobarArchivo(File archivo) {
	return archivo != null && archivo.length() > 0;
    }
    
    
    /**
     * <p>Método que comprueba si una cadena de caracteres representa la localización de un archivo válido.</p>
     * 
     * @param ubicacion {@link String} cadena que representa la ubicación de un archivo
     * 
     * @return {@link Boolean} {@code true} en caso de que el archivo exista en la localización
     */
    public static Boolean comprobarUbicacionArchivo(String ubicacion) {
	
	// Inicializar la variable que se retornará al final del método
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que la cadena de caracteres NO es nula
	if(StringUtils.isNotBlank(ubicacion)) {
	    
	    // Instanciar un archivo con la ubicación para realizar las comprobaciones
	    File archivo = new File(ubicacion);
	    
	    // Comprobar que el archivo existe, que es un directorio y que es un archivo
	    resultado = archivo.exists() && archivo.isFile();
	    
	}
	
	// Retornar el resultado de la comprobación del método
	return resultado;
	
    }

}
