package cl.duoc.risani.sosdrink.backend.services;

import cl.duoc.risani.sosdrink.backend.entities.Usuario;
import java.util.List;

public interface UsuarioServices {

    Usuario crear(Usuario tipoProducto);

    Usuario obtenerId(Long id);

    Usuario obtenerCorreo(String correo);

    List<Usuario> listarTodas();

    void eliminar(Long id);

    Usuario actualizar(Long id, Usuario usuarioActualizado);

}
