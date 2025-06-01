package backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "gastos")
public class Gastos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_gasto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuarios usuario;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    @JsonIgnore
    private CategoriaGastos categoria;

    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;

    @Column(name = "proveedor", nullable = false, length = 100)
    private String proveedor;

}
