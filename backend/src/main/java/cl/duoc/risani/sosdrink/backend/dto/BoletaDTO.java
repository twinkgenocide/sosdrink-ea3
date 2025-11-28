package cl.duoc.risani.sosdrink.backend.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoletaDTO {

    @Value("${empresa.razon}")
    private String empresaRazonSocial;
    @Value("${empresa.rut}")
    private String empresaRUT;
    @Value("${empresa.direccion}")
    private String empresaDireccion;
    @Value("${empresa.correo}")
    private String empresaCorreo;
    @Value("${empresa.telefono}")
    private String empresaTelefono;
    @Value("${empresa.giro}")
    private String empresaGiro;

    private String folio;
    private String fechaEmision;

    private String clienteNombre;
    private String clienteRUN;

    private Integer subtotal;
    private Integer iva;
    private Integer total;

    private List<LineaBoletaDTO> lineas;

}
