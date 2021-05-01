package es.clinica.podologia.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.clinica.podologia.entidades.Acceso;

/**
 * <p>
 * Interfaz del Objeto de Acceso a Datos de la tabla {@code acceso}.
 * </p>
 *
 * @author Ignacio Rafael
 *
 */
public interface AccesoRepository extends PagingAndSortingRepository<Acceso, String>, JpaRepository<Acceso, String> {

    /**
     * <p>
     * Consulta que busca autentica un usuario filtrando por su identificador
     * (usuario) y la contraseña.
     * </p>
     * 
     * @param usuario    {@link String} identificador del usuario
     * @param contrasena {@link String} contraseña del usuario
     * 
     * @return {@link Optional} {@link Acceso} contenedor con el objeto encontrado
     */
    public Optional<Acceso> findByUsuarioAndContrasena(String usuario, String contrasena);
}