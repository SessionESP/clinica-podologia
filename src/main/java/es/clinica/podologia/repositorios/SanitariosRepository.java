package es.clinica.podologia.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.clinica.podologia.entidades.Sanitarios;

/**
 * <p>Interfaz del Objeto de Acceso a Datos de la tabla {@code sanitarios}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface SanitariosRepository extends PagingAndSortingRepository<Sanitarios, Integer>, JpaRepository<Sanitarios, Integer> {
    
    /**
     * <p>Consulta filtrando por {@code SANITARIOS.DNI_SANITARIO}./p>
     * 
     * @param dniSanitario {@link String} DNI del sanitario
     * 
     * @return {@link Sanitarios} debería retornar un único resultado debido a que {@code SANITARIOS.DNI_SANITARIO} es único en la tabla
     */
    public Optional<Sanitarios> findByDniSanitario(String dniSanitario);
    
    
    /**
     * <p>Consulta filtrando por {@code SANITARIOS.DNI_SANITARIO} que comprueba si existe en la tabla./p>
     * 
     * @param dniSanitario {@link String} DNI del sanitario
     * 
     * @return {@link Boolean} {@code Boolean.TRUE} en caso de que exista un sanitario con ese DNI en la tabla
     */
    public Boolean existsByDniSanitario(String dniSanitario);

}
