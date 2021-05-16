package es.clinica.podologia.controladores;

import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.javafx.jfxsupport.GUIState;
import es.clinica.podologia.utilidades.Utilidades;
import javafx.fxml.FXML;

/**
 * <p>Controlador para el listado de citas.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class CitasListadoController {
    
    @Value("${citas.listado.consulta.titulo}")
    private String tituloVista;
    
    @FXML
    public void initialize() {
	
	// Aplicar el título de la vista
        GUIState.getStage().setTitle(Utilidades.comprobarCadena(tituloVista, ""));
        
    }

}
