package es.clinica.podologia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.clinica.podologia.entidades.Pacientes;

/**
 * <p>Interfaz del Objeto de Acceso a Datos de la tabla {@code pacientes}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface PacientesRepository extends PagingAndSortingRepository<Pacientes, String>, JpaRepository<Pacientes, String> {

}
