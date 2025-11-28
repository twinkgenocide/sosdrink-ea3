package cl.duoc.risani.sosdrink.backend.services;

import java.util.List;
import java.util.Optional;

import cl.duoc.risani.sosdrink.backend.entities.ItemCarrito;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;

public interface CarritoServices {
    
    List<ItemCarrito> findByUsuario(Usuario usuario);
    Optional<ItemCarrito> findByUsuarioAndId(Usuario usuario, Long id);

    ItemCarrito addItem(ItemCarrito itemCarrito);
    ItemCarrito updateItemCantidad(ItemCarrito itemCarrito, Integer cantidad);
    void removeItem(ItemCarrito itemCarrito);
    void clearCarrito(Usuario usuario);

}
