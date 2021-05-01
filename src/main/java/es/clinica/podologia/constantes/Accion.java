package es.clinica.podologia.constantes;

/**
 * <p>Enumerados que representan los modos en que una vista se abrirá.</p>
 * 
 * @author Ignacio Rafael
 *
 */
public enum Accion {

    ALTA("Alta"), BAJA("Baja"), MODIFICACION("Modificación"), CONSULTA("Consulta");

    // Atributo con la acción
    public final String nombre;

    /**
     * <p>Constructor de la clase de enumerados.</>
     * 
     * @param nombre {@link String} valor con el que se quiere inicializar
     */
    private Accion(String nombre) {
	this.nombre = nombre;
    }

}
