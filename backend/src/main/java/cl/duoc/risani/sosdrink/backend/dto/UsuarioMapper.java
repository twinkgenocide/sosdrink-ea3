package cl.duoc.risani.sosdrink.backend.dto;

import cl.duoc.risani.sosdrink.backend.entities.Usuario;

public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getRun(),
            usuario.getNombre(),
            usuario.getApellidos(),
            usuario.getCorreo(),
            usuario.getDireccion(),
            null,
            usuario.getTipoUsuario().getId(),
            usuario.getTipoUsuario().getNombre()
        );
    }

    public Usuario fromDTO(UsuarioDTO usuarioDTO) {
        return new Usuario(
            usuarioDTO.getRun(),
            usuarioDTO.getNombre(),
            usuarioDTO.getApellidos(),
            usuarioDTO.getCorreo(),
            usuarioDTO.getDireccion(),
            null,
            null
        );
    }
    
}
