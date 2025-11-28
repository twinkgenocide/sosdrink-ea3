package cl.duoc.risani.sosdrink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarritoDTO {
    
    private Long id;
    private Integer cantidad;
    private ProductoDTO producto;

}
