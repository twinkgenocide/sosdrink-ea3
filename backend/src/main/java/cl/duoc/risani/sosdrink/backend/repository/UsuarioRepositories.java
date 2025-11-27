package cl.duoc.risani.sosdrink.backend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;

public interface UsuarioRepositories extends CrudRepository<Usuario, Long> {
    
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);

}
