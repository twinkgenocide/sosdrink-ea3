package cl.duoc.risani.sosdrink.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LineaBoleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String detalle;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer valorUnitario;

    @Column(nullable = false)
    private Integer ivaUnitario;
    
    @ManyToOne
    @JoinColumn(name = "boleta_id", nullable = false)
    private Boleta boleta;

    public Integer getTotal() {
        return (this.valorUnitario + this.ivaUnitario) * this.cantidad;
    }

}
