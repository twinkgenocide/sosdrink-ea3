package cl.duoc.risani.sosdrink.backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.risani.sosdrink.backend.entities.ItemCarrito;
import cl.duoc.risani.sosdrink.backend.entities.Usuario;
import cl.duoc.risani.sosdrink.backend.repository.ItemCarritoRepository;
import jakarta.transaction.Transactional;

@Service
public class CarritoServicesImpl implements CarritoServices {

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Override
    public List<ItemCarrito> findByUsuario(Usuario usuario) {
        return itemCarritoRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<ItemCarrito> findByUsuarioAndId(Usuario usuario, Long id) {
        return itemCarritoRepository.findByUsuarioAndId(usuario, id);
    }

    @Override
    @Transactional
    public ItemCarrito addItem(ItemCarrito itemCarrito) {
        return itemCarritoRepository.save(itemCarrito);
    }

    @Override
    @Transactional
    public ItemCarrito updateItemCantidad(ItemCarrito itemCarrito, Integer cantidad) {
        itemCarrito.setCantidad(cantidad);
        return itemCarritoRepository.save(itemCarrito);
    }

    @Override
    @Transactional
    public void removeItem(ItemCarrito itemCarrito) {
        itemCarritoRepository.delete(itemCarrito);
    }

    @Override
    @Transactional
    public void clearCarrito(Usuario usuario) {
        for (ItemCarrito itemCarrito : itemCarritoRepository.findByUsuario(usuario)) {
            removeItem(itemCarrito);
        }
    }
    
}
