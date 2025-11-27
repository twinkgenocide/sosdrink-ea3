package cl.duoc.risani.sosdrink.backend.security;


import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import cl.duoc.risani.sosdrink.backend.entities.Usuario;
import cl.duoc.risani.sosdrink.backend.repository.UsuarioRepositories;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositories usuarioRepo;

    public CustomUserDetailsService(UsuarioRepositories usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con correo '" + correo + "' no encontrado."));
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getTipoUsuario().getNombre()));
        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),
                usuario.getClave(),    
                true,
                true,
                true,
                true,
                authorities
        );
    }
}
