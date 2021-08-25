package es.clinica.podologia.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.clinica.podologia.entidades.Pacientes;

/**
 * <p>Interfaz del Objeto de Acceso a Datos de la tabla {@code pacientes}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface PacientesRepository extends PagingAndSortingRepository<Pacientes, Integer>, JpaRepository<Pacientes, Integer> {
    
    /**
     * <p>Consulta filtrando por {@code PACIENTES.DNI_PACIENTE}./p>
     * 
     * @param dniPaciente {@link String} DNI del paciente
     * 
     * @return {@link Optional}<{@link Pacientes}> debería retornar un único resultado debido a que {@code PACIENTES.DNI_PACIENTE} es único en la tabla
     */
    public Optional<Pacientes> findByDniPaciente(String dniPaciente);
    
    
    /**
     * <p>Consulta filtrando por {@code PACIENTES.DNI_PACIENTE} que comprueba si existe en la tabla./p>
     * 
     * @param dniPaciente {@link String} DNI del paciente
     * 
     * @return {@link Boolean} {@code Boolean.TRUE} en caso de que exista un paciente con ese DNI en la tabla
     */
    public Boolean existsByDniPaciente(String dniPaciente);

}
