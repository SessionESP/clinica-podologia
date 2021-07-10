package es.clinica.podologia.servicios;

import java.util.List;

import es.clinica.podologia.modelos.PacientesModelo;
import es.clinica.podologia.repositorios.PacientesRepository;

/**
 * <p>Interfaz del servicio de la tabla {@code pacientes}.</p>
 *
 * @author Ignacio Rafael
 *
 */
public interface PacientesService {
    
    /**
     * <p>Método que retorna un listado con todos los registros de la vista.</p>
     * 
     * @return {@link List} {@link PacientesModelo} listado de Pacientes
     */
    public List<PacientesModelo> listarPacientes();
    
    /**
     * <p>Método que retorna un registro buscado por su identificador DNI.</p>
     * 
     * @param dniPaciente {@link String} identificador DNI del paciente
     * 
     * @return {@link PacientesModelo} modelo recuperado
     * 
     * @see PacientesRepository#findById(String)
     */
    public PacientesModelo encontrarPaciente(String dniPaciente);
    
    /**
     * <p>Método que comprueba si un paciente existe.</p>
     * 
     * @param dniPaciente {@link String} identificador DNI del paciente
     * 
     * @return {@link Boolean} {@code true} en caso de que el identificador se corresponda a un Paciente existente
     * 
     * @see PacientesRepository#existsById(String)
     */
    public Boolean comprobarExistenciaPaciente(String dniPaciente);
    
    /**
     * <p>Método que inserta o actualiza un registro de la tabla.</p>
     * 
     * @param modelo {@link PacientesModelo} modelo que se quiere insertar o actualizar
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el registro se haya insertado/actualizado correctamente
     * 
     * @see PacientesRepository#saveAndFlush(es.clinica.podologia.entidades.Pacientes)
     */
    public Boolean insertarActualizarPaciente(PacientesModelo modelo);
    
    /**
     * <p>Método que elimina un registro de la tabla.</p>
     * 
     * @param dniPaciente {@link String} identificador DNI del paciente
     * 
     * @return {@link Boolean} retorna {@code true} en caso de que el registro se haya eliminado correctamente
     * 
     * @see PacientesRepository#deleteById(String)
     * @see PacientesRepository#flush()
     * @see PacientesRepository#existsById(String)
     */
    public Boolean eliminarPaciente(String dniPaciente);

}
