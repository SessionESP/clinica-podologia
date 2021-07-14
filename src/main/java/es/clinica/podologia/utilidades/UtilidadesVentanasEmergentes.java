package es.clinica.podologia.utilidades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import es.clinica.podologia.JavaFxApplicationSupport;
import es.clinica.podologia.constantes.Accion;
import es.clinica.podologia.javafx.jfxsupport.AbstractFxmlView;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * <p>Clase con métodos estáticos para invocar ventanas emergentes de uso común a lo largo de toda la aplicación.</p>
 * 
 * @author Ignacio Rafael
 * 
 * @see Dialog
 *
 */
public class UtilidadesVentanasEmergentes {
    
    // Trazas
    private static Logger trazas = LoggerFactory.getLogger(UtilidadesVentanasEmergentes.class);
    
    /**
     * <p>Constructor privado vacío.</p>
     */
    private UtilidadesVentanasEmergentes() {
	throw new IllegalStateException("Constructor privado de la clase de utilidades de navegación.");
    }

    private static Stage dialogStage;
    private static Scene dialogScene;

    /**
     * <p>Método para abrir una vista dentro de una ventana emergente.</p>
     * 
     * @param vista       {@link AbstractFxmlView} vista que se quiere mostrar
     * @param controlador {@link String} cadena de caracteres que identifica el controlador
     * @param accion      {@link Accion} cadena de caracteres identifica la acción con la que se va a abrir la vista
     */
    public static void abrirVentanaEmergente(
	    final Class<? extends AbstractFxmlView> vista, 
	    String controlador,
	    Accion accion) {

	// Obtener el contexto de la aplicación
	ConfigurableApplicationContext contexto = JavaFxApplicationSupport.getContexto();
	
	try {

	    // Obtener la vista que se quiere mostrar
	    final AbstractFxmlView vistaJavaFXSpringBoot = contexto.getBean(vista);

	    // Comprobar si la escena está vacía
	    if (dialogScene == null) {
		
		// Si está vacía, se creará una nueva escena
		dialogScene = new Scene(vistaJavaFXSpringBoot.getView());
		
	    } else {
		
		// En caso de que no esté vacía, se sustituirá por la ruta de la vista que se quiere mostrar
		dialogScene.setRoot(vistaJavaFXSpringBoot.getView());
		
	    }
	    
	    dialogStage = new Stage();

	    // Aplicar la escena resultante
	    dialogStage.setScene(dialogScene);

	    // Aplicar propiedades del entorno a la vista
	    UtilidadesNavegacion.aplicarPropiedadesEntornoVista(dialogStage, contexto, controlador, accion);

	    // Añadir los iconos a la vista
	    dialogStage.getIcons().addAll(JavaFxApplicationSupport.getIconos());
	    
	    // Evita que se pueda hacer click fuera de la ventana emergente
	    dialogStage.initModality(Modality.APPLICATION_MODAL);

	    // Mostrar el diálogo
	    dialogStage.show();

	} catch (Exception excepcion) {

	    trazas.error("La ventana emergente no ha podido abrirse: ", excepcion);
	    excepcion.printStackTrace();
	    UtilidadesAlertas.mostrarAlertaError(excepcion.getMessage());

	}


    }
    
    /**
     * <p>Método que retorna el {@code Stage} de la ventana emergente.</p>
     * 
     * @return {@link Stage} donde se ubica al ventana emergente
     */
    public static Stage getDialogStage() {
        return dialogStage;
    }

    /**
     * <p>Método para cerrar la ventana emergente activa.</p>
     */
    public static void cerrarVentanaEmergente() {
	dialogStage.hide();
	dialogStage = null;
    }

}
