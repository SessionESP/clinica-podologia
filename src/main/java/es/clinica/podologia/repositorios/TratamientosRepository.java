package es.clinica.podologia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.clinica.podologia.entidades.Tratamientos;

/**
 * <p>Interfaz del Objeto de Acceso a Datos de la tabla {@code tratamientos}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface TratamientosRepository extends PagingAndSortingRepository<Tratamientos, Integer>, JpaRepository<Tratamientos, Integer> {

}
