package es.clinica.podologia.componentes;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.GenericJDBCException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesTrazas;

/**
 * <p>Clase con validaciones de uso común a lo largo de la aplicación.</p>
 * <p>Originariamente pensada como una clase de utilidades, se ha transformado en un componente para poder inyectar propiedades a través de Spring.</p>
 * 
 * @author Ignacio Rafael
 *
 */
@Component
public class ValidacionesComponent {
    
    @Value("${pacientes.error.dni.duplicado}")
    private String dniPacienteDuplicado;
    
    @Value("${sanitarios.error.dni.duplicado}")
    private String dniSanitarioDuplicado;

    @Value("${error.sqlite.clave.unica}")
    private String sqliteClaveDuplicada;
    
    /**
     * <p>Método que visualiza un mensaje de error tanto en las trazas como a través de una alerta informativa.</p>
     * 
     * @param excepcion {@link Exception} excepción que se quiere visualizar
     * @param trazas {@link Logger} interfaz de trazas para visualizar
     */
    public void visualizarError(Exception excepcion, Logger trazas) {
	
	// Comprobar que los parámetros de entrada NO son nulos
	if(excepcion != null && trazas != null) {
	    
	    // Obtener el texto con la traza completa del error
	    String textoError = Utilidades.comprobarCadena(ExceptionUtils.getStackTrace(excepcion), Constantes.CADENA_VACIA);
		
	    // Comprobar si se trata del error de intentar guardar duplicar un campo clave
	    if(textoError.contains(Constantes.ERROR_SQLLITE_EXCEPTION)) {
		
		// Visualizar un error de tipo SQLiteException
		visualizarErrorSQL(excepcion, textoError, trazas);
		
	    } else {
		
		// Visualizar un error sin clasificar explícitamente
		visualizarErrorSinClasificar(excepcion, trazas);
	    }
	    
	}
	
    }
    
    /**
     * <p>Método que visualiza un mensaje de error que no está clasificado dentro de este componente.</p>
     * 
     * @param excepcion {@link Exception} excepción que se quiere visualizar
     * @param trazas {@link Logger} interfaz de trazas para visualizar
     */
    private void visualizarErrorSinClasificar(Exception excepcion, Logger trazas) {
	
	// Imprimer la traza de error
	UtilidadesTrazas.imprimirTrazaError(trazas, excepcion.getMessage());
	
	// Imprimir el mensaje de error completo
	excepcion.printStackTrace();
	
	// Mostrar alerta de error con el mensaje de error de la traza
	UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
	
    }
    
    /**
     * <p>Método que visualiza un mensaje de error de tipo {@code GenericJDBCException}.</p>
     * 
     * @param excepcion {@link GenericJDBCException} excepción que se ha producido al realizar algún tipo de operación con la base de datos
     * @param trazas {@link Logger} interfaz de trazas para visualizar
     * 
     * @see GenericJDBCException
     */
    private void visualizarErrorSQL(Exception excepcion, String textoError, Logger trazas) {
	    
	// Inicializar el mensaje de error
	String mensajeError = null;

	// Comprobar la tabla y columna donde se ha producido el error
	if (textoError.contains(Constantes.ERROR_DNI_PACIENTE)) {

	    // El error se ha producido en PACIENTES.DNI_PACIENTE
	    mensajeError = dniPacienteDuplicado;

	} else if (textoError.contains(Constantes.ERROR_DNI_SANITARIO)) {

	    // El error se ha producido en SANITARIOS.DNI_SANITARIO
	    mensajeError = dniSanitarioDuplicado;

	} else {

	    // El error se ha producido en otra tabla
	    mensajeError = sqliteClaveDuplicada;

	}

	// Imprimer la traza de error
	UtilidadesTrazas.imprimirTrazaError(trazas, excepcion.getMessage());

	// Imprimir el mensaje de error completo
	excepcion.printStackTrace();

	// Mostrar alerta de error con el mensaje de error de la traza
	UtilidadesAlertas.mostrarAlertaError(mensajeError);
	
    }
    

}
