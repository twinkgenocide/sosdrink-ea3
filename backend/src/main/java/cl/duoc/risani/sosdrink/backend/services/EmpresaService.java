package cl.duoc.risani.sosdrink.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Service
@NoArgsConstructor
public class EmpresaService {

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
    
}
