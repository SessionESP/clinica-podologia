package es.clinica.podologia.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.constantes.Accion;
import es.clinica.podologia.entidades.Citas;
import es.clinica.podologia.entidades.Pacientes;
import es.clinica.podologia.entidades.Sanitarios;
import es.clinica.podologia.entidades.Tratamientos;
import es.clinica.podologia.javafx.jfxsupport.FXMLController;
import es.clinica.podologia.javafx.jfxsupport.GUIState;
import es.clinica.podologia.servicios.AccesoService;
import es.clinica.podologia.servicios.CitasService;
import es.clinica.podologia.servicios.PacientesService;
import es.clinica.podologia.servicios.SanitariosService;
import es.clinica.podologia.servicios.TratamientosService;
import es.clinica.podologia.utilidades.Utilidades;
import es.clinica.podologia.utilidades.UtilidadesAlertas;
import es.clinica.podologia.utilidades.UtilidadesNavegacion;
import es.clinica.podologia.vistas.PrincipalView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * <p>Controlador para el Acceso.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class AccesoController {
    
    @Value("${acceso.consulta.titulo}")
    private String tituloVista;

    @FXML
    private TextField usuarioTextField;

    @FXML
    private PasswordField contrasenaPasswordField;

    @FXML
    private Button aceptarButton;

    @Autowired
    private AccesoService accesoService;
    
    @Autowired
    private CitasService citasService;
    
    @Autowired
    private PacientesService pacientesService;
    
    @Autowired
    private SanitariosService sanitariosService;
    
    @Autowired
    private TratamientosService tratamientosService;
    
    @FXML
    public void initialize() {
	
	// Aplicar el título de la vista
        GUIState.getStage().setTitle(Utilidades.comprobarCadena(tituloVista, ""));
        
    }

    @FXML
    private void autenticarUsuario() {
	
	List<Pacientes> listadoPacientes = pacientesService.listarPacientes();
	
	List<Sanitarios> listadoSanitarios = sanitariosService.listarSanitarios();
	
	List<Tratamientos> listadoTratamientos = tratamientosService.listarTratamientos();
	
	List<Citas> listadoCitas = citasService.listarCitas();

	// Realizar la consulta en la tabla de la base de datos
	Boolean autenticado = accesoService.autenticarUsuario(usuarioTextField.getText(), contrasenaPasswordField.getText());

	// Generar la alerta correspondiente dependiendo del resultado de la consulta
	Optional<ButtonType> alerta = Boolean.TRUE.equals(autenticado) ? UtilidadesAlertas.mostrarAlertaConfirmacion("Acceso concedido") : UtilidadesAlertas.mostrarAlertaError("Acceso denegado");

	// Comprobar sii la conulta ha retornado 'true' y el usuario ha pulsado el botón 'aceptar' en la alerta de confirmación
	if (Boolean.TRUE.equals(autenticado) && Boolean.TRUE.equals(alerta.isPresent()) && alerta.get() == ButtonType.OK) {

	    // Mostrar la vista principal de la aplicación
	    UtilidadesNavegacion.mostrarVista(PrincipalView.class, "PrincipalController", Accion.CONSULTA);
	}
    }

}
