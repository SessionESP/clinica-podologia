package es.clinica.podologia.utilidades;

import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import es.clinica.podologia.JavaFxApplicationSupport;
import es.clinica.podologia.constantes.Accion;
import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.javafx.jfxsupport.AbstractFxmlView;
import es.clinica.podologia.javafx.jfxsupport.GUIState;
import es.clinica.podologia.javafx.jfxsupport.PropertyReaderHelper;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;

/**
 * <p>Clase con métodos estáticos para la navegación entre vistas.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class UtilidadesNavegacion {
    
    // Trazas
    private static Logger trazas = LoggerFactory.getLogger(UtilidadesNavegacion.class);
    
    /**
     * <p>Constructor privado vacío.</p>
     */
    private UtilidadesNavegacion() {
	throw new IllegalStateException("Constructor privado de la clase de utilidades de navegación.");
    }
    
    /**
     * <p>Método para mostrar una vista.</p>
     * 
     * @param vista {@link AbstractFxmlView} vista que se quiere mostrar
     * @param controlador {@link String} cadena de caracteres que identifica el controlador
     * @param accion {@link Accion} cadena de caracteres identifica la acción con la que se va a abrir la vista
     */
    @SuppressWarnings("unused")
    public static void mostrarVista(
	    final Class<? extends AbstractFxmlView> vista, 
	    String controlador, 
	    Accion accion) {
	
	// Obtener el contexto de la aplicación
	ConfigurableApplicationContext contexto = JavaFxApplicationSupport.getContexto();
	
	try {
	    
	    // Obtener la vista que se quiere mostrar
	    final AbstractFxmlView vistaJavaFXSpringBoot = contexto.getBean(vista);

	    // Comprobar si la escena está vacía
	    if (GUIState.getScene() == null) {
		// Si está vacía, se creará una nueva escena
		GUIState.setScene(new Scene(vistaJavaFXSpringBoot.getView()));
	    } else {
		// En caso de que no esté vacía, se sustituirá por la ruta de la vista que se quiere mostrar
		GUIState.getScene().setRoot(vistaJavaFXSpringBoot.getView());
	    }
	    
	    // Aplicar la escena resultante
	    GUIState.getStage().setScene(GUIState.getScene());
	    
	    // Aplicar propiedades del entorno a la vista
	    aplicarPropiedadesEntornoVista(contexto, controlador, accion);
	    
	    // Añadir los iconos a la vista
	    GUIState.getStage().getIcons().addAll(JavaFxApplicationSupport.getIconos());
	    
	    // Aquí se asignará el controlador correspondiente a la vista
//	    GUIState.getStage().addEventHandler(WindowEvent.WINDOW_SHOWN, e -> {
//		
//		if (vistaJavaFXSpringBoot.getFxmlLoader().getController() instanceof AccesoController) {
//		    // Vista para autenticarse en la aplicación
//		    AccesoController accesoController = (AccesoController) vistaJavaFXSpringBoot.getFxmlLoader().getController();
//		} else if (vistaJavaFXSpringBoot.getFxmlLoader().getController() instanceof CitasListadoController) {
//		    // Vista principal de la aplicación
//		    CitasListadoController citasListadoController = (CitasListadoController) vistaJavaFXSpringBoot.getFxmlLoader().getController();
//		}
//		trazas.debug("Vista que se va a mostrar: {} ", vistaJavaFXSpringBoot.getClass());
//	    });
	    
	    // Mostrar la vista
	    GUIState.getStage().show();
	    
	} catch (Exception excepcion) {
	    
	    trazas.error("La aplicación no ha podido iniciarse: ", excepcion);
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());
	    
	}
    }

    /**
     * <p>Método que recupera una vista del contexto.</p>
     * <p>Implementado para cargar una vista dentro de otra.</p>
     * 
     * @param vista {@link AbstractFxmlView} vista que se quiere mostrar
     * 
     * @return {@link Parent} nodo donde se encapsula la vista para ser añadida en otra
     */
    public static Parent cargarVista(final Class<? extends AbstractFxmlView> vista) {
	
	// Obtener el contexto de la aplicación
	ConfigurableApplicationContext contexto = JavaFxApplicationSupport.getContexto();
	
	// Inicializar el nodo que se va a retornar al final de la aplicación
	Parent nodoVista = null;

	try {

	    // Obtener la vista que se quiere mostrar
	    final AbstractFxmlView vistaJavaFXSpringBoot = contexto.getBean(vista);
	    nodoVista = vistaJavaFXSpringBoot.getView();

	} catch (Exception excepcion) {

	    trazas.error("La vista no ha podido cargarse: ", excepcion);
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}
	
	
	// Retornar el nodo obtenido
	return nodoVista;
	
    }

    /**
     * <p>Aplicar las propiedades del entorno a la vista.</p>
     * 
     * @param contexto {@link ConfigurableApplicationContext} contexto de la aplicación
     * @param controlador {@link String} cadena de caracteres que identifica el controlador
     * @param accion {@link Accion} cadena de caracteres identifica la acción con la que se va a abrir la vista
     */
    private static void aplicarPropiedadesEntornoVista(ConfigurableApplicationContext contexto, String controlador, Accion accion) {

	PropertyReaderHelper.setIfPresent(
		contexto.getEnvironment(), 
		obtenerSubmenuAccion(controlador, accion).add(Constantes.TITULO).toString(), 
		String.class, 
		GUIState.getStage()::setTitle);
	
	PropertyReaderHelper.setIfPresent(
		contexto.getEnvironment(), 
		obtenerSubmenuAccion(controlador, accion).add(Constantes.ALTURA).toString(), 
		Double.class, 
		GUIState.getStage()::setHeight);
	
	PropertyReaderHelper.setIfPresent(
		contexto.getEnvironment(), 
		obtenerSubmenuAccion(controlador, accion).add(Constantes.ANCHURA).toString(), 
		Double.class, 
		GUIState.getStage()::setWidth);
	
	PropertyReaderHelper.setIfPresent(
		contexto.getEnvironment(), 
		obtenerSubmenuAccion(controlador, accion).add(Constantes.REDIMENSIONABLE).toString(), 
		Boolean.class, 
		GUIState.getStage()::setResizable);
	
	// Colocar la ventana en el centro de la pantalla
        Rectangle2D tamanioPantalla = Screen.getPrimary().getVisualBounds();
        GUIState.getStage().setX((tamanioPantalla.getWidth() - GUIState.getStage().getWidth()) / 2); 
        GUIState.getStage().setY((tamanioPantalla.getHeight() - GUIState.getStage().getHeight()) / 2);  
	
    }
    
    /**
     * <p>Método para obtener la primera parte de la clave.</p>
     * 
     * @param controlador {@link String} cadena de caracteres que identifica el controlador
     * @param accion {@link Accion} cadena de caracteres identifica la acción con la que se va a abrir la vista
     * 
     * @return {@link StringJoiner} secuencia de caracteres con la primera parte de la clave
     */
    private static StringJoiner obtenerSubmenuAccion(String controlador, Accion accion) {
	
	// Preparar la secuencia de caracteres que se va a retornar al final del método
	StringJoiner titulo = new StringJoiner(Constantes.PUNTO);
	
	// Añadir las correspondientes partes a la secuencia
	titulo.add(obtenerSubmenu(controlador));
	titulo.add(accion.nombre.toLowerCase());
	
	// Retornar la secuencia de caracteres
	return titulo;
	
    }
    
    
    /**
     * <p>Método que retorna el submenú al que pertenece un controlador</p>
     * 
     * @param controlador {@link String} cadena de caracteres que identifica el controlador
     * 
     * @return {@link String} submenú correspondiente al controlador
     */
    private static String obtenerSubmenu(String controlador) {
	
	// Separar el nombre del controlador en palabras
	String[] controladorSeparado = Utilidades.comprobarCadenaNula(controlador).split(Constantes.PATRON_MAYUSCULAS);
	
	// Retornar la primera palabra en minúsculas
	return Boolean.TRUE.equals(Utilidades.comprobarArray(controladorSeparado)) ? controladorSeparado[0].toLowerCase() : Constantes.CADENA_VACIA;
	
    }
    

//	private static void showErrorAlert(Throwable throwable) {
//		Alert alert = new Alert(AlertType.ERROR, "Oops! An unrecoverable error occurred.\n"  + "Please contact your software vendor.\n\n" + "The application will stop now.");
//		alert.showAndWait().ifPresent(response -> Platform.exit());
//	}

}
