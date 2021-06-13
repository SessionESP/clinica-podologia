package es.clinica.podologia.constantes;

import java.time.format.DateTimeFormatter;

/**
 * <p>Clase de constantes comunes de la aplicación.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class Constantes {
    
    /**
     * <p>Constructor privado vacío.</p>
     */
    private Constantes() {
	throw new IllegalStateException("Constructor privado de la clase de constantes.");
    }
    
    public static final String CADENA_VACIA = "";
    public static final String ESPACIO = " ";
    
    public static final String ASTERISCO = "*";
    public static final String PUNTO = ".";
    
    public static final String TITULO = "titulo";
    public static final String ALTURA = "altura";
    public static final String ANCHURA = "anchura";
    public static final String REDIMENSIONABLE = "redimensionable";
    
    public static final String PATRON_FECHA = "dd/MM/yyyy";
    public static final String PATRON_HORA = "HH:mm";
    public static final String PATRON_MAYUSCULAS = "(?<!^)(?=[A-Z])";
    
    public static final String ACCESO = "citas";
    public static final String CITAS = "citas";
    public static final String CONFIGURACION = "configuracion";
    public static final String PACIENTES = "pacientes";
    public static final String SANITARIOS = "sanitarios";
    public static final String TRATAMIENTOS = "tratamientos";
    
    public static final DateTimeFormatter FORMATEADOR_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

}
