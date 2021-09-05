package es.clinica.podologia.utilidades;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.clinica.podologia.constantes.Constantes;
import javafx.scene.paint.Color;

/**
 * <p>Clase con métodos estáticos de conversión generales de uso común a lo largo de la aplicación.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class UtilidadesConversores {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(UtilidadesConversores.class);
    
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
    public static Integer convertirCadenaEntero(String cadena) {
	
	// Convertir siempre y cuando la cadena de caracteres pasada como parámetro NO sea nula NI esté vacía
	return StringUtils.isNotBlank(cadena) && NumberUtils.isCreatable(cadena) ? Integer.valueOf(cadena) : null;
	
    }
    
    /**
     * <p>Convertir una cadena de caracteres en un entero.</p>
     * 
     * @param cadena {@link Integer} entero que se quiere convertir
     * 
     * @return {@link String} cadena de caracteres convertida
     */
    public static String convertirEnteroCadena(Integer entero) {
	
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
    public static Integer convertirLongEntero(Long numero) {
	return numero != null ? Math.toIntExact(numero) : null ;
    }
    
    /**
     * <p>Convertir un {@code Long} a {@code Integer}.</p>
     * 
     * @param numero {@link Integer} valor que se quiere convertir
     * 
     * @return {@link Long} valor convertido
     */
    public static Long convertirEnteroLong(Integer numero) {
	return numero != null ? numero.longValue() : null;
    }
    
    /**
     * <p>Método que convierte una array de {@code String} en un listado.</p>
     * 
     * @param arrayCadenas {@link String}[] array de cadenas de caracteres
     * 
     * @return {@link List}<{@link String}> lsitado de cadenas de caracteres
     */
    public static List<String> convertirArrayCadenasListaCadenas(String[] arrayCadenas) {
	return Boolean.TRUE.equals(Utilidades.comprobarArray(arrayCadenas)) ? 
		Arrays.asList(arrayCadenas) : new ArrayList<>();
    }
    
    /**
     * <p>Método que convierte un listado de enteros ({@code Integer}) en uno de cadenas de caracteres ({@code String}).</p>
     * 
     * @param listadoCadenas {@link List}<{@link String}> listado de cadenas de caracteres
     * 
     * @return {@link List}<{@link Integer}> listado de cadenas de enteros
     */
    public static List<Integer> convertirListadoCadenasListadoEnteros(List<String> listadoCadenas) {
	return ListUtils.emptyIfNull(listadoCadenas).stream().map(Integer::valueOf).collect(Collectors.toList());
    }
    
    /**
     * <p>Método que convierte una array de cadenas ({@code String}) en un listado de enteros ({@code Integer}).</p>
     * 
     * @param arrayCadenas {@link String}[] array de cadenas de caracteres
     * 
     * @return {@link List}<{@link Integer}> listado de cadenas de enteros
     */
    public static List<Integer> convertirArrayCadenasListaEnteros(String[] arrayCadenas) {
	return Boolean.TRUE.equals(Utilidades.comprobarArray(arrayCadenas)) ? 
		convertirListadoCadenasListadoEnteros(convertirArrayCadenasListaCadenas(arrayCadenas)) : null;
    }
    
    // NÚMEROS ENTEROS - FIN
    
    // NÚMEROS DECIMALES - INICIO
    
    /**
     * <p>Método que convierte un número decimal en una cadena de caracteres.</p>
     * 
     * @param decimal {@link BigDecimal} valor decimal que se quiere convertir
     * 
     * @return {@link String} cadena de caracteres que representa un número decimal
     */
    public static String convertirDecimalCadena(BigDecimal decimal) {
	return decimal != null ? NumberFormat.getInstance().format(decimal) : Constantes.CADENA_VACIA;
    }
    
    /**
     * <p>Método que convierte una cadena de caracteres en un valor decimal.</p>
     * 
     * @param cadena {@link String} cadena de caracteres que representa un número decimal
     * 
     * @return {@link BigDecimal} valor decimal que se quiere convertir
     */
    public static BigDecimal convertirCadenaDecimal(String cadena) {
	
	// Inicializar el decimal que se va a retornar al final del método
	BigDecimal decimal = BigDecimal.ZERO;
	
	// Comprobar que la cadena que se quiere convertir NO es nula
	if (Boolean.TRUE.equals(StringUtils.isNotBlank(cadena))) {
		try {
		    decimal = new BigDecimal(NumberFormat.getInstance().parse(cadena).toString());
		} catch (ParseException excepcion) {
		    
		    // Error al intentar convertir la cadena de caracteres
		    TRAZAS.error(excepcion.getMessage());
		    excepcion.printStackTrace();
		    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
		    
		}
	}
	

	
	// Retornar el decimal convertido
	return decimal;
    }
    
    /**
     * <p>Método que convierte un número decimal en una cadena de caracteres que representa un precio en la moneda local.</p>
     * 
     * @param decimal {@link BigDecimal} valor decimal que se quiere convertir
     * 
     * @return {@link String} cadena de caracteres que representa un precio en la moneda local
     */
    public static String convertirDecimalMoneda(BigDecimal decimal) {
	return decimal != null ? NumberFormat.getCurrencyInstance().format(decimal) : NumberFormat.getCurrencyInstance().format(BigDecimal.ZERO);
    }
    
    /**
     * <p>Método que convierte una cadena de caracteres que representa un precio en la moneda local en un valor decimal.</p>
     * 
     * @param cadena {@link String} cadena de caracteres que representa un precio en la moneda local
     * 
     * @return {@link BigDecimal} valor decimal que se quiere convertir
     */
    public static BigDecimal convertirMonedaDecimal(String cadena) {
	
	// Inicializar el decimal que se va a retornar al final del método
	BigDecimal decimal = BigDecimal.ZERO;
	
	// Comprobar que la cadena que se quiere convertir NO es nula
	if (Boolean.TRUE.equals(StringUtils.isNotBlank(cadena))) {
		try {
		    decimal = new BigDecimal(NumberFormat.getCurrencyInstance().parse(cadena).toString());
		} catch (ParseException excepcion) {
		    
		    // Error al intentar convertir la cadena de caracteres
		    TRAZAS.error(excepcion.getMessage());
		    excepcion.printStackTrace();
		    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
		    
		}
	}
	

	
	// Retornar el decimal convertido
	return decimal;
    }
    
    // NÚMEROS DECIMALES - FIN
    
    
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
    public static LocalDate convertirCadenaFecha(String cadena) {
	
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
    public static String convertirFechaCadena(LocalDate fecha) {
	
	// Convertir siempre y cuando el la fecha pasada como parámetro de entrada NO sea nula
	return fecha != null ? formateadorFecha.format(fecha) : Constantes.CADENA_VACIA;
	
    }
    
    /**
     * <p>Convertir una fecha Epoch con milisegundos en una fecha.</p>
     * 
     * @param epoch {@link Long} fecha en formato Epoch con milisegundos que se desea convertir
     * 
     * @return {@link LocalDate} fecha equivalente convertida
     */
    public static LocalDate convertirLongFecha(Long epoch) {
	return epoch != null ? Instant.ofEpochSecond(epoch).atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }
    
    /**
     * <p>Convertir una fecha en una fecha Epoch con milisegundos.</p>
     * 
     * @param fecha {@link LocalDate} fecha que se desea convertir
     * 
     * @return {@link Long} fecha en formato Epoch con milisegundos equivalente convertida
     */
    public static Long convertirFechaLong(LocalDate fecha) {
	return fecha != null ? fecha.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() : null;
    }
    
    /**
     * <p>Método que calcula una fecha de nacimiento en base a una edad expresada como un número entero.</p>
     * <p>Ejemplo: </p>
     * <ul>
     * <li>Fecha actual: {@code 14 de agosto de 2.021}</li>
     * <li>Edad pasada como parámetro: {@code 20 años}</li>
     * <li>Fecha de nacimiento calculada: {@code 1 de enero de 2.001}</li>
     * </ul>
     * 
     * @param edad {@link Integer} entero que representa un número de años
     * 
     * @return {@link LocalDate} fecha de nacimiento calculada: primer día del año correspondiente
     */
    public static LocalDate calcularFechaNacimiento(Integer edad) {
	return edad != null ? LocalDate.now().minusYears(convertirEnteroLong(edad)).with(TemporalAdjusters.firstDayOfYear()) : LocalDate.now();
    }
    
    /**
     * <p>Método que calcula una edad expresada como un número entero en base a fecha de nacimiento.</p>
     * 
     * @param fechaNacimiento {@link LocalDate} fecha de nacimiento
     * 
     * @return {@link Integer} entero que representa una edad de años
     */
    public static Integer calcularEdad(LocalDate fechaNacimiento) {
	return fechaNacimiento != null ? Period.between(fechaNacimiento, LocalDate.now()).getYears() : 0;
    }
    
    /**
     * <p>Imprime la edad seguida de "años" en función de una fecha de nacimiento introducida como parámetro.</p>
     * 
     * @param fechaNacimiento {@link LocalDate} la que se ha introducido en el campo de la vista correspondiente
     * 
     * @return {@link String} edad calculada seguida de " años"
     */
    public static String imprimirEdad(LocalDate fechaNacimiento) {
	return convertirEnteroCadena(UtilidadesConversores.calcularEdad(fechaNacimiento)) + Constantes.ESPACIO + Constantes.ANIOS;
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
    public static LocalTime convertirCadenaHora(String cadena) {
	
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
    public static String convertirHoraCadena(LocalTime hora) {
	
	// Convertir siempre y cuando el la fecha pasada como parámetro de entrada NO sea nula
	return hora != null ? formateadorHora.format(hora) : Constantes.CADENA_VACIA;
	
    }
    
    /**
     * <p>Convertir una hora Epoch con milisegundos en una hora.</p>
     * 
     * @param epoch {@link Long} hora en formato Epoch con milisegundos que se desea convertir
     * 
     * @return {@link LocalTime} hora equivalente convertida
     */
    public static LocalTime convertirLongHora(Long epoch) {
	return epoch != null ? Instant.ofEpochSecond(epoch).atZone(ZoneId.of(Constantes.ZONA_HORARIA)).toLocalTime() : null;
    }
    
    /**
     * <p>Convertir una hora en una hora Epoch con milisegundos.</p>
     * <p>Para una mayor coherencia, se requiere de una fecha concreta, ya que la hora pertenecerá a un cita con una fecha concreta.</p>
     * 
     * @param fecha {@link LocalDate} fecha de referencia para la hora
     * @param hora {@link LocalTime} hora que se desea convertir
     * 
     * @return {@link Long} hora en formato Epoch con milisegundos equivalente convertida
     */
    public static Long convertirHoraLong(LocalDate fecha, LocalTime hora) {
	return hora != null ? hora.toEpochSecond(fecha, ZoneOffset.of(Constantes.ZONA_HORARIA)) : null;
    }
    
    /**
     * <p>Diferencia de minutos entre dos horas.</p>
     * 
     * @param horaInicio {@link LocalTime} primera hora
     * @param horaFin {@link LocalTime} segunda hora
     * 
     * @return {@link Integer} número de minutos entre las dos horas
     */
    public static Integer calcularDiferenciaMinutos(LocalTime horaInicio, LocalTime horaFin) {
	
	// Inicializar el entero que se retornará al fina del método
	Integer diferencia = null;
	
	// Comprobar que ambos parámetros de entrada NO son nulos
	if(horaInicio != null && horaFin != null) {
	    diferencia = convertirLongEntero(ChronoUnit.MINUTES.between(horaInicio, horaFin));
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
    public static String convertirBooleanoCadena(Boolean booleano) {
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
    
    
    // COLORES - INICIO
    
    /**
     * <p>Método que obtiene un color a partir de su valor hexadecimal.</p>
     * <p>Por ejemplo, el valor hexadecimal {@code #FFFFFF} se corresponde con {@code Color.WHITE}.</p>
     * 
     * @param colorHexadecimal {@link String} valor hexadecimal del color que se quiere obtener
     * 
     * @return {@link Color} color obtenido del valor hexadecimal pasado como parámetro
     */
    public static Color convertirHexadecimalColor(String colorHexadecimal) {
	return Boolean.TRUE.equals(StringUtils.isNotBlank(colorHexadecimal)) ? Color.web(colorHexadecimal) : Color.WHITE;
    }
    
    /**
     * <p>Método que obtiene el valor hexadecimal de un color.</p>
     * <p>Por ejemplo, {@code Color.WHITE} tiene como valor hexadecimal {@code #FFFFFF}</p>
     * 
     * @param color {@link Color} cuyo valor hexadecimal se quiere recuperar
     * 
     * @return {@link String} valor hexadecimal del color pasado como parámetro
     */
    public static String convertirColorHexadecimal(Color color) {

	// Inicializar la cadena de caracteres con el valor hexadecimal que se retornará al final de método
	String colorHexadecimal = Constantes.COLOR_BLANCO_HEXADECIMAL;

	// Comprobar que el color pasado como parámetro NO es nulo
	if (color != null) {
	    
	    // Descomponer el color en sus valores RGBA
	    int red = 	((int) Math.round(color.getRed() * 255)) << 24;
	    int green = ((int) Math.round(color.getGreen() * 255)) << 16;
	    int blue = 	((int) Math.round(color.getBlue() * 255)) << 8;
	    int alpha = ((int) Math.round(color.getOpacity() * 255));
	    
	    // Formatear el valor hexadecimal a partir de los valores RGBA del color
	    colorHexadecimal = String.format("#%08X", (red + green + blue + alpha));
	    
	}

	// Valor hexadecimal del color
	return colorHexadecimal;

    }
    
    // COLORES - FIN
    
    
    // FICHEROS - INICIO
    
    /**
     * <p>Método para convertir un archivo de tipo {@code File} en un array de {@code byte}.</p>
     * 
     * @param archivo {@link File} archivo que se quiere convertir
     * 
     * @return {@link byte}[] array de bytes resultante
     */
    public static byte[] convertirFicheroArrayBytes (File archivo) {
	
	// Inicializar el array de bytes que se va a retornar al final del método
	byte[] arrayBytes = null;
	
	// Comprobar que el fichero pasado como parámetro NO es nulo
	if(Boolean.TRUE.equals(Utilidades.comprobarArchivo(archivo))) {
	    
	    try {
		
		// Convertir el archivo en una array de bytes
		arrayBytes = FileUtils.readFileToByteArray(archivo);
		
	    } catch (IOException excepcion) {
		
		// Error al intentar convertir el archivo
		TRAZAS.error(excepcion.getMessage());
		excepcion.printStackTrace();
		UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
		
	    }
	    
	}
	
	// Retornar el array de bytes
	return arrayBytes;
	
    }
    

    /**
     * <p>Método para convertir un array de {@code byte} en un archivo de tipo {@code File}.</p>
     * 
     * @param arrayBytes {@link byte}[] array de bytes que se quiere convertir
     * @param archivo {@link String} ubicación donde se colocará el archivo resultante
     * 
     * @return {@link File} archivo que se quiere convertir
     */
    public static File convertirArrayBytesFichero (byte[] arrayBytes, String ubicacion) {
	
	// Inicializar el fichero que se va a retornar al final del método
	File archivo = new File(ubicacion);
	
	// Comprobar que el array de bytes pasado como parámetro NO es nulo
	if(Boolean.TRUE.equals(Utilidades.comprobarArrayByte(arrayBytes))) {
	    
	    try {
		
		// Eliminar el fichero si ya existe
		if(Boolean.TRUE.equals(Utilidades.comprobarUbicacionArchivo(ubicacion))) {
		    FileUtils.delete(archivo);
		}
		
		// Convertir el array de bytes en un archivo
		FileUtils.writeByteArrayToFile(archivo, arrayBytes);
		
	    } catch (IOException excepcion) {
		
		// Error al intentar convertir el array de bytes
		TRAZAS.error(excepcion.getMessage());
		excepcion.printStackTrace();
		UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
		
	    }
	    
	}
	
	
	// Retornar el fichero
	return archivo;
	
    }
    
    
    /**
     * <p>Método que elimina un archivo de una ubicación.</p>
     * 
     * @param archivo {@link File} archivo que se quiere eliminar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el archivo se haya borrado correctamente
     */
    public static Boolean eliminarArchivo (File archivo) {
	
	// Inicializar la variable que se va a retornar al final del método
	Boolean resultado = Boolean.FALSE;
	
	// Comprobar que el archivo NO es nulo y que existe
	if(Utilidades.comprobarArchivo(archivo) && Utilidades.comprobarUbicacionArchivo(archivo.getAbsolutePath())) {
	    
	    try {
		
		// Eliminar el archivo
		FileUtils.delete(archivo);
		
		// Comprobar que el archivo ya no existe
		resultado = !Utilidades.comprobarUbicacionArchivo(archivo.getAbsolutePath());
		
	    } catch (IOException excepcion) {
		
		// Error al intentar convertir el array de bytes
		TRAZAS.error(excepcion.getMessage());
		excepcion.printStackTrace();
		UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
		
	    }
	}

	
	// Retornar el resultado del borrado del fichero
	return resultado;
	
    }
    
    // FICHEROS -FIN

}
