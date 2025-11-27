package cl.duoc.risani.sosdrink.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
    indexes = {
        @Index(name = "idx_usuario_correo", columnList = "correo")
    }
)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String run;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String correo;
    @Column(nullable = false)
    private String clave;

    private String direccion;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario_id")
    private TipoUsuario tipoUsuario;

}
