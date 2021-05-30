package es.clinica.podologia.formateadores;

import java.time.LocalDate;

import es.clinica.podologia.constantes.Constantes;
import es.clinica.podologia.modelos.CitasModelo;
import javafx.scene.control.TableCell;

/**
 * <p>Clase donde se dará formato a la fecha del modelo {@code CitasModelo}.</p>
 * 
 * @author Ignacio Rafael
 * 
 * @see CitasModelo#fechaProperty()
 *
 */
public class CitasModeloFecha extends TableCell<CitasModelo, LocalDate> {
    
    /**
     * <p>Constructor vacío de la clase.</p>
     */
    public CitasModeloFecha() {
	// Constructor vacío de la clase
    }
    
    /**
     * <p>Método que se sobreescribe para formatear la fecha.</p>
     */
    @Override
    public void updateItem(LocalDate fecha, boolean vacio) {
        super.updateItem(fecha, vacio);
        setText(fecha != null ? Constantes.FORMATEADOR_FECHA.format(fecha) : null);
    }
        


}
