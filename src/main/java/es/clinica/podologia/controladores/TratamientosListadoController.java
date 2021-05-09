package es.clinica.podologia.controladores;

import org.springframework.beans.factory.annotation.Value;

import es.clinica.podologia.javafx.jfxsupport.FXMLController;

/**
 * <p>Controlador para los Tratamientos.</p>
 *
 * @author Ignacio Rafael
 *
 */
@FXMLController
public class TratamientosListadoController {
    
    @Value("${tratamientos.listado.alta.titulo}")
    private String tituloAltaVista;
    
    @Value("${tratamientos.listado.edicion.titulo}")
    private String tituloEdicionVista;

}
