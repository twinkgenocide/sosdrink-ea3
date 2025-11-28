package cl.duoc.risani.sosdrink.backend.dto;

import cl.duoc.risani.sosdrink.backend.entities.Producto;

public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        String stockStatus = producto.getStock() == 0 ? "OUT_OF_STOCK" : producto.getStock() < producto.getCriticalStock() ? "CRITICAL" : "NORMAL";
        return new ProductoDTO(
            producto.getId(),
            producto.getNombre(),
            producto.getDetalle(),
            producto.getValor(),
            producto.getIva(),
            producto.getImagen(),
            stockStatus,
            producto.getTipoProducto().getId(),
            producto.getTipoProducto().getNombre()
        );
    }
    
}
