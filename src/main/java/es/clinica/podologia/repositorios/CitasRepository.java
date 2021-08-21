package es.clinica.podologia.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.clinica.podologia.entidades.Citas;
import es.clinica.podologia.entidades.Sanitarios;

/**
 * <p>Interfaz del Objeto de Acceso a Datos de la tabla {@code citas}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Repository
public interface CitasRepository extends JpaRepository<Citas, Integer> {
    
    /**
     * <p>Consulta filtrando por la columna {@code CITAS.FECHA}.</p>
     * 
     * @param fecha {@link Long} valor de la fecha por el que se quiere filtrar
     * 
     * @return {@link List}<{@link Citas}> listado de {@code CITAS} coincidentes
     * 
     * @see Citas
     */
    public List<Citas> findByFecha(Long fecha);
    
    /**
     * <p>Consulta filtrando por la columna {@code CITAS.FECHA}, retornando los registros con una fecha menor.</p>
     * 
     * @param fecha {@link Long} valor de la fecha por el que se quiere filtrar
     * 
     * @return {@link List}<{@link Citas}> listado de {@code CITAS} coincidentes
     * 
     * @see Citas
     */
    public List<Citas> findByFechaBefore(Long fecha);
    
    /**
     * <p>Consulta filtrando por la columna {@code CITAS.FECHA}, retornando los registros con una fecha mayor.</p>
     * 
     * @param fecha {@link Long} valor de la fecha por el que se quiere filtrar
     * 
     * @return {@link List}<{@link Citas}> listado de {@code CITAS} coincidentes
     * 
     * @see Citas
     */
    public List<Citas> findByFechaAfter(Long fecha);
    
    /**
     * <p>Consulta filtrando por la columna {@code CITAS.FECHA}, retornando los registros con una fecha dentro del rango.</p>
     * 
     * @param inicio {@link Long} valor de la fecha de inicio por la que se quiere filtrar
     * @param fin {@link Long} valor de la fecha de fin por la que se quiere filtrar
     * 
     * @return {@link List}<{@link Citas}> listado de {@code CITAS} coincidentes
     * 
     * @see Citas
     */
    public List<Citas> findByFechaBetween(Long inicio, Long fin);
    
    /**
     * <p>Consulta filtrando por las columnas: </p>
     * <ul>
     * <li>Igual a {@code CITAS.FECHA}</li>
     * <li>Igual a {@code CITAS.DNI_SANITARIO}</li>
     * </ul>
     * 
     * @param fecha {@link Long} valor de la fecha por el que se quiere filtrar
     * @param sanitario {@link Sanitarios} valor del sanitario por el que se quiere filtrar
     * 
     * @return {@link List}<{@link Citas}> listado de {@code CITAS} coincidentes
     */
    public List<Citas> findByFechaAndSanitario(Long fecha, Sanitarios sanitario);
    
    /**
     * <p>Consulta filtrando por las columnas: </p>
     * <ul>
     * <li>Igual a {@code CITAS.FECHA}</li>
     * <li>Menor o igual a {@code CITAS.HORA_DESDE}</li>
     * <li>Mayor que {@code CITAS.HORA_HASTA}</li>
     * <li>Igual a {@code CITAS.DNI_SANITARIO}</li>
     * </ul>
     * 
     * @param fecha {@link Long} valor de la fecha por el que se quiere filtrar
     * @param horaDesde {@link Long} valor de la hora desde la que se quiere filtrar
     * @param FechaHasya {@link Long} valor de la hora hasta la que se quiere filtrar
     * @param sanitario {@link Sanitarios} valor del sanitario por el que se quiere filtrar
     * 
     * @return {@link List}<{@link Citas}> listado de {@code CITAS} coincidentes
     */
    public List<Citas> findByFechaAndHoraDesdeLessThanEqualAndHoraHastaGreaterThanAndSanitario(Long fecha, Long horaDesde, Long fechaHasta, Sanitarios sanitario);
    
    /**
     * <p>Eliminación filtrando por la columna {@code CITAS.FECHA}.</p>
     * 
     * @param inicio {@link Long} valor de la fecha de inicio por la que se quiere filtrar
     * @param fin {@link Long} valor de la fecha de fin por la que se quiere filtrar
     * 
     * @see Citas
     */
    @Transactional
    public void deleteByFechaBetween(Long inicio, Long fin);

}
