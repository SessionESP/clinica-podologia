package es.clinica.podologia.servicios;

import java.util.List;

import es.clinica.podologia.modelos.SanitariosModelo;
import es.clinica.podologia.repositorios.SanitariosRepository;

/**
 * <p>Interfaz del servicio de la tabla {@code sanitarios}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface SanitariosService {
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     * 
     * @return {@link List} {@link SanitariosModelo} listado de Sanitarios
     */
    public List<SanitariosModelo> listarSanitarios();
    
    /**
     * <p>Método que retorna un registro buscado por su identificador.</p>
     * 
     * @param dniSanitario {@link Integer} identificador del sanitario
     * 
     * @return {@link SanitariosModelo} modelo recuperado
     * 
     * @see SanitariosRepository#findById(Integer)
     */
    public SanitariosModelo encontrarSanitario(Integer idSanitario);
    
    /**
     * <p>Método que comprueba si un sanitario existe.</p>
     * 
     * @param idSanitario {@link Integer} identificador del sanitario
     * 
     * @return {@link Boolean} {@code true} en caso de que el identificador se corresponda a un Sanitario existente
     * 
     * @see SanitariosRepository#existsById(Integer)
     */
    public Boolean comprobarExistenciaSanitario(Integer idSanitario);
    
    /**
     * <p>Método que retorna un registro buscado por su identificador DNI.</p>
     * 
     * @param dniSanitario {@link String} identificador DNI del sanitario
     * 
     * @return {@link SanitariosModelo} modelo recuperado
     * 
     * @see SanitariosRepository#findBy
     */
    public SanitariosModelo encontrarSanitarioDNI(String dniSanitario);
    
    /**
     * <p>Método que comprueba si un sanitario existe por su DNI.</p>
     * 
     * @param dniSanitario {@link String} identificador DNI del sanitario
     * 
     * @return {@link Boolean} {@code true} en caso de que el identificador se corresponda a un Sanitario existente
     * 
     * @see SanitariosRepository#existsById(Integer)
     */
    public Boolean comprobarExistenciaSanitarioDNI(String dniSanitario);
    
    /**
     * <p>Método que inserta o actualiza un registro de la tabla.</p>
     * 
     * @param modelo {@link SanitariosModelo} modelo que se quiere insertar o actualizar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el registro se haya insertado/actualizado correctamente
     * 
     * @see SanitariosRepository#saveAndFlush(es.clinica.podologia.entidades.Sanitarios)
     */
    public Boolean insertarActualizarSanitario(SanitariosModelo modelo);
    
    /**
     * <p>Método que elimina un registro de la tabla.</p>
     * 
     * @param dniSanitario {@link String} identificador del sanitario
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el registro se haya eliminado correctamente
     * 
     * @see SanitariosRepository#deleteById(Integer)
     * @see SanitariosRepository#flush()
     * @see SanitariosRepository#existsById(Integer)
     */
    public Boolean eliminarSanitario(Integer idSanitario);

}
