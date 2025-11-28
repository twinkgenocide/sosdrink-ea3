package cl.duoc.risani.sosdrink.backend.dto;

import cl.duoc.risani.sosdrink.backend.entities.ItemCarrito;

public class ItemCarritoMapper {

    private static ProductoMapper productoMapper = new ProductoMapper();

    public ItemCarritoDTO toDTO(ItemCarrito itemCarrito) {
        return new ItemCarritoDTO(
            itemCarrito.getId(),
            itemCarrito.getCantidad(),
            productoMapper.toDTO(itemCarrito.getProducto())
        );
    }
    
}
