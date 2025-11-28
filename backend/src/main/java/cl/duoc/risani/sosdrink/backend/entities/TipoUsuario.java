package cl.duoc.risani.sosdrink.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String rol;
    private String nombre;

    @OneToMany(mappedBy = "tipoUsuario")
    @JsonIgnore
    private List<Usuario> usuarios;

    @Override
    public String toString() {
        return "TipoUsuario [id=" + id + ", nombre=" + nombre + ", rol=" + rol + "]";
    }

}
