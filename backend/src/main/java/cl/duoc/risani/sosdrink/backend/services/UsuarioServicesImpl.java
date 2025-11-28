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
            null,
            usuario.getRun(),
            usuario.getNombre(),
            usuario.getApellidos(),
            usuario.getCorreo(),
            claveCifrada,
            usuario.getDireccion(),
            usuario.getTipoUsuario(),
            null
        );
        return usuarioRepositories.save(nuevoUsuario);
    }

    @Override
    public Usuario obtenerId(Long id) {
        return usuarioRepositories.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public Usuario obtenerCorreo(String correo) {
        return usuarioRepositories.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }


    @Override
    public List<Usuario> listarTodas() {
        return (List<Usuario>) usuarioRepositories.findAll();
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepositories.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepositories.deleteById(id);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        Usuario existente = obtenerId(id);
        existente.setNombre(usuarioActualizado.getNombre());
        return usuarioRepositories.save(existente);
    }

}
