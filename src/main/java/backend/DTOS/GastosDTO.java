package backend.DTOS;

import backend.Entity.MetodoPago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GastosDTO {

    private Date fecha;
    private String descripcion;
    private BigDecimal monto;

    // Relaciones
    private Long proveedor;
    private Long usuario;
    private Long factura;

    // Enums
    private Long categoria;
    private MetodoPago metodoPago;
}
