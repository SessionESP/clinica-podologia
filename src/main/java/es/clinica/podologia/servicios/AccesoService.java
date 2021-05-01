package es.clinica.podologia.servicios;

/**
 * <p>
 * Interfaz del servicio de la tabla {@code acceso}.
 * </p>
 *
 * @author Ignacio Rafael
 *
 */
public interface AccesoService {

    /**
     * <p>
     * Método que autentica un usuario para darle acceso a la aplicación.
     * </p>
     *
     * @param usuario    {@link String} usuario que se quiere comprobar
     * @param contrasena {@link String} contraseña con la que se quiere autenticar
     *
     * @return {@link Boolean} retorna {@code true} en caso de que la combinación de
     *         ambos parámetros exista en la tabla {@code acceso}
     */
    public Boolean autenticarUsuario(String usuario, String contrasena);

}
