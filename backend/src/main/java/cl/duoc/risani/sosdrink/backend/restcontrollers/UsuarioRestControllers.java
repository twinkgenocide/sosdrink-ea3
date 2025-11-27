package cl.duoc.risani.sosdrink.backend.restcontrollers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.risani.sosdrink.backend.entities.Usuario;
import cl.duoc.risani.sosdrink.backend.services.UsuarioServices;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")

public class UsuarioRestControllers {

    @Autowired
    private UsuarioServices usuarioservices;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevousuario = usuarioservices.crear(usuario);
        return ResponseEntity.ok(nuevousuario);
    }

    @GetMapping("/{run}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable String run) {
        Usuario usuario = usuarioservices.obtenerRun(run);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioservices.listarTodas();
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{run}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String run) {
        usuarioservices.eliminar(run);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{run}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable String run,
            @RequestBody Usuario usuarioActualizado) {
        Usuario usuario = usuarioservices.actualizar(run, usuarioActualizado);
        return ResponseEntity.ok(usuario);
    }
}
