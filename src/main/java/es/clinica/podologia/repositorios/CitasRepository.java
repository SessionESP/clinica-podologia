package es.clinica.podologia.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.clinica.podologia.entidades.Citas;

/**
 * <p>Interfaz del Objeto de Acceso a Datos de la tabla {@code citas}.</p>
 *
 * @author Ignacio Rafael
 *
 */
@Repository
public interface CitasRepository extends JpaRepository<Citas, Integer> {
    
    /**
     * <p>Consulta filtrando por la columna CITAS.FECHA</p>
     * 
     * @param fecha {@link String} valor de la fecha por el que se quiere filtrar
     * 
     * @return {@link List} {@link Citas} listado de CITAS
     * 
     * @see Citas
     */
    public List<Citas> findByFecha(String fecha);

}
