package es.clinica.podologia.utilidades;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.clinica.podologia.constantes.Constantes;

/**
 * <p>Clase con métodos estáticos de uso común para el manejo de propiedades a lo largo de toda la aplicación.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class UtilidadesPropiedades {
    
    private static final Logger TRAZAS = LoggerFactory.getLogger(UtilidadesPropiedades.class);
    
    /**
     * <p>Constructor privado vacío.</p>
     */
    private UtilidadesPropiedades() {
	throw new IllegalStateException("Constructor privado de la clase de utilidades de propiedades.");
    }
    
    
    /**
     * <p>Método que crea un constructor para poder utilizar las propiedades de un fichero externo.</p>
     * 
     * @param parametros {@link Parameters} parámetros adicionales para crear el constructor
     * @param ubicacion {@link String} localización del fichero de propiedades, puede ser una ruta relativa o absoluta
     * @param delimitador {@link String} cadena con el caracter delimitador en el caso de las listas
     * 
     * @return {@link FileBasedConfigurationBuilder} {@link FileBasedConfiguration} constructor del fichero de propiedades
     */
    public static FileBasedConfigurationBuilder<FileBasedConfiguration> crearConstructor(Parameters parametros, String ubicacion, String delimitador) {
	
	// Inicializar el constructor que se va a retornar al final del método
	FileBasedConfigurationBuilder<FileBasedConfiguration> constructor = null;
	
	// Comprobar que los parámetros obligatorios NO son nulos
	if(parametros != null && StringUtils.isNotBlank(ubicacion)) {
	    
	    // Asignar un valor por defecto al delimitador de listas
	    delimitador = Utilidades.comprobarCadena(delimitador, Constantes.COMA);
	    
	    constructor = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		    .configure(parametros.properties().setFileName(ubicacion)
			    .setListDelimiterHandler(new DefaultListDelimiterHandler(delimitador.charAt(0))));
	    
	}
	
	
	// Retornar el constructor
	return constructor;
	
    }
    
    
    /**
     * <p>Método que guarda una propiedad en el fichero correspondiente.</p>
     * 
     * @param constructor {@link FileBasedConfigurationBuilder} {@link FileBasedConfiguration} del fichero de propiedades
     * @param identificador {@link String} de la propiedad que se quiere guardar
     * @param valor {@link Object} de la propiedad que se quiere guardar
     */
    public static void guardarPropiedad(FileBasedConfigurationBuilder<FileBasedConfiguration> constructor, String identificador, Object valor) {
	
	// Comprobar que ninguno de los parámetros necesarios es nulo
	if(constructor != null && StringUtils.isNotBlank(identificador)) {
	    
	    try {
		
		// Asignar el valor de la propiedad correspondiente
		constructor.getConfiguration().setProperty(identificador, valor);
		
		// Guardar las propiedades
		constructor.save();
		
	    } catch (ConfigurationException excepcion) {

		// Error al intentar guardar las propiedades del fichero en un objeto
		TRAZAS.error(excepcion.getMessage());
		excepcion.printStackTrace();
		UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	    }
	    
	}
	
    }

}
