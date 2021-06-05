package es.clinica.podologia.controladores;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.utilidades.UtilidadesNavegacion;
import es.clinica.podologia.vistas.AgendaEdicionView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

/**
 * <p>
 * Controlador para la vista principal de la aplicación.
 * </p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class PrincipalEdicionController {

    @FXML
    private AnchorPane agendaAnchorPane;

    /**
     * <p>
     * Método que se ejecuta al inicializarse la vista principal de la aplicación.
     * </p>
     */
    @FXML
    public void initialize() {

	agendaAnchorPane.getChildren().add(UtilidadesNavegacion.cargarVista(AgendaEdicionView.class));

    }

}
