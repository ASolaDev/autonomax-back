package backend.DTOS;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import backend.Entity.DetalleFactura;
import backend.Entity.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDetallesDTO {

    private String numeroFactura;

    private Date fechaEmision;
    private Date fechaPago;

    private BigDecimal subtotal;

    private BigDecimal iva;

    private BigDecimal total;

    private Estado estado;

    private List<DetalleFactura> facturasDetalles = new ArrayList<>();

    // Datos que necesito en las relaciones
    private String cifCliente;
    private String emailUsuario;
    private String nombreEmpresa;

}
