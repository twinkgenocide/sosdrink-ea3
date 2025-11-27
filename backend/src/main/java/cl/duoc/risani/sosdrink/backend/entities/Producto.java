package cl.duoc.risani.sosdrink.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String detalle;

    private Integer valor;
    private Integer iva;
    
    private String imagen;
    private Integer stock;
    private Integer criticalStock;

    @ManyToOne
    @JoinColumn(name = "tipo_producto_id")
    private TipoProducto tipoProducto;

    public Producto(Long id, String nombre, String detalle, Integer valor, String imagen, Integer stock, Integer criticalStock) {
        this.id = id;
        this.nombre = nombre;
        this.detalle = detalle;

        this.valor = valor;
        this.iva = (int)(Math.ceil(valor * 0.19 / 10) * 10);

        this.imagen = imagen;
        this.stock = stock;
        this.criticalStock = criticalStock;
    }
    
}