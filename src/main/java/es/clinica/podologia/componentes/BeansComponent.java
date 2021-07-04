package es.clinica.podologia.componentes;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.Utilidades;

/**
 * <p>Componente para listar y recuperar instancias de {@code Beans} de la aplicación.</p>
 * <p>Aquí se centralizará la llamada a métodos de un controlador a otro, para refrescar las vistas, así como el paso de parámetros.</p>
 * 
 * @author Ignacio Rafael
 *
 */
@Component
public class BeansComponent {
    
    @Autowired
    private ListableBeanFactory listableBeanFactory;
    
    /**
     * <p>Método que recupera la instancia de un {@link Bean} cualquiera.</p>
     * 
     * @param nombre {@link String} nombre del bean
     * 
     * @return {@link Object} instancia del {@code Bean}, será necesario castearlo a su correspondiente
     */
    public Object obtenerComponente(String nombre) {
	return StringUtils.isNotBlank(nombre) ? listableBeanFactory.getBean(nombre) : null;
    }
    
    /**
     * <p>Método que recupera la instancia de un {@link FXMLController} cualquiera.</p>
     * 
     * @param nombre {@link String} nombre del bean
     * 
     * @return {@link Object} instancia del {@code FXMLController}, será necesario castearlo a su correspondiente
     */
    public Object obtenerControlador(String nombre) {
	
	// Inicializar el objeto que se va a devolver al final de la ejecución del método
	Object controlador = null;
	
	// Comprobar que el nombre del controlador NO es nulo
	if(StringUtils.isNotBlank(nombre)) {
	    
	    // Recuperar el mapa con todos los controladores de la aplicación
	    Map<String, Object> controladores = listableBeanFactory.getBeansWithAnnotation(FXMLController.class);
	    
	    // Comprobar que el mapa recuperado NO es nulo NI está vacío
	    if(Boolean.TRUE.equals(Utilidades.comprobarMapa(controladores))) {
		
		// Obtener el controlador que se buscaba
		controlador = controladores.get(nombre);
		
	    }
	    
	}
	
	// Retornar el controlador buscado
	return controlador;
	
    }
    
    /**
     * <p>Devuelve un mapa donde la clave es el nombre del {@code Bean} de cada controlador.</p>
     * 
     * @return {@link Map}<{@link String}, {@link Object}> mapa con un listado de todos los controladores de la aplicación
     */
    public Map<String, Object> listarComtroladores() {
	return listableBeanFactory.getBeansWithAnnotation(FXMLController.class);
    }

}
