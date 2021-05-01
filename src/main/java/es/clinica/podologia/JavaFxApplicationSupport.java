package es.clinica.podologia;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import es.clinica.podologia.constantes.Accion;
import es.clinica.podologia.javafx.jfxsupport.GUIState;
import es.clinica.podologia.utilidades.UtilidadesNavegacion;
import es.clinica.podologia.vistas.AccesoView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * <p>Clase principal de SpringBoot para inicializar la aplciación de JavaFX.</p>
 * 
 * @author Ignacio Rafael
 *
 */
@SpringBootApplication
@PropertySource("classpath:es-ES.properties")
public class JavaFxApplicationSupport extends Application {

	private static Logger logger = LoggerFactory.getLogger(JavaFxApplicationSupport.class);
	private static ConfigurableApplicationContext contexto;
	private static List<Image> iconos = new ArrayList<>();

	/**
	 * <p>Inicializar el contexto de la aplicación.</p>
	 */
	@Override
	public void init() throws Exception {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(JavaFxApplicationSupport.class);
		builder.application().setWebApplicationType(WebApplicationType.NONE);
		contexto = builder.run(getParameters().getRaw().toArray(new String[0]));
		iconos.add(new Image(getClass().getResource("/es/clinica/podologia/imagenes/podologia.png").toExternalForm()));
	}

	/**
	 * <p>Inicializar la aplicación.</p>
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		GUIState.setStage(primaryStage);
		GUIState.setHostServices(this.getHostServices());
		mostrarVistaInicial();
		
	}

	/**
	 * <p>Detener la aplicación.</p>
	 */
	@Override
	public void stop() throws Exception {
	    contexto.close();
	}
	
	/**
	 * <p>Método estático para obtener el contexto de la aplicación.</p>
	 * 
	 * @return the contexto {@link Configurablecontexto}
	 */
	public static ConfigurableApplicationContext getContexto() {
	    return contexto;
	}
	
	/**
	 * <p>Obtener el listado de iconos de la aplicación.</p>
	 * 
	 * @return {@link List} {@link Image} listado de iconos de la aplicación
	 */
	public static List<Image> getIconos() {
	    return iconos;
	}

	/**
	 * <p>Mostrar la vista inicial de la aplicación.</p>
	 */
	private void mostrarVistaInicial() {
	    
	    // Aplicar el estilo configurado en propiedades o uno por defecto en caso de que no se haya indicado
	    final String estilo = contexto.getEnvironment().getProperty("javafx.stage.style");
	    GUIState.getStage().initStyle(Boolean.TRUE.equals(StringUtils.isNotBlank(estilo)) ? StageStyle.valueOf(estilo.toUpperCase()) : StageStyle.DECORATED);
	    
	    // Mostrar la vista
	    UtilidadesNavegacion.mostrarVista(AccesoView.class, "AccesoController", Accion.CONSULTA);
	    
	}
	
	/**
	 * <p>Función principal de la clase.</p>
	 * 
	 * @param args {@link String} array de cadenas de caracteres con los parámetros que se puedan requerir
	 */
	public static void main(String[] args) {
	    launch(args);
	}
	
}
