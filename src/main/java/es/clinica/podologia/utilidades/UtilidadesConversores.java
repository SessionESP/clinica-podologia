package es.clinica.podologia.utilidades;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import es.clinica.podologia.constantes.Constantes;

/**
 * <p>Clase con métodos estáticos de conversión generales de uso común a lo largo de la aplicación.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class UtilidadesConversores {
    
    /**
     * <p>Constructor privado vacío.</p>
     */
    private UtilidadesConversores() {
	throw new IllegalStateException("Constructor privado de la clase de utilidades de conversión.");
    }
    
    
    // Formato de conversión para fechas
    private static DateTimeFormatter formateadorFecha = DateTimeFormatter.ofPattern(Constantes.PATRON_FECHA);
    
    // Formato de conversión para horas
    private static DateTimeFormatter formateadorHora = DateTimeFormatter.ofPattern(Constantes.PATRON_HORA);
    
    
    // NÚMEROS ENTEROS - INICIO
    
    /**
     * <p>Convertir una cadena de caracteres en un entero.</p>
     * 
     * @param cadena {@link String} cadena de caracteres que se quiere convertir
     * 
     * @return {@link Integer} entero convertido
     */
    public static Integer cadenaEntero(String cadena) {
	
	// Convertir siempre y cuando la cadena de caracteres pasada como parámetro NO sea nula NI esté vacía
	return StringUtils.isNotBlank(cadena) ? Integer.valueOf(cadena) : null;
	
    }
    
    /**
     * <p>Convertir una cadena de caracteres en un entero.</p>
     * 
     * @param cadena {@link Integer} entero que se quiere convertir
     * 
     * @return {@link String} cadena de caracteres convertida
     */
    public static String enteroCadena(Integer entero) {
	
	// Convertir siempre y cuando la cadena de caracteres pasada como parámetro NO sea nula NI esté vacía
	return entero != null ? String.valueOf(entero) : null;
	
    }
    
    // NÚMEROS ENTEROS - FIN
    
    
    // FECHAS - INICIO
    
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
	
	// Convertir siempre y cuando la cadena de caracteres pasada como parámetro NO sea nula NI esté vacía
	return StringUtils.isNotBlank(cadena) ? LocalDate.parse(cadena, formateadorFecha) : null;
	
    }
    
    /**
     * <p>Convertir una {@code LocalDate} en una cadena de caracteres formateada.</p>
     * 
     * @param fecha {@link LocalDate} fecha que se quiere convertir
     * 
     * @return {@link String} cadena de caracteres convertida
     * 
     * @see DateTimeFormatter
     */
    public static String fechaCadena(LocalDate fecha) {
	
	// Convertir siempre y cuando el la fecha pasada como parámetro de entrada NO sea nula
	return fecha != null ? formateadorFecha.format(fecha) : Constantes.CADENA_VACIA;
	
    }
    
    // FECHAS - FIN
    
    
    // HORAS - INICIO
    
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
	
	// Convertir siempre y cuando la cadena de caracteres pasada como parámetro NO sea nula NI esté vacía
	return StringUtils.isNotBlank(cadena) ? LocalTime.parse(cadena, formateadorHora) : null;
	
    }
    
    /**
     * <p>Convertir una {@code LocalTime} en una cadena de caracteres formateada.</p>
     * 
     * @param fecha {@link LocalTime} fecha que se quiere convertir
     * 
     * @return {@link String} cadena de caracteres convertida
     * 
     * @see DateTimeFormatter
     */
    public static String horaCadena(LocalDate fecha) {
	
	// Convertir siempre y cuando el la fecha pasada como parámetro de entrada NO sea nula
	return fecha != null ? formateadorHora.format(fecha) : Constantes.CADENA_VACIA;
	
    }
    
    // HORAS - FIN

}
