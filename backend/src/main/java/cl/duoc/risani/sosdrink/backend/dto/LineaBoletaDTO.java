package cl.duoc.risani.sosdrink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LineaBoletaDTO {
    private String detalle;
    private Integer cantidad;
    private Integer valorUnitario;
    private Integer ivaUnitario;
    private Integer valorTotal;
}