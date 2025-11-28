package cl.duoc.risani.sosdrink.backend.restcontrollers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.risani.sosdrink.backend.dto.BoletaDTO;
import cl.duoc.risani.sosdrink.backend.dto.BoletaMapper;
import cl.duoc.risani.sosdrink.backend.entities.Boleta;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;
import cl.duoc.risani.sosdrink.backend.security.JwtUtils;
import cl.duoc.risani.sosdrink.backend.services.BoletaServices;
import cl.duoc.risani.sosdrink.backend.services.CarritoServices;
import cl.duoc.risani.sosdrink.backend.services.UsuarioServices;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/compras")
public class CompraRestController {
    
    @Autowired
    private BoletaServices boletaServices;

    @Autowired
    private CarritoServices carritoServices;

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private BoletaMapper boletaMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<BoletaDTO> nuevaCompra(HttpServletRequest request) {
        String header = jwtUtils.getJwtFromRequest(request);
        if (header == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String correo = jwtUtils.getUsernameFromToken(header);

        try {
            Usuario usuario = usuarioServices.obtenerCorreo(correo);
            Boleta boletaGuardada = boletaServices.saveUsuarioBoleta(usuario);
            carritoServices.clearCarrito(usuario);
            return ResponseEntity.ok(boletaMapper.toDTO(boletaGuardada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{folio}")
    public ResponseEntity<BoletaDTO> buscarBoleta(HttpServletRequest request, @PathVariable String folio) {
        String header = jwtUtils.getJwtFromRequest(request);
        if (header == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String correo = jwtUtils.getUsernameFromToken(header);

        try {
            Usuario usuario = usuarioServices.obtenerCorreo(correo);
            Boleta boleta = boletaServices
                .findByFolioAndClienteRUN(folio, usuario.getRun())
                .orElseThrow(() -> new NotFoundException());
            return ResponseEntity.ok(boletaMapper.toDTO(boleta));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BoletaDTO>> listarBoletas(HttpServletRequest request) {
        String header = jwtUtils.getJwtFromRequest(request);
        if (header == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String correo = jwtUtils.getUsernameFromToken(header);

        try {
            Usuario usuario = usuarioServices.obtenerCorreo(correo);
            List<Boleta> boletas = boletaServices.findByClienteRUN(usuario.getRun());
            return ResponseEntity.ok(
                boletas.stream().map((boleta) -> boletaMapper.toDTO(boleta))
                .collect(Collectors.toList())
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
