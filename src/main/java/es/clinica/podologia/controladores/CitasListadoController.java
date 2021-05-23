package es.clinica.podologia.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.modelos.CitasModel;
import es.clinica.podologia.servicios.CitasService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para el listado de citas.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class CitasListadoController {
    
    @FXML
    private TextField identificadorTextField;
    
    @FXML
    private DatePicker fechaDesdeDatePicker;
    
    @FXML
    private DatePicker fechaHastaDatePicker;
    
    @FXML
    private TextField pacienteTextField;
    
    @FXML
    private TextField sanitarioTextField;
    
    @FXML
    private TextField tratamientoTextField;

    @FXML
    private Button aceptarButton;
    
    @FXML
    private Button cancelarButton;
    
    @Autowired
    private CitasService citasService;
    
    
    
    @FXML
    public void initialize() {
	
	List<CitasModel> listado = citasService.listarCitas();
	
	listado.isEmpty();
        
    }

}
