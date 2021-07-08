package es.clinica.podologia.servicios;

import java.util.List;

import es.clinica.podologia.modelos.TratamientosModelo;
import es.clinica.podologia.repositorios.TratamientosRepository;

/**
 * <p>Interfaz del servicio de la tabla {@code tratamientos}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface TratamientosService {
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     * 
     * @return {@link List}<{@link TratamientosModelo}> listado de Tratamientos
     * 
     * @see TratamientosRepository#findAll()
     */
    public List<TratamientosModelo> listarTratamientos();
    
    /**
     * <p>Método que retorna un registro buscado por su identificador.</p>
     * 
     * @param identificador {@link Integer} identificador del registro que se quiere recuperar
     * 
     * @return {@link TratamientosModelo} modelo recuperado
     * 
     * @see TratamientosRepository#findById(Integer)
     */
    public TratamientosModelo encontrarTratamiento(Integer identificador);
    
    /**
     * <p>Método que comprueba si un tratamiento existe.</p>
     * 
     * @param identificador {@link Integer} identificador del registro que se quiere recuperar
     * 
     * @return {@link Boolean} {@code true} en caso de que el identificado se corresponda a un tratamiento existente
     * 
     * @see TratamientosRepository#existsById(Integer)
     */
    public Boolean comprobarExistenciaTratamiento(Integer identificador);
    
    /**
     * <p>Método que inserta o actualiza un registro de la tabla.</p>
     * 
     * @param modelo {@link TratamientosModelo} modelo que se quiere insertar o actualizar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el registro se haya insertado/actualizado correctamente
     * 
     * @see TratamientosRepository#saveAndFlush(es.clinica.podologia.entidades.Tratamientos)
     */
    public Boolean insertarActualizarTratamiento(TratamientosModelo modelo);
    
    /**
     * <p>Método que elimina un registro de la tabla.</p>
     * 
     * @param identificador {@link Integer} identificador del registro que se quiere eliminar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el registro se haya eliminado correctamente
     * 
     * @see TratamientosRepository#deleteById(Integer)
     * @see TratamientosRepository#flush()
     * @see TratamientosRepository#existsById(Integer)
     */
    public Boolean eliminarTratamiento(Integer identificador);

}
