package es.clinica.podologia.formateadores;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 * <p>Clase que extiende de {@code TextField} añadiendo formato predeterminado.</p>
 * <p>Útil en caso de querer cajas de texto que solo admitan valores numéricos.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public class CajaTextoFormateada extends TextField {
    
    private String patron;
    
    private Integer longitud;
    
    /**
     * <p>Constructor vacío.</p>
     */
    public CajaTextoFormateada() {
	super();
    }
    
    /**
     * <p>Constructor con el patrón y la longitud de la caja de texto.</p>
     * 
     * @param patron {@link String} patrón que se quiere validar
     * @param longitud
     */
    public CajaTextoFormateada(String patron, Integer longitud) {
	super();
	setPatron(patron);
	setLongitud(longitud);
    }
    
    /**
     * <p>Patrón con el que se quiere validar la entrada del usuario en la caja de texto.</p>
     * 
     * @param patron {@link String} patrón que se quiere validar
     */
    public void setPatron(String patron) {
        this.patron = patron;
    }
    
    /**
     * <p>Número de caracteres máximos con los que se quieren validar la entrada del usuario en la caja de texto.</p>
     * 
     * @param longitud {@link Integer} número de caracteres máximos de la caja de texto
     */
    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }
    
    UnaryOperator<TextFormatter.Change> validarFormato = cambio -> {
	if (cambio.getText().matches(patron)) {
	    return cambio; // if change is a number
	} else {
	    cambio.setText(""); // else make no change
	    cambio.setRange( // don't remove any selected text either.
		    cambio.getRangeStart(), cambio.getRangeStart());
	    return cambio;
	}
    };

}
