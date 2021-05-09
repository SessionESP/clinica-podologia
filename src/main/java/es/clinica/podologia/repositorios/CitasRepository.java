package es.clinica.podologia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.clinica.podologia.entidades.Citas;

/**
 * <p>Interfaz del Objeto de Acceso a Datos de la tabla {@code citas}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface CitasRepository extends PagingAndSortingRepository<Citas, Integer>, JpaRepository<Citas, Integer> {

}
