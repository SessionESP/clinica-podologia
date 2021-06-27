package es.clinica.podologia.utilidades;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
    
    /**
     * <p>Convertir un {@code Long} a {@code Integer}.</p>
     * 
     * @param numero {@link Long} valor que se quiere convertir
     * 
     * @return {@link Integer} valor convertido
     */
    public static Integer longEntero(Long numero) {
	return numero != null ? Math.toIntExact(numero) : null ;
    }
    
    /**
     * <p>Convertir un {@code Long} a {@code Integer}.</p>
     * 
     * @param numero {@link Integer} valor que se quiere convertir
     * 
     * @return {@link Long} valor convertido
     */
    public static Long enteroLong(Integer numero) {
	return numero != null ? numero.longValue() : null;
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
    public static String horaCadena(LocalTime hora) {
	
	// Convertir siempre y cuando el la fecha pasada como parámetro de entrada NO sea nula
	return hora != null ? formateadorHora.format(hora) : Constantes.CADENA_VACIA;
	
    }
    
    /**
     * <p>Diferencia de minutos entre dos horas.</p>
     * 
     * @param horaInicio {@link LocalTime} primera hora
     * @param horaFin {@link LocalTime} segunda hora
     * 
     * @return {@link Integer} número de minutos entre las dos horas
     */
    public static Integer diferenciaMinutos(LocalTime horaInicio, LocalTime horaFin) {
	
	// Inicializar el entero que se retornará al fina del método
	Integer diferencia = null;
	
	// Comprobar que ambos parámetros de entrada NO son nulos
	if(horaInicio != null && horaFin != null) {
	    diferencia = longEntero(ChronoUnit.MINUTES.between(horaInicio, horaFin));
	}
	
	// Retornar el la diferencia entre las dos horas
	return diferencia;
	
    }
    
    // HORAS - FIN
    
    
    // BOOLEANO - INICIO
    
    /**
     * <p>Convertir un {@code Boolean} en una cadena que represente su valor.</p>
     * 
     * @param booleano {@link Boolean} objeto booleano
     * 
     * @return {@link String} cadena de caracteres correspondiente al valor del objeto booleano
     */
    public static String booleanoCadena(Boolean booleano) {
	return booleano != null ? booleano.toString() : Constantes.CADENA_VACIA;
    }
    
    /**
     * <p>Convertir una cadena de caracteres en un {@code Booleano}.</p>
     * 
     * @param cadenaBooleana {@link String} cadena de caracteres que represente un valor posible
     * @return {@link Boolean} booleano correspondiente
     */
    public static Boolean cadenaBooleano(String cadenaBooleana) {
	return StringUtils.isNotBlank(cadenaBooleana) ? Boolean.valueOf(cadenaBooleana) : null;
    }
    
    
    // BOOLEANO - INICIO

}
