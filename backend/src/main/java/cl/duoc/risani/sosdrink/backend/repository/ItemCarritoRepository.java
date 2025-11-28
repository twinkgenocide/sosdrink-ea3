package cl.duoc.risani.sosdrink.backend.repository;

import org.springframework.data.repository.CrudRepository;

import cl.duoc.risani.sosdrink.backend.entities.ItemCarrito;
import cl.duoc.risani.sosdrink.backend.entities.Producto;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;

import java.util.List;
import java.util.Optional;


public interface ItemCarritoRepository extends CrudRepository<ItemCarrito, Long> {
    
    List<ItemCarrito> findByUsuario(Usuario usuario);
    Optional<ItemCarrito> findByUsuarioAndId(Usuario usuario, Long id);
    Optional<ItemCarrito> findByUsuarioAndProducto(Usuario usuario, Producto producto);

}
