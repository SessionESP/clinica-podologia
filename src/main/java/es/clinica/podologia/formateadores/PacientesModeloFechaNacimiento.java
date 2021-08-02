package es.clinica.podologia.formateadores;

import java.time.LocalDate;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.modelos.PacientesModelo;
import javafx.scene.control.TableCell;

/**
 * <p>Clase donde se dará formato a la fecha de nacimiento del modelo {@code PacientesModelo}.</p>
 * 
 * @author Ignacio Rafael
 * 
 * @see PacientesModelo#fechaNacimientoProperty()
 *
 */
public class PacientesModeloFechaNacimiento extends TableCell<PacientesModelo, LocalDate> {
    
    /**
     * <p>Constructor vacío de la clase.</p>
     */
    public PacientesModeloFechaNacimiento() {
	// Constructor vacío de la clase
    }
    
    /**
     * <p>Método que se sobreescribe para formatear la fecha.</p>
     */
    @Override
    public void updateItem(LocalDate fechaNacimiento, boolean vacio) {
        super.updateItem(fechaNacimiento, vacio);
        setText(fechaNacimiento != null ? Constantes.FORMATEADOR_FECHA.format(fechaNacimiento) : null);
    }
        


}
