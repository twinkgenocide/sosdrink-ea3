package cl.duoc.risani.sosdrink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Long id;

    private String nombre;
    private String detalle;

    private Integer valor;
    private Integer iva;

    private String imagenUrl;
    private String stockStatus;

    private Long tipoProductoId;
    private String tipoProductoNombre;
    
}
