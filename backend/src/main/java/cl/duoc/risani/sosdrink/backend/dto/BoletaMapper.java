package cl.duoc.risani.sosdrink.backend.dto;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.duoc.risani.sosdrink.backend.entities.Boleta;
import cl.duoc.risani.sosdrink.backend.services.EmpresaService;

@Component
public class BoletaMapper {

    @Autowired
    private EmpresaService empresaService;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public BoletaDTO toDTO(Boleta boleta) {
        // DTO de la boleta

        BoletaDTO boletaDTO = new BoletaDTO();

        boletaDTO.setEmpresaRUT(empresaService.getEmpresaRUT());
        boletaDTO.setEmpresaRazonSocial(empresaService.getEmpresaRazonSocial());
        boletaDTO.setEmpresaGiro(empresaService.getEmpresaGiro());
        boletaDTO.setEmpresaDireccion(empresaService.getEmpresaDireccion());
        boletaDTO.setEmpresaCorreo(empresaService.getEmpresaCorreo());
        boletaDTO.setEmpresaTelefono(empresaService.getEmpresaTelefono());

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
