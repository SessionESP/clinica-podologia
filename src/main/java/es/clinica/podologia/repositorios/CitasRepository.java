package es.clinica.podologia.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
     * <p>Consulta filtrando por la columna {@code CITAS.FECHA}</p>
     * 
     * @param fecha {@link String} valor de la fecha por el que se quiere filtrar
     * 
     * @return {@link List}<{@link Citas}> listado de {@code CITAS} coincidentes
     * 
     * @see Citas
     */
    public List<Citas> findByFecha(String fecha);
    
    /**
     * <p>Consulta filtrando por las columnas: </p>
     * <ul>
     * <li>{@code CITAS.FECHA}</li>
     * <li>{@code CITAS.DNI_SANITARIO}</li>
     * </ul>
     * 
     * @param fecha {@link String} valor de la fecha por el que se quiere filtrar
     * @param sanitario {@link Sanitarios} valor del sanitario por el que se quiere filtrar
     * 
     * @return {@link List}<{@link Citas}> listado de {@code CITAS} coincidentes
     */
    public List<Citas> findByFechaAndSanitarios(String fecha, Sanitarios sanitario);

}
