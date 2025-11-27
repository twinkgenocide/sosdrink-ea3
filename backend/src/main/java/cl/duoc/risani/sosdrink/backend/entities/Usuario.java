package cl.duoc.risani.sosdrink.backend.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    private String run;

    private String nombre;
    private String apellidos;
    private String correo;
    private String direccion;
    private String clave;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario_id")
    private TipoUsuario tipoUsuario;

}
