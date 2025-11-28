package cl.duoc.risani.sosdrink.backend.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import cl.duoc.risani.sosdrink.backend.dto.UsuarioDTO;
import cl.duoc.risani.sosdrink.backend.dto.UsuarioMapper;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;
import cl.duoc.risani.sosdrink.backend.security.JwtUtils;
import cl.duoc.risani.sosdrink.backend.services.TipoUsuarioServices;
import cl.duoc.risani.sosdrink.backend.services.UsuarioServices;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService uds;
    private final JwtUtils jwtUtils;

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private TipoUsuarioServices tipoUsuarioServices;

    private UsuarioMapper usuarioMapper = new UsuarioMapper();

    public AuthController(AuthenticationManager am,
                          UserDetailsService uds,
                          JwtUtils jwtUtils) {
        this.authManager = am;
        this.uds = uds;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        String token = createTokenForUser(req.correo(), req.clave());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody UsuarioDTO usuarioDTO) {
        if (!usuarioDTO.isValidSignup()) return ResponseEntity.badRequest().build();

        try {
            Usuario usuario = usuarioMapper.fromDTO(usuarioDTO);
            usuario.setId(null);
            usuario.setTipoUsuario(tipoUsuarioServices.obtenerId(1L));
            usuario.setClave(usuarioDTO.getClave());

            usuarioServices.crear(usuario);

            String token = createTokenForUser(usuarioDTO.getCorreo(), usuarioDTO.getClave());
            return ResponseEntity.ok(new AuthResponse(token));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    private String createTokenForUser(String correo, String clave) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(correo, clave)
        );
        UserDetails user = uds.loadUserByUsername(correo);
        return jwtUtils.generateToken(user.getUsername());
    }
    
}
