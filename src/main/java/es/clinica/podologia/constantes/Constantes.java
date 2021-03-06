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
    public static final String GUION = "-";
    public static final String GUION_ESPACIADO = ESPACIO + GUION + ESPACIO;
    
    public static final String ASTERISCO = "*";
    public static final String COMA = ",";
    public static final String EXTENSION_PDF = ".pdf";
    public static final String GUION_BAJO = "_";
    public static final String PUNTO = ".";
    public static final String SALTO_LINEA = "\n";
    public static final String ANIOS = "años";
    public static final String LIBRE = "Libre";
    public static final String ZONA_HORARIA = "+00:00";
    public static final String COLOR_BLANCO_HEXADECIMAL = "#FFFFFF";
    public static final String NUMERO_UNO = "1";
    public static final String NUMERO_DOS = "2";
    public static final String NUMERO_TRES = "3";
    
    public static final String TITULO = "titulo";
    public static final String ALTURA = "altura";
    public static final String ANCHURA = "anchura";
    public static final String REDIMENSIONABLE = "redimensionable";
    
    public static final Integer LIMITE_3 = 3;
    public static final Integer LIMITE_5 = 5;
    public static final Integer LIMITE_20 = 20;
    public static final Integer LIMITE_50 = 50;
    public static final Integer LIMITE_100 = 100;
    public static final Long LIMITE_1024 = 1024L;
    
    public static final String PATRON_FECHA = "dd-MM-yyyy";
    public static final String PATRON_HORA = "HH:mm:ss";
    public static final String PATRON_MAYUSCULAS = "(?<!^)(?=[A-Z])";
    public static final Pattern PATRON_NUMEROS_ENTEROS = Pattern.compile("\\d*");
    public static final Pattern PATRON_TODO_MENOS_ENTEROS = Pattern.compile("\\D+");
    public static final Pattern PATRON_PRECIO = Pattern.compile("^\\d{0,3}(\\,\\d{1,2})?$");
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
    public static final String CONFIGURACION_ELIMINACION_CITAS = "clinica.citas.eliminacion.seleccionada";
    public static final String CONFIGURACION_ELIMINACION_CITAS_ULTIMA_SEMANA = "Última semana";
    public static final String CONFIGURACION_ELIMINACION_CITAS_ULTIMO_MES = "Último mes";
    public static final String CONFIGURACION_ELIMINACION_CITAS_ULTIMO_ANIO = "Último año";
    
    public static final String ESTADOS_AGENDA_SANITARIOS_EDICION_SANITARIO="estados.agenda.sanitario";
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
    
    public static final String AGENDA_EDICION_CONTROLLER = "agendaEdicionController";
    public static final String AGENDA_SANITARIOS_EDICION_CONTROLLER = "agendaSanitariosEdicionController";
    public static final String CITAS_LISTADO_CONTROLLER = "citasListadoController";
    public static final String CITAS_EDICION_CONTROLLER = "citasEdicionController";
    public static final String PACIENTES_LISTADO_CONTROLLER = "pacientesListadoController";
    public static final String PACIENTES_EDICION_CONTROLLER = "pacientesEdicionController";
    public static final String SANITARIOS_LISTADO_CONTROLLER = "sanitariosListadoController";
    public static final String SANITARIOS_EDICION_CONTROLLER = "sanitariosEdicionController";
    public static final String TRATAMIENTOS_LISTADO_CONTROLLER = "tratamientosListadoController";
    public static final String TRATAMIENTOS_EDICION_CONTROLLER = "tratamientosEdicionController";
    
    public static final String ERROR_SQLLITE_EXCEPTION = "SQLITE_CONSTRAINT_UNIQUE";
    public static final String ERROR_DNI_PACIENTE = "dni_paciente";
    public static final String ERROR_DNI_SANITARIO = "dni_sanitario";
    
    public static final DateTimeFormatter FORMATEADOR_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

}
