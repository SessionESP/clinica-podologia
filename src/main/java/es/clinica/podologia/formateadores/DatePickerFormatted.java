package es.clinica.podologia.formateadores;

import java.time.LocalDate;

import es.clinica.podologia.utilidades.UtilidadesConversores;
import javafx.util.StringConverter;

/**
 * <p>Clase para dar formato y funcionalidad a los recogedores de fechas de la aplicación.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class DatePickerFormatted extends StringConverter<LocalDate> {
    
    /**
     * <p>Constructor vacío de la clase.</p>
     */
    public DatePickerFormatted() {
	// Constructor vacío de la clase
    }

    @Override
    public String toString(LocalDate fecha) {
	return UtilidadesConversores.convertirFechaCadena(fecha);
    }

    @Override
    public LocalDate fromString(String cadena) {
	return UtilidadesConversores.convertirCadenaFecha(cadena);
    }

}
