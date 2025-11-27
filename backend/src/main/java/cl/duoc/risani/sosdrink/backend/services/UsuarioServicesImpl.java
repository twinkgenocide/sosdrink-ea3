package cl.duoc.risani.sosdrink.backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.duoc.risani.sosdrink.backend.entities.Usuario;

import cl.duoc.risani.sosdrink.backend.repository.UsuarioRepositories;;

@Service
public class UsuarioServicesImpl implements UsuarioServices {

    @Autowired
    private UsuarioRepositories usuarioRepositories;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Usuario crear(Usuario usuario) {
        if (usuarioRepositories.existsByCorreo(usuario.getCorreo())) throw new RuntimeException("Usuario ya existe!");

        String claveCifrada = passwordEncoder.encode(usuario.getClave());
        Usuario nuevoUsuario = new Usuario(
            usuario.getRun(),
            usuario.getNombre(),
            usuario.getApellidos(),
            usuario.getCorreo(),
            usuario.getDireccion(),
            claveCifrada,
            usuario.getTipoUsuario()
        );
        return usuarioRepositories.save(nuevoUsuario);
    }

    @Override
    public Usuario obtenerRun(String run) {
        return usuarioRepositories.findById(run)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public List<Usuario> listarTodas() {
        return (List<Usuario>) usuarioRepositories.findAll();
    }

    @Override
    public void eliminar(String run) {
        if (!usuarioRepositories.existsById(run)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepositories.deleteById(run);
    }

    @Override
    public Usuario actualizar(String run, Usuario usuarioActualizado) {
        Usuario existente = obtenerRun(run);
        existente.setNombre(usuarioActualizado.getNombre());
        return usuarioRepositories.save(existente);
    }

}
