package es.clinica.podologia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.clinica.podologia.entidades.Sanitarios;

/**
 * <p>Interfaz del Objeto de Acceso a Datos de la tabla {@code sanitarios}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface SanitariosRepository extends PagingAndSortingRepository<Sanitarios, String>, JpaRepository<Sanitarios, String> {

}
