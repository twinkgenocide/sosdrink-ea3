package cl.duoc.risani.sosdrink.backend.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import cl.duoc.risani.sosdrink.backend.security.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService uds;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager am,
                          UserDetailsService uds,
                          JwtUtils jwtUtils) {
        this.authManager = am;
        this.uds = uds;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.correo(), req.clave())
        );

        UserDetails user = uds.loadUserByUsername(req.correo());

        String token = jwtUtils.generateToken(user.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
