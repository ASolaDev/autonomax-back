package backend.Entity;

// Tabla intermedia entre Factura y Detalles

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalle_factura")
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_factura_detalle;

    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "id_factura", nullable = false)
    @JsonIgnore
    private Facturas factura;

    @ManyToOne
    @JoinColumn(name = "id_detalle", nullable = false)
    @JsonIgnore
    private Detalles detalle;
}
