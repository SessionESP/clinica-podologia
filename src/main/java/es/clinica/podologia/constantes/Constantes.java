package es.clinica.podologia.constantes;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

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
    public static final String COMA = ",";
    public static final String EXTENSION_PDF = ".pdf";
    public static final String GUION_BAJO = "_";
    public static final String PUNTO = ".";
    public static final String SIN_DNI = "sin_dni";
    public static final String SIN_NOMBRE = "sin_nombre";
    public static final String SIN_APELLIDOS = "sin_apellidos";
    
    public static final String TITULO = "titulo";
    public static final String ALTURA = "altura";
    public static final String ANCHURA = "anchura";
    public static final String REDIMENSIONABLE = "redimensionable";
    
    public static final String PATRON_FECHA = "dd-MM-yyyy";
    public static final String PATRON_HORA = "HH:mm:ss";
    public static final String PATRON_MAYUSCULAS = "(?<!^)(?=[A-Z])";
    public static final Pattern PATRON_NUMEROS_ENTEROS = Pattern.compile("\\d*");
    public static final Pattern PATRON_DNI = Pattern.compile("^(([A-Z]\\d{8})|(\\d{8}[A-Z]))$");
    
    public static final String ACCESO = "citas";
    public static final String CITAS = "citas";
    public static final String CONFIGURACION = "configuracion";
    public static final String PACIENTES = "pacientes";
    public static final String SANITARIOS = "sanitarios";
    public static final String TRATAMIENTOS = "tratamientos";
    
    public static final String CONFIGURACION_HORA_APERTURA = "clinica.horario.apertura";
    public static final String CONFIGURACION_HORA_CIERRE = "clinica.horario.cierre";
    public static final String CONFIGURACION_DURACION = "clinica.citas.duracion";
    public static final String CONFIGURACION_ELIMINACION_CITAS_PASADAS = "clinica.citas.eliminacion.pasadas";
    public static final String CONFIGURACION_ELIMINACION_FISICA_CITAS = "clinica.citas.eliminacion.fisica";
    
    public static final String ESTADOS_CITAS_PAGINACIONES = "estados.citas.paginaciones";
    public static final String ESTADOS_CITAS_PAGINACION = "estados.citas.paginacion";
    public static final String ESTADOS_PACIENTES_PAGINACIONES = "estados.pacientes.paginaciones";
    public static final String ESTADOS_PACIENTES_PAGINACION = "estados.pacientes.paginacion";
    public static final String ESTADOS_TRATAMIENTOS_PAGINACIONES = "estados.tratamientos.paginaciones";
    public static final String ESTADOS_TRATAMIENTOS_PAGINACION = "estados.tratamientos.paginacion";
    public static final String ESTADOS_SANITARIOS_PAGINACIONES = "estados.sanitarios.paginaciones";
    public static final String ESTADOS_SANITARIOS_PAGINACION = "estados.sanitarios.paginacion";
    public static final Integer ESTADOS_PAGINACION_DEFECTO_5 = 5;
    public static final Integer ESTADOS_PAGINACION_DEFECTO_10 = 10;
    
    public static final String CONFIGURACION_APERTURA_DEFECTO = "09:00:00";
    public static final String CONFIGURACION_CIERRE_DEFECTO = "21:00:00";
    public static final Integer CONFIGURACION_DURACION_DEFECTO = 30;
    
    public static final String CITAS_LISTADO_CONTROLLER = "citasListadoController";
    public static final String CITAS_EDICION_CONTROLLER = "citasEdicionController";
    public static final String PACIENTES_LISTADO_CONTROLLER = "pacientesListadoController";
    public static final String PACIENTES_EDICION_CONTROLLER = "pacientesEdicionController";
    public static final String SANITARIOS_LISTADO_CONTROLLER = "sanitariosListadoController";
    public static final String SANITARIOS_EDICION_CONTROLLER = "sanitariosEdicionController";
    public static final String TRATAMIENTOS_LISTADO_CONTROLLER = "tratamientosListadoController";
    public static final String TRATAMIENTOS_EDICION_CONTROLLER = "tratamientosEdicionController";
    
    public static final DateTimeFormatter FORMATEADOR_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

}
