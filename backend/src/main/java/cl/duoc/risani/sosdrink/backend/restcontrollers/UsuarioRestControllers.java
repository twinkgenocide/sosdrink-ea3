package cl.duoc.risani.sosdrink.backend.restcontrollers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.risani.sosdrink.backend.dto.UsuarioDTO;
import cl.duoc.risani.sosdrink.backend.dto.UsuarioMapper;
import cl.duoc.risani.sosdrink.backend.entities.TipoUsuario;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;
import cl.duoc.risani.sosdrink.backend.services.TipoUsuarioServices;
import cl.duoc.risani.sosdrink.backend.services.UsuarioServices;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")

public class UsuarioRestControllers {

    @Autowired
    private UsuarioServices usuarioservices;
    @Autowired
    private TipoUsuarioServices tipoUsuarioServices;
    private UsuarioMapper usuarioMapper = new UsuarioMapper();

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        TipoUsuario tipoUsuario = tipoUsuarioServices.obtenerId(usuarioDTO.getTipoUsuarioId());

        Usuario usuario = usuarioMapper.fromDTO(usuarioDTO);
        usuario.setClave(usuarioDTO.getClave());
        usuario.setTipoUsuario(tipoUsuario);

        Usuario usuarioGuardado = usuarioservices.crear(usuario);
        UsuarioDTO responseDTO = usuarioMapper.toDTO(usuarioGuardado);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{run}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable String run) {
        Usuario usuario = usuarioservices.obtenerRun(run);
        UsuarioDTO usuarioDTO = usuarioMapper.toDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioservices.listarTodas()
            .stream().map(usuario -> usuarioMapper.toDTO(usuario))
            .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{run}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable String run) {
        usuarioservices.eliminar(run);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{run}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable String run, @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioservices.obtenerRun(run);
        TipoUsuario tipoUsuario = tipoUsuarioServices.obtenerId(
            usuarioDTO.getTipoUsuarioId()
        );

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellidos(usuarioDTO.getApellidos());
        usuario.setClave(usuarioDTO.getCorreo());
        usuario.setDireccion(usuarioDTO.getDireccion());
        usuario.setTipoUsuario(tipoUsuario);

        Usuario usuarioGuardado = usuarioservices.actualizar(run, usuario);
        return ResponseEntity.ok(usuarioMapper.toDTO(usuarioGuardado));
    }
}
