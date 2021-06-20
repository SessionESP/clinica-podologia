/**
 * 
 */
package es.clinica.podologia.utilidades;

import org.springframework.context.ConfigurableApplicationContext;

import es.clinica.podologia.JavaFxApplicationSupport;
import es.clinica.podologia.constantes.Accion;
import es.clinica.podologia.javafx.jfxsupport.AbstractFxmlView;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

/**
 * <p>
 * Clase con métodos estáticos para invocar ventanas emergentes de uso común a
 * lo largo de toda la aplicación.
 * </p>
 * 
 * @author Ignacio Rafael
 * 
 * @see Dialog
 *
 */
public class UtilidadesVentanasEmergentes {

    private static Stage dialogStage;
    private static Scene dialogScene;

    /**
     * <p>
     * Método para abrir una vista dentro de una ventana emergente.
     * </p>
     * 
     * @param vista       {@link AbstractFxmlView} vista que se quiere mostrar
     * @param controlador {@link String} cadena de caracteres que identifica el
     *                    controlador
     * @param accion      {@link Accion} cadena de caracteres identifica la acción
     *                    con la que se va a abrir la vista
     */
    public static void abrirVentanaEmergente(final Class<? extends AbstractFxmlView> vista, String controlador,
	    Accion accion) {

	// Obtener el contexto de la aplicación
	ConfigurableApplicationContext contexto = JavaFxApplicationSupport.getContexto();

	// Obtener la vista que se quiere mostrar
	final AbstractFxmlView vistaJavaFXSpringBoot = contexto.getBean(vista);

	dialogStage = new Stage();

	dialogScene = new Scene(vistaJavaFXSpringBoot.getView());
	dialogStage.setTitle("Dialog");
	dialogStage.setScene(dialogScene);
	dialogStage.show();
    }
    
    public static void cerrarVentanaEmergente() {
	dialogStage.hide();
	dialogStage.setScene(null);
	dialogScene = null;
    }

}
