package cl.duoc.risani.sosdrink.backend.services;

import java.util.List;

import cl.duoc.risani.sosdrink.backend.entities.ItemCarrito;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;

public interface CarritoServices {
    
    List<ItemCarrito> findByUsuario(Usuario usuario);

}
