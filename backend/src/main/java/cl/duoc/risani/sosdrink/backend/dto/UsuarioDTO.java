package cl.duoc.risani.sosdrink.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private String run;
    private String nombre;
    private String apellidos;
    private String correo;
    private String direccion;

    private String clave;
    private Long tipoUsuarioId;
    private String tipoUsuarioNombre;
}