package cl.duoc.risani.sosdrink.backend.dto;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import cl.duoc.risani.sosdrink.backend.entities.Boleta;

public class BoletaMapper {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public BoletaDTO toDTO(Boleta boleta) {
        // DTO de la boleta

        BoletaDTO boletaDTO = new BoletaDTO();

        boletaDTO.setFolio(boleta.getFolio());
        boletaDTO.setFechaEmision(boleta.getFechaEmision().format(dateTimeFormatter));

        boletaDTO.setClienteNombre(boleta.getClienteNombre());
        boletaDTO.setClienteRUN(boleta.getClienteRun());

        boletaDTO.setSubtotal(boleta.getSubtotal());
        boletaDTO.setIva(boleta.getIva());
        boletaDTO.setTotal(boleta.getTotal());

        // Lista de DTOs por cada linea de la boleta

        boletaDTO.setLineas(
            boleta.getLineasBoleta().stream().map(
                (lineaBoleta) -> new LineaBoletaDTO(
                    lineaBoleta.getDetalle(),
                    lineaBoleta.getCantidad(),
                    lineaBoleta.getValorUnitario(),
                    lineaBoleta.getIvaUnitario(),
                    lineaBoleta.getTotal()
                )
            ).collect(Collectors.toList())
        );

        return boletaDTO;
    }    
}
