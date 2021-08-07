package es.clinica.podologia.servicios;

import java.time.LocalDate;
import java.util.List;

import es.clinica.podologia.modelos.CitasModelo;
import es.clinica.podologia.repositorios.CitasRepository;

/**
 * <p>Interfaz del servicio de la vista {@code vista_citas}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface CitasService {
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     * 
     * @return {@link List}<{@link CitasModelo}> listado de {@code CitasModelo}
     */
    public List<CitasModelo> listarCitas();
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista para una determinada fecha.</p>
     * 
     * @param fecha {@link LocalDate} fecha para la que se quiere buscar las citas
     * 
     * @return {@link List}<{@link CitasModelo}> listado de {@code CitasModelo}
     */
    public List<CitasModelo> listarCitasPorFecha(LocalDate fecha);
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista para un determinado rango de fechas.</p>
     * 
     * @param fechaInicial {@link LocalDate} fecha inicial para la que se quiere buscar las citas
     * @param fechaFinal {@link LocalDate} fecha final para la que se quiere buscar las citas
     * 
     * @return {@link List}<{@link CitasModelo}> listado de {@code CitasModelo}
     */
    public List<CitasModelo> listarCitasPorRangoDeFechas(LocalDate fechaInicial, LocalDate fechaFinal);
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista para una determinada fecha y sanitario.</p>
     * 
     * @param fecha {@link LocalDate} fecha para la que se quiere buscar las citas
     * @param dniSanitario {@link String} DNI identificador del sanitario
     * 
     * @return {@link List}<{@link CitasModelo}> listado de {@code CitasModelo}
     */
    public List<CitasModelo> listarCitasPorFechaYSanitario(LocalDate fecha, String dniSanitario);
    
    /**
     * <p>Método que retorna un registro buscado por su identificador.</p>
     * 
     * @param identificador {@link Integer} identificador del registro que se quiere recuperar
     * 
     * @return {@link CitasModelo} modelo recuperado
     * 
     * @see CitasRepository#findById(Integer)
     */
    public CitasModelo encontrarCita(Integer identificador);
    
    /**
     * <p>Método que comprueba si una cita existe.</p>
     * 
     * @param identificador {@link Integer} identificador del registro que se quiere recuperar
     * 
     * @return {@link Boolean} {@code true} en caso de que el identificador se corresponda a una cita existente
     * 
     * @see CitasRepository#existsById(Integer)
     */
    public Boolean comprobarExistenciaCita(Integer identificador);
    
    /**
     * <p>Método que inserta o actualiza un registro de la tabla.</p>
     * 
     * @param modelo {@link CitasModelo} modelo que se quiere insertar o actualizar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el registro se haya insertado/actualizado correctamente
     * 
     * @see CitasRepository#saveAndFlush(es.clinica.podologia.entidades.Citas)
     */
    public Boolean insertarActualizarCita(CitasModelo modelo);
    
    /**
     * <p>Método que elimina un registro de la tabla.</p>
     * 
     * @param identificador {@link Integer} identificador del registro que se quiere eliminar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el registro se haya eliminado correctamente
     * 
     * @see CitasRepository#deleteById(Integer)
     * @see CitasRepository#flush()
     * @see CitasRepository#existsById(Integer)
     */
    public Boolean eliminarCita(Integer identificador);
    
    /**
     * <p>Método que elimina un grupo de citas dentro de un determinado rango de fechas.</p>
     * 
     * @param fechaInicial {@link LocalDate} fecha inicial para la que se quiere eliminar las citas
     * @param fechaFinal {@link LocalDate} fecha final para la que se quiere eliminar las citas
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que los registros se hayan eliminado correctamente
     */
    public Boolean eliminarCitasPorRangoDeFechas(LocalDate fechaInicial, LocalDate fechaFinal);

}
