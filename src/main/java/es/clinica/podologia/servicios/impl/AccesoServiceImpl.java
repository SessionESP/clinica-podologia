package es.clinica.podologia.servicios.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.clinica.podologia.entidades.Acceso;
import es.clinica.podologia.repositorios.AccesoRepository;
import es.clinica.podologia.servicios.AccesoService;

/**
 * <p>
 * Implementación de la interfaz del servicio de la tabla {@code acceso}.
 * </p>
 *
 * @author Ignacio Rafael
 *
 */
@Service()
public class AccesoServiceImpl implements AccesoService {

    @Autowired
    private AccesoRepository accesoRepository;

    /**
     * <p>
     * Método que autentica un usuario para darle acceso a la aplicación.
     * </p>
     */
    @Override
    public Boolean autenticarUsuario(String usuario, String contrasena) {
	Optional<Acceso> acceso = accesoRepository.findByUsuarioAndContrasena(usuario, contrasena);
	return acceso.isPresent();
    }

}