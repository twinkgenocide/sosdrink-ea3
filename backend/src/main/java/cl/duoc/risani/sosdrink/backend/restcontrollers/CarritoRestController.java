package cl.duoc.risani.sosdrink.backend.restcontrollers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.risani.sosdrink.backend.dto.ItemCarritoDTO;
import cl.duoc.risani.sosdrink.backend.dto.ItemCarritoMapper;
import cl.duoc.risani.sosdrink.backend.entities.ItemCarrito;
import cl.duoc.risani.sosdrink.backend.entities.Producto;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;
import cl.duoc.risani.sosdrink.backend.security.JwtUtils;
import cl.duoc.risani.sosdrink.backend.services.CarritoServices;
import cl.duoc.risani.sosdrink.backend.services.ProductoServices;
import cl.duoc.risani.sosdrink.backend.services.UsuarioServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("api/carrito")
public class CarritoRestController {

    @Autowired
    private CarritoServices carritoServices;

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private ProductoServices productoServices;

    @Autowired
    private JwtUtils jwtUtils;

    ItemCarritoMapper itemCarritoMapper = new ItemCarritoMapper();

    @GetMapping
    public ResponseEntity<List<ItemCarritoDTO>> showCarrito(HttpServletRequest request) {
        String header = jwtUtils.getJwtFromRequest(request);
        if (header == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String correo = jwtUtils.getUsernameFromToken(header);

        try {
            Usuario usuario = usuarioServices.obtenerCorreo(correo);
            return ResponseEntity.ok(
                usuario.getCarrito()
                .stream().map((item) -> itemCarritoMapper.toDTO(item))
                .collect(Collectors.toList())
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping
    public ResponseEntity<ItemCarritoDTO> addItemToCarrito(HttpServletRequest request, @RequestParam Long productoId) {
        String header = jwtUtils.getJwtFromRequest(request);
        if (header == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String correo = jwtUtils.getUsernameFromToken(header);

        try {
            Usuario usuario = usuarioServices.obtenerCorreo(correo);
            Producto producto = productoServices.obtenerId(productoId);
            ItemCarrito itemCarrito = carritoServices.addItem(
                new ItemCarrito(null, usuario, producto, 1)
            );
            return ResponseEntity.ok(itemCarritoMapper.toDTO(itemCarrito));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemCarritoDTO> updateCarritoCantidad(HttpServletRequest request, @PathVariable Long id, @RequestParam Integer cantidad) {
        String header = jwtUtils.getJwtFromRequest(request);
        if (header == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String correo = jwtUtils.getUsernameFromToken(header);

        try {
            Usuario usuario = usuarioServices.obtenerCorreo(correo);
            ItemCarrito itemCarrito = carritoServices.findByUsuarioAndId(usuario, id)
                .orElseThrow(() -> new EntityNotFoundException());
            itemCarrito = carritoServices.updateItemCantidad(itemCarrito, cantidad);
            return ResponseEntity.ok(itemCarritoMapper.toDTO(itemCarrito));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItemFromCarrito(HttpServletRequest request, @PathVariable Long id) {
        String header = jwtUtils.getJwtFromRequest(request);
        if (header == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        String correo = jwtUtils.getUsernameFromToken(header);

        try {
            Usuario usuario = usuarioServices.obtenerCorreo(correo);
            ItemCarrito itemCarrito = carritoServices.findByUsuarioAndId(usuario, id)
                .orElseThrow(() -> new EntityNotFoundException());
            carritoServices.removeItem(itemCarrito);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
}
