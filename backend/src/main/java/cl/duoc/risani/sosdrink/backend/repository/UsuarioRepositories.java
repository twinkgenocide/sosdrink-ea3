package cl.duoc.risani.sosdrink.backend.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;

public interface UsuarioRepositories extends CrudRepository<Usuario, String> {

    
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
