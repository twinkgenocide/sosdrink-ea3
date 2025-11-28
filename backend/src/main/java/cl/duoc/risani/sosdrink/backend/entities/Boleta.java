package cl.duoc.risani.sosdrink.backend.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
    // indexación para poder buscar boletas por su folio o RUN del cliente de manera eficiente
    indexes = {
        @Index(name = "idx_boleta_folio", columnList = "folio"),
        @Index(name = "idx_boleta_cliente_run", columnList = "cliente_run"),
    }
)
public class Boleta {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String folio;

    @Column(nullable = false)
    private LocalDateTime fechaEmision;

    @Column(nullable = false)
    private String clienteRun;

    @Column(nullable = false)
    private String clienteNombre;

    @Column(nullable = false)
    private Integer subtotal;

    @Column(nullable = false)
    private Integer iva;

    @OneToMany(mappedBy = "boleta")
    private List<LineaBoleta> lineasBoleta;

    public Integer getTotal() {
        return this.subtotal + this.iva;
    }

}
