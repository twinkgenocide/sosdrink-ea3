package cl.duoc.risani.sosdrink.backend.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoletaDTO {

    private String empresaRazonSocial;
    private String empresaRUT;
    private String empresaDireccion;
    private String empresaCorreo;
    private String empresaTelefono;
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
